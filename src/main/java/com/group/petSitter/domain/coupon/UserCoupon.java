package com.group.petSitter.domain.coupon;

import com.group.petSitter.domain.coupon.exception.InvalidUsedCouponException;
import com.group.petSitter.domain.user.User;
import com.group.petSitter.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCoupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCouponId;

    @Column(nullable = false)
    private boolean isUsed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    public int getDiscount() {
        return this.getCoupon().getDiscount();
    }

    public void use() {
        if (isUsed == true) {
            throw new InvalidUsedCouponException("이미 사용한 쿠폰입니다.");
        }
        isUsed = true;
    }

    public void unUse() {
        if (isUsed == false) {
            throw new InvalidUsedCouponException("사용하지 않은 쿠폰입니다.");
        }
        isUsed = false;
    }
}
