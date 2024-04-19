package com.group.petSitter.domain.order.service.response;

import com.group.petSitter.domain.order.Order;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public record FindOrderResponse(
    Long orderId,
    String status,
    Integer totalPrice,
    LocalDateTime createdAt,
    Long petId
) {

    public static FindOrderResponse from(Order order) {
        return new FindOrderResponse(
            order.getOrderId(),
            order.getStatus().toString(),
            order.getPrice(),
            order.getCreatedAt(),
            order.getPetId()
        );
    }
}
