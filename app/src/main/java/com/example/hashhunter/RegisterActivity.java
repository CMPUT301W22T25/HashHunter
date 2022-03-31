package com.example.hashhunter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.WriterException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "com.example.hashhunter.RegisterActivity";
    public static final String KEY_UNAME = "com.example.hashhunter.username";
    public static final String KEY_EMAIL = "com.example.hashhunter.email";
    public static final String KEY_UNIQUE_ID = "com.example.hashhunter.unique_id";
    private SharedPreferences sharedPreferences;

    private EditText usernameEdit;
    private EditText emailEdit;
    private String unique_id;
    private Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEdit = findViewById(R.id.username_edit_text);
        emailEdit = findViewById(R.id.email_edit_text);

        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF_NAME, MODE_PRIVATE);
                unique_id = sharedPreferences.getString(MainActivity.PREF_UNIQUE_ID, "IDNOTFOUND");

                Boolean validInput = validateInput(username, email);
                if (!validInput) {
                    // if the edittext inputs are empty then execute
                    Toast.makeText(RegisterActivity.this, "Enter valid input", Toast.LENGTH_SHORT).show();
                } else {
                    // still need to check and deny if the user already exists
                    //  https://firebase.google.com/docs/firestore/query-data/get-data#java_4
                    FirestoreController.getUsernames(username).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Toast.makeText(RegisterActivity.this, "Username already exists!", Toast.LENGTH_SHORT).show();
                            } else {
                                // https://www.youtube.com/watch?v=MILE4PVx1kE&list=PLrnPJCHvNZuDrSqu-dKdDi3Q6nM-VUyxD&index=2
                                Map<String, Object> info = new HashMap<>();
                                info.put(KEY_UNAME, username);
                                info.put(KEY_EMAIL, email);
                                info.put(MainActivity.PREF_UNIQUE_ID, unique_id);
                                FirestoreController.postUserInfo(unique_id, info)
                                        .addOnSuccessListener(unused -> {
                                            // store userId and username in shared preferences
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString(MainActivity.PREF_UNIQUE_ID, unique_id);
                                            editor.putString(MainActivity.PREF_USERNAME, username);
                                            editor.commit();
                                            // store username
                                            Map<String, Object> newUsername = new HashMap<>();
                                            newUsername.put(KEY_UNAME, username);
                                            newUsername.put(KEY_UNIQUE_ID, unique_id);
                                            FirestoreController.postUsernames(username, info)
                                                    .addOnSuccessListener(unused2 -> {
                                                        // add a player with username to firestore
                                                        Player player = new Player(username);
                                                        FirestoreController.postPlayers(unique_id, player)
                                                                .addOnSuccessListener(unused3 -> {
                                                                    Toast.makeText(RegisterActivity.this, "registration successful", Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
                                                                    startActivity(intent);
                                                                })
                                                                .addOnFailureListener(e -> {
                                                                    Toast.makeText(RegisterActivity.this, "registration error!", Toast.LENGTH_SHORT).show();
                                                                    Log.d(TAG, e.toString());
                                                                });
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Toast.makeText(RegisterActivity.this, "registration error!", Toast.LENGTH_SHORT).show();
                                                        Log.d(TAG, e.toString());
                                                    });
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(RegisterActivity.this, "registration error!", Toast.LENGTH_SHORT).show();
                                            Log.d(TAG, e.toString());
                                        });
                            }
                        } else {
                            Log.d(TAG, "get failed with", task.getException());
                        }
                    });
                }
            }
        });
    }

    /**
     * Check if the information entered by the user is valid or not
     * @param username
     * username that was entered by the user
     * @param email
     * email that was entered by the user
     * @return
     * true if the input is valid (non-empty and unique username and valid email address), false otherwise
     */
    public Boolean  validateInput(String username, String email) {
        //https://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
        Boolean ans = true;
     //https://www.javatpoint.com/java-email-validation
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            email = email.replaceAll("\\s+", "");
            if (email.length() > 0) {
                Toast.makeText(RegisterActivity.this, "Invalid email!", Toast.LENGTH_SHORT).show();
                ans = false;
            }
        }

        if (username.equals("") ) {
            // check that none of the fields are empty
            Toast.makeText(RegisterActivity.this, "Invalid username!", Toast.LENGTH_SHORT).show();
            ans = false;
        }

        return ans;
    };
}