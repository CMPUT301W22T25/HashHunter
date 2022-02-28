package com.example.hashhunter;

import java.util.ArrayList;

public class GameCode {
    private String qrCode;
    private Photo photo;
    private Location location;
    private Integer numPlayers;
    private Integer points;
    private ArrayList<Comment> commentList;

    public Photo getPhoto() {
        return this.photo;
    }

    public void setPhoto(Photo p) {
        this.photo = p;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location l) {
        this.location = l;
    }

    public Integer getPlayers() {
        return this.numPlayers;
    }

    public void setPlayers(Integer n) {
        this.numPlayers = n;
    }

    public Integer getPoints() {
        return this.points;
    }

    public void setPoints(Integer p) {
        this.points = p;
    }

    public void storeComment(Comment c) {
        this.commentList.add(c);
    }
}
