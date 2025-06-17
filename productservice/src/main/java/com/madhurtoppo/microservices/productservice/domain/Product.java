/* (C) 2025 */
package com.madhurtoppo.microservices.productservice.domain;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document("products")
@Getter
@Setter
public class Product {

  @Id private UUID id;

  @Size(max = 255)
  private String name;

  @Size(max = 255)
  private String description;

  @Digits(integer = 10, fraction = 2)
  @Field(targetType = FieldType.DECIMAL128)
  private BigDecimal price;

  // Ensure id is auto-generated if not set
  public void ensureId() {
    if (this.id == null) {
      this.id = UUID.randomUUID();
    }
  }
}
