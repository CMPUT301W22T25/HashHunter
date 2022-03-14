package com.example.hashhunter;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    String userName;
    String comment;
    //Constructor for comments
    Comment(String name, String comment) {
        this.userName = name;
        this.comment = comment;
    }

    protected Comment(Parcel in) {
        userName = in.readString();
        comment = in.readString();
    }

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

    //Gets string representation of comment
    public String getComment() {
        return comment;
    }
    //Gets username
    public String getUserName(){
        return userName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userName);
        parcel.writeString(comment);
    }
}
