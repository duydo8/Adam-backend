package com.example.adambackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {
    @Bean
    public javax.sql.DataSource dataSource() {
        DriverManagerDataSource dataSource =  new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://103.179.189.73:3306/adam_store");
        dataSource.setUsername("master");
        dataSource.setPassword("Fpoly@2022");
        return (javax.sql.DataSource) dataSource;
    }

}
