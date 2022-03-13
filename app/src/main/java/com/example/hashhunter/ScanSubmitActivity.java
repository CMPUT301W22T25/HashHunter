package com.example.hashhunter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ScanSubmitActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Get database of GameCodes
                 * Check if this has same string and location as them
                 * If yes, add that GameCode to the current player and increase numPlayers by 1
                 * If not, construct a new GameCode object and add to player GameCodeList, and to
                 * database if it has a location
                 */
                ArrayList qrStrings = new ArrayList<String>();
                // https://stackoverflow.com/questions/50035752/how-to-get-list-of-documents-from-a-collection-in-firestore-android
                db.collection("GameCode")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                           @Override
                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                               if (task.isSuccessful()) {
                                   ArrayList qrStrings = new ArrayList<String>();
                                   for (QueryDocumentSnapshot document : task.getResult()) {
                                       qrStrings.add(document.getId());
                                   }

                               } else {

                               }

                           }
                       });
                for (Object qrCode: qrStrings) {
                    if (qrCode == qrCodeString){
                        // Edit GameCode in firestore
                        Intent intent = new Intent(ScanSubmitActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }
                }
                // No match so create new GameCode and store in firestore


            }
        });
    }
}
