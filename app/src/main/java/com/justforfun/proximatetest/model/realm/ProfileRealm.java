package com.justforfun.proximatetest.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hectormoreno on 9/5/17.
 */

public class ProfileRealm extends RealmObject{

    @PrimaryKey
    int id;

    String name,last_name,email,document_number,last_session;
    int deleted;
    int document_id;
    String document_abbreviation,document_label,state_user_label;

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public int getDeleted() {
        return deleted;
    }

    public String getDocument_abbreviation() {
        return document_abbreviation;
    }

    public int getDocument_id() {
        return document_id;
    }

    public String getDocument_label() {
        return document_label;
    }

    public String getDocument_number() {
        return document_number;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getLast_session() {
        return last_session;
    }

    public String getName() {
        return name;
    }

    public String getState_user_label() {
        return state_user_label;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public void setDocument_abbreviation(String document_abbreviation) {
        this.document_abbreviation = document_abbreviation;
    }

    public void setDocument_id(int document_id) {
        this.document_id = document_id;
    }

    public void setDocument_label(String document_label) {
        this.document_label = document_label;
    }

    public void setDocument_number(String document_number) {
        this.document_number = document_number;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setLast_session(String last_session) {
        this.last_session = last_session;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState_user_label(String state_user_label) {
        this.state_user_label = state_user_label;
    }
}
