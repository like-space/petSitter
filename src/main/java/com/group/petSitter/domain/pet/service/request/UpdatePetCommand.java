package com.group.petSitter.domain.pet.service.request;

import com.group.petSitter.domain.pet.controller.request.UpdatePetRequest;

public record UpdatePetCommand(UpdatePetRequest updatePetRequest) {

    public static UpdatePetCommand of(UpdatePetRequest updatePetRequest){
        return new UpdatePetCommand(updatePetRequest);
    }
}
