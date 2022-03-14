package com.example.hashhunter;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
//https://stackoverflow.com/questions/10071502/read-writing-arrays-of-parcelable-objects
//Use this list to help me implement my parcelable array
public class GameCodeController {
    private GameCode TheGameCode;
    public GameCodeController(GameCode myGameCode) {
        this.TheGameCode = myGameCode;
    }

    public Integer getQRPoints() {
        return TheGameCode.getPoints();
    }

    public String getQRTitle() {
        return TheGameCode.getTitle();
    }

    public Comment getComment(int position){
        if (TheGameCode.getCommentAmount() > position + 1) {
            return (Comment) TheGameCode.getComment(position);
        }
        else{
            return null;
        }
    }
    public GameCode getGameCode(){
        return TheGameCode;
    }
    public void makeNewComment(String User, String content) {
        TheGameCode.addComment((org.w3c.dom.Comment) new Comment(User, content));

    }


}
