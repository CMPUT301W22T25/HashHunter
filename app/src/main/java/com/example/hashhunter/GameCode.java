package com.example.hashhunter;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

public class GameCode implements Parcelable {
    private String title; // title of the code
    private String code; // string representation of the code
    private Integer points; // points of code
    private ArrayList<String> photos; // id of photos objects
    private ArrayList<String> owners; // username
    private ArrayList<String> comments; // id of comment object
    private Double latitude;
    private Double longitude;

    /**
     * Constructors
     */
    // without location and photos
    public GameCode(String title, String code, Integer points, String owner) {
        this.title = title;
        this.code = code;
        this.points = points;
        this.owners = new ArrayList<>(Arrays.asList(owner));
        this.photos = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    // with photos, without location
    public GameCode(String title, String code, Integer points, String photo, String owner) {
        this.title = title;
        this.code = code;
        this.points = points;
        this.photos = new ArrayList<>(Arrays.asList(photo));
        this.owners = new ArrayList<>(Arrays.asList(owner));
        this.comments = new ArrayList<>();
    }

    // with location, without photos
    public GameCode(String title, String code, Integer points, String owner, Double latitude, Double longitude) {
        this.title = title;
        this.code = code;
        this.points = points;
        this.photos = new ArrayList<>();
        this.owners = new ArrayList<>(Arrays.asList(owner));
        this.comments = new ArrayList<>();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // with location and photos
    public GameCode(String title, String code, Integer points, String photo, String owner, Double latitude, Double longitude) {
        this.title = title;
        this.code = code;
        this.points = points;
        this.photos = new ArrayList<>(Arrays.asList(photo));
        this.owners = new ArrayList<>(Arrays.asList(owner));
        this.comments = new ArrayList<>();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Getters and setters
     */


    protected GameCode(Parcel in) {
        code = in.readString();

        if (in.readByte() == 0) {
            points = null;
        } else {
            points = in.readInt();
        }
        title = in.readString();
        comments = new ArrayList<>();
        in.readList(comments, getClass().getClassLoader());

    }

    public static final Creator<GameCode> CREATOR = new Creator<GameCode>() {
        @Override
        public GameCode createFromParcel(Parcel in) {
            return new GameCode(in);
        }

        @Override
        public GameCode[] newArray(int size) {
            return new GameCode[size];
        }
    };

    /**
     * Getter for the number of players
     * @return
     * The number of players that have scanned the QR code
     */

    public void setTitle(String title) {
        this.title = title;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(code);

        if (points == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(points);
        }
        parcel.writeString(title);
        parcel.writeList(comments);
    }
    public String getTitle(){
        return title;
    }

    public int getCommentAmount() {
        return comments.size();
    }
    public String getComment(int position){
        return comments.get(position);
    }

    public void setComment(int userName, String commentContent){

    }


    /**
     * Stores a comment that is about the QR code
     * //@param
     * The comment to be stored
     */
//    public void storeComment(Comment c) {
//        this.commentList.add(c);
//    }
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
    public void addComment(String code){
        comments.add(code);
    }
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
