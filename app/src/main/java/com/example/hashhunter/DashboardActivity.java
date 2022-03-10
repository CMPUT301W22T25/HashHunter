package com.example.hashhunter;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> requestCameraLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Intent intent = new Intent(DashboardActivity.this, ScanActivity.class);
                    DashboardActivity.this.startActivity(intent);
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied.
                }
            });

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
                requestCameraLauncher.launch(Manifest.permission.CAMERA);
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