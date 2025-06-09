/* (C) 2025 */
package com.madhurtoppo.microservices.productservice.config;

import com.madhurtoppo.microservices.productservice.ProductserviceApplication;
import com.madhurtoppo.microservices.productservice.repos.ProductRepository;
import io.restassured.RestAssured;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StreamUtils;
import org.testcontainers.containers.MongoDBContainer;

/**
 * Abstract base class to be extended by every IT test. Starts the Spring Boot context with a
 * Datasource connected to the Testcontainers Docker instance. The instance is reused for all tests,
 * with all data wiped out before each test.
 */
@SpringBootTest(
    classes = ProductserviceApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
public abstract class BaseIT {

  @ServiceConnection
  private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8.0.9");

  static {
    mongoDBContainer.withReuse(true).start();
  }

  @LocalServerPort public int serverPort;

  @Autowired public TestData testData;

  @Autowired public ProductRepository productRepository;

  @PostConstruct
  public void initRestAssured() {
    RestAssured.port = serverPort;
    RestAssured.urlEncodingEnabled = false;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }

  @BeforeEach
  public void beforeEach() {
    testData.clearAll();
  }

  @SneakyThrows
  public String readResource(final String resourceName) {
    return StreamUtils.copyToString(
        getClass().getResourceAsStream(resourceName), StandardCharsets.UTF_8);
  }
}
