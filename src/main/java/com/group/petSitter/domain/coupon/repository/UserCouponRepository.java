package com.group.petSitter.domain.coupon.repository;

import com.group.petSitter.domain.coupon.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
}
