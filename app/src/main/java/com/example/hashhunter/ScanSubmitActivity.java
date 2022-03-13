package com.example.hashhunter;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScanSubmitActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Bitmap photoBitmap; // bitmap received from camera app
    private String photoId; // id of photo in firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_submit);

        Intent intent = getIntent();
        Integer points = (Integer) intent.getSerializableExtra("points");
        String qrCodeString = intent.getStringExtra("qrcode string");


        TextView showPoints = findViewById(R.id.qr_code_points);
        showPoints.setText(points + " Points");

        Button addPhoto = findViewById(R.id.add_photo_button);
        Button addLocation = findViewById(R.id.add_location_button);
        Button saveButton = findViewById(R.id.save_button);


        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Upload photo to firebase storage
                 * Add photo object containing url to firestore
                 * Get database of GameCodes
                 * Check if a code with the same string and location exists
                 * If yes, add that gamecode to the current player and increase numPlayers by 1
                 * If not, construct a new GameCode object and add to player GameCodeList, and to
                 * database if it has a location
                 */
                if (photoBitmap != null) {
                    // only if user take photos
                    uploadPhotoToStorage();
                }

                Intent intent = new Intent(ScanSubmitActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }
    // upload photo to firebase storage
    private void uploadPhotoToStorage() {
        // construct byte array to be uploaded
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        // send packet to firebase storage
        String path = "photos/" + UUID.randomUUID() + ".png";
        StorageReference storageRef = storage.getReference();
        StorageReference pathRef = storageRef.child(path);
        UploadTask uploadTask = pathRef.putBytes(data); // return progress update
        // get the download url
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                return pathRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    // get url
                    Uri downloadUri = task.getResult();
                    // store url in db
                    storePhotoDataInDB(downloadUri.toString());
                } else {
                    // Handle failures
                }
            }
        });
    }

    /**
     * Store photo data like owner username and url into firestore "Photo" collection
     * GameCode object will store the id of Photo object to refer to the url
     * @param photoUrl url of photo to be stored
     */
    private void storePhotoDataInDB(String photoUrl) {
        Map<String, Object> photo = new HashMap<>();
        photo.put("owner", "username"); // replace value with logged in user
        photo.put("url", photoUrl);
        photoId = UUID.randomUUID().toString();
        db.collection("Photo").document(photoId).set(photo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("DB_OPERATION", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("DB_OPERATION", "Error writing document", e);
                    }
                });
    }
    // launch camera app
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
            e.printStackTrace();
        }
    }
    // get bitmap result from camera app
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            photoBitmap = imageBitmap;
            // display preview
            ImageView imageView = findViewById(R.id.scan_submit_photo_preview);
            imageView.setImageBitmap(imageBitmap);
        }
    }

}
