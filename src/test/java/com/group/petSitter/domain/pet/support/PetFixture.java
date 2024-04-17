package com.group.petSitter.domain.pet.support;

import com.group.petSitter.domain.pet.Pet;
import com.group.petSitter.domain.pet.PetStatus;
import com.group.petSitter.domain.pet.controller.request.CreatePetRequest;
import com.group.petSitter.domain.pet.controller.request.CreatePetRequest.CreatePetInfo;
import com.group.petSitter.domain.pet.controller.request.UpdatePetRequest;
import com.group.petSitter.domain.pet.service.request.CreatePetCommand;
import com.group.petSitter.domain.pet.service.request.FindPetDetailCommand;
import com.group.petSitter.domain.pet.service.request.FindPetsByUserCommand;
import com.group.petSitter.domain.pet.service.request.UpdatePetCommand;
import com.group.petSitter.domain.pet.service.response.FindPetDetailResponse;
import com.group.petSitter.domain.pet.service.response.FindPetsByUserResponse;
import com.group.petSitter.domain.user.User;
import com.group.petSitter.domain.user.support.UserFixture;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static com.group.petSitter.domain.pet.service.response.FindPetsByUserResponse.*;
import static com.group.petSitter.domain.user.support.UserFixture.*;

public class PetFixture {

    private static final String petName = "버터";
    private static final PetStatus PET_STATUS = PetStatus.PENDING;

    public static Pet pet(User user){
        ReflectionTestUtils.setField(user, "userId", 1L);
        Pet pet = Pet.builder()
                .petName(petName)
                .petStatus(PET_STATUS)
                .user(user)
                .build();
        ReflectionTestUtils.setField(pet, "petId", 1L);

        return pet;
    }

    public static CreatePetRequest createPetRequest(){
        CreatePetInfo createPetInfo = new CreatePetInfo(petName);
        return new CreatePetRequest(List.of(createPetInfo));
    }

    public static UpdatePetRequest updatePetRequest(String petName){
        return new UpdatePetRequest(petName);
    }

    public static CreatePetCommand createPetCommand(Long user_id){
        return CreatePetCommand.of(user_id,createPetRequest());
    }

    public static FindPetsByUserCommand findPetsByUserCommand(Long user_id){
        return FindPetsByUserCommand.of(user_id);
    }

    public static FindPetsByUserResponse findPetsByUserResponse(Pet pet){
        return FindPetsByUserResponse.from(List.of(pet));
    }

    public static FindPetDetailCommand findPetDetailCommand(Long pet_id){
        return FindPetDetailCommand.of(pet_id);
    }

    public static FindPetDetailResponse findPetDetailResponse(Pet pet){
        return FindPetDetailResponse.from(pet);
    }

    public static UpdatePetCommand updatePetCommand(Long userId, Long petId, UpdatePetRequest updatePetRequest){
        return new UpdatePetCommand(userId, petId, updatePetRequest.petName());
    }

}
