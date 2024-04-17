package com.group.petSitter.domain.pet.exception;

public class NotFoundPetException extends PetException {

    public NotFoundPetException(final String message) {
        super(message);
    }
}
