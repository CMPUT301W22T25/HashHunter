package com.example.hashhunter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserInfoFirebaseController {
    private FirebaseFirestore db;
    private static SharedPreferences sharedPreferences;
    private static final String TAG = "com.example.hashhunter.UserInfoFirebaseController";
    private static final String DB_USERNAME_KEY = "com.example.hashhunter.username";
    private static final String DB_UNIQUE_ID_KEY = "com.example.hashhunter.unique_id";

    public UserInfoFirebaseController(Context context) {
        db = FirebaseFirestore.getInstance();
        sharedPreferences = context.getSharedPreferences(MainActivity.SHARED_PREF_NAME, MODE_PRIVATE);
    }

    void setSharedPrefUserInfo(String username) {
        DocumentReference userDocRef = db.collection("Usernames").document(username);
        userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    // log them in and start another activity
                    if (document.exists()) {
                        String username = (String) document.getData().get(DB_USERNAME_KEY);
                        String unique_id = (String) document.getData().get(DB_UNIQUE_ID_KEY);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(MainActivity.PREF_UNIQUE_ID, unique_id);
                        editor.putString(MainActivity.PREF_USERNAME, username);
                        editor.putString(MainActivity.PREF_DB_OP_SUCCESS, "success");
                        editor.commit();
                        // Should this be called?
                        //finish();
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(MainActivity.PREF_DB_OP_SUCCESS, "failure");
                        editor.commit();
                    }
                }
            }
        });
    }
}
