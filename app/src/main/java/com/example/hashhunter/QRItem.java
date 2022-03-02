package com.example.hashhunter;

public class QRItem {
    private int treePic;
    private String qrPointText;
    private String qrName;

    public QRItem(int tree, String points, String name) {
        treePic = tree;
        qrPointText = points;
        qrName = name;

    }

    public int getImageRsrc() {
        return treePic;
    }

    public String getQrPointText(){
        return qrPointText;
    }
    public String getQrName() {
        return qrName;
    }




}
