package com.group.petSitter.domain.coupon.service;

import com.group.petSitter.domain.coupon.Coupon;
import com.group.petSitter.domain.coupon.repository.CouponRepository;
import com.group.petSitter.domain.coupon.repository.UserCouponRepository;
import com.group.petSitter.domain.coupon.service.request.RegisterCouponCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    private final UserCouponRepository userCouponRepository;

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

}
