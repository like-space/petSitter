package com.group.petSitter.domain.pet.service.request;

import com.group.petSitter.domain.pet.controller.request.CreatePetRequest;

import java.util.List;

import static com.group.petSitter.domain.pet.controller.request.CreatePetRequest.*;

public record CreatePetCommand(
        Long user_id,
        List<CreatePetInfo> createPetInfo
)
{
    public static CreatePetCommand of(
            Long user_id
            ,CreatePetRequest createPetRequest)
    {
        return new CreatePetCommand(user_id,createPetRequest.createPetsRequest());
    }
}
