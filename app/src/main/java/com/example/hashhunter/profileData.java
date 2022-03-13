package com.example.hashhunter;

import java.util.ArrayList;

public class profileData {
    String username;
    String totalPoints;
    String totalTrees;
    String email;
    private int loginCode;
    private int profileCode;
    ArrayList<QRItem> QRItemList;
    public String getUsername() {
        return username;
    }
    public String getTotalPoints(){
        return totalPoints;
    }

    public String getEmail() {
        return email;
    }
    public String getTotalTrees() {
        return totalTrees;
    }
    //Don't know if this is cohesive enough as I am adding the QR Items to the list and I feel like this might go on a different class
    //But at the same time I also feel like I would essentially be doing the exact same thing, but through a different class
    //Like assume I had a class called QRItemList
    // If we want a comment we need to access the profileData class
    //And then access the QRItemList as otherwise it would not be neat
    //But in this case I would not increase cohesion as my other class would be doing the same thing
    //And I would increase coupling as this class is now connected to yet another class which is QRList

    //Hence why I decided to make it this way
    public QRItem getQRItem(Integer pos){
        //Get a QR item and in case the qr is not in the list return null
        if ((pos+1) <= QRItemList.size()) {
            return QRItemList.get(pos);
        }
        else {
            return null;
        }
    }
    public void addQRItem(QRItem item) {
        QRItemList.add(item);
    }
    public void RemoveQRItem(QRItem item){
        return;
       // for (int i = 0; i < QRItemList.size(); i++){
        //    QRItem myItem = QRItemList.get(i);

    }
}



