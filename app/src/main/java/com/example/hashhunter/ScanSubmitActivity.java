package com.example.hashhunter;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScanSubmitActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Bitmap photoBitmap; // bitmap received from camera app

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_submit);

        Intent intent = getIntent();
        Integer points = (Integer) intent.getSerializableExtra("points");

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
                 * Get database of GameCodes
                 * Check if this has same string and location as them
                 * If yes, add that gamecode to the current player and increase numPlayers by 1
                 * If not, construct a new GameCode object and add to player GameCodeList, and to
                 * database if it has a location
                 */
                Intent intent = new Intent(ScanSubmitActivity.this, DashboardActivity.class);
                startActivity(intent);
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
