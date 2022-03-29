package com.example.hashhunter;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

public class SearchActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ImageView profilePic;
    private String name;
    private TextView searchText;
    private TextView username;
    private Player player;
    private String scannedUserId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Bundle extras = getIntent().getExtras();
        System.out.println(extras.size());
        if (extras.size() == 1) {
            name = extras.getString("search");
        }
        else {
            name = extras.getString("search");
            player = extras.getParcelable("player");
        }

        searchText = (TextView) findViewById(R.id.search_text);
        username = (TextView) findViewById(R.id.search_username);

        searchText.setText("Search result for username '"+name+"'");

        if (player == null) {
            username.setText("Player Not Found");
        }
        else {
            username.setText(name);
            username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // get the user id
                    db.collection("Players")
                            .whereEqualTo("username", name)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            scannedUserId = document.getId();
                                            break; // only get the first result
                                        }
                                        // relocate to profile page sending userId
                                        Intent newIntent = new Intent(SearchActivity.this, ProfileActivity.class);
                                        newIntent.putExtra("userId", scannedUserId);
                                        startActivity(newIntent);
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }
            });
        }

        profilePic = (ImageView) findViewById(R.id.search_profile_picture);
        profilePic.setImageResource(R.drawable.ic_android);














    }
}
