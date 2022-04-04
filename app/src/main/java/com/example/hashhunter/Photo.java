package com.example.hashhunter;

/**
 * A model class that represents a photo object, used as Schema to the database
 */
public class Photo {
    private String url;
    private String owner;

    /**
     * an empty constructor, to be used with firebase
     */
    public Photo(){}

    /**
     * a constructor that uses a string url
     * @param url the string url of the photo
     */
    public Photo(String url) {
        this.url = url;
    }

    /**
     * a constructor that uses a string url and a string owner
     * @param url the string url of the photo
     * @param owner the string username of the player that owns the photo
     */
    public Photo(String url, String owner) {
        this.owner = owner;
        this.url = url;
    }

    /**
     * gets the url of the photo
     * @return the string url of the photo
     */
    public String getUrl() {
        return url;
    }

    /**
     * sets the url of the photo
     * @param url the string url that is to be set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * gets the username of the player that owns the photo
     * @return string username
     */
    public String getOwner() {
        return owner;
    }

    /**
     * sets the username of the player that owns the photo
     * @param owner string username of the player that is to be set as the owner of the photo
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }
}
