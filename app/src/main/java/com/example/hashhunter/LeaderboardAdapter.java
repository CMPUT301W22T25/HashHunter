package com.example.hashhunter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.leaderboardViewHolder> {



    private ArrayList<Player> playerList;

    public LeaderboardAdapter(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }


    public class leaderboardViewHolder extends RecyclerView.ViewHolder {


        //variables for the list
        private TextView rankView;
        private TextView usernameView;
        private TextView pointsView;


        public leaderboardViewHolder(final View view) {
            super(view);


            rankView = view.findViewById(R.id.rank);
            usernameView = view.findViewById(R.id.username);
            pointsView = view.findViewById(R.id.points);


        }
    }


    @NonNull
    @Override
    public LeaderboardAdapter.leaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item, parent, false);
        return new leaderboardViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardAdapter.leaderboardViewHolder holder, int position) {
        Player player = playerList.get(position);



        holder.pointsView.setText(Integer.toString(player.getDisplayTotal()));
        holder.usernameView.setText(player.getUsername());
        holder.rankView.setText(Integer.toString(position+1));


    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }







}
