package com.example.hashhunter;

public class QRItem {
    private int treePic;
    private int locationPic;

    private String strQRCode;
    private String QRLocationText;
    private String QRPoints;
    private String QRTitle;

    public QRItem(int tree, int locPic, String code, String locText, String points, String title) {
        treePic = tree;
        QRPoints = points;
        QRTitle = title;

    }
    public String getQRLocation(){
        return QRLocationText;
    }
    public int getTreeImage() {
        return treePic;
    }

    public int getLocationPic() {
        return locationPic;
    }
    public String getQRPoints(){
        return QRPoints;
    }
    public String getQRTitle() {
        return QRTitle;
    }




}
