package com.example.hashhunter;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
//https://stackoverflow.com/questions/10071502/read-writing-arrays-of-parcelable-objects
//Use this list to help me implement my parcelable array
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
    public GameCodeController(){}

    public GameCodeController(GameCode myGameCode) {
        this.TheGameCode = myGameCode;
    }

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

    public Integer getPoints() {
        return this.points;
    }

    public String getTitle() {
        return this.title;
    }

    public String getComment(int position){
        if (comments.size() > position + 1) {
            return  comments.get(position);
        }
        else{
            return null;
        }
    }
    public GameCode getGameCode(){
        return TheGameCode;
    }
    public void makeNewComment(String commentCode) {
        TheGameCode.addComment(commentCode);

    }
    public void SyncController(){
        this.title = TheGameCode.getTitle(); // title of the code
        this.code = TheGameCode.getCode(); // string representation of the code

        this.points = TheGameCode.getPoints(); // points of code
        this.photos = TheGameCode.getPhotos(); // id of photos objects
        this.owners = TheGameCode.getOwners(); // username
        this.comments = TheGameCode.getComments(); // id of comment object
        this.latitude = TheGameCode.getLatitude();
        this.longitude = TheGameCode.getLongitude();
    }
    public ArrayList<String> getPhotos(){
        return this.photos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

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
    }
}
