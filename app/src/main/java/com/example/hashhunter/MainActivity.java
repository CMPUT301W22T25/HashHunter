package com.example.hashhunter;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button scannerButton = findViewById(R.id.scannerButton);

        scannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button camButton = findViewById(R.id.cam_button);
        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

    }
}

// Commented this out, was not sure what is needed
// package com.example.hashhunter;


// import androidx.appcompat.app.AppCompatActivity;

// import android.content.Intent;
// import android.os.Bundle;
// import android.view.View;
// import android.widget.Button;

// public class MainActivity extends AppCompatActivity {


//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_main);

//         Button loginButton = findViewById(R.id.login_button);
//         loginButton.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                 startActivity(intent);
//             }
//         });

//     }
//     // DEBUG while login is under construction
//     public void launchTempActivity(View v) {
//         Intent intent = new Intent(this, ScanActivity.class);
//         startActivity(intent);
//     }
//     // DEBUG while login is under construction
//     public void launchCamActivity(View v) {
//         Intent intent = new Intent(this, CameraActivity.class);
//         startActivity(intent);
//     }
// }
