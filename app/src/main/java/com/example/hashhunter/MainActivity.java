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
  * It checks to see if the user has ever logged into the app before
  * If yes, it starts the Dashboard activity
  * Otherwise, it starts the Login activity, which asks the user to either
  * login using a QR code or register themselves if they are a first time user
  */
 public class MainActivity extends AppCompatActivity {
     // https://www.youtube.com/watch?v=4WxKQTUweVg
     public static final String SHARED_PREF_NAME = "com.example.hashhunter.shared_prefs";
     public static final String PREF_UNIQUE_ID = "com.example.hashhunter.unique_id";
     public static final String PREF_IS_OWNER = "com.example.hashhunter.owner_id";
     public static final String PREF_USERNAME = "com.example.hashhunter.username";
     public static final String PREF_DB_OP_SUCCESS = "com.example.hashhunter.database";
     private SharedPreferences sharedPreferences;
     private Boolean firstLogin = false;


     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

         // https://www.youtube.com/watch?v=4WxKQTUweVg
         // https://ssaurel.medium.com/how-to-retrieve-an-unique-id-to-identify-android-devices-6f99fd5369eb
         sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
         String unique_id = sharedPreferences.getString(PREF_UNIQUE_ID, null);

         String isOwner = sharedPreferences.getString(PREF_IS_OWNER, null);
         if (isOwner == null) {
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
         } else {
             Intent intent = new Intent(MainActivity.this, OwnerActivity.class);
             startActivity(intent);
         }

     }

 }
