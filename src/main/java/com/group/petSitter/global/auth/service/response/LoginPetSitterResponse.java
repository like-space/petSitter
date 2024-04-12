package com.group.petSitter.global.auth.service.response;

public record LoginPetSitterResponse(String accessToken) {

    public static LoginPetSitterResponse from(String accessToken) {
        return new LoginPetSitterResponse(accessToken);
    }
}
