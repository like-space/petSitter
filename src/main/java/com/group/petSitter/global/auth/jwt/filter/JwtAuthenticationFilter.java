package com.group.petSitter.global.auth.jwt.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.group.petSitter.global.auth.exception.InvalidJwtException;
import com.group.petSitter.global.auth.jwt.JwtAuthenticationProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private static final String HEADER = "Authorization";

    private final JwtAuthenticationProvider authenticationProvider;

    @Override
    public void doFilter(
            ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        response.setContentType("application/json;charset=UTF-8"); // 문자 인코딩 설정

        try {
            String accessToken = request.getHeader(HEADER);
            if (Objects.nonNull(accessToken)) {
                Authentication authentication = authenticationProvider.authenticate(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(req, res);
        } catch (TokenExpiredException ex) {
            handleException(response, HttpServletResponse.SC_UNAUTHORIZED, "Expired token", ex.getMessage());
            log.info("TokenExpiredException: 토큰이 만료되었습니다.");
        } catch (InvalidJwtException ex) {
            handleException(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token", ex.getMessage());
            log.info("JWTVerificationException: 유효하지 않은 토큰입니다.");
        }
    }

    private void handleException(HttpServletResponse response, int status, String error, String message) throws IOException {
        response.setStatus(status);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode errorResponse = mapper.createObjectNode();
        errorResponse.put("error", error);
        errorResponse.put("message", message);
        response.getWriter().write(errorResponse.toString());
    }
}
