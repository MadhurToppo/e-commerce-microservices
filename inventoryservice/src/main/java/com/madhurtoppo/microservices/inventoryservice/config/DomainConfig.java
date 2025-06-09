package com.madhurtoppo.microservices.inventoryservice.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.madhurtoppo.microservices.inventoryservice.domain")
@EnableJpaRepositories("com.madhurtoppo.microservices.inventoryservice.repos")
@EnableTransactionManagement
public class DomainConfig {
}
