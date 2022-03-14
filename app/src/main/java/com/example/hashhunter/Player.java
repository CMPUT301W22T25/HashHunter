package com.example.hashhunter;

import java.util.ArrayList;

/**
 * This is a class that keeps track of a player
 */

public class Player {

    private String username;
    private String playerCode;
    private String profileCode;
    private ArrayList<GameCode> gameCodeList;
    private int totalPoints;
    private int totalGameCode;
    private int maxGameCodePoints;
    private int displayTotal;

    /**
     * constructors
     */
    public Player() {}

    public Player(String username) {
        this.username = username;
    }


    /**
     * Gets the players username
     * @return
     *      Returns the string representing the players username
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Gets the players player code
     * @return
     *      Returns the string representing the players player code
     */
    public String getPlayerCode(){
        return this.playerCode;
    }

    /**
     * Gets the players profile code
     * @return
     *      Returns the string representing the players profile code
     */
    public String getProfileCode() {
        return this.profileCode;
    }

    /**
     * gets the players game code list
     * @return
     *      Returns the list of GameCodes
     */
    public ArrayList<GameCode> getGameCodeList() {
        return this.gameCodeList;
    }

    /**
     * gets the players total points
     * @return
     *      Returns the int representing the players total points
     */
    public int getTotalPoints() {
        return this.totalPoints;
    }

    /**
     * gets the players total game code
     * @return
     *      Returns the int representing the players total game code
     */
    public int getTotalGameCode() {
        return this.totalGameCode;
    }

    /**
     * gets the players max game code points
     * @return
     *      Returns the int representing the players max game code points
     */
    public int getMaxGameCodePoints() {
        return this.maxGameCodePoints;
    }

    /**
     * adds a gameCode to the players gameCodeList
     * @param gameCode
     *      this is a game code to add to gameCodeList
     */
    public void addGameCode(GameCode gameCode) {
        this.gameCodeList.add(gameCode);
    }

    /**
     * removes a gameCode from the players gameCodeList
     * @param gameCode
     *      this is a game code to remove from gameCodeList
     */
    public void removeGameCode(GameCode gameCode) {
        this.gameCodeList.remove(gameCode);
    }

    /**
     * sets the players total points to a provided value
     * @param points
     *      this is the int value that total points will be set to
     */
    public void setTotalPoints(int points) {
        this.totalPoints = points;
    }

    /**
     * sets the total game code
     * @param total
     *      this is the int value that total game code will be set to
     */
    public void setTotalGameCode(int total) {
        this.totalGameCode = total;
    }

    /**
     * sets the max game code points
     * @param points
     *      this is the int value that max game code points will be set to
     */
    public void setMaxGameCodePoints(int points) {
        this.maxGameCodePoints = points;
    }

    /**
     * gets the points that are to be displayed
     * @return
     *      returns the int points
     */
    public int getDisplayTotal() {
        return this.displayTotal;
    }

    /**
     * sets the points that are to be displayed
     * @param points
     *      the points that are set
     */
    public void setDisplayTotal(int points) {
        this.displayTotal = points;
    }






}
