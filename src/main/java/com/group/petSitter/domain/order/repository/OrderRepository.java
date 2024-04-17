package com.group.petSitter.domain.order.repository;

import com.group.petSitter.domain.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByUser_UserId(Long userId, PageRequest of);
}
