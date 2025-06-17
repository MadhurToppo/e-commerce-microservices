package com.madhurtoppo.microservices.inventoryservice.service;

import com.madhurtoppo.microservices.inventoryservice.domain.Inventory;
import com.madhurtoppo.microservices.inventoryservice.model.InventoryDTO;
import com.madhurtoppo.microservices.inventoryservice.repos.InventoryRepository;
import com.madhurtoppo.microservices.inventoryservice.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(final InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public List<InventoryDTO> findAll() {
        final List<Inventory> inventories = inventoryRepository.findAll(Sort.by("id"));
        return inventories.stream()
                .map(inventory -> mapToDTO(inventory, new InventoryDTO()))
                .toList();
    }

    @Override
    public InventoryDTO get(final Long id) {
        return inventoryRepository.findById(id)
                .map(inventory -> mapToDTO(inventory, new InventoryDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final InventoryDTO inventoryDTO) {
        final Inventory inventory = new Inventory();
        mapToEntity(inventoryDTO, inventory);
        return inventoryRepository.save(inventory).getId();
    }

    @Override
    public void update(final Long id, final InventoryDTO inventoryDTO) {
        final Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(inventoryDTO, inventory);
        inventoryRepository.save(inventory);
    }

    @Override
    public void delete(final Long id) {
        inventoryRepository.deleteById(id);
    }

    @Override
    public boolean isInStock(String skuCode, Integer quantity) {
        return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
    }

    private InventoryDTO mapToDTO(final Inventory inventory, final InventoryDTO inventoryDTO) {
        inventoryDTO.setId(inventory.getId());
        inventoryDTO.setSkuCode(inventory.getSkuCode());
        inventoryDTO.setQuantity(inventory.getQuantity());
        return inventoryDTO;
    }

    private Inventory mapToEntity(final InventoryDTO inventoryDTO, final Inventory inventory) {
        inventory.setSkuCode(inventoryDTO.getSkuCode());
        inventory.setQuantity(inventoryDTO.getQuantity());
        return inventory;
    }

}
