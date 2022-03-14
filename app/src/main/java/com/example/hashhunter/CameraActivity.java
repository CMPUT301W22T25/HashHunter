package com.example.hashhunter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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


import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;

/**
 * test activity to build camera functionality for ScanSubmitActivity
 * Storing in cloud: https://www.youtube.com/watch?v=7puuTDSf3pk
 */
public class CameraActivity extends AppCompatActivity {
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap uploadBitmap;
    ArrayList<String> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        // test image view from url
        ImageView imageViewTest = findViewById(R.id.cam_photo_url);
        PhotoController testPhoto = new PhotoController("https://www.petmd.com/sites/default/files/2020-11/picture-of-golden-retriever-dog_0.jpg");
        testPhoto.displayImage(imageViewTest);
        // test submit button
        Button camButton = findViewById(R.id.cam_submit_button);
        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                uploadBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();
                // save file
                String path = "test/" + UUID.randomUUID() + ".png";
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
                            Log.d("CAMERA_DEBUG", downloadUri.toString());
                            // upload url to firestore
                            DocumentReference codeRef = db.collection("GameCode").document("FtHjTlwFHYmVk7fvMKx8");
//                            // get the initial photos array first
//                            codeRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                    if (task.isSuccessful()) {
//                                        DocumentSnapshot document = task.getResult();
//                                        photos = (ArrayList<String>) document.get("photos");
//                                    }
//                                }
//                            });
                            // update photos array
//                            photos.add(downloadUri.toString());
                            codeRef.update("photos", downloadUri.toString());
                            // test display url
                            codeRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        PhotoController newPhoto = new PhotoController(document.getString("photos"));
                                        newPhoto.displayImage(imageViewTest);
                                    }
                                }
                            });
                        } else {
                            // Handle failures
                        }
                    }
                });
            }
        });
        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            uploadBitmap = imageBitmap;
            ImageView imageView = findViewById(R.id.cam_photo_preview);
            imageView.setImageBitmap(imageBitmap);
        }
    }
}