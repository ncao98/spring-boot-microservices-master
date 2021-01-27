package com.course.orders.repositories;

import com.course.orders.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;@
public interface OrderRepository extends JpaRepository<Order,Long> {
}
