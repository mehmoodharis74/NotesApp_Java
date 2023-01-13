package com.harismehmood.i200902;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class aboutScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_screen);

        ImageView about_img = findViewById(R.id.about_screen_image);
        Glide.with(this).load(R.drawable.about).into(about_img);
     }
    @Override
    public void onBackPressed(){
        finish();
        super.onBackPressed();
    }
}