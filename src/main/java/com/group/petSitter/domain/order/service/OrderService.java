package com.group.petSitter.domain.order.service;

import com.group.petSitter.domain.coupon.UserCoupon;
import com.group.petSitter.domain.coupon.exception.NotFoundCouponException;
import com.group.petSitter.domain.coupon.repository.UserCouponRepository;
import com.group.petSitter.domain.order.Order;
import com.group.petSitter.domain.order.controller.request.UpdateOrderByCouponCommand;
import com.group.petSitter.domain.order.controller.response.UpdateOrderByCouponResponse;
import com.group.petSitter.domain.order.exception.NotFoundOrderException;
import com.group.petSitter.domain.order.repository.OrderRepository;
import com.group.petSitter.domain.order.service.response.CreateOrderResponse;
import com.group.petSitter.domain.order.service.request.CreateOrdersCommand;
import com.group.petSitter.domain.order.service.response.FindOrderDetailResponse;
import com.group.petSitter.domain.order.service.response.FindOrdersResponse;
import com.group.petSitter.domain.pet.Pet;
import com.group.petSitter.domain.pet.exception.InvalidPetException;
import com.group.petSitter.domain.pet.repository.PetRepository;
import com.group.petSitter.domain.user.User;
import com.group.petSitter.domain.user.exception.NotFoundUserException;
import com.group.petSitter.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private static final Integer PAGE_SIZE = 10;

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final UserCouponRepository userCouponRepository;

    @Transactional
    public CreateOrderResponse createOrder(CreateOrdersCommand createOrdersCommand) {
        User findUser = findUserByUserId(createOrdersCommand.userId());
        Pet orderPet = findPetByUserId(findUser, createOrdersCommand.createOrderRequest().petId());
        Order order = new Order(findUser, orderPet.getPetId());
        orderRepository.save(order);

        return CreateOrderResponse.from(order);
    }

    @Transactional(readOnly = true)
    public FindOrdersResponse findOrders(final Long userId, final Integer page) {
        final Page<Order> pagination =
            orderRepository.findByUser_UserId(userId, PageRequest.of(page, PAGE_SIZE));

        return FindOrdersResponse.of(pagination.getContent(), pagination.getTotalPages());
    }

    @Transactional
    public UpdateOrderByCouponResponse updateOrderByCoupon(
            final UpdateOrderByCouponCommand updateOrderByCouponCommand) {
        Order findOrder = getOrderByOrderIdAndUserId(updateOrderByCouponCommand.orderId(),
            updateOrderByCouponCommand.userId());
        UserCoupon findUserCoupon =
            findUserCouponByIdWithCoupon(updateOrderByCouponCommand.couponId());
        findOrder.setUserCoupon(findUserCoupon);

        return UpdateOrderByCouponResponse.of(findOrder, findUserCoupon.getCoupon());
    }

    @Transactional(readOnly = true)
    public FindOrderDetailResponse findOrderByIdAndUserId(final Long userId, final Long orderId) {
        final Order order = getOrderByOrderIdAndUserId(orderId, userId);
        return FindOrderDetailResponse.of(order);
    }

    private Order getOrderByOrderIdAndUserId(Long orderId, Long userId) {
        return orderRepository.findByOrderIdAndUser_UserId(orderId, userId)
                .orElseThrow(() -> new NotFoundOrderException("존재하지 않는 주문입니다."));
    }

    private User findUserByUserId(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundUserException("존재하지 않는 사용자입니다."));
    }

    private Pet findPetByUserId(final User user, Long petId) {
        return petRepository.findPetByUserAndPetId(user, petId)
                .orElseThrow(()-> new InvalidPetException("해당 반려동물에 접근권한이 없습니다."));
    }

    private UserCoupon findUserCouponByIdWithCoupon(Long UserCouponId) {
        return userCouponRepository.findByIdWithCoupon(UserCouponId)
                .orElseThrow(() -> new NotFoundCouponException("존재하지 않는 쿠폰입니다"));
    }
}
