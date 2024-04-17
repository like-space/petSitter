package com.group.petSitter.domain.order;

import com.group.petSitter.domain.coupon.UserCoupon;
import com.group.petSitter.domain.payment.Payment;
import com.group.petSitter.domain.user.User;
import com.group.petSitter.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "orders")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private Long petId;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, unique = true)
    private String uuid;

    @Column
    private String address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_coupon_id")
    private UserCoupon userCoupon;

    public Order(final User user, final Long petId) {
        this.user = user;
        this.petId = petId;
        this.uuid = UUID.randomUUID().toString();
        this.price = 50000; // 현재는 5만원으로 고정
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }
}
