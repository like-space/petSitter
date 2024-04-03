package com.group.petSitter.global.auth.oauth.client;

import com.group.petSitter.domain.user.service.response.FindUserDetailResponse;
import com.group.petSitter.global.auth.oauth.dto.OAuthHttpMessage;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

import java.util.Map;
import java.util.Optional;

public interface OAuthHttpMessageProvider {

    OAuthHttpMessage createUnlinkUserRequest(
        final FindUserDetailResponse userDetailResponse,
        final OAuth2AuthorizedClient authorizedClient);

    void verifySuccessUnlinkUserRequest(Map<String, Object> unlinkResponse);

    OAuthHttpMessage createRefreshAccessTokenRequest(OAuth2AuthorizedClient authorizedClient);

    OAuth2AccessToken extractAccessToken(Map response);

    Optional<OAuth2RefreshToken> extractRefreshToken(Map response);
}
