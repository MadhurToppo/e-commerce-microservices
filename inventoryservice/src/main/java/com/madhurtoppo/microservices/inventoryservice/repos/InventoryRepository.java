package com.madhurtoppo.microservices.inventoryservice.repos;

import com.madhurtoppo.microservices.inventoryservice.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsBySkuCodeAndQuantityIsGreaterThanEqual(String skuCode, Integer quantity);
}
