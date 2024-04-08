package com.group.petSitter.domain.coupon.service;

import com.group.petSitter.domain.coupon.Coupon;
import com.group.petSitter.domain.coupon.UserCoupon;
import com.group.petSitter.domain.coupon.repository.CouponRepository;
import com.group.petSitter.domain.coupon.repository.UserCouponRepository;
import com.group.petSitter.domain.user.User;
import com.group.petSitter.domain.user.repository.UserRepository;
import com.group.petSitter.domain.user.support.UserFixture;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class CouponServiceTest {
    @InjectMocks
    private CouponService couponService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private UserCouponRepository userCouponRepository;

    User user;
    Coupon coupon;
    UserCoupon userCoupon;

    @BeforeEach
    void setUp() {
        givenUser = UserFixture.user();
        givenCoupon = CouponFixture.coupon();
        givenUserCoupon = UserCouponFixture.userCoupon(givenUser, givenCoupon);
    }

    @Nested
    @DisplayName("createCoupon 메서드 실행 시")
    class CreateCouponTests {

        RegisterCouponCommand registerCouponCommand = CouponFixture.registerCouponCommand();

        @Test
        @DisplayName("성공")
        void success() {
            // Given
            Coupon coupon = givenCoupon;
            when(couponRepository.save(any(Coupon.class))).thenReturn(
                    coupon);

            // When
            couponService.createCoupon(registerCouponCommand);

            // Then
            verify(couponRepository, times(1)).save(any(Coupon.class));
        }
    }

}