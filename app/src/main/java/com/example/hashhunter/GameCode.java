package com.example.hashhunter;

import android.location.Location;
import java.util.ArrayList;
import java.util.Arrays;

public class GameCode {
    private String title; // title of the code
    private String code; // string representation of the code
    private Location location; // location of code
    private Integer points; // points of code
    private ArrayList<String> photos; // id of photos objects
    private ArrayList<String> owners; // username
    private ArrayList<String> comments; // id of comment object

    /**
     * Constructors
     */
    // without location and photos
    public GameCode(String title, String code, Integer points, String owner) {
        this.title = title;
        this.code = code;
        this.location = null;
        this.points = points;
        this.owners = new ArrayList<>(Arrays.asList(owner));
        this.photos = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    // with photos, without location
    public GameCode(String title, String code, Integer points, String photo, String owner) {
        this.title = title;
        this.code = code;
        this.location = null;
        this.points = points;
        this.photos = new ArrayList<>(Arrays.asList(photo));
        this.owners = new ArrayList<>(Arrays.asList(owner));
        this.comments = new ArrayList<>();
    }

    // with location, without photos
    public GameCode(String title, String code, Location location, Integer points, String owner) {
        this.title = title;
        this.code = code;
        this.location = location;
        this.points = points;
        this.photos = new ArrayList<>();
        this.owners = new ArrayList<>(Arrays.asList(owner));
        this.comments = new ArrayList<>();
    }

    // with location and photos
    public GameCode(String title, String code, Location location, Integer points, String photo, String owner) {
        this.title = title;
        this.code = code;
        this.location = location;
        this.points = points;
        this.photos = new ArrayList<>(Arrays.asList(photo));
        this.owners = new ArrayList<>(Arrays.asList(owner));
        this.comments = new ArrayList<>();
    }

    /**
     * Getters and setters
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    public ArrayList<String> getOwners() {
        return owners;
    }

    public void setOwners(ArrayList<String> owners) {
        this.owners = owners;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }
}
