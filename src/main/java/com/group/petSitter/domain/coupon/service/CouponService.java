package com.group.petSitter.domain.coupon.service;

import com.group.petSitter.domain.coupon.Coupon;
import com.group.petSitter.domain.coupon.UserCoupon;
import com.group.petSitter.domain.coupon.exception.InvalidCouponException;
import com.group.petSitter.domain.coupon.exception.NotFoundCouponException;
import com.group.petSitter.domain.coupon.repository.CouponRepository;
import com.group.petSitter.domain.coupon.repository.UserCouponRepository;
import com.group.petSitter.domain.coupon.service.request.RegisterCouponCommand;
import com.group.petSitter.domain.coupon.service.request.RegisterUserCouponCommand;
import com.group.petSitter.domain.coupon.service.response.FindCouponsResponse;
import com.group.petSitter.domain.coupon.service.response.FindIssuedCouponsResponse;
import com.group.petSitter.domain.user.User;
import com.group.petSitter.domain.user.exception.NotFoundUserException;
import com.group.petSitter.domain.user.repository.UserRepository;
import com.group.petSitter.domain.user.service.request.RegisterUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.Nested;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final UserRepository userRepository;

    /**
     * 관리자가 쿠폰 등록
     * @param command
     * @return couponId
     */
    @Transactional
    public Long createCoupon(RegisterCouponCommand command) {
        Coupon coupon = Coupon.builder()
                .name(command.name())
                .discount(command.discount())
                .description(command.description())
                .endAt(command.endAt())
                .build();

        return couponRepository.save(coupon).getCouponId();
    }

    @Transactional(readOnly = true)
    public FindCouponsResponse findCoupons() {
        List<Coupon> findCoupons =
            couponRepository.findByEndAtGreaterThanEqual(LocalDate.now());

        return FindCouponsResponse.from(findCoupons);
    }

    @Transactional
    public Long registerUserCoupon(RegisterUserCouponCommand command) {
        User findUser = findUserByUserId(command.userId());
        Coupon findCoupon = findCouponByCouponId(command.couponId());

        validateCouponExpiration(findCoupon.getEndAt());
        validateAlreadyIssuedCoupon(findUser, findCoupon);

        UserCoupon userCoupon = new UserCoupon(findUser, findCoupon);
        return userCouponRepository.save(userCoupon).getUserCouponId();
    }

    @Transactional(readOnly = true)
    public FindIssuedCouponsResponse findIssuedCoupons(Long userId) {
        User findUser = findUserByUserId(userId);
        List<UserCoupon> findUserCoupons = userCouponRepository.findByUserAndIsUsedAndCouponEndAtAfter(
            findUser, false,
            LocalDate.now());

        return FindIssuedCouponsResponse.from(findUserCoupons);
    }

    private void validateAlreadyIssuedCoupon(User user, Coupon coupon) {
        if (userCouponRepository.existsByUserAndCoupon(user, coupon)) {
            throw new InvalidCouponException("이미 발급받은 쿠폰입니다.");
        }
    }

    private void validateCouponExpiration(LocalDate endAt) {
        if (endAt.isBefore(LocalDate.now())) {
            throw new InvalidCouponException("쿠폰이 이미 만료되었습니다");
        }
    }

    private User findUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException("존재하지 않는 사용자입니다."));
    }

    private Coupon findCouponByCouponId(Long couponId) {
        return couponRepository.findById(couponId)
            .orElseThrow(() -> new NotFoundCouponException("존재하지 않은 쿠폰입니다."));
    }

}
