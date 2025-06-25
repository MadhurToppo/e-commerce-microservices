package com.madhurtoppo.microservices.orderservice.client;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.bind.annotation.PathVariable;

public interface InventoryClient {

    @GetExchange("/api/inventories/stock/{skuCode}/{quantity}")
    boolean isInStock(@PathVariable("skuCode") String skuCode, @PathVariable("quantity") Integer quantity);
}
