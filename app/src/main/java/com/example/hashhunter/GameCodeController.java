package com.example.hashhunter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
//https://stackoverflow.com/questions/10071502/read-writing-arrays-of-parcelable-objects
//Use this list to help me implement my parcelable array

/**
 * a controller for the gamecode class
 */
public class GameCodeController implements Parcelable {
    private GameCode TheGameCode;
    private String title; // title of the code
    private String code; // string representation of the code

    private Integer points; // points of code
    private ArrayList<String> photos; // id of photos objects
    private ArrayList<String> owners; // username
    private ArrayList<String> comments; // id of comment object
    private Double latitude;
    private Double longitude;
    private String dataBasePointer;

    /**
     * an empty constructor, to be used with firebase
     */
    public GameCodeController(){}

    /**
     * constructor that uses a gamecode object to create the controller
     * @param myGameCode the gamecode object
     */
    public GameCodeController(GameCode myGameCode) {
        this.TheGameCode = myGameCode;
    }

    /**
     * for making the gamecodecontroller parcelable
     * @param in the parcel that is to be read from
     */
    protected GameCodeController(Parcel in) {
        TheGameCode = in.readParcelable(GameCode.class.getClassLoader());
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
        dataBasePointer = in.readString();
    }

    public static final Creator<GameCodeController> CREATOR = new Creator<GameCodeController>() {
        @Override
        public GameCodeController createFromParcel(Parcel in) {
            return new GameCodeController(in);
        }

        @Override
        public GameCodeController[] newArray(int size) {
            return new GameCodeController[size];
        }
    };

    /**
     * returns the points that the gamecode is worth
     * @return points
     */
    public Integer getPoints() {
        return this.points;
    }

    /**
     * gets the title of the gamecode
     * @return the title of the gamecode
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * gets the number of owners of the gamecode
     * @return number of owners
     */
    public int getOwnerAmount(){
        return this.owners.size();
    }

    /**
     * returns the id of a single comment associated with the gamecode
     * @param position the index of the id in the array of comments
     * @return a string (comment id)
     */
    public String getComment(int position){
        if (comments.size() > position + 1) {
            return  comments.get(position);
        }
        else{
            return null;
        }
    }

    /**
     * gets the gamecode that the controller uses
     * @return a gamecode object
     */
    public GameCode getGameCode(){
        return TheGameCode;
    }

    /**
     * gets the array of strings of the ids of all the players that have scanned the gamecode
     * @return an array of strings (player unique ids)
     */
    public ArrayList<String> getOwners(){
        return this.owners;
    }

    /**
     * adds a comment id to the array of comments associated with the gamecode
     * @param theComment a string (the id of the comment to be added)
     */
    public void addComment(Comment theComment) {

        //Get the database reference

        FirestoreController dbController = new FirestoreController();

        String commentCode = dbController.postComment(theComment);

        dbController.updateGameCodeComment(dataBasePointer, commentCode);

        TheGameCode.addComment(commentCode);



    }

    /**
     * gets an array of strings of ids of all the comments associated with the gamecode
     * @return an array of strings (comment ids)
     */
    public ArrayList<String> getComments(){
        return this.comments;
    }

    /**
     * sets the string dataBasePointer
     * @param pointer the string that is to be set
     */
    public void setDataBasePointer(String pointer){
        dataBasePointer = pointer;
    }

    /**
     * sets the attributes for the controller using the gamecode
     */
    public void SyncController(){
        this.title = TheGameCode.getTitle(); // title of the code
        this.code = TheGameCode.getCode(); // string representation of the code

        this.points = TheGameCode.getPoints(); // points of code
        this.photos = TheGameCode.getPhotos(); // id of photos objects
        this.owners = TheGameCode.getOwners(); // username
        this.comments = TheGameCode.getComments(); // id of comment object
        this.latitude = TheGameCode.getLatitude(); // longitude
        this.longitude = TheGameCode.getLongitude(); // latitude
    }


    /**
     * gets the array of strings of the ids of all the photos associated with the gamecode
     * @return an array of strings (photo ids)
     */
    public ArrayList<String> getPhotos(){
        return this.photos;
    }

    /**
     * gets the string dataBasePointer
     * @return a string
     */
    public String getDataBasePointer(){
        return this.dataBasePointer;
    }

    /**
     * used to make the gamecode controller parcelable
     * @return returns 0
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * gets the latitude of the gamecode
     * @return a double
     */
    public Double getLatitude(){
        return (Double)this.latitude;
    }

    /**
     * gets the longitude of the gamecode
     * @return a double
     */
    public Double getLongitude(){
        return (Double) this.longitude;
    }

    /**
     * to make the gamecode controller parcelable
     * @param parcel the parcel that is to be written to
     * @param i an integer, not used
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(TheGameCode, i);
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

        parcel.writeString(dataBasePointer);
    }
}
