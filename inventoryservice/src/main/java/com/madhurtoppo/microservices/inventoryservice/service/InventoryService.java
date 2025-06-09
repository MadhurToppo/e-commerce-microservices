package com.madhurtoppo.microservices.inventoryservice.service;

import com.madhurtoppo.microservices.inventoryservice.model.InventoryDTO;
import java.util.List;


public interface InventoryService {

    List<InventoryDTO> findAll();

    InventoryDTO get(Long id);

    Long create(InventoryDTO inventoryDTO);

    void update(Long id, InventoryDTO inventoryDTO);

    void delete(Long id);

    boolean isInStock(String skuCode, Integer quantity);

}
