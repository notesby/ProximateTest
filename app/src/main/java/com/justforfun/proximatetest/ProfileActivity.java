package com.justforfun.proximatetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.justforfun.proximatetest.model.GetProfileResponse;
import com.justforfun.proximatetest.model.Profile;
import com.justforfun.proximatetest.model.realm.ProfileRealm;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class ProfileActivity extends AppCompatActivity {

    private Realm realmUI;
    private Disposable subscription;


    int id = 0;
    String token = "";

    MaterialDialog messageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Intent intent = getIntent();

        if(intent.hasExtra("id")){
            id = intent.getIntExtra("id",0);
        }

        if (intent.hasExtra("token")){
            token = intent.getStringExtra("token");
        }

        messageDialog = new MaterialDialog.Builder(this).title("Error").content("Error").build();


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


}
