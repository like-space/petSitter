package com.group.petSitter.domain.order.service.response;

import com.group.petSitter.domain.order.Order;

public record CreateOrderResponse(
        Long orderId,
        Long petId,
        Integer totalPrice,
        String address
) {

    public static CreateOrderResponse from(Order order) {
        return new CreateOrderResponse(
                order.getOrderId(),
                order.getPetId(),
                order.getPrice(),
                order.getAddress()
        );
    }
}
