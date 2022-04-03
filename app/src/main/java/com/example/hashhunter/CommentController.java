package com.example.hashhunter;

/**
 * Controller for comment class, will be useful once we do more stuff with comment
 */
public class CommentController {
    Comment comment;

    /**
     * constructor for CommentController
     * @param myComment comment object
     */
    CommentController(Comment myComment){
        this.comment = myComment;
    }

    /**
     * getter for creator of comment
     * @return
     */
    public String getOwner(){
        return comment.getOwner();
    }

    /**
     * getter for comment content
     * @return
     */
    public String getComment(){
        return comment.getComment();
    }
}
