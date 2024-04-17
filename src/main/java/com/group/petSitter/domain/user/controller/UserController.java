package com.group.petSitter.domain.user.controller;


import com.group.petSitter.domain.user.exception.UserException;
import com.group.petSitter.domain.user.service.UserService;
import com.group.petSitter.domain.user.service.request.FindUserCommand;
import com.group.petSitter.domain.user.service.response.FindUserDetailResponse;
import com.group.petSitter.global.auth.LoginUser;
import com.group.petSitter.global.auth.oauth.client.OAuthRestClient;
import com.group.petSitter.global.dto.CommonResponse;
import com.group.petSitter.global.dto.ErrorResponse;
import com.group.petSitter.global.message.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final OAuthRestClient restClient;

    @GetMapping("/users/me")
    public ResponseEntity<CommonResponse> findUser(@LoginUser Long userId) {
        FindUserDetailResponse findUserDetailResponse =
                userService.findUser(FindUserCommand.from(userId));

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(findUserDetailResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/users/me")
    public ResponseEntity<CommonResponse> deleteUser(@LoginUser Long userId) {
        FindUserCommand findUserDetailCommand = FindUserCommand.from(userId);
        FindUserDetailResponse findUserDetailResponse = userService.findUser(findUserDetailCommand);
        restClient.callUnlinkOAuthUser(findUserDetailResponse);
        userService.deleteUser(userId);

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(Message.ACCOUNT_DELETED_SUCCESSFULLY)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> userExHandle(UserException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("USER_ERROR_CODE") // 예외 코드
                .status(HttpStatus.BAD_REQUEST.value()) // HTTP 상태 코드
                .message(ex.getMessage()) // 예외 메시지
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

}
