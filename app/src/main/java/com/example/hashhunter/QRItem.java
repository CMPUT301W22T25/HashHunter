package com.example.hashhunter;

public class QRItem {
    private int treePic;
    private int qrPoints;
    private String qrName;

    public QRItem(int tree, int points, String name) {
        treePic = tree;
        qrPoints = points;
        qrName = name;

    }

    public int getImageRsrc() {
        return treePic;
    }

    public int getQrPoints(){
        return qrPoints;
    }
    public String getQrName() {
        return qrName;
    }




}
