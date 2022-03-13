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

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "com.example.hashhunter.RegisterActivity";
    private static final String KEY_UNAME = "com.example.hashhunter.username";
    private static final String KEY_EMAIL = "com.example.hashhunter.email";
    private static SharedPreferences sharedPreferences;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        assert intent != null;

                        String scannedUsername = intent.getStringExtra(ScanActivity.EXTRA_SCANNED_UNAME);

                       //  https://firebase.google.com/docs/firestore/query-data/get-data#java_4
                        DocumentReference userDocRef = db.collection("UserInfo").document(scannedUsername);
                        userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
                                        editor.commit();
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "no username found", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.d(TAG, "get failed with", task.getException());
                                }
                            }
                        });

                    }
                }
            });
    private ActivityResultLauncher<String> requestCameraLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Intent intent = new Intent(LoginActivity.this, ScanActivity.class);
                    mStartForResult.launch(intent);
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied.
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
                finish();
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