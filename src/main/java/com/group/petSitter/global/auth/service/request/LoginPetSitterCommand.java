package com.group.petSitter.global.auth.service.request;

public record LoginPetSitterCommand(String username, String password) {
    public static LoginPetSitterCommand of(final String username, final String password) {
        return new LoginPetSitterCommand(username, password);
    }
}
