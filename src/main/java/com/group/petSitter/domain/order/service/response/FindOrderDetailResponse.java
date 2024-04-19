package com.group.petSitter.domain.order.service.response;

import com.group.petSitter.domain.order.Order;

import java.time.LocalDateTime;
import java.util.List;

public record FindOrderDetailResponse(
        LocalDateTime createAt,
        Integer totalPrice,
        String status,
        Long pets
) {

    public static FindOrderDetailResponse of(final Order order) {
        return new FindOrderDetailResponse(
                order.getCreatedAt(),
                order.getPrice(),
                order.getStatus().toString(),
                order.getPetId()
        );
    }
}
