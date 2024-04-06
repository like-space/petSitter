package com.group.petSitter.domain.pet;

import lombok.Getter;

@Getter
public enum PetStatus {
    PENDING("pending"),
/*    PAYING("paying"),
    PAYED("payed"),*/

    DELIVERING("delivering"),
    COMPLETED("completed"),
    CANCELED("canceled");

    private final String value;

    PetStatus(String value) {
        this.value = value;
    }
}
