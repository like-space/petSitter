package com.group.petSitter.domain.order.controller.response;

import com.group.petSitter.domain.coupon.Coupon;
import com.group.petSitter.domain.order.Order;

public record UpdateOrderByCouponResponse(
        Integer totalPrice,
        Integer discountPrice
) {

    public static UpdateOrderByCouponResponse of(final Order order, final Coupon coupon) {
        return new UpdateOrderByCouponResponse(order.getPrice(), coupon.getDiscount());
    }
}
