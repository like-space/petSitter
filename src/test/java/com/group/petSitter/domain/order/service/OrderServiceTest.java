package com.group.petSitter.domain.order.service;

import com.group.petSitter.domain.coupon.repository.UserCouponRepository;
import com.group.petSitter.domain.order.Order;
import com.group.petSitter.domain.order.repository.OrderRepository;
import com.group.petSitter.domain.order.service.request.CreateOrdersCommand;
import com.group.petSitter.domain.order.service.response.CreateOrderResponse;
import com.group.petSitter.domain.order.service.response.FindOrdersResponse;
import com.group.petSitter.domain.user.User;
import com.group.petSitter.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static com.group.petSitter.domain.order.support.OrderFixture.*;
import static com.group.petSitter.domain.user.support.UserFixture.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    UserCouponRepository userCouponRepository;


    @Nested
    @DisplayName("createOrder 메서드 실행 시")
    class CreateOrderTest {

        @Test
        @DisplayName("성공")
        void success() {
            //given
            User user = user();
            Order order = pendingOrder(1L, user);
            ReflectionTestUtils.setField(order, "orderId", null);
            CreateOrdersCommand createOrdersCommand = createOrdersCommand();
            CreateOrderResponse expected = createOrderResponse(order);

            given(userRepository.findById(any())).willReturn(Optional.ofNullable(user));
            given(orderRepository.save(any())).willReturn(order);

            // when
            CreateOrderResponse result = orderService.createOrder(createOrdersCommand);

            // then
            assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        }

        @Nested
        @DisplayName("findOrders 메서드 실행 시")
        class FindOrdersTest {

            @Test
            @DisplayName("성공")
            void success() {
                // given
                User user = user();
                Order order = completedOrder(1L, user);
                Pageable pageable = PageRequest.of(0, 10);
                PageImpl<Order> pagination = new PageImpl<>(List.of(order), pageable, 1L);
                FindOrdersResponse expected = FindOrdersResponse.of(List.of(order), 1);

                given(orderRepository.findByUser_UserId(eq(user.getUserId()), any()))
                    .willReturn(pagination);

                // when
                FindOrdersResponse result = orderService.findOrders(user.getUserId(), 0);

                // then
                assertThat(result).usingRecursiveComparison().isEqualTo(expected);
            }
        }

    }

}