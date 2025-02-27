package com.example.spring_webflux.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Configuration
@EnableR2dbcRepositories
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    @Value("${spring.r2dbc.host}")
    private String dbhost;

    @Value("${spring.r2dbc.username}")
    private String dbUsername;

    @Value("${spring.r2dbc.password}")
    private String dbPassword;

    @Value("${spring.r2dbc.driver}")
    private String dbDriver;

    @Value("${spring.r2dbc.database.name}")
    private String database;

    @Value("${spring.r2dbc.port}")
    private  int port;

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(
                builder()
                        .option(DRIVER, dbDriver)
                        .option(HOST, dbhost)
                        .option(PORT, port)
                        .option(USER, dbUsername)
                        .option(PASSWORD, dbPassword)
                        .option(DATABASE, database)
                        .option(SSL, false)
                        .build()
        );
    }
}
