package com.group.petSitter.domain.user.service.response;

import com.group.petSitter.domain.user.User;
import com.group.petSitter.domain.user.UserRole;

public record RegisterUserResponse(
    Long userId,
    String nickname,
    String providerId,
    UserRole userRole) {

    public static RegisterUserResponse from(final User user) {
        return new RegisterUserResponse(
            user.getUserId(),
            user.getNickname(),
            user.getProviderId(),
            user.getUserRole()
        );
    }
}
