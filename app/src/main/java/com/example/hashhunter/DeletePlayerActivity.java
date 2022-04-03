package com.example.hashhunter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class DeletePlayerActivity extends AppCompatActivity {
    private static final String TAG = "com.example.hashhunter.DeletePlayerActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_player);

        Button submitButton = findViewById(R.id.del_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usernameText = findViewById(R.id.del_username_edit_text);
                String username = usernameText.getText().toString();

                FirestoreController.getUsernames(username).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            // log them in and start another activity
                            if (document.exists()) {
                                // https://firebase.google.com/docs/firestore/manage-data/delete-data#java
                                Map<String, Object> data =  document.getData();
                                String unique_id = data.get(RegisterActivity.KEY_UNIQUE_ID).toString();
                                Toast.makeText(DeletePlayerActivity.this, unique_id, Toast.LENGTH_SHORT).show();

                                // delete from Usernames
                                FirestoreController.deleteUsernames(username)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error deleting document", e);
                                            }
                                        });

                                // delete from UserInfo
                                FirestoreController.deleteUserInfo(unique_id)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error deleting document", e);
                                            }
                                        });

                                // for all the gamecodes, if this user is in the owners
                                // array, remove them
                                //https://stackoverflow.com/questions/62675759/how-to-remove-an-element-from-an-array-in-multiple-documents-in-firestore
                                //https://cloud.google.com/firestore/docs/query-data/get-data#javaandroid_4
                                FirestoreController.getGameCodesWithOwner(unique_id)
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                                        document.getReference().update("owners", FieldValue.arrayRemove(username));
                                                    }

                                                } else {
                                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                                }
                                            }
                                        });

                                // delete related comments
                                FirestoreController.getCommentList().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                try {
                                                    if (document.getString("owner").equals(username)) {
                                                        document.getReference().delete();
                                                    }
                                                } catch (NullPointerException e) {
                                                    Log.d(TAG, "nullpointer: " + e.toString());
                                                }
                                            }
                                        }
                                    }
                                });

                                // delete related photos
                                FirestoreController.getPhotoList().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document: task.getResult()) {
                                                try {
                                                    if (document.getString("owner").equals(username)) {
                                                        document.getReference().delete();
                                                    }
                                                } catch (NullPointerException e) {
                                                    Log.d(TAG, "nullpointer: " + e.toString());
                                                }
                                            }
                                        }
                                    }
                                });

                                // delete from Players
                                FirestoreController.deletePlayers(unique_id)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error deleting document", e);
                                            }
                                        });

                                finish();

                            } else {
                                Toast.makeText(DeletePlayerActivity.this, "no username found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d(TAG, "get failed with", task.getException());
                        }
                    }
                });
            }
        });
    }
}