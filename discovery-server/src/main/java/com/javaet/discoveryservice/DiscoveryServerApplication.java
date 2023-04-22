package com.javaet.discoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/*In real time projects we don't have discovery-server as another module. When Microservices run
* we don't need run discovery-server externally, discovery-server and discovery configurations run
* internally and automatically.*/

//TODO: Check out pm codes in our company, how eureka run in pm internally and automatically!

/*Maybe pm sees local copy of instances on client side registry? No. Because we at first time
* we try to start pm we need to go discovery-server eureka at least one time.*/

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }
}
