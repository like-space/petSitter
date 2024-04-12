package com.group.petSitter.domain.coupon.repository;

import com.group.petSitter.domain.coupon.Coupon;
import com.group.petSitter.domain.coupon.UserCoupon;
import com.group.petSitter.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    boolean existsByUserAndCoupon(User user, Coupon coupon);

    @Query("SELECT uc FROM UserCoupon uc JOIN FETCH uc.coupon c "
        + "WHERE uc.user = :user AND uc.isUsed = :isUsed AND c.endAt >= :currentDate")
    List<UserCoupon> findByUserAndIsUsedAndCouponEndAtAfter(
        @Param("user") User findUser,
        @Param("isUsed") boolean isUsed,
        @Param("currentDate") LocalDate currentDate);
}
