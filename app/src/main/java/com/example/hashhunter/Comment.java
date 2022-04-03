package com.example.hashhunter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class to represent a comment of qr code
 */
public class Comment implements Parcelable {
    String owner;
    String comment;

    /**
     * Empty constructor for comment
     */
    public Comment() {}

    /**
     * Constructor to create comment with specified name and content
     * @param name
     * @param comment
     */
    public Comment(String name, String comment) {
        this.owner = name;
        this.comment = comment;
    }

    /**
     * Constructor to crate comment based on parcelable object
     * @param in
     */
    protected Comment(Parcel in) {
        owner = in.readString();
        comment = in.readString();
    }

    /**
     * Gets string representation of comment
     * @return comment content
     */
    public String getComment() {
        return comment;
    }

    /**
     * Gets username
     * @return owner the person creating the comment
     */
    public String getOwner(){
        return owner;
    }

    /**
     * Utility functions to allow comment to be parcellable
     */
    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Convert comment into parcel
     * @param parcel
     * @param i
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(owner);
        parcel.writeString(comment);
    }
}
