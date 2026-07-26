package com.example.exception;

public class InvalidMessagePostedUser extends RuntimeException{
    public InvalidMessagePostedUser (String postedBy) {
        super ("Message Posted By Invalid User : " + postedBy);
    }
}
