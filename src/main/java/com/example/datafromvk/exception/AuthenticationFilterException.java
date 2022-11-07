package com.example.datafromvk.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationFilterException extends AuthenticationException {
    public AuthenticationFilterException(String msg) {
        super(msg);
    }
}
