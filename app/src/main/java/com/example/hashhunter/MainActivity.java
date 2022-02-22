package com.example.hashhunter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * DEBUG: only for testing Scanner class
     */
    public void getCodePoints(View V) {
        Scanner.getCodePoints("BFG5DGW54\n");
    }
}