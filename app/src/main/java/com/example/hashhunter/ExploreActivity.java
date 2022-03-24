package com.example.hashhunter;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * displays the leaderboard, which is able to sort based of a dropdown menu and display the users rank
 */
public class ExploreActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private PlayerList playerList;
    private RecyclerView leaderboardRecycler;
    private LeaderboardAdapter adapter;
    private String unique_id;
    private SharedPreferences sharedPreferences;
    private Player myPlayer;
    private TextView myRank;
    private TextView myUsername;
    private TextView myScore;
    private String name;

    // this handles the result from the scan activity
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        String scannedUsername = intent.getStringExtra(ScanActivity.EXTRA_SCANNED_UNAME);
                        // check if username exists
                        boolean username_found = false;

                        // relocate to profile page of the username if available
                        if (username_found) {
                            Intent newIntent = new Intent(ExploreActivity.this, ProfileActivity.class);
                            startActivity(newIntent);
                        } else {
                            Toast.makeText(ExploreActivity.this, "username " + scannedUsername + " does not exist", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(ExploreActivity.this, "result not ok", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    private ActivityResultLauncher<String> requestCameraLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Intent intent = new Intent(ExploreActivity.this, ScanActivity.class);
                    mStartForResult.launch(intent);
                } else {
                    Toast.makeText(ExploreActivity.this, "Permission denied to access your camera", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        leaderboardRecycler = findViewById(R.id.leaderboard);
        playerList = new PlayerList();


        // retrieves all the players from database
        //https://stackoverflow.com/questions/51361951/retrieve-all-documents-from-firestore-as-custom-objects
        db.collection("Players")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (DocumentSnapshot document : task.getResult()) {
                                Player player = document.toObject(Player.class);
                                playerList.addPlayerList(player);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });







        adapter = setAdapter();


        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF_NAME, MODE_PRIVATE);
        unique_id = sharedPreferences.getString(MainActivity.PREF_UNIQUE_ID, "IDNOTFOUND");

        setupSort(adapter, playerList);


        DocumentReference docRef = db.collection("Players").document(unique_id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
               Player player = documentSnapshot.toObject(Player.class);
               name = player.getUsername();


            }
        });

        Button scanButton = findViewById(R.id.scan_profile_button);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestCameraLauncher.launch(Manifest.permission.CAMERA);
            }
        });

    }

    private void setupSort(LeaderboardAdapter listAdapter, PlayerList playerList) {
        Spinner spinner = (Spinner) findViewById(R.id.dropdown_menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sorting_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int i;

                //if QRScore is selected
                if (position ==0) {
                    playerList.sortByQRScore();
                    for (i=0; i < playerList.getSize(); i++) {
                        Player player = playerList.getPlayer(i);
                        player.setDisplayTotal(player.getMaxGameCodePoints());
                        displayMyRank(name, playerList);
                    }



                }
                // if Total points is selected
                else if (position == 1) {
                    playerList.sortByTotalPoints();
                    for (i=0; i < playerList.getSize(); i++) {
                        Player player = playerList.getPlayer(i);
                        player.setDisplayTotal(player.getTotalPoints());
                        displayMyRank(name, playerList);
                    }
                }

                else {
                    //if most qr is selected
                    playerList.sortByMostQR();
                    for (i=0; i < playerList.getSize(); i++) {
                        Player player = playerList.getPlayer(i);
                        player.setDisplayTotal(player.getTotalGameCode());
                        displayMyRank(name, playerList);
                    }
                }
                listAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }




    private LeaderboardAdapter setAdapter() {
        LeaderboardAdapter adapter = new LeaderboardAdapter(playerList.getPlayerList());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        leaderboardRecycler.setLayoutManager(layoutManager);
        leaderboardRecycler.setAdapter(adapter);
        return adapter;

    }


    /**
     * displays the users rank at the bottom
     * @param name
     *      this is the username of the player
     * @param playerList
     *      this is the list of all the players
     */
    private void displayMyRank(String name, PlayerList playerList) {

        int pos = playerList.findPlayerPos(name);
        myPlayer = playerList.getPlayer(pos);

        //display users position on leaderboard
        myRank = (TextView) findViewById(R.id.my_rank);
        myUsername = (TextView) findViewById(R.id.my_username);
        myScore = (TextView) findViewById(R.id.my_points);

        myRank.setText(Integer.toString(playerList.indexOfPlayer(myPlayer)+1));
        myUsername.setText(myPlayer.getUsername());
        myScore.setText(Integer.toString(myPlayer.getDisplayTotal()));


    }




}