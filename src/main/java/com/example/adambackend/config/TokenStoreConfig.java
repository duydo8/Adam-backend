package com.example.adambackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Component;

@Component
public class TokenStoreConfig {
    @Autowired
    BeanConfig beanConfig;
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(beanConfig.dataSource()) ;
    }
}
