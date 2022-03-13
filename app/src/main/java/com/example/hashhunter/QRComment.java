package com.example.hashhunter;

import android.os.Parcel;
import android.os.Parcelable;

public class QRComment implements Parcelable {
    String userName;
    String comment;
    //Constructor for comments
    QRComment(String name, String comment) {
        this.userName = name;
        this.comment = comment;
    }

    protected QRComment(Parcel in) {
        userName = in.readString();
        comment = in.readString();
    }

    public static final Creator<QRComment> CREATOR = new Creator<QRComment>() {
        @Override
        public QRComment createFromParcel(Parcel in) {
            return new QRComment(in);
        }

        @Override
        public QRComment[] newArray(int size) {
            return new QRComment[size];
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
