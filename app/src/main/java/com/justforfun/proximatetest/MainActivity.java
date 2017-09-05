package com.justforfun.proximatetest;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.justforfun.proximatetest.model.LoginRequest;
import com.justforfun.proximatetest.model.LoginResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    TextInputEditText email,password;

    MaterialDialog progressDialog;
    MaterialDialog messageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (TextInputEditText) findViewById(R.id.text_input_edit_text_email);
        password = (TextInputEditText) findViewById(R.id.text_input_edit_text_password);


        progressDialog = new MaterialDialog.Builder(this)
                                .title("Iniciando sesión")
                                .content("espere un momento por favor...")
                                .progress(true,1)
                                .build();

        messageDialog = new MaterialDialog.Builder(this)
                            .title("Error")
                            .content("error al iniciar sesión")
                            .build();

    }


    public void login(View view){
        progressDialog.show();

        LoginRequest request =  new LoginRequest();

        request.setEmail(email.getText().toString());
        request.setPassword(password.getText().toString());

        MyApplication.getService().login(request)
                .doOnSubscribe(disposable -> {progressDialog.show();})
                .doOnTerminate(() -> {progressDialog.dismiss();})
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    if (loginResponse.getError()){
                        messageDialog.setContent(loginResponse.getMessage());
                        messageDialog.show();
                    }
                    else{
                        Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                        intent.putExtra("id",loginResponse.getId());
                        intent.putExtra("token",loginResponse.getToken());
                        startActivity(intent);
                    }
                });
    }
}
