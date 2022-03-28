package com.example.hashhunter;

import static com.example.hashhunter.MainActivity.SHARED_PREF_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private ImageView scoreImage;
    private TextView scoreDisplay;
    private Integer playerScore;
    private int imageName;
    private String unique_id;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        scoreImage = findViewById(R.id.score_image);
        scoreDisplay = findViewById(R.id.score_display);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        unique_id = sharedPreferences.getString(SHARED_PREF_NAME, "IDNOTFOUND");

        DocumentReference docRef = db.collection("Players").document(unique_id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    playerScore = (Integer) document.get("totalPoints");
                } else {
                    System.out.println("Get failed with " + task.getException());
                }
                if (playerScore == null) {
                    playerScore = 0;
                }
                if (playerScore < 25) {
                    imageName = R.drawable.ic_baseline_star_border_24;
                } else if (playerScore >= 25 && playerScore < 50) {
                    imageName = R.drawable.ic_baseline_star_half_24;
                } else if (playerScore >= 50 && playerScore < 75) {
                    imageName = R.drawable.ic_baseline_star_rate_24;
                }else if (playerScore >= 75) {
                    imageName = R.drawable.ic_baseline_stars_24;
                }
                scoreImage.setImageResource(imageName);
                scoreDisplay.setText(String.valueOf(playerScore));
            }
        });


    }
}
