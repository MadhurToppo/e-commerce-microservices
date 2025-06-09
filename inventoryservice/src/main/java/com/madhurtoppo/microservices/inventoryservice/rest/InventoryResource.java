package com.madhurtoppo.microservices.inventoryservice.rest;

import com.madhurtoppo.microservices.inventoryservice.model.InventoryDTO;
import com.madhurtoppo.microservices.inventoryservice.service.InventoryService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/inventories", produces = MediaType.APPLICATION_JSON_VALUE)
public class InventoryResource {

    private final InventoryService inventoryService;

    public InventoryResource(final InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAllInventories() {
        return ResponseEntity.ok(inventoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getInventory(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(inventoryService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createInventory(
            @RequestBody @Valid final InventoryDTO inventoryDTO) {
        final Long createdId = inventoryService.create(inventoryDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateInventory(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final InventoryDTO inventoryDTO) {
        inventoryService.update(id, inventoryDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteInventory(@PathVariable(name = "id") final Long id) {
        inventoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stock/{skuCode}/{quantity}")
    public ResponseEntity<Boolean> isInStock(@PathVariable(name = "skuCode") final String skuCode,
            @PathVariable(name = "quantity") final Integer quantity) {
        boolean inStock = inventoryService.isInStock(skuCode, quantity);
        return ResponseEntity.ok(inStock);
    }
}
