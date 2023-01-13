package com.harismehmood.i200902;

import android.net.Uri;

public class contact_model {
    int id;
    Uri img;
    String name;
    String note_type;
    String category;
    String data;
    String password;

    //create constructor
    public contact_model(int id, String name, String note_type,String category, String data, Uri img,String password) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.note_type = note_type;
        this.category = category;
        this.data = data;
        this.password = password;
    }
    public Uri getImg(){return this.img;}
}
