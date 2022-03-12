package com.example.hashhunter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScanSubmitActivity extends AppCompatActivity {

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
            }
        });
    }
}
