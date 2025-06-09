/* (C) 2025 */
package com.madhurtoppo.microservices.productservice.service;

import com.madhurtoppo.microservices.productservice.domain.Product;
import com.madhurtoppo.microservices.productservice.model.ProductDTO;
import com.madhurtoppo.microservices.productservice.repos.ProductRepository;
import com.madhurtoppo.microservices.productservice.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  public ProductServiceImpl(final ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public List<ProductDTO> findAll() {
    final List<Product> products = productRepository.findAll(Sort.by("id"));
    return products.stream().map(product -> mapToDTO(product, new ProductDTO())).toList();
  }

  @Override
  public ProductDTO get(final UUID id) {
    return productRepository
        .findById(id)
        .map(product -> mapToDTO(product, new ProductDTO()))
        .orElseThrow(NotFoundException::new);
  }

  @Override
  public UUID create(final ProductDTO productDTO) {
    final Product product = new Product();
    mapToEntity(productDTO, product);
    product.ensureId(); // Ensure UUID is set if not provided
    return productRepository.save(product).getId();
  }

  @Override
  public void update(final UUID id, final ProductDTO productDTO) {
    final Product product = productRepository.findById(id).orElseThrow(NotFoundException::new);
    mapToEntity(productDTO, product);
    productRepository.save(product);
  }

  @Override
  public void delete(final UUID id) {
    productRepository.deleteById(id);
  }

  private ProductDTO mapToDTO(final Product product, final ProductDTO productDTO) {
    productDTO.setId(product.getId());
    productDTO.setName(product.getName());
    productDTO.setDescription(product.getDescription());
    productDTO.setPrice(product.getPrice());
    return productDTO;
  }

  private Product mapToEntity(final ProductDTO productDTO, final Product product) {
    product.setName(productDTO.getName());
    product.setDescription(productDTO.getDescription());
    product.setPrice(productDTO.getPrice());
    return product;
  }
}
