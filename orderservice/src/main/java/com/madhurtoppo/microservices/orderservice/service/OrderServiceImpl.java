package com.madhurtoppo.microservices.orderservice.service;

import com.madhurtoppo.microservices.orderservice.client.InventoryClient;
import com.madhurtoppo.microservices.orderservice.domain.Order;
import com.madhurtoppo.microservices.orderservice.model.OrderDTO;
import com.madhurtoppo.microservices.orderservice.repos.OrderRepository;
import com.madhurtoppo.microservices.orderservice.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public OrderServiceImpl(final OrderRepository orderRepository, InventoryClient inventoryClient) {
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("id"));
        return orders.stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .toList();
    }

    @Override
    public OrderDTO get(final UUID id) {
        return orderRepository.findById(id)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public UUID create(final OrderDTO orderDTO) {
        var isProductInStock = inventoryClient.isInStock(orderDTO.getSkuCode(), orderDTO.getQuantity());
        if (isProductInStock) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        return orderRepository.save(order).getId();
        } else {
            throw new NotFoundException("Product is not in stock");
        }
    }

    @Override
    public void update(final UUID id, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    @Override
    public void delete(final UUID id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setId(order.getId());
        orderDTO.setOrderNumber(order.getOrderNumber());
        orderDTO.setSkuCode(order.getSkuCode());
        orderDTO.setPrice(order.getPrice());
        orderDTO.setQuantity(order.getQuantity());
        return orderDTO;
    }

    private Order mapToEntity(final OrderDTO orderDTO, final Order order) {
        order.setOrderNumber(orderDTO.getOrderNumber());
        order.setSkuCode(orderDTO.getSkuCode());
        order.setPrice(orderDTO.getPrice());
        order.setQuantity(orderDTO.getQuantity());
        return order;
    }

}