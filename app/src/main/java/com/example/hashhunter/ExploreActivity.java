package com.example.hashhunter;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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

public class ExploreActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<Player> playerList;
    private RecyclerView leaderboardRecycler;
    private LeaderboardAdapter adapter;
    private String unique_id;
    private SharedPreferences sharedPreferences;
    private Player myPlayer;
    private TextView myRank;
    private TextView myUsername;
    private TextView myScore;
    private String name;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        leaderboardRecycler = findViewById(R.id.leaderboard);
        playerList = new ArrayList<>();

        //https://stackoverflow.com/questions/51361951/retrieve-all-documents-from-firestore-as-custom-objects
        db.collection("Players")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (DocumentSnapshot document : task.getResult()) {
                                Player player = document.toObject(Player.class);
                                playerList.add(player);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });





        //adds data for testing
        //setPlayerInfo();

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



    }

    private void setupSort(LeaderboardAdapter listAdapter, ArrayList<Player> playerList) {
        Spinner spinner = (Spinner) findViewById(R.id.dropdown_menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sorting_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int i;
                if (position ==0) {
                    sortByQRScore();
                    for (i=0; i < playerList.size(); i++) {
                        Player player = playerList.get(i);
                        player.setDisplayTotal(player.getMaxGameCodePoints());
                        displayMyRank(name, playerList);
                    }



                }
                else if (position == 1) {
                    sortByTotalPoints();
                    for (i=0; i < playerList.size(); i++) {
                        Player player = playerList.get(i);
                        player.setDisplayTotal(player.getTotalPoints());
                        displayMyRank(name, playerList);
                    }
                }

                else {
                    sortByMostQR();
                    for (i=0; i < playerList.size(); i++) {
                        Player player = playerList.get(i);
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

    private void sortByQRScore() {
        Collections.sort(playerList, (a, b) -> {
            if (a.getMaxGameCodePoints() > b.getMaxGameCodePoints()) {
                return -1;
            }
            else if (a.getMaxGameCodePoints() < b.getMaxGameCodePoints()) {
                return 1;
            }
            else {
                return 0;
            }
        });
    }

    private void sortByMostQR() {
        Collections.sort(playerList, (a, b) -> {
            if (a.getTotalGameCode() > b.getTotalGameCode()) {
                return -1;
            }
            else if (a.getTotalGameCode() < b.getTotalGameCode()) {
                return 1;
            }
            else {
                return 0;
            }
        });
    }

    private void sortByTotalPoints() {
        Collections.sort(playerList, (a, b) -> {
            if (a.getTotalPoints() > b.getTotalPoints()) {
                return -1;
            }
            else if (a.getTotalPoints() < b.getTotalPoints()) {
                return 1;
            }
            else {
                return 0;
            }
        });
    }


    private LeaderboardAdapter setAdapter() {
        LeaderboardAdapter adapter = new LeaderboardAdapter(playerList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        leaderboardRecycler.setLayoutManager(layoutManager);
        leaderboardRecycler.setAdapter(adapter);
        return adapter;

    }



    private int findPlayerPos(String username, ArrayList<Player> playerList) {

        for (int i =0; i < playerList.size(); i++) {

            if (playerList.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }


    private void displayMyRank(String name, ArrayList<Player> playerList) {

        int pos = findPlayerPos(name, playerList);
        myPlayer = playerList.get(pos);

        //display users position on leaderboard
        myRank = (TextView) findViewById(R.id.my_rank);
        myUsername = (TextView) findViewById(R.id.my_username);
        myScore = (TextView) findViewById(R.id.my_points);

        myRank.setText(Integer.toString(playerList.indexOf(myPlayer)+1));
        myUsername.setText(myPlayer.getUsername());
        myScore.setText(Integer.toString(myPlayer.getDisplayTotal()));


    }


    //fake testing data
    //private void setPlayerInfo() {
        //playerList.add(new Player("Test1", 25, 235, 1));
        //playerList.add(new Player("Test2", 32, 214, 12345));
        //playerList.add(new Player("Test3", 56, 10, 4));
    //}




}