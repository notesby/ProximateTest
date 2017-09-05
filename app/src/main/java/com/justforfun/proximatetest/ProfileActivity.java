package com.justforfun.proximatetest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.justforfun.proximatetest.model.GetProfileResponse;
import com.justforfun.proximatetest.model.Profile;
import com.justforfun.proximatetest.model.realm.ProfileRealm;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class ProfileActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    String mCurrentPhotoPath;

    private Realm realmUI;
    private Disposable subscription;


    int id = 0;
    String token = "";

    MaterialDialog messageDialog;

    ImageView profileImageView;

    Uri profileImagePath;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        profileImageView = (ImageView) findViewById(R.id.image_view_profile);

        Intent intent = getIntent();

        if(intent.hasExtra("id")){
            id = intent.getIntExtra("id",0);
        }

        if (intent.hasExtra("token")){
            token = intent.getStringExtra("token");
        }

        messageDialog = new MaterialDialog.Builder(this).title("Error").content("Error").build();

        preferences = this.getSharedPreferences("settings",MODE_PRIVATE);

        String path = preferences.getString("profile_picture_path","");
        if (!path.isEmpty()){
            profileImagePath = Uri.parse(path);
            displayImage();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        realmUI = Realm.getDefaultInstance();

        Observable<ProfileRealm> observable =
                MyApplication.getService().getProfile(token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.computation())
                        .map(response -> {
                            return response.getData().get(0);
                        })
                        .map(this::saveProfile)
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(this::getProfileRealm);


        ProfileRealm cachedProfile = getProfileRealm(id);
        if (cachedProfile != null)
        {
            observable = observable.mergeWith(Observable.just(cachedProfile));
        }

        subscription = observable.subscribe(this::displayProfile, this::displayError);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realmUI.close();
        subscription.dispose();
    }

    private ProfileRealm getProfileRealm(int id){
        return findInRealm(realmUI,id);
    }

    private int saveProfile(Profile profile){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(transactionRealm -> {
            ProfileRealm profileRealm = findInRealm(transactionRealm, profile.getId());
            if (profileRealm == null) {
                profileRealm = transactionRealm.createObject(ProfileRealm.class, profile.getId());
            }
            profileRealm.setEmail(profile.getCorreo());
            profileRealm.setDeleted(profile.getEliminado());
            profileRealm.setDocument_id(profile.getDocumentosId());
            profileRealm.setDocument_abbreviation(profile.getDocumentosAbrev());
            profileRealm.setDocument_label(profile.getDocumentosLabel());
            profileRealm.setLast_name(profile.getApellidos());
            profileRealm.setName(profile.getNombres());
            profileRealm.setLast_session(profile.getUltimaSesion());
        });
        realm.close();
        return profile.getId();
    }

    private ProfileRealm findInRealm(Realm realm, int id){
        return realm.where(ProfileRealm.class).equalTo("id",id).findFirst();
    }


    private void displayError(Throwable e){
        messageDialog.setContent(e.getMessage());
        messageDialog.show();
    }

    private void displayProfile(ProfileRealm profile){
        messageDialog.setContent(profile.getName());
        messageDialog.show();
    }

    public void takePicture(View view){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (photoFile != null) {
                profileImagePath = FileProvider.getUriForFile(this,
                        "com.justforfun.android.fileprovider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, profileImagePath);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            savePath();
            displayImage();
        }
    }


    void savePath(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("profile_picture_path",profileImagePath.toString());
        editor.apply();
    }

    void displayImage(){

        Glide.with(this).load(profileImagePath)
                .apply(RequestOptions.circleCropTransform())
                .into(profileImageView);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


}
