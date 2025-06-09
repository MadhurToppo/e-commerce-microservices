/* (C) 2025 */
package com.madhurtoppo.microservices.productservice.repos;

import com.madhurtoppo.microservices.productservice.domain.Product;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, UUID> {}
