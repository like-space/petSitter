package com.group.petSitter.domain.pet.service.response;

import com.group.petSitter.domain.pet.Pet;

import java.util.List;
import java.util.stream.Collectors;

public record FindPetsByUserResponse(
        List<FindPetResponse> pets
)
{
    public static FindPetsByUserResponse from(List<Pet> petList){
        List<FindPetResponse> FindPetsResponse =
                petList.stream()
                        .map(FindPetResponse::from)
                        .collect(Collectors.toList());
        return new FindPetsByUserResponse(FindPetsResponse);
    }

    public record FindPetResponse(String petName){

        public static FindPetResponse from(Pet pet)
        {
            return new FindPetResponse(pet.getPetName());
        }

    }

}
