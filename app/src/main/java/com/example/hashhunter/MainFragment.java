package com.example.hashhunter;

import static com.example.hashhunter.MainActivity.SHARED_PREF_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private ImageView scoreImage;
    private TextView scoreDisplay;
    private Integer playerScore;
    private TextView flairView;
    private int imageName;
    private String unique_id;
    private String flairText;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scoreImage = getActivity().findViewById(R.id.score_image);
        scoreDisplay = getActivity().findViewById(R.id.score_display);
        flairView = getActivity().findViewById(R.id.flair_text);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        unique_id = sharedPreferences.getString(SHARED_PREF_NAME, "IDNOTFOUND");

        DocumentReference docRef = db.collection("Players").document(unique_id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Player player = document.toObject(Player.class);
                } else {
                    System.out.println("Get failed with " + task.getException());
                }
                if (playerScore == null) {
                    playerScore = 75;
                }
                if (playerScore < 25) {
                    imageName = R.drawable.ic_baseline_star_border_24;
                    flairText = "Your forest could use some work! \nKeep on hunting!";
                } else if (playerScore >= 25 && playerScore < 50) {
                    imageName = R.drawable.ic_baseline_star_half_24;
                    flairText = "Nice forest! \nKeep it growing!";
                } else if (playerScore >= 50 && playerScore < 75) {
                    imageName = R.drawable.ic_baseline_star_rate_24;
                    flairText = "Wow you are quite the hunter! \nGreat job!";
                }else if (playerScore >= 75) {
                    imageName = R.drawable.ic_baseline_stars_24;
                    flairText = "Amazing work! \nYou are truly a top hunter!";
                }
                scoreImage.setImageResource(imageName);
                scoreDisplay.setText(String.valueOf(playerScore));
                flairView.setText(flairText);


            }

        });
    }
}
