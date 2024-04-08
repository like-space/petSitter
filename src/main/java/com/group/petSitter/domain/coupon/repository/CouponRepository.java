package com.group.petSitter.domain.coupon.repository;

import com.group.petSitter.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
