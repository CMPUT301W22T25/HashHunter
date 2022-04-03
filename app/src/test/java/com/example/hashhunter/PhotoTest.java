package com.example.hashhunter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PhotoTest {
    Photo testPhoto;

    /**
     * create Comment object to setup the rest of the testing
     */
    @Before
    public void createPhoto() {
        testPhoto = new Photo("www.google.com", "alphabet");
    }

    /**
     * test getter and setterof url attribute
     */
    @Test
    public void checkUrlSetterGetter() {
        assertEquals("www.google.com", testPhoto.getUrl());
        testPhoto.setUrl("www.facebook.com");
        assertEquals("www.facebook.com", testPhoto.getUrl());
    }

    /**
     * test getter and setter of owner attribute
     */
    @Test
    public void checkOwnerSetterGetter() {
        assertEquals("alphabet", testPhoto.getOwner());
        testPhoto.setOwner("meta");
        assertEquals("meta", testPhoto.getOwner());
    }



}
