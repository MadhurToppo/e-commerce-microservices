package com.madhurtoppo.microservices.inventoryservice.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.madhurtoppo.microservices.inventoryservice.config.BaseIT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;


public class InventoryResourceTest extends BaseIT {

    @Test
    @Sql("/data/inventoryData.sql")
    void getAllInventories_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/inventories")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).id", Matchers.equalTo(1000));
    }

    @Test
    @Sql("/data/inventoryData.sql")
    void getInventory_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/inventories/1000")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("skuCode", Matchers.equalTo("Duis autem vel."));
    }

    @Test
    void getInventory_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/inventories/1666")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createInventory_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/inventoryDTORequest.json"))
                .when()
                    .post("/api/inventories")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, inventoryRepository.count());
    }

    @Test
    @Sql("/data/inventoryData.sql")
    void updateInventory_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/inventoryDTORequest.json"))
                .when()
                    .put("/api/inventories/1000")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("Eget est lorem.", inventoryRepository.findById(((long)1000)).orElseThrow().getSkuCode());
        assertEquals(2, inventoryRepository.count());
    }

    @Test
    @Sql("/data/inventoryData.sql")
    void deleteInventory_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/inventories/1000")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, inventoryRepository.count());
    }

}
