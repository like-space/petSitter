package com.group.petSitter.domain.order.support;

import com.group.petSitter.domain.order.Order;
import com.group.petSitter.domain.order.OrderStatus;
import com.group.petSitter.domain.order.controller.request.CreateOrderRequest;
import com.group.petSitter.domain.order.service.request.CreateOrdersCommand;
import com.group.petSitter.domain.order.service.response.CreateOrderResponse;
import com.group.petSitter.domain.user.User;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static com.group.petSitter.domain.user.support.UserFixture.user;

public class OrderFixture {

    public static Order pendingOrder(long orderId, User user) {
        Order order = new Order(user, 1L);
        ReflectionTestUtils.setField(order, "orderId", orderId);

        return order;
    }

    public static CreateOrdersCommand createOrdersCommand() {
        return new CreateOrdersCommand(user().getUserId(), createOrderRequest());
    }

    public static CreateOrderRequest createOrderRequest() {
        return new CreateOrderRequest(1L);
    }

    public static CreateOrderResponse createOrderResponse(Order order) {
        return CreateOrderResponse.from(order);
    }

    public static Order completedOrder(long orderId, User user) {
        Order order = new Order(user, 1L);
        ReflectionTestUtils.setField(order, "orderId", orderId);
        ReflectionTestUtils.setField(order, "status", OrderStatus.PAYED);

        return order;
    }
}
