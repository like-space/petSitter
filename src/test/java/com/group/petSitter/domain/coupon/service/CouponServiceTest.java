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
import com.group.petSitter.domain.coupon.support.CouponFixture;
import com.group.petSitter.domain.coupon.support.UserCouponFixture;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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

    User givenUser;
    Coupon givenCoupon;
    UserCoupon givenUserCoupon;

    @BeforeEach
    void setUp() {
        givenUser = UserFixture.user();
        givenCoupon = CouponFixture.coupon();
        givenUserCoupon = UserCouponFixture.userCoupon(givenUser, givenCoupon);
    }

    @Nested
    @DisplayName("RegisterUserCoupon 메서드 실행 시")
    class CreateCouponTests {
        RegisterCouponCommand registerCouponCommand = CouponFixture.registerCouponCommand();

        @Test
        @DisplayName("성공")
        void success() {
            // Given
            given(couponRepository.save(any(Coupon.class))).willReturn(givenCoupon);

            // When
            couponService.createCoupon(registerCouponCommand);

            // Then
            verify(couponRepository, times(1)).save(any(Coupon.class));
        }
    }

    @Nested
    @DisplayName("findCoupons 메서드 실행 시")
    class FindCouponsTest {

        @Test
        @DisplayName("성공")
        void success() {
            // given
            ReflectionTestUtils.setField(givenCoupon, "couponId", 1L);
            given(couponRepository.findByEndAtGreaterThanEqual(any())).willReturn(List.of(givenCoupon));

            // when
            FindCouponsResponse findCouponsResponse = couponService.findCoupons();

            // then
            assertThat(findCouponsResponse.coupons())
                .usingRecursiveComparison()
                .isEqualTo(List.of(givenCoupon));
        }
    }

    @Nested
    @DisplayName("registerUserCoupon 메서드 실행 시")
    class RegisterCouponTests {
        RegisterUserCouponCommand registerUserCouponCommand = UserCouponFixture.registerUserCouponCommand();

        @Test
        @DisplayName("성공")
        void success() {
            //given
            given(userCouponRepository.save(any(UserCoupon.class))).willReturn(givenUserCoupon);
            given(couponRepository.findById(any())).willReturn(Optional.ofNullable(givenCoupon));
            given(userRepository.findById(any())).willReturn(Optional.ofNullable(givenUser));
            given(userCouponRepository.existsByUserAndCoupon(any(User.class), any())).willReturn(false);

            //when
            couponService.registerUserCoupon(registerUserCouponCommand);

            //then
            verify(userCouponRepository, times(1)).save(any(UserCoupon.class));
        }

        @Test
        @DisplayName("예외: coupon 이 존재하지 않는 경우, NotFoundCouponException 발생")
        void throwExceptionWhenNotFoundCoupon() {
            //given
            given(couponRepository.findById(any())).willReturn(Optional.empty());
            given(userRepository.findById(any())).willReturn(Optional.ofNullable(givenUser));

            //when
            Exception exception =
                catchException(() -> couponService.registerUserCoupon(registerUserCouponCommand));

            //then
            assertThat(exception).isInstanceOf(NotFoundCouponException.class);
        }

        @Test
        @DisplayName("예외: coupon 이 존재하지 않는 경우, NotFoundCouponException 발생")
        void throwExceptionWhenAlreadyIssuedCoupon() {
            //given
            given(couponRepository.findById((any()))).willReturn(Optional.ofNullable(givenCoupon));
            given(userRepository.findById(any())).willReturn(Optional.ofNullable(givenUser));
            given(userCouponRepository.existsByUserAndCoupon(
                any(User.class), any(Coupon.class))).willReturn(true);

            // When
            Exception exception =
                catchException(() -> couponService.registerUserCoupon(registerUserCouponCommand));

            // Then
            assertThat(exception).isInstanceOf(InvalidCouponException.class);
            assertThat(exception.getMessage()).isEqualTo("이미 발급받은 쿠폰입니다.");
        }
    }

    @Nested
    @DisplayName("findIssuedCoupons 메서드 실행 시")
    class FindIssuedCoupons {

        @Test
        @DisplayName("성공")
        void success() {
            //given
            ReflectionTestUtils.setField(givenUserCoupon, "userCouponId", 1L);
            given(userRepository.findById(any())).willReturn(Optional.ofNullable(givenUser));
            given(userCouponRepository.findByUserAndIsUsedAndCouponEndAtAfter(
                any(), eq(false), any())).willReturn(List.of(givenUserCoupon));

            //when
            FindIssuedCouponsResponse findIssuedCouponsResponse
                = couponService.findIssuedCoupons(1L);

            //then
            assertThat(findIssuedCouponsResponse.coupons())
                .usingRecursiveComparison()
                .isEqualTo(List.of(givenUserCoupon.getCoupon()));
        }
    }
}