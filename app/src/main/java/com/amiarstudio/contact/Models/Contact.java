package com.amiarstudio.contact.Models;

import android.net.Uri;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Contact extends RealmObject {

    @PrimaryKey
    private String ContactName;
    private String ContactUriPicture;
    private String ContactNumber;


    public Contact(String contactName, String contactUriPicture, String contactNumber) {
        ContactName = contactName;
        ContactUriPicture = contactUriPicture;
        ContactNumber = contactNumber;
    }

    public Contact() {

    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getContactUriPicture() {
        return ContactUriPicture;
    }

    public void setContactUriPicture(String contactUriPicture) {
        ContactUriPicture = contactUriPicture;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

}
