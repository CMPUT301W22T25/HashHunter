package com.example.hashhunter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;

public class ExploreActivity extends AppCompatActivity {

    private ArrayList<Player> playerList;
    private RecyclerView leaderboardRecycler;
    private LeaderboardAdapter adapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);



        leaderboardRecycler = findViewById(R.id.leaderboard);
        playerList = new ArrayList<>();

        //adds data for testing
        //setPlayerInfo();

        adapter = setAdapter();

        setupSort(adapter, playerList);
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
                    }

                }
                else if (position == 1) {
                    sortByTotalPoints();
                    for (i=0; i < playerList.size(); i++) {
                        Player player = playerList.get(i);
                        player.setDisplayTotal(player.getTotalPoints());
                    }
                }

                else {
                    sortByMostQR();
                    for (i=0; i < playerList.size(); i++) {
                        Player player = playerList.get(i);
                        player.setDisplayTotal(player.getTotalGameCode());
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

    //fake testing data
    //private void setPlayerInfo() {
        //playerList.add(new Player("Test1", 25, 235, 1));
        //playerList.add(new Player("Test2", 32, 214, 12345));
        //playerList.add(new Player("Test3", 56, 10, 4));
    //}




}