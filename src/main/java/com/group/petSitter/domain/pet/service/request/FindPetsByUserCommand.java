package com.group.petSitter.domain.pet.service.request;

import com.group.petSitter.domain.user.User;

public record FindPetsByUserCommand(Long userId) {

    public static FindPetsByUserCommand of(Long userId){
        return new FindPetsByUserCommand(userId);
    }
}
