package com.group.petSitter.domain.order;

public enum OrderStatus {
    PENDING("pending"),
    PAYING("paying"),
    PAYED("payed");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
