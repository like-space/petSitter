package com.group.petSitter.global.dto;

import lombok.Builder;

import java.util.Date;
import java.util.UUID;

public record CommonResponse(
        String id,
        Date dateTime,
        Boolean success,
        Object response
) {
    @Builder
    public CommonResponse(
            String id,
            Date dateTime,
            Boolean success,
            Object response
    ) {
        this.id = UUID.randomUUID().toString();
        this.dateTime = new Date();
        this.success = success;
        this.response = response;
    }

}
