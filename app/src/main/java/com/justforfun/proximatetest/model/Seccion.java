package com.justforfun.proximatetest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hectormoreno on 9/5/17.
 */

public class Seccion {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("seccion")
    @Expose
    private String seccion;
    @SerializedName("abrev")
    @Expose
    private String abrev;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getAbrev() {
        return abrev;
    }

    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }
}
