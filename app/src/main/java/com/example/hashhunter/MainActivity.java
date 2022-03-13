 package com.example.hashhunter;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.UUID;

 /**
  * The Main activity for the app
  */
 public class MainActivity extends AppCompatActivity {
    // https://www.youtube.com/watch?v=4WxKQTUweVg
    public static final String SHARED_PREF_NAME = "com.example.hashhunter.shared_prefs";
    public static final String PREF_UNIQUE_ID = "com.example.hashhunter.unique_id";
    private SharedPreferences sharedPreferences;
    private Boolean firstLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button scannerButton = findViewById(R.id.scannerButton);

        // https://www.youtube.com/watch?v=4WxKQTUweVg
        // https://ssaurel.medium.com/how-to-retrieve-an-unique-id-to-identify-android-devices-6f99fd5369eb
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String unique_id = sharedPreferences.getString(PREF_UNIQUE_ID, null);

        // if unique_id is null, that means the user is logging in to the activity for the first
        // time
        if (unique_id == null) {
            unique_id = UUID.randomUUID().toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(PREF_UNIQUE_ID, unique_id);
            editor.commit();
            firstLogin = true;
        }

        // if it is the first time logging in, start the log in activity, else
        // go straight to the dashboard
        if (firstLogin) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(intent);
        }

        // I don't think we need this anymore
//        scannerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        Button loginButton = findViewById(R.id.login_button);
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });

    }
//
//    public void launchDashboardActivity(View v) {
//        Intent intent = new Intent(this, DashboardActivity.class);
//        startActivity(intent);
//    }
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
