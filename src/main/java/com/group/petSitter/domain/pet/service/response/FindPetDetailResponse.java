package com.group.petSitter.domain.pet.service.response;

import com.group.petSitter.domain.pet.Pet;

public record FindPetDetailResponse(String petName) {
    public static FindPetDetailResponse from(Pet pet){
        return new FindPetDetailResponse(pet.getPetName());
    }
}
