package com.madhurtoppo.microservices.orderservice.service;

import com.madhurtoppo.microservices.orderservice.model.OrderDTO;
import java.util.List;
import java.util.UUID;


public interface OrderService {

    List<OrderDTO> findAll();

    OrderDTO get(UUID id);

    UUID create(OrderDTO orderDTO);

    void update(UUID id, OrderDTO orderDTO);

    void delete(UUID id);

}