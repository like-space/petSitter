package com.group.petSitter.domain.order.controller;

import com.group.petSitter.base.BaseControllerTest;
import com.group.petSitter.domain.order.Order;
import com.group.petSitter.domain.order.controller.request.CreateOrderRequest;
import com.group.petSitter.domain.order.service.response.CreateOrderResponse;
import com.group.petSitter.domain.order.service.response.FindOrdersResponse;
import com.group.petSitter.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.group.petSitter.domain.order.support.OrderFixture.*;
import static com.group.petSitter.domain.user.support.UserFixture.user;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class OrderControllerTest extends BaseControllerTest {



    @Nested
    @DisplayName("CreateOrder 메서드 실행 시")
    class CreateOrderTest {

        @Test
        @DisplayName("성공")
        void createOrder() throws Exception {
            //given
            User user = user();
            Order order = pendingOrder(1L, user);
            CreateOrderRequest createOrderRequest = createOrderRequest();
            CreateOrderResponse createOrderResponse = createOrderResponse(order);
            given(orderService.createOrder(any())).willReturn(createOrderResponse);

            //when
            ResultActions result = mockMvc.perform(
                post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createOrderRequest))
                    .header(AUTHORIZATION, accessToken));

            //then
            result
                .andExpect(status().isCreated())
                .andDo(restDocs.document(
                    responseFields(
                        fieldWithPath("id").type(JsonFieldType.STRING).description("응답 ID"),
                        fieldWithPath("dateTime").type(JsonFieldType.STRING).description("응답 날짜 및 시간"),
                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                        fieldWithPath("response.orderId").type(NUMBER).description("주문 아이디"),
                        fieldWithPath("response.petId").type(NUMBER).description("주문 펫"),
                        fieldWithPath("response.totalPrice").type(NUMBER).description("최종 가격"),
                        fieldWithPath("response.address").type(STRING).description("기본 배송지")
                     )));
        }
    }

    @Nested
    @DisplayName("findOrders 메서드 실행 시")
    class FindOrdersTest {

        @Test
        @DisplayName("성공")
        void findOrders() throws Exception {
            // given
            User user = user();
            Order order = completedOrder(1L, user);
            FindOrdersResponse findOrdersResponse = FindOrdersResponse.of(List.of(order), 1);

            given(orderService.findOrders(any(), eq(1))).willReturn(findOrdersResponse);

            // when
            ResultActions result = mockMvc.perform(
                get("/api/v1/orders?page={page}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(AUTHORIZATION, accessToken));

            // then
            result
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                    queryParameters(
                        parameterWithName("page").description("페이지 번호")
                    ),
                    responseFields(
                        fieldWithPath("id").type(JsonFieldType.STRING).description("응답 ID"),
                        fieldWithPath("dateTime").type(JsonFieldType.STRING).description("응답 날짜 및 시간"),
                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                        fieldWithPath("response.totalPages").type(NUMBER).description("총 페이지 수"),
                        fieldWithPath("response.orders[].orderId").optional().type(NUMBER).description("주문 ID"),
                        fieldWithPath("response.orders[].createdAt").optional().type(STRING).description("주문 시각"),
                        fieldWithPath("response.orders[].totalPrice").type(NUMBER).description("최종 가격"),
                        fieldWithPath("response.orders[].status").type(STRING).description("주문 상태"),
                        fieldWithPath("response.orders[].petId").optional().type(NUMBER).description("상품 ID")
                    )));
            }
    }

}