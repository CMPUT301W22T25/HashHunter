 package com.example.hashhunter;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

     private ActivityResultLauncher<String> requestCameraLauncher =
             registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                 if (isGranted) {
                     Intent intent = new Intent(MainActivity.this,ScanActivity.class);
                     startActivity(intent);
                 } else {
                     Toast.makeText(MainActivity.this, "Permission denied to access your camera", Toast.LENGTH_SHORT).show();
                 }
             });
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

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
             requestCameraLauncher.launch(Manifest.permission.CAMERA);
         }


     }
 }
