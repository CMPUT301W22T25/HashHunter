package com.example.hashhunter;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;
import java.util.function.LongFunction;

/**
 * This is the activity for user login for the first time
 * If they scan a QR code, it logs them in if they are an existing user in the database
 * If they register, it generates a unique ID for them and adds their information to the database
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "com.example.hashhunter.LoginActivity";

    private static SharedPreferences sharedPreferences;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private UserInfoFirebaseController userInfodb;


    // this handles the result from the scan activity
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        assert intent != null;

                        String scannedUsername = intent.getStringExtra(ScanActivity.EXTRA_SCANNED_UNAME);
//
//                        Boolean x = userInfodb.userNameExists(scannedUsername);
//                        if (x) {
//                            Toast.makeText(LoginActivity.this, "username exists", Toast.LENGTH_SHORT);
//                        } else {
//                            Toast.makeText(LoginActivity.this, "hmm", Toast.LENGTH_SHORT);
//                        }
//                        //  https://firebase.google.com/docs/firestore/query-data/get-data#java_4
                        DocumentReference userDocRef = db.collection("Usernames").document(scannedUsername);
                        userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    // log them in and start another activity
                                    if (document.exists()) {
                                        String username = (String) document.getData().get("com.example.hashhunter.username");
                                        Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                                        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF_NAME, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(MainActivity.PREF_UNIQUE_ID, scannedUsername);
                                        editor.putString(MainActivity.PREF_USERNAME, username);
                                        editor.commit();
                                        Button loginButton = findViewById(R.id.login_button);
                                        loginButton.setText("Logged In");

                                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                        startActivity(intent);
                                        // Should this be called?
                                        //finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "no username found", Toast.LENGTH_SHORT).show();
                                        Button loginButton = findViewById(R.id.login_button);
                                        loginButton.setText("Log In Failed");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with", task.getException());
                                    Button loginButton = findViewById(R.id.login_button);
                                    loginButton.setText("Log In Failed");
                                }
                            }
                        });

                        DocumentReference ownerDocRef = db.collection("Owners").document(scannedUsername);
                        ownerDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    // log them in and start another activity
                                    if (document.exists()) {
                                        Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                                        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF_NAME, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(MainActivity.PREF_UNIQUE_ID, scannedUsername);
                                        editor.putString(MainActivity.PREF_IS_OWNER, "hmmYesOwner");
                                        editor.commit();
                                        Button loginButton = findViewById(R.id.login_button);
                                        loginButton.setText("Logged In");

                                        Intent intent = new Intent(LoginActivity.this, OwnerActivity.class);
                                        startActivity(intent);
                                        // Should this be called?
                                        //finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "no username found", Toast.LENGTH_SHORT).show();
                                        Button loginButton = findViewById(R.id.login_button);
                                        loginButton.setText("Log In Failed");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with", task.getException());
                                    Button loginButton = findViewById(R.id.login_button);
                                    loginButton.setText("Log In Failed");
                                }
                            }
                        });

                    } else {
                        Toast.makeText(LoginActivity.this, "result not ok", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    private ActivityResultLauncher<String> requestCameraLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Intent intent = new Intent(LoginActivity.this, ScanActivity.class);
                    mStartForResult.launch(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Permission denied to access your camera", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // registering a new user
        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                // Should this be called?
                //finish();
            }
        });

        // logging in an existing user
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestCameraLauncher.launch(Manifest.permission.CAMERA);

                // https://developer.android.com/training/basics/firstapp/starting-activity#java
                // https://www.youtube.com/watch?v=AD5qt7xoUU8
                // https://www.youtube.com/watch?v=7Fc79qTq7yc
                // I used all the above links to learn about intents and starting activities
                // The code is not from any single source

            }
        });
    }
}