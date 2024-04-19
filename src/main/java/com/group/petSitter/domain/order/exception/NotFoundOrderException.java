package com.group.petSitter.domain.order.exception;

public class NotFoundOrderException extends OrderException {

    public NotFoundOrderException(final String message) {
        super(message);
    }
}
