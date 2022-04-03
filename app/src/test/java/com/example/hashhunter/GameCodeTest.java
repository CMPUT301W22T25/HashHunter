package com.example.hashhunter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;

public class GameCodeTest {
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

    @Test
    public void testGetTitle() {
        GameCode gc = mockGameCode();
        String title = gc.getTitle();
        assertEquals("gcTitle", title);
    }

    @Test
    public void testSetTitle() {
        GameCode gc = mockGameCode();
        String title = gc.getTitle();
        assertEquals("gcTitle", title);
        title = "newGCTitle";
        gc.setTitle(title);
        assertEquals(title, gc.getTitle());
    }

    @Test
    public void testGetCode() {
        GameCode gc = mockGameCode();
        String code = gc.getCode();
        assertEquals("gcCode", code);
    }

    @Test
    public void testSetCode() {
        GameCode gc = mockGameCode();
        String code = gc.getCode();
        assertEquals("gcCode", code);
        code = "newGCCode";
        gc.setCode(code);
        assertEquals(code, gc.getCode());
    }

    @Test
    public void testGetPoints() {
        GameCode gc = mockGameCode();
        Integer p = gc.getPoints();
        assertEquals(Integer.valueOf(10), p);
    }

    @Test
    public void testSetPoints() {
        GameCode gc = mockGameCode();
        Integer p = gc.getPoints();
        assertEquals(Integer.valueOf(10), p);
        p = 20;
        gc.setPoints(p);
        assertEquals(p, gc.getPoints());
    }

    @Test
    public void testGetPhotos() {
        GameCode gc = mockGameCode();
        ArrayList<String> photos = gc.getPhotos();
        assertEquals(1, photos.size());
        assertTrue(photos.contains("supposedToBePhotoID"));
    }

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

    @Test
    public void testGetOwners() {
        GameCode gc = mockGameCode();
        ArrayList<String> owners = gc.getOwners();
        assertEquals(1, owners.size());
        assertTrue(owners.contains("player1"));
    }

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

    @Test
    public void testGetComments() {
        GameCode gc = mockGameCode();
        ArrayList<String> comments = gc.getComments();
        assertEquals(0, comments.size());
    }

    @Test
    public void testSetComments() {
        GameCode gc = mockGameCode();
        ArrayList<String> comments = gc.getComments();
        assertEquals(0, comments.size());
        comments.add("commentID");
        gc.setComments(comments);
        assertEquals(1, gc.getComments().size());
    }

    @Test
    public void testGetCommentAmount() {
        GameCode gc = mockGameCode();
        Integer commentAmount = gc.getCommentAmount();
        assertEquals(Integer.valueOf(0), commentAmount);
    }

    @Test
    public void testAddComment() {
        GameCode gc = mockGameCode();
        assertEquals(0, gc.getCommentAmount());
        gc.addComment("commentID");
        assertEquals(1, gc.getCommentAmount());
        assertTrue(gc.getComments().contains("commentID"));
    }

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

    @Test
    public void testGetLatitude() {
        GameCode gc = mockGameCode();
        Double lat = gc.getLatitude();
        assertEquals(Double.valueOf(-35), lat);
    }

    @Test
    public void testSetLatitude() {
        GameCode gc = mockGameCode();
        Double lat = gc.getLatitude();
        assertEquals(Double.valueOf(-35), lat);
        lat = Double.valueOf(50);
        gc.setLatitude(lat);
        assertEquals(Double.valueOf(50), gc.getLatitude());
    }

    @Test
    public void testGetLongitude() {
        GameCode gc = mockGameCode();
        Double lon = gc.getLongitude();
        assertEquals(Double.valueOf(55), lon);
    }

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
