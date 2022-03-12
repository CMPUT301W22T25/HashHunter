package com.example.hashhunter;

public class QRComment {
    String userName;
    String comment;
    //Constructor for comments
    QRComment(String name, String comment) {
        this.userName = name;
        this.comment = comment;
    }
    //Gets string representation of comment
    public String getComment() {
        return comment;
    }
    //Gets username
    public String getUserName(){
        return userName;
    }




}
