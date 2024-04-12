package com.group.petSitter.domain.pet.service.request;

public record FindPetDetailCommand(Long petId) {

    public static FindPetDetailCommand of(Long petId){
        return new FindPetDetailCommand(petId);
    }
}
