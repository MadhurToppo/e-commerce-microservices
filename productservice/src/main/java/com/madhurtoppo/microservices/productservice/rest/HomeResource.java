/* (C) 2025 */
package com.madhurtoppo.microservices.productservice.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {

  @GetMapping("/")
  public String index() {
    return "\"Hello World!\"";
  }
}
