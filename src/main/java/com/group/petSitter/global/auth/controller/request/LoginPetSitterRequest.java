package com.group.petSitter.global.auth.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record LoginPetSitterRequest(
    @NotNull(message = "사용자 이름은 필수 입니다.")
    @Pattern(regexp = "^(?=.*[a-z])[a-z0-9]{6,20}$",
        message = "사용자 이름은 영어 소문자 또는 영어 소문자와 숫자 6자 이상, 20자 이하로 구성 되어야 합니다.")
    String username,
    @NotNull(message = "패스워드는 필수 입니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)[a-z\\d]{8,20}$",
        message = "패스워드는 영어 소문자, 숫자 8자 이상, 20자 이하로 구성 되어야 합니다.")
    String password) {

}
