/* (C) 2025 */
package com.madhurtoppo.microservices.productservice.config;

import com.madhurtoppo.microservices.productservice.domain.Product;
import com.madhurtoppo.microservices.productservice.repos.ProductRepository;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestData {

  @Autowired public ProductRepository productRepository;

  public void clearAll() {
    productRepository.deleteAll();
  }

  public void product() {
    final Product product = new Product();
    product.setId(UUID.fromString("a9b7ba70-783b-317e-9998-dc4dd82eb3c5"));
    product.setName("Zed diam voluptua.");
    product.setDescription("Xonsectetuer adipiscing.");
    product.setPrice(new BigDecimal("71.08"));
    productRepository.save(product);
    final Product product1 = new Product();
    product1.setId(UUID.fromString("b8c37e33-defd-351c-b91e-1e03e51657da"));
    product1.setName("At vero eos.");
    product1.setDescription("Quis nostrud exerci.");
    product1.setPrice(new BigDecimal("72.08"));
    productRepository.save(product1);
  }
}
