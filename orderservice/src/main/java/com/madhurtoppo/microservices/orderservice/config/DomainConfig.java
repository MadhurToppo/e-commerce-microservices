package com.madhurtoppo.microservices.orderservice.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.madhurtoppo.microservices.orderservice.domain")
@EnableJpaRepositories("com.madhurtoppo.microservices.orderservice.repos")
@EnableTransactionManagement
public class DomainConfig {
}
