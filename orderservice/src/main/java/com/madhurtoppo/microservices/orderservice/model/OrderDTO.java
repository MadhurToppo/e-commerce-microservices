package com.madhurtoppo.microservices.orderservice.model;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderDTO(
    UUID id,
    String orderNumber,
    String skuCode,
    BigDecimal price,
    Integer quantity,
    UserDetails userDetails
) {
    public record UserDetails(String email, String firstName, String lastName) {
    }
}
