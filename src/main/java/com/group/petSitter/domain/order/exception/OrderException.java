package com.group.petSitter.domain.order.exception;

public abstract class OrderException extends RuntimeException {
    public OrderException(String message) {
        super(message);
    }
}
