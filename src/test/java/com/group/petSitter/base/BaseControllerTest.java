package com.group.petSitter.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.petSitter.domain.coupon.service.CouponService;
import com.group.petSitter.domain.pet.service.PetService;
import com.group.petSitter.domain.user.service.UserService;
import com.group.petSitter.global.auth.jwt.JwtAuthenticationProvider;
import com.group.petSitter.global.auth.jwt.filter.JwtAuthenticationFilter;
import com.group.petSitter.global.auth.oauth.client.OAuthRestClient;
import com.group.petSitter.global.auth.support.AuthFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest
@Import(RestDocsConfig.class)
@ExtendWith(RestDocumentationExtension.class)
public abstract class BaseControllerTest {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected RestDocumentationResultHandler restDocs;

    @MockBean
    protected UserService userService;

    @MockBean
    protected CouponService couponService;

    @MockBean
    protected PetService petService;

    @MockBean
    protected OAuthRestClient oAuthRestClient;



    protected static final String AUTHORIZATION = "Authorization";

    protected String accessToken;

    @BeforeEach
    void authenticationSetUp() {
        accessToken = AuthFixture.accessToken();
    }

    @BeforeEach
    void mockMvcSetUp(
        final WebApplicationContext context,
        final RestDocumentationContextProvider provider) {
        JwtAuthenticationProvider jwtAuthenticationProvider
            = new JwtAuthenticationProvider(AuthFixture.tokenProvider());

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .alwaysDo(print())
            .alwaysDo(restDocs)
            .alwaysDo(
                document("{method-name}",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()))
            )
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .addFilter(new JwtAuthenticationFilter(jwtAuthenticationProvider))
            .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
            .build();
    }

    @Test
    void contextLoads() {

    }
}
