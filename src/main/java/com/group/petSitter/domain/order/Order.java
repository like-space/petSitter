package com.group.petSitter.domain.order;

import com.group.petSitter.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String uuid = UUID.randomUUID().toString();

    @Builder
    public Order(OrderStatus orderStatus, User user, String uuid) {
        this.orderStatus = orderStatus;
        this.user = user;
        this.uuid = uuid;
    }
}
