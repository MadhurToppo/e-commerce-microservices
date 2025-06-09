/* (C) 2025 */
package com.madhurtoppo.microservices.productservice.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.madhurtoppo.microservices.productservice.config.BaseIT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ProductResourceTest extends BaseIT {

  @Test
  void getAllProducts_success() {
    testData.product();

    RestAssured.given()
        .accept(ContentType.JSON)
        .when()
        .get("/api/products")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("size()", Matchers.equalTo(2))
        .body("get(0).id", Matchers.equalTo("a9b7ba70-783b-317e-9998-dc4dd82eb3c5"));
  }

  @Test
  void getProduct_success() {
    testData.product();

    RestAssured.given()
        .accept(ContentType.JSON)
        .when()
        .get("/api/products/a9b7ba70-783b-317e-9998-dc4dd82eb3c5")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("name", Matchers.equalTo("Zed diam voluptua."));
  }

  @Test
  void getProduct_notFound() {
    RestAssured.given()
        .accept(ContentType.JSON)
        .when()
        .get("/api/products/23d7c8a0-8b4a-3a1b-87c5-99473f5dddda")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .body("code", Matchers.equalTo("NOT_FOUND"));
  }

  @Test
  void createProduct_success() {
    RestAssured.given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .body(readResource("/requests/productDTORequest.json"))
        .when()
        .post("/api/products")
        .then()
        .statusCode(HttpStatus.CREATED.value());
    assertEquals(1, productRepository.count());
  }

  @Test
  void updateProduct_success() {
    testData.product();

    RestAssured.given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .body(readResource("/requests/productDTORequest.json"))
        .when()
        .put("/api/products/a9b7ba70-783b-317e-9998-dc4dd82eb3c5")
        .then()
        .statusCode(HttpStatus.OK.value());
    assertEquals(
        "Duis autem vel.",
        productRepository
            .findById(UUID.fromString("a9b7ba70-783b-317e-9998-dc4dd82eb3c5"))
            .orElseThrow()
            .getName());
    assertEquals(2, productRepository.count());
  }

  @Test
  void deleteProduct_success() {
    testData.product();

    RestAssured.given()
        .accept(ContentType.JSON)
        .when()
        .delete("/api/products/a9b7ba70-783b-317e-9998-dc4dd82eb3c5")
        .then()
        .statusCode(HttpStatus.NO_CONTENT.value());
    assertEquals(1, productRepository.count());
  }
}
