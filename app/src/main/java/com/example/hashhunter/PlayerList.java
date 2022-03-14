package com.example.hashhunter;

import java.util.ArrayList;
import java.util.Collections;

public class PlayerList {

    private ArrayList<Player> playerList;


    public PlayerList() {
        this.playerList = new ArrayList<>();
    }



    public void sortByQRScore() {
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

    public void sortByMostQR() {
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

    public void sortByTotalPoints() {
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



    public void addPlayerList(Player player) {
        this.playerList.add(player);

    }


    public int getSize() {
        return this.playerList.size();
    }


    public Player getPlayer(int i) {
        return this.playerList.get(i);
    }


    public ArrayList<Player> getPlayerList() {
        return this.playerList;
    }


    public int findPlayerPos(String username) {

        for (int i =0; i < playerList.size(); i++) {

            if (playerList.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }


    public int indexOfPlayer(Player player) {
        return this.playerList.indexOf(player);
    }

}
