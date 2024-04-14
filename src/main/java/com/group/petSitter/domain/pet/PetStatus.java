package com.group.petSitter.domain.pet;

import lombok.Getter;

@Getter
public enum PetStatus {
    PENDING("pending"),
    DELIVERING("delivering"),
    COMPLETED("completed"),
    CANCELED("canceled");

    private final String value;

    PetStatus(String value) {
        this.value = value;
    }
}
