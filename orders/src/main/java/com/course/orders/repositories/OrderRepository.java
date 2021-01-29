package com.course.orders.repositories;

import com.course.orders.domain.Orderz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orderz,Long> {
}
