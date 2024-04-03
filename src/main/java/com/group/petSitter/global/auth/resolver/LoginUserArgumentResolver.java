package com.group.petSitter.global.auth.resolver;

import com.group.petSitter.global.auth.LoginUser;
import com.group.petSitter.global.auth.exception.UnAuthenticationException;
import com.group.petSitter.global.auth.jwt.dto.JwtAuthentication;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasParameterAnnotation = parameter.hasParameterAnnotation(LoginUser.class);
        boolean hasLongParameterType = parameter.getParameterType().isAssignableFrom(Long.class);
        return hasLongParameterType & hasParameterAnnotation;
    }

    @Override
    public Object resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        checkAuthenticated(authentication);
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication.getPrincipal();
        return jwtAuthentication.userId();
    }

    private void checkAuthenticated(Authentication authentication) {
        if(Objects.isNull(authentication)) {
            throw new UnAuthenticationException("인증되지 않은 요청입니다.");
        }
    }
}
