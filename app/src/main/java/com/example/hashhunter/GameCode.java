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
     * an empty constructor, to be used with firebase
     */
    public GameCode(){
        //For firebase constructor
    }

    // without location and photos

    /**
     * used to construct the gamecode without providing a location or photos
     * @param title the title of the gamecode
     * @param code the string representation of the gamecode
     * @param points the points that the gamecode is worth
     * @param owner the unique id of the player who scanned the code
     */
    public GameCode(String title, String code, Integer points, String owner) {
        this.title = title;
        this.code = code;
        this.points = points;
        this.owners = new ArrayList<>(Arrays.asList(owner));
        this.photos = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    // with photos, without location

    /**
     * used to construct the gamecode without providing a location
     * @param title the title of the gamecode
     * @param code the string representation of the gamecode
     * @param points the points that the gamecode is worth
     * @param photo the id of the photo associated with the gamecode
     * @param owner the unique id of the player who scanned the code
     */
    public GameCode(String title, String code, Integer points, String photo, String owner) {
        this.title = title;
        this.code = code;
        this.points = points;
        this.photos = new ArrayList<>(Arrays.asList(photo));
        this.owners = new ArrayList<>(Arrays.asList(owner));
        this.comments = new ArrayList<>();
    }

    // with location, without photos

    /**
     * used to construct the gamecode without providing photos
     * @param title the title of the gamecode
     * @param code the string representation of the gamecode
     * @param points the points that the gamecode is worth
     * @param owner the unique id of the player who scanned the code
     * @param latitude the latitude of the gamecode
     * @param longitude the longitude of the gamecode
     */
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

    /**
     * used to construct the gamecode with both location and photos
     * @param title the title of the gamecode
     * @param code  the string representation of the gamecode
     * @param points the points that the gamecode is worth
     * @param photo the id of the photo associated with the gamecode
     * @param owner the unique id of the player who scanned the code
     * @param latitude the latitude of the gamecode
     * @param longitude the longitude of the gamecode
     */
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
     * Used to make the gamecode parcelable
     * @param in the parcel that is to be read
     */
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
     * gets the title of the gamecode
     * @return the title of the gamecode
     */
    public String getTitle(){ return title; }

    /**
     * sets the title of the gamecode to the given title
     * @param title the title that is to be set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * gets the string representation of the gamecode (string representation of the qr code that was scanned)
     * @return the string representation of the gamecode
     */
    public String getCode() {
        return code;
    }

    /**
     * sets the code for the gamecode to the given code
     * @param code string representation of the gamecode that is to be set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * gets the points for the gamecode
     * @return the points that the gamecode is worth
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * sets the points for the gamecode
     * @param points the points that are to be set
     */
    public void setPoints(Integer points) {
        this.points = points;
    }

    /**
     * gets the array of strings of the ids of all the photos associated with the gamecode
     * @return an array of strings (photo ids)
     */
    public ArrayList<String> getPhotos() {
        return photos;
    }

    /**
     * sets the array of photo ids associated to the gamecode
     * @param photos an array of strings (photo ids)
     */
    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    /**
     * gets the array of strings of the ids of all the players that have scanned the gamecode
     * @return an array of strings (player unique ids)
     */
    public ArrayList<String> getOwners() {
        return owners;
    }

    /**
     * sets the array owners to a new array
     * @param owners an array of strings (unique ids of the players that have scanned the code)
     */
    public void setOwners(ArrayList<String> owners) {
        this.owners = owners;
    }

    /**
     * gets an array of strings of ids of all the comments associated with the gamecode
     * @return an array of strings (comment ids)
     */
    public ArrayList<String> getComments() {
        return comments;
    }

    /**
     * sets the array comments to a new array
     * @param comments an array of strings (comment ids)
     */
    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    /**
     * returns the number of comments associated with the gamecode
     * @return an integer
     */
    public int getCommentAmount() {
        return comments.size();
    }

    /**
     * returns the id of a single comment associated with the gamecode
     * @param position the index of the id in the array of comments
     * @return a string (comment id)
     */
    public String getComment(int position){
        return comments.get(position);
    }

    /**
     * adds a comment id to the array of comments associated with the gamecode
     * @param commentId a string (the id of the comment to be added)
     */
    public void addComment(String commentId) {comments.add(commentId);}

    /**
     * gets the latitude of the gamecode
     * @return a double
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * sets the latitude of the gamecode
     * @param latitude a double (the latitude that is to be set)
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * gets the longitude of the gamecode
     * @return a double
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * sets the longitude of the gamecode
     * @param longitude a double (the longitude that is to be set)
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * prints all the attributes of the gamecode
     */
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

    /**
     * for making the gamecode parcelable
     * @return returns 0
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * to make the gamecode parcelable
     * @param parcel the parcel that is to be written to
     * @param i an integer, not used
     */
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
