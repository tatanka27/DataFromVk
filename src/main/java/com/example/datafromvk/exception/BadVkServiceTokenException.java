package com.example.datafromvk.exception;

public class BadVkServiceTokenException extends RuntimeException {
    public BadVkServiceTokenException(String message) {
        super(message);
    }
}