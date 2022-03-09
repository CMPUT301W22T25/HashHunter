package com.example.hashhunter;

import org.w3c.dom.Comment;

import java.util.ArrayList;

public class GameCode {
    private String qrCode;
    private Photo photo;
    private Location location;
    private Integer numPlayers;
    private Integer points;
    private ArrayList<Comment> commentList;

    public void GameCode(String qrCode, Photo photo, Location location, Integer numPlayers,
            Integer points, ArrayList<Comment> commentList) {
        this.qrCode = qrCode;
        this.photo = photo;
        this.location = location;
        this.numPlayers = numPlayers;
        this.points = points;
        this.commentList = commentList;
    }

    /**
     * Getter for stored photo
     * @return
     * The stored photo
     */
    public Photo getPhoto() {
        return this.photo;
    }

    /**
     * Setter for the photo
     * @param p
     * The photo that is to be set as the stored photo
     */
    public void setPhoto(Photo p) {
        this.photo = p;
    }

    /**
     * Getter for the stored location
     * @return
     * The location stored
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Setter for the location
     * @param l
     * The location that is to be set as the stored location
     */
    public void setLocation(Location l) {
        this.location = l;
    }

    /**
     * Getter for the number of players
     * @return
     * The number of players that have scanned the QR code
     */
    public Integer getPlayers() {
        return this.numPlayers;
    }

    /**
     * Setter for the number of players
     * @param n
     * The integer that is to be set as the number of players that have scanned the QR code
     */
    public void setPlayers(Integer n) {
        this.numPlayers = n;
    }

    /**
     * Getter for the points
     * @return
     * The number of points that the QR code is worth
     */
    public Integer getPoints() {
        return this.points;
    }

    /**
     * Setter for the points
     * @param p
     * The number of points that the qr code is worth
     */
    public void setPoints(Integer p) {
        this.points = p;
    }

    /**
     * Stores a comment that is about the QR code
     * @param c
     * The comment to be stored
     */
    public void storeComment(Comment c) {
        this.commentList.add(c);
    }
}
