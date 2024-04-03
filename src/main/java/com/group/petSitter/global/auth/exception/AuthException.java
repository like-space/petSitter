package com.group.petSitter.global.auth.exception;

public abstract class AuthException extends RuntimeException {

    protected AuthException(String message) {
        super(message);
    }
}
