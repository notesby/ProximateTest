package com.justforfun.proximatetest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hectormoreno on 9/5/17.
 */

public class Profile {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nombres")
    @Expose
    private String nombres;
    @SerializedName("apellidos")
    @Expose
    private String apellidos;
    @SerializedName("correo")
    @Expose
    private String correo;
    @SerializedName("numero_documento")
    @Expose
    private String numeroDocumento;
    @SerializedName("ultima_sesion")
    @Expose
    private String ultimaSesion;
    @SerializedName("eliminado")
    @Expose
    private Integer eliminado;
    @SerializedName("documentos_id")
    @Expose
    private Integer documentosId;
    @SerializedName("documentos_abrev")
    @Expose
    private String documentosAbrev;
    @SerializedName("documentos_label")
    @Expose
    private String documentosLabel;
    @SerializedName("estados_usuarios_label")
    @Expose
    private String estadosUsuariosLabel;
    @SerializedName("secciones")
    @Expose
    private List<Seccion> secciones = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getUltimaSesion() {
        return ultimaSesion;
    }

    public void setUltimaSesion(String ultimaSesion) {
        this.ultimaSesion = ultimaSesion;
    }

    public Integer getEliminado() {
        return eliminado;
    }

    public void setEliminado(Integer eliminado) {
        this.eliminado = eliminado;
    }

    public Integer getDocumentosId() {
        return documentosId;
    }

    public void setDocumentosId(Integer documentosId) {
        this.documentosId = documentosId;
    }

    public String getDocumentosAbrev() {
        return documentosAbrev;
    }

    public void setDocumentosAbrev(String documentosAbrev) {
        this.documentosAbrev = documentosAbrev;
    }

    public String getDocumentosLabel() {
        return documentosLabel;
    }

    public void setDocumentosLabel(String documentosLabel) {
        this.documentosLabel = documentosLabel;
    }

    public String getEstadosUsuariosLabel() {
        return estadosUsuariosLabel;
    }

    public void setEstadosUsuariosLabel(String estadosUsuariosLabel) {
        this.estadosUsuariosLabel = estadosUsuariosLabel;
    }

    public List<Seccion> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<Seccion> secciones) {
        this.secciones = secciones;
    }
}
