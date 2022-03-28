package com.example.hashhunter;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
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





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        leaderboardRecycler = findViewById(R.id.leaderboard);
        playerList = new PlayerList();
        adapter = setAdapter();

        playerList.populate(adapter);





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


        SearchView usernameSearchView = (SearchView) findViewById(R.id.username_search);

        usernameSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent;
                intent = new Intent(ExploreActivity.this, SearchActivity.class);
                intent.putExtra("search", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
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