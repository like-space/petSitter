package com.group.petSitter.global.auth.service;

import com.group.petSitter.domain.user.UserRole;
import com.group.petSitter.domain.walk.PetSitter;
import com.group.petSitter.domain.walk.repository.PetSitterRepository;
import com.group.petSitter.global.auth.exception.DuplicateUsernameException;
import com.group.petSitter.global.auth.exception.InvalidPasswordException;
import com.group.petSitter.global.auth.exception.InvalidUsernameException;
import com.group.petSitter.global.auth.jwt.TokenProvider;
import com.group.petSitter.global.auth.jwt.dto.CreateTokenCommand;
import com.group.petSitter.global.auth.service.request.LoginPetSitterCommand;
import com.group.petSitter.global.auth.service.request.SignupPetSitterCommand;
import com.group.petSitter.global.auth.service.response.LoginPetSitterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PetSitterAutenticationService {

    private final PetSitterRepository petSitterRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public Long signupPetSitter(SignupPetSitterCommand signupPetSitterCommand) {
        checkUsernameDuplication(signupPetSitterCommand.username());
        PetSitter petSitter = createPetSitter(signupPetSitterCommand);
        petSitterRepository.save(petSitter);
        return petSitter.getPetSitterId();
    }

    private void checkUsernameDuplication(final String username) {
        if (petSitterRepository.existsByUsername(username)) {
            throw new DuplicateUsernameException("사용할 수 없는 아이디입니다.");
        }
    }

    private PetSitter createPetSitter(SignupPetSitterCommand signupPetSitterCommand) {
        String encodedPassword = passwordEncoder.encode(signupPetSitterCommand.password());
        return PetSitter.builder()
                .username(signupPetSitterCommand.username())
                .password(encodedPassword)
                .address(signupPetSitterCommand.address())
                .build();
    }

    @Transactional(readOnly = true)
    public LoginPetSitterResponse loginPetSitter(LoginPetSitterCommand loginPetSitterCommand) {
        PetSitter petSitter = findPetSitterByUsername(loginPetSitterCommand);
        verifyPetSitterPassword(loginPetSitterCommand, petSitter);
        CreateTokenCommand createTokenCommand
                = CreateTokenCommand.of(petSitter.getPetSitterId(), UserRole.ROLE_PET_SITTER);
        String accessToken = tokenProvider.createToken(createTokenCommand);
        return LoginPetSitterResponse.from(accessToken);
    }

    private PetSitter findPetSitterByUsername(LoginPetSitterCommand loginPetSitterCommand) {
        return petSitterRepository.findByUsername(loginPetSitterCommand.username())
                .orElseThrow(() -> new InvalidUsernameException("사용자의 정보와 일치하지 않습니다."));
    }

    private void verifyPetSitterPassword(LoginPetSitterCommand loginPetSitterCommand, final PetSitter petSitter) {
        if (!passwordEncoder.matches(loginPetSitterCommand.password(), petSitter.getPassword())) {
            throw new InvalidPasswordException("사용자의 정보와 일치하지 않습니다.");
        }
    }

}
