package com.example.hashhunter;

/**
 * A model class that represents a photo object, used as Schema to the database
 */
public class Photo {
    private String url;
    private String owner;

    public Photo(String url) {
        this.url = url;
    }

    public Photo(String url, String owner) {
        this.owner = owner;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
