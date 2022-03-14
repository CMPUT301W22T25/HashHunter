package com.example.hashhunter;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> requestCameraLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Intent intent = new Intent(DashboardActivity.this,ScanActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(DashboardActivity.this, "Permission denied to access your camera", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initializeActivity();
    }

    private void initializeActivity() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MainFragment()).commit();
        }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Intent intent;

                    switch (item.getItemId()) {
                        case R.id.home:
                            intent = new Intent(DashboardActivity.this, MainActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.map:
                            intent = new Intent(DashboardActivity.this, MapActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.explore:
                            intent = new Intent(DashboardActivity.this, ExploreActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.scan:
                            requestCameraLauncher.launch(Manifest.permission.CAMERA);

                            break;
                        case R.id.profile:
                            intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                            startActivity(intent);
                            break;
                    }

                    return true;
                }
    };
}