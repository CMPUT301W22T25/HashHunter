package com.example.hashhunter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * test activity to build camera functionality for ScanSubmitActivity
 * Storing in cloud: https://www.youtube.com/watch?v=7puuTDSf3pk
 */
public class CameraActivity extends AppCompatActivity {
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap uploadBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Button camButton = findViewById(R.id.cam_submit_button);
        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                uploadBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();
                // save file
                String path = "test/" + UUID.randomUUID() + ".png";
                StorageReference pathRef = storage.getReference(path);
                UploadTask uploadTask = pathRef.putBytes(data); // return progress update
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
            // test image view from url
            ImageView imageViewTest = findViewById(R.id.cam_photo_url);
            Photo testPhoto = new Photo("https://www.petmd.com/sites/default/files/2020-11/picture-of-golden-retriever-dog_0.jpg");
            testPhoto.displayImage(imageViewTest);
        }
    }
}