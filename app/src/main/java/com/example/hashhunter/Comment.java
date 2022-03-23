package com.example.hashhunter;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    String owner;
    String comment;
    //Constructor for comments
    public Comment() {

    }
    Comment(String name, String comment) {
        this.owner = name;
        this.comment = comment;
    }

    protected Comment(Parcel in) {
        owner = in.readString();
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
    public String getOwner(){
        return owner;
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(owner);
        parcel.writeString(comment);
    }
}
