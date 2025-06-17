package com.madhurtoppo.microservices.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "inventoryservice", url = "http://localhost:8083")
public interface InventoryClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/inventories/stock/{skuCode}/{quantity}")
    boolean isInStock(@PathVariable("skuCode") String skuCode, @PathVariable("quantity") Integer quantity);
}
