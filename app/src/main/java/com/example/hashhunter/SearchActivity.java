package com.example.hashhunter;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

/**
 * Displays the results of searching for a player, shows "Player not found" if there is no player with that username
 * otherwise shows the players username and on click goes to that players profile
 */
public class SearchActivity extends AppCompatActivity {

    private String name;
    private TextView searchText;
    private TextView username;
    private Player player;
    private String userId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //get the results from the explore activity
        Bundle extras = getIntent().getExtras();
        if (extras.size() == 1) {
            name = extras.getString("search");
        }
        else {
            name = extras.getString("search");
            player = extras.getParcelable("player");
        }

        //display results
        searchText = (TextView) findViewById(R.id.search_text);
        username = (TextView) findViewById(R.id.search_username);

        searchText.setText("Search result for username '"+name+"'");

        if (player == null) {
            username.setText("Player Not Found");
        }
        // get the unique id for the username and pass to profile activity
        else {
            username.setText(name);
            username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // get the user id
                    FirestoreController.getPlayersName(name)
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            userId = document.getId();
                                            break; // only get the first result
                                        }
                                        // relocate to profile page sending userId
                                        Intent newIntent = new Intent(SearchActivity.this, ProfileActivity.class);
                                        newIntent.putExtra("userId", userId);
                                        startActivity(newIntent);
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }
            });

        }















    }
}
