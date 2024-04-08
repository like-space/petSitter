package com.group.petSitter.domain.pet.service.request;

import com.group.petSitter.domain.pet.controller.request.CreatePetRequest;

public record CreatePetCommand(
        Long user_id,
        CreatePetRequest createPetRequest
)
{
    public static CreatePetCommand of(
            Long user_id
            ,CreatePetRequest createPetRequest)
    {
        return new CreatePetCommand(user_id,createPetRequest);
    }
}
