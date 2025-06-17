/* (C) 2025 */
package com.madhurtoppo.microservices.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.mapping.event.ValidatingEntityCallback;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@EnableMongoRepositories("com.madhurtoppo.microservices.productservice.repos")
public class MongoConfig {

  @Bean
  public MongoTransactionManager transactionManager(final MongoDatabaseFactory databaseFactory) {
    return new MongoTransactionManager(databaseFactory);
  }

  @Bean
  public ValidatingEntityCallback validatingEntityCallback(
      final LocalValidatorFactoryBean factory) {
    return new ValidatingEntityCallback(factory);
  }
}
