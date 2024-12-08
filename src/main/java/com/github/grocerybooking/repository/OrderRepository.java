package com.github.grocerybooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.grocerybooking.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
