package com.madhurtoppo.microservices.inventoryservice.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InventoryDTO {

    private Long id;

    @Size(max = 255)
    private String skuCode;

    private Integer quantity;

}
