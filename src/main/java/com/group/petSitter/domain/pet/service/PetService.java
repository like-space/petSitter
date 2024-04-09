package com.group.petSitter.domain.pet.service;


import com.group.petSitter.domain.pet.Pet;
import com.group.petSitter.domain.pet.PetStatus;
import com.group.petSitter.domain.pet.controller.request.UpdatePetRequest;
import com.group.petSitter.domain.pet.repository.PetRepository;
import com.group.petSitter.domain.pet.service.request.CreatePetCommand;
import com.group.petSitter.domain.pet.service.request.UpdatePetCommand;
import com.group.petSitter.domain.pet.service.response.FindPetDetailResponse;
import com.group.petSitter.domain.pet.service.response.FindPetsByUserResponse;
import com.group.petSitter.domain.user.User;
import com.group.petSitter.domain.user.exception.NotFoundUserException;
import com.group.petSitter.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetService {

    private final PetRepository petRepository;

    private final UserRepository userRepository;

    @Transactional
    public Long savePet(
            CreatePetCommand createPetCommand
    ){
        User findUser = findUserByUserId(createPetCommand.user_id());

        List<Pet> pets = createPetCommand
                .createPetRequest()
                .createPetsRequest()
                .stream()
                .map(item ->
                        Pet.builder()
                                .petName(item.petName())
                                .user(findUser)
                                .petStatus(PetStatus.PENDING)
                                .build()
                ).collect(Collectors.toList());

        petRepository.saveAll(pets);

        return findUser.getUserId();
    }

    @Transactional
    public FindPetDetailResponse findPetDetailResponse(Long petId){
        Pet pets = petRepository.findPetByPetId(petId);

        return FindPetDetailResponse.from(pets);
    }

    @Transactional
    public FindPetDetailResponse updatePet(
            Long petId,
            UpdatePetCommand updatePetCommand
    ){
        Pet pet = petRepository.findPetByPetId(petId);

        UpdatePetRequest updatePetRequest = updatePetCommand.updatePetRequest();

        pet.updatePet(
                updatePetRequest.petName()
        );

        return FindPetDetailResponse.from(pet);
    }

    @Transactional
    public void deletePet(Long petId){
       petRepository.deleteById(petId);
    }

    @Transactional
    public FindPetsByUserResponse findPetsByUserResponse(Long userId){
        List<Pet> petsByUserId = petRepository.findPetsByUser(findUserByUserId(userId));

        return FindPetsByUserResponse.from(petsByUserId);
    }

    private User findUserByUserId(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException("존재하지 않은 사용자입니다."));
    }

}
