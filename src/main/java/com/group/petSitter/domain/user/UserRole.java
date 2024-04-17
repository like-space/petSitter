package com.group.petSitter.domain.user;

import lombok.Getter;

import java.util.List;

@Getter
public enum UserRole {
    ROLE_USER(Constants.ROLE_USER, List.of(Constants.ROLE_USER)),
    ROLE_PET_SITTER(Constants.ROLE_PET_SITTER, List.of(Constants.ROLE_PET_SITTER)),
    ROLE_EMPLOYEE(Constants.ROLE_EMPLOYEE, List.of(Constants.ROLE_EMPLOYEE)),
    ROLE_ADMIN(Constants.ROLE_ADMIN,
        List.of(Constants.ROLE_ADMIN, Constants.ROLE_EMPLOYEE, Constants.ROLE_USER));

    private final String value;
    private final List<String> authorities;

    UserRole(String value, List<String> authorities) {
        this.value = value;
        this.authorities = authorities;
    }

    private static class Constants {
        private static final String ROLE_USER = "ROLE_USER";
        private static final String ROLE_PET_SITTER = "ROLE_PET_SITTER";
        private static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";
        private static final String ROLE_ADMIN = "ROLE_ADMIN";
    }
}
