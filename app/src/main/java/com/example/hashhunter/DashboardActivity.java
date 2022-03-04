package com.example.hashhunter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initializeActivity();
    }

    private void initializeActivity() {
        final Button exploreButton = (Button) findViewById(R.id.button_explore);
        final Button scanButton = (Button) findViewById(R.id.button_scan);
        final Button mapButton = (Button) findViewById(R.id.button_map);
        final Button profileButton = (Button) findViewById(R.id.button_profile);
        exploreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ExploreActivity.class);
                DashboardActivity.this.startActivity(intent);
            }
        });
        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ScanActivity.class);
                DashboardActivity.this.startActivity(intent);
            }
        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, MapActivity.class);
                DashboardActivity.this.startActivity(intent);
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                DashboardActivity.this.startActivity(intent);
            }
        });
    }
}