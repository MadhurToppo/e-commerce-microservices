package com.madhurtoppo.microservices.orderservice.repos;

import com.madhurtoppo.microservices.orderservice.domain.Order;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, UUID> {
}
