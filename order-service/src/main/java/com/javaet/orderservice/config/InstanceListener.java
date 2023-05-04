package com.javaet.orderservice.config;

import com.javaet.orderservice.dto.InventoryResponse;
import com.javaet.orderservice.dto.OrderRequest;
import com.javaet.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class InstanceListener implements ApplicationListener<HeartbeatEvent> {

    @Autowired
    private OrderService orderService;

    @Override
    public void onApplicationEvent(HeartbeatEvent event) {
        List<String> skuCodes = new ArrayList<>();
        skuCodes.add("1");

        Mono<InventoryResponse[]> result = orderService.getWebClientBuilder(skuCodes);
        result.subscribe(
                value -> System.out.println(value),
                error -> error.printStackTrace()
        );

    }
}
