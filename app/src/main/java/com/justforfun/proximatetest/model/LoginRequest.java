package com.justforfun.proximatetest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hectormoreno on 9/5/17.
 */

public class LoginRequest {
    @SerializedName("correo")
    @Expose
    String email;

    @SerializedName("contrasenia")
    @Expose
    String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
