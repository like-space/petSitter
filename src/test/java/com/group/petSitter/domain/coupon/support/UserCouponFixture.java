package com.group.petSitter.domain.coupon.support;

import com.group.petSitter.domain.coupon.Coupon;
import com.group.petSitter.domain.coupon.UserCoupon;
import com.group.petSitter.domain.coupon.service.request.RegisterUserCouponCommand;
import com.group.petSitter.domain.user.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserCouponFixture {

    private static final Long userId = 1L;

    private static final Long couponId = 1L;

    public static UserCoupon userCoupon(User user, Coupon coupon) {
        return new UserCoupon(user, coupon);
    }

    public static RegisterUserCouponCommand registerUserCouponCommand() {
        return new RegisterUserCouponCommand(userId, couponId);
    }
}
