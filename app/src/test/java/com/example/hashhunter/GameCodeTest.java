package com.example.hashhunter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;

public class GameCodeTest {
    /**
     * Creates a mock gamecode for testing purposes
     * @return
     */
    public GameCode mockGameCode() {
        String title = "gcTitle";
        String code = "gcCode";
        Integer points = 10;
        String photo = "supposedToBePhotoID";
        String owner = "player1";
        Double latitude = Double.valueOf(-35);
        Double longitude = Double.valueOf(55);

        GameCode gc = new GameCode(title, code, points, photo, owner, latitude, longitude);
        return gc;
    }

    /**
     * tests the get title method
     */
    @Test
    public void testGetTitle() {
        GameCode gc = mockGameCode();
        String title = gc.getTitle();
        assertEquals("gcTitle", title);
    }

    /**
     * tests the set title method
     */
    @Test
    public void testSetTitle() {
        GameCode gc = mockGameCode();
        String title = gc.getTitle();
        assertEquals("gcTitle", title);
        title = "newGCTitle";
        gc.setTitle(title);
        assertEquals(title, gc.getTitle());
    }

    /**
     * tests the get code method
     */
    @Test
    public void testGetCode() {
        GameCode gc = mockGameCode();
        String code = gc.getCode();
        assertEquals("gcCode", code);
    }

    /**
     * tests the set code method
     */
    @Test
    public void testSetCode() {
        GameCode gc = mockGameCode();
        String code = gc.getCode();
        assertEquals("gcCode", code);
        code = "newGCCode";
        gc.setCode(code);
        assertEquals(code, gc.getCode());
    }

    /**
     * tests the get points method
     */
    @Test
    public void testGetPoints() {
        GameCode gc = mockGameCode();
        Integer p = gc.getPoints();
        assertEquals(Integer.valueOf(10), p);
    }

    /**
     * tests the set points method
     */
    @Test
    public void testSetPoints() {
        GameCode gc = mockGameCode();
        Integer p = gc.getPoints();
        assertEquals(Integer.valueOf(10), p);
        p = 20;
        gc.setPoints(p);
        assertEquals(p, gc.getPoints());
    }

    /**
     * tests the get photos method
     */
    @Test
    public void testGetPhotos() {
        GameCode gc = mockGameCode();
        ArrayList<String> photos = gc.getPhotos();
        assertEquals(1, photos.size());
        assertTrue(photos.contains("supposedToBePhotoID"));
    }

    /**
     * tests the set photos method
     */
    @Test
    public void testSetPhotos() {
        GameCode gc = mockGameCode();
        ArrayList<String> photos = gc.getPhotos();
        assertEquals(1, photos.size());
        assertTrue(photos.contains("supposedToBePhotoID"));
        photos.add("newPhotoID");
        gc.setPhotos(photos);
        assertEquals(2, gc.getPhotos().size());
    }

    /**
     * tests the get owners method
     */
    @Test
    public void testGetOwners() {
        GameCode gc = mockGameCode();
        ArrayList<String> owners = gc.getOwners();
        assertEquals(1, owners.size());
        assertTrue(owners.contains("player1"));
    }

    /**
     * tests the set owners method
     */
    @Test
    public void testSetOwners() {
        GameCode gc = mockGameCode();
        ArrayList<String> owners = gc.getOwners();
        assertEquals(1, owners.size());
        assertTrue(owners.contains("player1"));
        owners.add("player2");
        gc.setOwners(owners);
        assertEquals(2, gc.getOwners().size());
    }

    /**
     * tests the get comments method
     */
    @Test
    public void testGetComments() {
        GameCode gc = mockGameCode();
        ArrayList<String> comments = gc.getComments();
        assertEquals(0, comments.size());
    }

    /**
     * tests the set comments method
     */
    @Test
    public void testSetComments() {
        GameCode gc = mockGameCode();
        ArrayList<String> comments = gc.getComments();
        assertEquals(0, comments.size());
        comments.add("commentID");
        gc.setComments(comments);
        assertEquals(1, gc.getComments().size());
    }

    /**
     * tests the get comment amount method
     */
    @Test
    public void testGetCommentAmount() {
        GameCode gc = mockGameCode();
        Integer commentAmount = gc.getCommentAmount();
        assertEquals(Integer.valueOf(0), commentAmount);
    }

    /**
     * tests the add comment method
     */
    @Test
    public void testAddComment() {
        GameCode gc = mockGameCode();
        assertEquals(0, gc.getCommentAmount());
        gc.addComment("commentID");
        assertEquals(1, gc.getCommentAmount());
        assertTrue(gc.getComments().contains("commentID"));
    }

    /**
     * tests the get comment method
     */
    @Test
    public void testGetComment() {
        GameCode gc = mockGameCode();
        assertEquals(0, gc.getCommentAmount());
        gc.addComment("commentID");
        assertEquals(1, gc.getCommentAmount());
        assertTrue(gc.getComments().contains("commentID"));
        String comment = gc.getComment(0);
        assertEquals("commentID", comment);
    }

    /**
     * tests the get latitude method
     */
    @Test
    public void testGetLatitude() {
        GameCode gc = mockGameCode();
        Double lat = gc.getLatitude();
        assertEquals(Double.valueOf(-35), lat);
    }

    /**
     * tests the set latitude method
     */
    @Test
    public void testSetLatitude() {
        GameCode gc = mockGameCode();
        Double lat = gc.getLatitude();
        assertEquals(Double.valueOf(-35), lat);
        lat = Double.valueOf(50);
        gc.setLatitude(lat);
        assertEquals(Double.valueOf(50), gc.getLatitude());
    }

    /**
     * tests the get longitude method
     */
    @Test
    public void testGetLongitude() {
        GameCode gc = mockGameCode();
        Double lon = gc.getLongitude();
        assertEquals(Double.valueOf(55), lon);
    }

    /**
     * tests the set longitude method
     */
    @Test
    public void testSetLongitude() {
        GameCode gc = mockGameCode();
        Double lon = gc.getLongitude();
        assertEquals(Double.valueOf(55), lon);
        lon = Double.valueOf(100);
        gc.setLongitude(lon);
        assertEquals(Double.valueOf(100), gc.getLongitude());
    }

}
