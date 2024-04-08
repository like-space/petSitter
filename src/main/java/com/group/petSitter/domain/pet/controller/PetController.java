package com.group.petSitter.domain.pet.controller;

import com.group.petSitter.domain.pet.Pet;
import com.group.petSitter.domain.pet.controller.request.CreatePetRequest;
import com.group.petSitter.domain.pet.service.PetService;
import com.group.petSitter.domain.pet.service.request.CreatePetCommand;
import com.group.petSitter.domain.pet.service.response.FindPetsByUserResponse;
import com.group.petSitter.global.auth.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<FindPetsByUserResponse> findPetsByUser(
            @PathVariable("userId") Long userId
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(petService.findPetsByUserResponse(userId));
    }



}
