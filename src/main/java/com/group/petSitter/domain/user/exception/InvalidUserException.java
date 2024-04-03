package com.group.petSitter.domain.user.exception;

public class InvalidUserException extends UserException{
    public InvalidUserException(final String message) {
        super(message);
    }
}