package com.example.hashhunter;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A Model class that represent a GameCode object (QR code and associated metadata like points and location)
 * Used as a schema to the database
 * It has 4 different constructor for different kinds of object: with/without location and photos
 */
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
    public GameCode(){
        //For firebase constructor
    }
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

    protected GameCode(Parcel in) {
        title = in.readString();
        code = in.readString();
        if (in.readByte() == 0) {
            points = null;
        } else {
            points = in.readInt();
        }
        photos = in.createStringArrayList();
        owners = in.createStringArrayList();
        comments = in.createStringArrayList();
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
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
     * Getters and setters
     */

    public String getTitle(){ return title; }

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

    public int getCommentAmount() {
        return comments.size();
    }

    public String getComment(int position){
        return comments.get(position);
    }

    public void addComment(String commentId) {comments.add(commentId);}

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

    public void printAttributes(){
        System.out.println("Title: ");
        System.out.println(title); // title of the code
        System.out.println("Code: ");
        System.out.println(code); // string representation of the code
        System.out.println("Points: ");
        System.out.println(points); // points of code
        System.out.println("Photos: ");

        System.out.println(photos); // id of photos objects
        System.out.println("Owners: ");
        System.out.println(owners); // username
        System.out.println("Comments: ");
        System.out.println(comments); // id of comment object
        System.out.println("Latitude: ");
        System.out.println(latitude);
        System.out.println("Longitude: ");
        System.out.println(longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(code);
        if (points == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(points);
        }
        parcel.writeStringList(photos);
        parcel.writeStringList(owners);
        parcel.writeStringList(comments);
        if (latitude == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(latitude);
        }
        if (longitude == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(longitude);
        }
    }
}
