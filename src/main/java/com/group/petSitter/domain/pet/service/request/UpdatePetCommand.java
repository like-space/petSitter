package com.group.petSitter.domain.pet.service.request;

import com.group.petSitter.domain.pet.controller.request.UpdatePetRequest;

public record UpdatePetCommand(Long petId,String petName) {

    public static UpdatePetCommand of(Long petId,UpdatePetRequest updatePetRequest){
        return new UpdatePetCommand(petId,updatePetRequest.petName());
    }
}
