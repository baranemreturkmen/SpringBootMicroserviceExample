package com.javaet.orderservice.service;

import com.javaet.orderservice.dto.InventoryResponse;
import com.javaet.orderservice.dto.OrderLineItemsDto;
import com.javaet.orderservice.dto.OrderRequest;
import com.javaet.orderservice.entity.Order;
import com.javaet.orderservice.entity.OrderLineItems;
import com.javaet.orderservice.event.OrderPlacedEvent;
import com.javaet.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional //Spring framework will automatically create and commit the transactions.
public class OrderService {

    private final OrderRepository orderRepository;
    //@LoadBalanced
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;

    //Make a call to kafka cluster whenever order is placed.
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map (OrderLineItems::getSkuCode)
                .toList();

        //Create your own span names with sleuth tracer

        Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");

        try(Tracer.SpanInScope spanInScope = tracer.withSpan(inventoryServiceLookup.start())){
            /*allMatch will check whether the isInStock variable is true inside the array or not
             * if all the elements inside the inventoryResponse list contains isInStock it will return
             * true. Even if one of them is false we will get allProductsInStock false*/
            boolean allProductsInStock = Arrays.stream(Objects.requireNonNull(getWebClientBuilder(skuCodes).block()))
                    .allMatch(InventoryResponse::isInStock);

            if(allProductsInStock){
                orderRepository.save(order);
                //Send message to kafka topic.
                kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
                return "Order Placed Successfully";
            }
            else{
                throw new IllegalArgumentException("Product is not in stock, please try again later!");
            }
        } finally{
            inventoryServiceLookup.end();
        }
    }
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice (orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode (orderLineItemsDto.getSkuCode ());
        return orderLineItems;
    }

    public Mono<InventoryResponse[]> getWebClientBuilder(List<String> skuCodes){
        //Call the inventory-service and place order if product is in stock
        /*Mono data type in reactive framework, to able to read the data from the web
         * client response you have to add this body to mono method and inside this
         * method you have to give type of the response. */
        /*With block webclient will make synchronous request to http://localhost:8082 to the
         * inventory port.*/
        return webClientBuilder.build().get()
                .uri("http://localhost:8083/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve().bodyToMono(InventoryResponse[].class);

    }
}
