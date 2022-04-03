package com.example.hashhunter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import android.os.Parcel;

/**
 * Testing for Comment class
 * Need to test parcelable functionality from androidTest if not using mockito
 */
public class CommentTest {
    Comment testComment;

    /**
     * create Comment object to setup the rest of the testing
     */
    @Before
    public void createComment() {
        testComment = new Comment("testUser", "this is a fake comment to test");
    }

    /**
     * test getter of owner attribute
     */
    @Test
    public void checkOwnerGetter() {
        assertEquals("testUser", testComment.getOwner());
    }

    /**
     * test getter of comment attribute
     */
    @Test
    public void checkCommentGetter() {
        assertEquals("this is a fake comment to test", testComment.getComment());
    }
}
