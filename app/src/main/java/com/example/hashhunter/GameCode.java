package com.example.hashhunter;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import org.w3c.dom.Comment;

import java.util.ArrayList;
public class GameCode implements Parcelable {
    private String qrCode;
    private String title;
    private Photo photo;
    private Integer treePic;
    private Location location;
    private Integer numPlayers;
    private Integer points;
    private ArrayList<Comment> commentList;

    // DEBUG: I think constructor doesn't need void keyword, this might cause problem later
    // I removed the void keyword while resolving a merge conflict
    public GameCode(String qrCode, String title, Integer treePic, Photo photo, Location location, Integer numPlayers,
            Integer points, ArrayList<Comment> commentList) {
        this.qrCode = qrCode;
        this.photo = photo;
        this.location = location;
        this.numPlayers = numPlayers;
        this.points = points;
        this.commentList = commentList;
        this.title = title;
        this.treePic = treePic;
    }

    /**
     * Getter for stored photo
     * @return
     * The stored photo
     */

//    public Photo getPhoto() {
//        return this.photo;
//    }
    /**
     * Setter for the photo
     * @param p
     * The photo that is to be set as the stored photo
     */

//    public void setPhoto(Photo p) {
//        this.photo = p;
//    }

    /**
     * Getter for the stored location
     * @return
     * The location stored
     */
//    public Location getLocation() {
//        return this.location;
//    }

    /**
     * Setter for the location
     * @param l
     * The location that is to be set as the stored location
     */
//    public void setLocation(Location l) {
//        this.location = l;
//    }

    protected GameCode(Parcel in) {
        qrCode = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
        if (in.readByte() == 0) {
            numPlayers = null;
        } else {
            numPlayers = in.readInt();
        }
        if (in.readByte() == 0) {
            points = null;
        } else {
            points = in.readInt();
        }
        title = in.readString();
        commentList = new ArrayList<>();
        in.readList(commentList, getClass().getClassLoader());

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(qrCode);
        parcel.writeParcelable(location, i);
        if (numPlayers == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(numPlayers);
        }
        if (points == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(points);
        }
        parcel.writeString(title);
        parcel.writeList(commentList);
    }
    public String getTitle(){
        return title;
    }
    public int getTreePic(){
        return treePic;
    }
    public int getCommentAmount() {
        return commentList.size();
    }
    public Comment getComment(int position){
        return commentList.get(position);
    }
    public void addComment(Comment c){
        commentList.add(c);
    }
    public void setComment(int userName, String commentContent){

    }


    /**
     * Stores a comment that is about the QR code
     * @param c
     * The comment to be stored
     */
//    public void storeComment(Comment c) {
//        this.commentList.add(c);
//    }
}
