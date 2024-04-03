package com.group.petSitter.global.auth.jwt;


import com.group.petSitter.global.auth.jwt.dto.Claims;
import com.group.petSitter.global.auth.jwt.dto.CreateTokenCommand;

public interface TokenProvider {

    String createToken(final CreateTokenCommand createTokenCommand);

    Claims validateToken(final String accessToken);
}
