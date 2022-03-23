package com.example.hashhunter;

public class CommentController {
    Comment comment;
    CommentController(Comment myComment){
        this.comment = myComment;
    }

    public String getOwner(){
        return comment.getOwner();
    }
    public String getComment(){
        return comment.getComment();
    }
}
