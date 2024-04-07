package com.group.petSitter.domain.coupon;

import com.group.petSitter.domain.coupon.exception.InvalidCouponException;
import com.group.petSitter.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    @Column(nullable = false)
    private Integer discount;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private LocalDate endAt;

    @Builder
    public Coupon(Integer discount, String name, String description, LocalDate endAt) {
        validateEndAt(endAt);
        this.discount = discount;
        this.name = name;
        this.description = description;
        this.endAt = endAt;
    }

    private void validateEndAt(LocalDate endAt) {
        LocalDate currentDate = LocalDate.now();
        if (endAt.isBefore(currentDate)) {
            throw new InvalidCouponException("쿠폰 종료일은 현재 날짜보다 이전일 수 없습니다.");
        }
    }

}
