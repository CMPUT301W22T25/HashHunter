package com.example.hashhunter;

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
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference UserInfoRef = db.collection("UserInfo");
    private CollectionReference UsernameRef = db.collection("Usernames");

    Boolean userNameExists(String username) {
        final Boolean[] exists = {false};
        DocumentReference userDocRef = UsernameRef.document(username);
        userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        exists[0] = true;
                    }
                }
            }
        });

        return exists[0];
    }
}
