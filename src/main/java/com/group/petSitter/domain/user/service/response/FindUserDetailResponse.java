package com.group.petSitter.domain.user.service.response;

import com.group.petSitter.domain.user.User;
import com.group.petSitter.domain.user.UserGrade;
import com.group.petSitter.domain.user.UserRole;

public record FindUserDetailResponse(
    Long userId,
    String nickname,
    String email,
    String provider,
    String providerId,
    UserRole userRole,
    UserGrade userGrade) {

    public static FindUserDetailResponse from(final User findUser) {
        return new FindUserDetailResponse(
            findUser.getUserId(),
            findUser.getNickname(),
            findUser.getEmail(),
            findUser.getProvider(),
            findUser.getProviderId(),
            findUser.getUserRole(),
            findUser.getUserGrade());
    }
}
