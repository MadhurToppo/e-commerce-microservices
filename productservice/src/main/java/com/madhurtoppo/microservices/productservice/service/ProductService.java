/* (C) 2025 */
package com.madhurtoppo.microservices.productservice.service;

import com.madhurtoppo.microservices.productservice.model.ProductDTO;
import java.util.List;
import java.util.UUID;

public interface ProductService {

  List<ProductDTO> findAll();

  ProductDTO get(UUID id);

  UUID create(ProductDTO productDTO);

  void update(UUID id, ProductDTO productDTO);

  void delete(UUID id);
}
