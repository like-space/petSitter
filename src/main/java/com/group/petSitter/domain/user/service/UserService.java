package com.group.petSitter.domain.user.service;

import com.group.petSitter.domain.user.User;
import com.group.petSitter.domain.user.exception.NotFoundUserException;
import com.group.petSitter.domain.user.repository.UserRepository;
import com.group.petSitter.domain.user.service.request.FindUserCommand;
import com.group.petSitter.domain.user.service.request.RegisterUserCommand;
import com.group.petSitter.domain.user.service.response.FindUserDetailResponse;
import com.group.petSitter.domain.user.service.response.RegisterUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public RegisterUserResponse getOrRegisterUser(final RegisterUserCommand registerUserCommand) {

        User findUser = userRepository.findByProviderAndProviderId(
                registerUserCommand.provider(),
                registerUserCommand.providerId())
            .orElseGet(() -> {
                User user = User.builder()
                    .nickname(registerUserCommand.nickname())
                    .email(registerUserCommand.email())
                    .userRole(registerUserCommand.userRole())
                    .userGrade(registerUserCommand.userGrade())
                    .build();
                userRepository.save(user);
                return user;
            });

        return RegisterUserResponse.from(findUser);
    }

    @Transactional(readOnly = true)
    public FindUserDetailResponse findUser(FindUserCommand findUserCommand) {
        User findUser = findUserByUserId(findUserCommand.userId());
        return FindUserDetailResponse.from(findUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User findUser = findUserByUserId(userId);
        userRepository.delete(findUser);
    }

    private User findUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException("존재하지 않는 유저입니다."));
    }
}
