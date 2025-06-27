package com.madhurtoppo.microservices.orderservice.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.bind.annotation.PathVariable;

public interface InventoryClient {

    Logger log = LoggerFactory.getLogger(InventoryClient.class);

    @GetExchange("/api/inventories/stock/{skuCode}/{quantity}")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory")
    boolean isInStock(@PathVariable("skuCode") String skuCode, @PathVariable("quantity") Integer quantity);

    default boolean fallbackMethod(String skuCode, Integer quantity, Throwable throwable) {
        log.info("Cannot get inventory for skuCode: {}, for quantity: {}, error: {}", skuCode, quantity, throwable.getMessage());
        return false;
    }
}
