package com.group.petSitter.domain.coupon.controller;

import com.group.petSitter.domain.coupon.controller.request.RegisterCouponRequest;
import com.group.petSitter.domain.coupon.service.CouponService;
import com.group.petSitter.domain.coupon.service.request.RegisterCouponCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/coupons")
    public ResponseEntity<Void> createCoupon(
        @Valid @RequestBody RegisterCouponRequest registerCouponRequest) {
        RegisterCouponCommand registerCouponCommand =
                RegisterCouponCommand.from(registerCouponRequest);

        Long couponId = couponService.createCoupon(registerCouponCommand);
        URI location = URI.create("/api/v1/coupons/" + couponId);

        log.info("location={}", location);

        return ResponseEntity.created(location).build();
    }

}
