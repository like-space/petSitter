package com.group.petSitter.domain.coupon.service.request;

import com.group.petSitter.domain.coupon.controller.request.RegisterCouponRequest;

import java.time.LocalDate;

public record RegisterCouponCommand(
        String name,
        Integer discount,
        String description,
        LocalDate endAt
) {
    public static RegisterCouponCommand from(RegisterCouponRequest request) {
        return new RegisterCouponCommand(
                request.name(),
                request.discount(),
                request.description(),
                request.endAt()
        );
    }
}
