package com.example.hashhunter;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ScanSubmitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_submit);

        Intent intent = getIntent();
        Integer points = (Integer) intent.getSerializableExtra("points");
        String qrCodeString = intent.getStringExtra("qr string");
    }
}
