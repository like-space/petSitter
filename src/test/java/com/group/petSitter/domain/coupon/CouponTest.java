package com.group.petSitter.domain.coupon;

import com.group.petSitter.domain.coupon.exception.InvalidCouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CouponTest {

    @Nested
    @DisplayName("성공")
    class newCouponTest {

        @Test
        @DisplayName("쿠톤 등록 성공")
        public void success() {
            //given
            String name = "testName";

            //when
            Coupon coupon = Coupon.builder()
                    .name(name)
                    .discount(10000)
                    .description("description")
                    .endAt(LocalDate.parse("2024-12-31"))
                    .build();

            //then
            assertThat(coupon.getName()).isEqualTo(name);
        }

        @Test
        @DisplayName("예외 : 현재시간 보다 이전 만료일")
        public void throwExceptionWhenInvalidEndAt() {
            //given
            String name = "testName";

            //when && then
            assertThatThrownBy(() -> Coupon.builder()
                    .name(name)
                    .discount(10000)
                    .description("description")
                    .endAt(LocalDate.parse("2023-12-31"))
                    .build())
                    .isInstanceOf(InvalidCouponException.class)
                    .hasMessage("쿠폰 종료일은 현재 날짜보다 이전일 수 없습니다.");
        }
    }
}