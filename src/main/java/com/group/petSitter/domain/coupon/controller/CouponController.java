package com.group.petSitter.domain.coupon.controller;

import com.group.petSitter.domain.coupon.controller.request.RegisterCouponRequest;
import com.group.petSitter.domain.coupon.exception.CouponException;
import com.group.petSitter.domain.coupon.service.CouponService;
import com.group.petSitter.domain.coupon.service.request.RegisterCouponCommand;
import com.group.petSitter.domain.coupon.service.request.RegisterUserCouponCommand;
import com.group.petSitter.domain.coupon.service.response.FindCouponsResponse;
import com.group.petSitter.domain.coupon.service.response.FindIssuedCouponsResponse;
import com.group.petSitter.global.auth.LoginUser;
import com.group.petSitter.global.dto.CommonResponse;
import com.group.petSitter.global.dto.ErrorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coupons")
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<Void> createCoupon(
        @Valid @RequestBody RegisterCouponRequest registerCouponRequest) {
        RegisterCouponCommand registerCouponCommand =
                RegisterCouponCommand.from(registerCouponRequest);
        Long couponId = couponService.createCoupon(registerCouponCommand);
        URI location = URI.create("/api/v1/coupons/" + couponId);
        log.info("location={}", location);

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<CommonResponse> findCoupons() {
        FindCouponsResponse findCouponsResponse = couponService.findCoupons();
        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(findCouponsResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/my-coupons/{couponId}")
    public ResponseEntity<Void> RegisterUserCoupon(
            @PathVariable final Long couponId,
            @LoginUser final Long userId) {
        RegisterUserCouponCommand registerUserCouponCommand =
            RegisterUserCouponCommand.of(userId, couponId);
        Long userCouponId = couponService.registerUserCoupon(registerUserCouponCommand);
        URI location = URI.create("/api/v1/my-coupons/" + userCouponId);

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/my-coupons")
    public ResponseEntity<CommonResponse> findIssuedCoupons(
        @LoginUser final Long userId) {
        FindIssuedCouponsResponse findIssuedCouponsResponse =
            couponService.findIssuedCoupons(userId);
        CommonResponse response = CommonResponse.builder()
            .success(true)
            .response(findIssuedCouponsResponse)
            .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(CouponException.class)
    public ResponseEntity<ErrorResponse> handleException(
        final CouponException couponException) {
        log.info("couponExceptionMessage= {}", couponException.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("Coupon_ERROR_CODE") // 예외 코드
                .status(HttpStatus.BAD_REQUEST.value())
                .message(couponException.getMessage())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
