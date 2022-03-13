package com.example.hashhunter;

import android.os.Parcel;
import android.os.Parcelable;

import org.w3c.dom.Comment;

import java.util.ArrayList;
//https://stackoverflow.com/questions/10071502/read-writing-arrays-of-parcelable-objects
//Use this list to help me implement my parcelable array
public class QRItem implements Parcelable {
    private int treePic;
    private ArrayList<Integer> locationPics;
    private ArrayList<QRComment> QRcomments;
    private String strQRCode;
    private String QRLocationText;
    private String QRPoints;
    private String QRTitle;

    public QRItem(int tree, String points, String title, ArrayList<Integer> locPics, ArrayList<QRComment> comments) {
        treePic = tree;
      //  locationPic = locPic;
       /// QRLocationText = locText;
        QRPoints = points;
        QRTitle = title;
        locationPics = locPics;
        QRcomments = comments;
    }

    protected QRItem(Parcel in) {
        treePic = in.readInt();
        strQRCode = in.readString();
        QRLocationText = in.readString();
        QRPoints = in.readString();
        QRTitle = in.readString();
        QRcomments = new ArrayList<QRComment>();
        in.readList(QRcomments, getClass().getClassLoader());
        locationPics = new ArrayList<Integer>();
        in.readList(locationPics, getClass().getClassLoader());
    }

    public static final Creator<QRItem> CREATOR = new Creator<QRItem>() {
        @Override
        public QRItem createFromParcel(Parcel in) {
            return new QRItem(in);
        }

        @Override
        public QRItem[] newArray(int size) {
            return new QRItem[size];
        }
    };

    public String getQRLocation() {
        return QRLocationText;
    }

    public int getTreePic() {
        return treePic;
    }


    public String getQRPoints() {
        return QRPoints;
    }

    public String getQRTitle() {
        return QRTitle;
    }
    public ArrayList<Integer> getLocationPics(){
        return locationPics;
    }
    public ArrayList<QRComment> getQRComments(){
        return QRcomments;
    }
    public String getStrQRCode() {
        return strQRCode;
    }

    public String getQRLocationText() {
        return QRLocationText;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(treePic);
        parcel.writeString(strQRCode);
        parcel.writeString(QRLocationText);
        parcel.writeString(QRPoints);
        parcel.writeString(QRTitle);
        parcel.writeList(QRcomments);
        parcel.writeList(locationPics);


    }
}
