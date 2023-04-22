package com.javaet.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    /*It will create a bean of type WebClient and whenever we add a bean annotation
    * this bean will created with the name of the message method name. Whatever method name
    * you have given it will create Bean with this particular name.*/

    /*We have multiple inventory-service instances. Whenever order-service request for inventory service,
    * not understand it which instance to call. Therefore we need LoadBalanced annotation.
    * We have to enable client side load balancing in our eureka clients.*/

    /*It will add client side load balancing capabilities to webclient buider whenever we are
    * creating an instance of webclient using this web client builder it will automatically
    * create the client side load balancer and it will use the client side load balancing to call
    * the inventory service. In this way order service will find multiple instances of the inventory service
    * It won't be confused and it will just try call this inventory service one after another.*/
    @Bean
    @LoadBalanced
    public WebClient.Builder webClient() {

        return WebClient.builder();
    }

}
