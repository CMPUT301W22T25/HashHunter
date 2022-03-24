package com.example.hashhunter;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    private ImageView profilePic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);



        //profile picture
        profilePic = findViewById(R.id.profilePicture);
        profilePic.setImageResource(R.drawable.ic_android);



    }
}
