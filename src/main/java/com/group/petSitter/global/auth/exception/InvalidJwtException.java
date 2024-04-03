package com.group.petSitter.global.auth.exception;

public class InvalidJwtException extends AuthException {

    public InvalidJwtException(String message) {
        super(message);
    }
}
