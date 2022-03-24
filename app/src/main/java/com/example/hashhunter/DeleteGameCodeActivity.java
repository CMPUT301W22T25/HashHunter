package com.example.hashhunter;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DeleteGameCodeActivity extends AppCompatActivity {
    private static final String TAG = "com.example.hashhunter.DeleteGameCodeActivity";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    // this handles the result from the scan activity
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        assert intent != null;

                        String scannedCode = intent.getStringExtra(ScanActivity.EXTRA_SCANNED_UNAME);

                        //check which gamecodes have the same title
                        //https://stackoverflow.com/questions/62675759/how-to-remove-an-element-from-an-array-in-multiple-documents-in-firestore
                        //https://cloud.google.com/firestore/docs/query-data/get-data#javaandroid_4
                        db.collection("GameCode")
                                .whereEqualTo("code", scannedCode)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Log.d(TAG, document.getId() + " => " + document.getData());
                                                // put each code in the qr list
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });

                    } else {
                        Toast.makeText(DeleteGameCodeActivity.this, "result not ok", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private ActivityResultLauncher<String> requestCameraLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Intent intent = new Intent(DeleteGameCodeActivity.this, ScanActivity.class);
                    mStartForResult.launch(intent);
                } else {
                    Toast.makeText(DeleteGameCodeActivity.this, "Permission denied to access your camera", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_game_code);

        requestCameraLauncher.launch(Manifest.permission.CAMERA);
    }
}