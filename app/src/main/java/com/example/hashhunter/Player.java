package com.example.hashhunter;

import java.util.ArrayList;

public class Player {

    private String username;
    private String playerCode;
    private String profileCode;
    private ArrayList<GameCode> gameCodeList;
    private int totalPoints;
    private int totalGameCode;
    private int maxGameCodePoints;






    public String getUsername() {
        return this.username;
    }


    public String getPlayerCode() {
        return this.playerCode;
    }


    public String getProfileCode() {
        return this.profileCode;
    }


    public ArrayList<GameCode> getGameCodeList() {
        return this.gameCodeList;
    }


    public int getTotalPoints() {
        return this.totalPoints;
    }


    public int getTotalGameCode() {
        return this.totalGameCode;
    }


    public int getMaxGameCodePoints() {
        return this.maxGameCodePoints;
    }


    public void addGameCode(GameCode gameCode) {
        this.gameCodeList.add(gameCode);
    }


    public void removeGameCode(GameCode gameCode) {
        this.gameCodeList.remove(gameCode);
    }


    public void setTotalPoints(int points) {
        this.totalPoints = points;
    }


    public void setTotalGameCode(int total) {
        this.totalGameCode = total;
    }


    public void setMaxGameCodePoints(int points) {
        this.maxGameCodePoints = points;
    }




}
