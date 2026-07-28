package com.example.exception;

public class InvalidMessageText extends RuntimeException {
    public InvalidMessageText (String message) {
        super (message);
    }
}
