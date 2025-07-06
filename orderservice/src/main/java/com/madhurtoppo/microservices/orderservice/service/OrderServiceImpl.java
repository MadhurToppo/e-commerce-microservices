package com.madhurtoppo.microservices.orderservice.service;

import com.madhurtoppo.microservices.orderservice.client.InventoryClient;
import com.madhurtoppo.microservices.orderservice.domain.Order;
import com.madhurtoppo.microservices.orderservice.event.OrderPlacedEvent;
import com.madhurtoppo.microservices.orderservice.model.OrderDTO;
import com.madhurtoppo.microservices.orderservice.repos.OrderRepository;
import com.madhurtoppo.microservices.orderservice.util.NotFoundException;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Override
    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("id"));
        return orders.stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public OrderDTO get(final UUID id) {
        return orderRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public UUID create(final OrderDTO orderDTO) {
        var isProductInStock = inventoryClient.isInStock(orderDTO.skuCode(), orderDTO.quantity());
        if (isProductInStock) {
            Order order = new Order();
            mapToEntity(orderDTO, order);
            Order saved = orderRepository.save(order);

            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
            orderPlacedEvent.setOrderNumber(saved.getOrderNumber());
            orderPlacedEvent.setEmail(orderDTO.userDetails().email());
            orderPlacedEvent.setFirstName(orderDTO.userDetails().firstName());
            orderPlacedEvent.setLastName(orderDTO.userDetails().lastName());
            log.info("Start -Sending OrderPlaced {} to topic order-placed", orderPlacedEvent);
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            log.info("End - Sending OrderPlaced {} to topic order-placed", orderPlacedEvent);

        return saved.getId();
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

    private OrderDTO mapToDTO(final Order order) {
        return new OrderDTO(order.getId(), order.getOrderNumber(), order.getSkuCode(), order.getPrice(), order.getQuantity(), null);
    }

    private void mapToEntity(final OrderDTO orderDTO, final Order order) {
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setSkuCode(orderDTO.skuCode());
        order.setPrice(orderDTO.price());
        order.setQuantity(orderDTO.quantity());
    }

}
