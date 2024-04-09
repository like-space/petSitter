package com.group.petSitter.domain.pet.controller;

import com.group.petSitter.domain.pet.Pet;
import com.group.petSitter.domain.pet.controller.request.CreatePetRequest;
import com.group.petSitter.domain.pet.controller.request.UpdatePetRequest;
import com.group.petSitter.domain.pet.service.PetService;
import com.group.petSitter.domain.pet.service.request.CreatePetCommand;
import com.group.petSitter.domain.pet.service.request.UpdatePetCommand;
import com.group.petSitter.domain.pet.service.response.FindPetDetailResponse;
import com.group.petSitter.domain.pet.service.response.FindPetsByUserResponse;
import com.group.petSitter.global.auth.LoginUser;
import com.group.petSitter.global.dto.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pets")
public class PetController {

    private static final String BASE_URI = "/api/v1/pets";

    private final PetService petService;

    @PostMapping
    public ResponseEntity<Void> savePet(
            @LoginUser Long user_id,
            @Valid @RequestBody CreatePetRequest createPetRequest
    )
    {
        petService.savePet(CreatePetCommand.of(user_id,createPetRequest));

        URI location = URI.create(BASE_URI + "/myPets/");

        return ResponseEntity
                .created(location)
                .build();
    }

    @GetMapping("/{petId}")
    public ResponseEntity<CommonResponse> findPetDetail(
            @PathVariable("petId") Long petId
    ){
        FindPetDetailResponse petDetailResponse = petService.findPetDetailResponse(petId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .response(petDetailResponse)
                        .success(true)
                        .build());
    }

    @PatchMapping("/{petId}")
    public ResponseEntity<CommonResponse> updatePet(
            @PathVariable("petId") Long petId,
            @Valid @RequestBody UpdatePetRequest updatePetRequest
    ){
        FindPetDetailResponse findPetDetailResponse = petService.updatePet(petId, UpdatePetCommand.of(updatePetRequest));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .response(findPetDetailResponse)
                        .success(true)
                        .build());
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<CommonResponse> deletePet(
            @PathVariable("petId") Long petId
    ){
       petService.deletePet(petId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .success(true)
                        .build());

    }

    @GetMapping("/myPets")
    public ResponseEntity<CommonResponse> findPetsByUser(
            @LoginUser Long userId
    ){
        FindPetsByUserResponse petsByUserResponse = petService.findPetsByUserResponse(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .response(petsByUserResponse)
                        .success(true)
                        .build());
    }

}
