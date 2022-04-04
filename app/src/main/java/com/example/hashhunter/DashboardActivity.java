package com.example.hashhunter;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Contains 5 dashboard buttons that open other activities - will be fragments in the future
 */
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
    private ActivityResultLauncher<String> requestCoarseLocationLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Intent intent = new Intent(DashboardActivity.this,MapActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(DashboardActivity.this, "Permission denied to access your location", Toast.LENGTH_SHORT).show();
                }
            });
    private ActivityResultLauncher<String> requestFineLocationLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Intent intent = new Intent(DashboardActivity.this,MapActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(DashboardActivity.this, "Permission denied to access your Precise location", Toast.LENGTH_SHORT).show();
                    requestCoarseLocationLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initializeActivity();
    }

    /**
     * method for initializing the activity
     */
    private void initializeActivity() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,new MainFragment(),"HOMEPAGE")
                .commit();


        }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                /**
                 * Uses a switch statement to determine which icon was clicked on the dashboard to move to whichever activity
                 */
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Intent intent;

                    switch (item.getItemId()) {
                        case R.id.home:

                            break;
                        case R.id.map:
                            requestFineLocationLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
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