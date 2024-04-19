package com.group.petSitter.domain.order.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CreateOrderRequest(
    @NotNull(message = "펫 아이디는 필수 입력 항목입니다")
    Long petId
) {
}
