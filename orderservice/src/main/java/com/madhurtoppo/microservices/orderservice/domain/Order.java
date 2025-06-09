package com.madhurtoppo.microservices.orderservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;


@Entity
@Table(name = "Orders")
@Getter
@Setter
public class Order {

    @Id
    @Column(nullable = false, updatable = false, columnDefinition = "char(36)")
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column
    private String orderNumber;

    @Column
    private String skuCode;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column
    private Integer quantity;

}
