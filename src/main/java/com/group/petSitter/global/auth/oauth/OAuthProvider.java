package com.group.petSitter.global.auth.oauth;

import com.group.petSitter.global.auth.exception.InvalidProviderException;
import com.group.petSitter.global.auth.oauth.client.KakaoMessageProvider;
import com.group.petSitter.global.auth.oauth.client.NaverMessageProvider;
import com.group.petSitter.global.auth.oauth.client.OAuthHttpMessageProvider;
import com.group.petSitter.global.auth.oauth.dto.OAuthUserInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum OAuthProvider {
    KAKAO("kakao", OAuthProvider::extractKakaoUserInfo, new KakaoMessageProvider()),
    NAVER("naver", OAuthProvider::extractNaverUserInfo, new NaverMessageProvider());

    private static final Map<String, OAuthProvider> PROVIDERS =
        Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(OAuthProvider::getName, Function.identity())));

    private final String name;
    private final Function<Map<String, Object>, OAuthUserInfo> extractUserInfo;
    private final OAuthHttpMessageProvider oAuthHttpMessageProvider;

    private static OAuthUserInfo extractNaverUserInfo(Map<String, Object> attributes) {
        Map<String, String> response = (Map<String, String>) attributes.get("response");
        String oAuthUserId = response.get("id");
        String nickname = response.get("name");
        String email = response.get("email");
        return new OAuthUserInfo(oAuthUserId, nickname, email);
    }

    private static OAuthUserInfo extractKakaoUserInfo(Map<String, Object> attributes) {
        Map<String, String> properties = (Map<String, String>) attributes.get("properties");
        String oAuthUserId = String.valueOf(attributes.get("id"));
        String nickname = properties.get("nickname");
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String email = String.valueOf(kakaoAccount.get("email"));
        return new OAuthUserInfo(oAuthUserId, nickname, email);
    }

    public static OAuthProvider getOAuthProvider(final String provider) {
        OAuthProvider oAuthProvider = PROVIDERS.get(provider);
        return Optional.ofNullable(oAuthProvider)
            .orElseThrow(() -> new InvalidProviderException("지원하지 않는 소셜 로그인입니다."));
    }

    public OAuthUserInfo getOAuthUserInfo(final Map<String, Object> attributes) {
        return this.extractUserInfo.apply(attributes);
    }
}