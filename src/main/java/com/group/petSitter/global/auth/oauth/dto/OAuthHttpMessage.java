package com.group.petSitter.global.auth.oauth.dto;

import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public record OAuthHttpMessage(
    String uri,
    HttpEntity<MultiValueMap<String, String>> httpMessage,
    Map<String, String> uriVariables) {

}
