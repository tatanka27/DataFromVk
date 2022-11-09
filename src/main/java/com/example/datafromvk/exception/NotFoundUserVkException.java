package com.example.datafromvk.exception;

public class NotFoundUserVkException extends RuntimeException {
    public NotFoundUserVkException(String message) {
        super(message);
    }
}