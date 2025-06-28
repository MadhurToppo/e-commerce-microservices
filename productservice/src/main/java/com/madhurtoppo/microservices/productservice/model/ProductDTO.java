/* (C) 2025 */
package com.madhurtoppo.microservices.productservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

  @Schema(
      description = "Product ID. Not required when creating a product.",
      example = "b3b8c7e2-8c2e-4e7a-9c2e-8c2e4e7a9c2e")
  private UUID id;

  @Size(max = 255)
  private String name;

  @Size(max = 255)
  private String description;

  @Digits(integer = 10, fraction = 2)
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @Schema(type = "string", example = "75.08")
  private BigDecimal price;

  @Size(max = 255)
  private String skuCode;
}
