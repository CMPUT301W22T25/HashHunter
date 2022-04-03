package com.example.hashhunter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CommentControllerTest {
    CommentController testCommentController;

    /**
     * create CommentController object to setup the rest of the testing
     */
    @Before
    public void createComment() {
        testCommentController = new CommentController(new Comment("testUser", "this is a fake comment to test"));
    }

    /**
     * test getter of owner attribute
     */
    @Test
    public void checkOwnerGetter() {
        assertEquals("testUser", testCommentController.getOwner());
    }

    /**
     * test getter of comment attribute
     */
    @Test
    public void checkCommentGetter() {
        assertEquals("this is a fake comment to test", testCommentController.getComment());
    }
}
