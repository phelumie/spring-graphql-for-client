package com.ajisegiri.graphql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.client.HttpGraphQlClient;

@Configuration(proxyBeanMethods = false)
public class Config {
    @Bean
    public HttpGraphQlClient httpGraphQlClient(){
        return HttpGraphQlClient.builder().url("https://countries.trevorblades.com/graphql")
                .build();
    }
}
