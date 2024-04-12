package com.group.petSitter.domain.pet.controller.request;

import com.group.petSitter.domain.pet.PetStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdatePetRequest(
        @NotBlank(message = "반려견이름은 필수 항목입니다.") String petName
) {}
