package com.backend.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories("com.backend.backend.repository")
@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration {
}
