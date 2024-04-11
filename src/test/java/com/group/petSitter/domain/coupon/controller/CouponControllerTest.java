package com.group.petSitter.domain.coupon.controller;

import com.group.petSitter.base.BaseControllerTest;
import com.group.petSitter.domain.coupon.service.request.RegisterCouponCommand;
import com.group.petSitter.domain.coupon.service.request.RegisterUserCouponCommand;
import com.group.petSitter.domain.coupon.service.response.FindCouponsResponse;
import com.group.petSitter.domain.coupon.support.CouponFixture;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
            // given
            RegisterCouponCommand registerCouponCommand = new RegisterCouponCommand("TestName",
                    10000,
                    "TestDescription", LocalDate.parse("2024-12-31"));
            given(couponService.createCoupon(registerCouponCommand)).willReturn(1L);

            // when
            ResultActions resultActions = mockMvc.perform(post("/api/v1/coupons")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(registerCouponCommand)));

            // then
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

    @Nested
    @DisplayName("발급가능한 쿠폰 조회 api 호출 시")
    class findCouponsApi {

        @Test
        @DisplayName("성공")
        void findCoupons() throws Exception {
            // Given
            FindCouponsResponse findCouponsResponse = CouponFixture.findCouponsResponse();
            given(couponService.findCoupons()).willReturn(findCouponsResponse);

            // When
            ResultActions resultActions = mockMvc.perform(get("/api/v1/coupons")
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            resultActions.andExpect(status().isOk())
                .andDo(restDocs.document(
                    responseFields(
                        fieldWithPath("id").type(JsonFieldType.STRING).description("응답 ID"),
                        fieldWithPath("dateTime").type(JsonFieldType.STRING).description("응답 날짜 및 시간"),
                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                        fieldWithPath("response.coupons").type(ARRAY).description("발급 가능한 쿠폰 리스트"),
                        fieldWithPath("response.coupons[].couponId").type(NUMBER).description("쿠폰 아이디"),
                        fieldWithPath("response.coupons[].name").type(STRING).description("쿠폰 이름"),
                        fieldWithPath("response.coupons[].description").type(STRING).description("쿠폰 설명"),
                        fieldWithPath("response.coupons[].discount").type(NUMBER).description("할인 금액"),
                        fieldWithPath("response.coupons[].endAt").type(STRING).description("쿠폰 만료 일자 (yyyy-MM-dd)")
                    )));
        }
    }

    @Nested
    @DisplayName("유저한테 쿠폰 발급하는 api 호출 시")
    class RegisterUserCouponApi {

        @Test
        @DisplayName("성공")
        void RegisterUserCoupon() throws Exception {
            //given
            Long couponId = 1L;
            RegisterUserCouponCommand registerUserCouponCommand =
                    new RegisterUserCouponCommand(1L, couponId);
            given(couponService.registerUserCoupon(registerUserCouponCommand)).willReturn(1L);

            //when
            ResultActions resultActions = mockMvc.perform(
                    post("/api/v1/my-coupons/{couponId}", couponId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registerUserCouponCommand))
                            .header(AUTHORIZATION, accessToken));

            //then
            resultActions
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "/api/v1/my-coupons/1"))
                    .andDo(print())
                    .andDo(restDocs.document(requestFields(
                                    fieldWithPath("userId").type(NUMBER).description("userId"),
                                    fieldWithPath("couponId").type(NUMBER)
                                            .description("couponId"))
                            )
                    );

        }
    }


}