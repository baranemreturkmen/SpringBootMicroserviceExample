package com.javaet.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    /*It will create a bean of type WebClient and whenever we add a bean annotation
    * this bean will created with the name of the message method name. Whatever method name
    * you have given it will create Bean with this particular name.*/
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

}
