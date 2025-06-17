/* (C) 2025 */
package com.madhurtoppo.microservices.productservice.rest;

import com.madhurtoppo.microservices.productservice.model.ProductDTO;
import com.madhurtoppo.microservices.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
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
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductResource {

  private final ProductService productService;

  public ProductResource(final ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public ResponseEntity<List<ProductDTO>> getAllProducts() {
    return ResponseEntity.ok(productService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDTO> getProduct(@PathVariable(name = "id") final UUID id) {
    return ResponseEntity.ok(productService.get(id));
  }

  @PostMapping
  @ApiResponse(responseCode = "201")
  public ResponseEntity<UUID> createProduct(@RequestBody @Valid final ProductDTO productDTO) {
    final UUID createdId = productService.create(productDTO);
    return new ResponseEntity<>(createdId, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UUID> updateProduct(
      @PathVariable(name = "id") final UUID id, @RequestBody @Valid final ProductDTO productDTO) {
    productService.update(id, productDTO);
    return ResponseEntity.ok(id);
  }

  @DeleteMapping("/{id}")
  @ApiResponse(responseCode = "204")
  public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") final UUID id) {
    productService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
