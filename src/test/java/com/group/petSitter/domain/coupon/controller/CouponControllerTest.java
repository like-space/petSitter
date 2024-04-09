package com.group.petSitter.domain.coupon.controller;

import com.group.petSitter.base.BaseControllerTest;
import com.group.petSitter.domain.coupon.service.request.RegisterCouponCommand;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
class CouponControllerTest extends BaseControllerTest {

    @Nested
    @DisplayName("쿠폰 생성하는 api 호출 시")
    class CreateCouponApi {

        @Test
        @DisplayName("성공")
        void RegisterCoupon() throws Exception {
            // Given
            RegisterCouponCommand registerCouponCommand = new RegisterCouponCommand("TestName",
                    10000,
                    "TestDescription", LocalDate.parse("2023-12-31"));
            when(couponService.createCoupon(registerCouponCommand)).thenReturn(1L);

            // When
            ResultActions resultActions = mockMvc.perform(post("/api/v1/coupons")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(registerCouponCommand)));

            // Then
            resultActions
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "/api/v1/coupons/1"))
                    .andDo(print())
                    .andDo(restDocs.document(requestFields(
                                    fieldWithPath("name").type(STRING)
                                            .description("couponName"),
                                    fieldWithPath("discount").type(NUMBER)
                                            .description("discount"),
                                    fieldWithPath("description").type(STRING)
                                            .description("description"),
                                    fieldWithPath("endAt").type(STRING)
                                            .description("endAt (yyyy-MM-dd)"))
                            )
                    );
        }
    }
}