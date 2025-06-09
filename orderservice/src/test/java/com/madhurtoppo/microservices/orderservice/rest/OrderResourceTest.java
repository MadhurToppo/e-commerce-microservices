package com.madhurtoppo.microservices.orderservice.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.madhurtoppo.microservices.orderservice.config.BaseIT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;


public class OrderResourceTest extends BaseIT {

    @Test
    @Sql("/data/orderData.sql")
    void getAllOrders_success() {
        RestAssured
                .given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/orders")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", Matchers.equalTo(2))
                .body("get(0).id", Matchers.equalTo("a9b7ba70-783b-317e-9998-dc4dd82eb3c5"));
    }

    @Test
    @Sql("/data/orderData.sql")
    void getOrder_success() {
        RestAssured
                .given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/orders/a9b7ba70-783b-317e-9998-dc4dd82eb3c5")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("orderNumber", Matchers.equalTo("Vel illum dolore."));
    }

    @Test
    void getOrder_notFound() {
        RestAssured
                .given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/orders/23d7c8a0-8b4a-3a1b-87c5-99473f5dddda")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createOrder_success() {
        RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(readResource("/requests/orderDTORequest.json"))
                .when()
                .post("/api/orders")
                .then()
                .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, orderRepository.count());
    }

    @Test
    @Sql("/data/orderData.sql")
    void updateOrder_success() {
        RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(readResource("/requests/orderDTORequest.json"))
                .when()
                .put("/api/orders/a9b7ba70-783b-317e-9998-dc4dd82eb3c5")
                .then()
                .statusCode(HttpStatus.OK.value());
        assertEquals("Xed diam nonumy.", orderRepository.findById(UUID.fromString("a9b7ba70-783b-317e-9998-dc4dd82eb3c5")).orElseThrow().getOrderNumber());
        assertEquals(2, orderRepository.count());
    }

    @Test
    @Sql("/data/orderData.sql")
    void deleteOrder_success() {
        RestAssured
                .given()
                .accept(ContentType.JSON)
                .when()
                .delete("/api/orders/a9b7ba70-783b-317e-9998-dc4dd82eb3c5")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, orderRepository.count());
    }

}