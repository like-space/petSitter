package com.group.petSitter.domain.order.controller;

import com.group.petSitter.domain.order.controller.request.CreateOrderRequest;
import com.group.petSitter.domain.order.controller.request.UpdateOrderByCouponCommand;
import com.group.petSitter.domain.order.controller.response.UpdateOrderByCouponResponse;
import com.group.petSitter.domain.order.service.request.CreateOrdersCommand;
import com.group.petSitter.domain.order.service.OrderService;
import com.group.petSitter.domain.order.service.response.CreateOrderResponse;
import com.group.petSitter.domain.order.service.response.FindOrderDetailResponse;
import com.group.petSitter.domain.order.service.response.FindOrdersResponse;
import com.group.petSitter.global.auth.LoginUser;
import com.group.petSitter.global.dto.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CommonResponse> createOrder(
            @Valid @RequestBody final CreateOrderRequest createOrderRequest,
            @LoginUser final Long userId) {
        CreateOrdersCommand createOrdersCommand =
            CreateOrdersCommand.of(userId, createOrderRequest);
        CreateOrderResponse createOrderResponse = orderService.createOrder(createOrdersCommand);
        CommonResponse response = CommonResponse.builder()
            .success(true)
            .response(createOrderResponse)
            .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CommonResponse> findOrders(
            @RequestParam(defaultValue = "0") Integer page,
            @LoginUser Long userId
    ) {
    CommonResponse response = CommonResponse.builder()
        .success(true)
        .response(orderService.findOrders(userId, page))
        .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{orderId}/apply-coupon")
    public ResponseEntity<CommonResponse> updateOrderByCoupon(
            @PathVariable final Long orderId,
            @LoginUser final Long userId,
            @RequestParam final Long couponId
    ) {
        UpdateOrderByCouponCommand updateOrderByCouponCommand
            = UpdateOrderByCouponCommand.of(orderId, userId, couponId);

        CommonResponse response = CommonResponse.builder()
            .success(true)
            .response(orderService.updateOrderByCoupon(updateOrderByCouponCommand))
            .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<CommonResponse> findOrderByIdAndUserId(
            @PathVariable Long orderId,
            @LoginUser Long userId
    ) {
        CommonResponse response = CommonResponse.builder()
            .success(true)
            .response(orderService.findOrderByIdAndUserId(orderId, userId))
            .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
