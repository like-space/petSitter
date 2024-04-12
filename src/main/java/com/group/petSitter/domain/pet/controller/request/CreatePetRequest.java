package com.group.petSitter.domain.pet.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreatePetRequest(
        @NotNull @Valid List<CreatePetInfo> createPetsRequest
)
{
        public record CreatePetInfo(
                @NotBlank(message = "반려견이름은 필수 항목입니다.")
                String petName
        ) {}
}
