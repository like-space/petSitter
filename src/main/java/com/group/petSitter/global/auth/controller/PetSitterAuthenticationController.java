package com.group.petSitter.global.auth.controller;

import com.group.petSitter.global.auth.controller.request.LoginPetSitterRequest;
import com.group.petSitter.global.auth.controller.request.SignupPetSitterRequest;
import com.group.petSitter.global.auth.service.PetSitterAutenticationService;
import com.group.petSitter.global.auth.service.request.LoginPetSitterCommand;
import com.group.petSitter.global.auth.service.request.SignupPetSitterCommand;
import com.group.petSitter.global.auth.service.response.LoginPetSitterResponse;
import com.group.petSitter.global.dto.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PetSitterAuthenticationController {

    private final PetSitterAutenticationService petSitterAutenticationService;

    @PostMapping("/petSitter/signup")
    public ResponseEntity<Void> createPetSitter(
            @Valid @RequestBody SignupPetSitterRequest signupPetSitterRequest
    ){
        petSitterAutenticationService.signupPetSitter(SignupPetSitterCommand.of(
                signupPetSitterRequest.username(),
                signupPetSitterRequest.password(),
                signupPetSitterRequest.address()
        ));

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/petSitter/login")
    public ResponseEntity<CommonResponse> loginPetSitter(
            @Valid @RequestBody LoginPetSitterRequest loginPetSitterRequest
    ){
        LoginPetSitterResponse loginPetSitterResponse =
                petSitterAutenticationService.loginPetSitter(LoginPetSitterCommand.of(
                    loginPetSitterRequest.username(),
                    loginPetSitterRequest.password()
            ));

        CommonResponse commonResponse =
                CommonResponse.builder()
                        .success(true)
                        .response(loginPetSitterResponse)
                        .build();

        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

}
