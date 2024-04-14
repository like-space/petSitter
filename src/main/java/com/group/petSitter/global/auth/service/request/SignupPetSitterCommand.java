package com.group.petSitter.global.auth.service.request;

public record SignupPetSitterCommand(String username, String password, String address) {

    public static SignupPetSitterCommand of(
        final String username,
        final String password,
        final String address) {
        return new SignupPetSitterCommand(username, password, address);
    }
}
