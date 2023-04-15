package com.javaet.orderservice.service;

import com.javaet.orderservice.dto.InventoryResponse;
import com.javaet.orderservice.dto.OrderLineItemsDto;
import com.javaet.orderservice.dto.OrderRequest;
import com.javaet.orderservice.entity.Order;
import com.javaet.orderservice.entity.OrderLineItems;
import com.javaet.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional //Spring framework will automatically create and commit the transactions.
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
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

        //Call the inventory-service and place order if product is in stock
        /*Mono data type in reactive framework, to able to read the data from the web
        * client response you have to add this body to mono method and inside this
        * method you have to give type of the response. */
        /*With block webclient will make synchronous request to http://localhost:8082 to the
        * inventory port.*/
        InventoryResponse[] inventoryResponseArray = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve().bodyToMono(InventoryResponse[].class)
                        .block();

        /*allMatch will check whether the isInStock variable is true inside the array or not
        * if all the elements inside the inventoryResponse list contains isInStock it will return
        * true. Even if one of them is false we will get allProductsInStock false*/
        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::isInStock);

        if(allProductsInStock){
            orderRepository.save(order);
        }
        else{
            throw new IllegalArgumentException("Product is not in stock, please try again later!");
        }
    }
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice (orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode (orderLineItemsDto.getSkuCode ());
        return orderLineItems;
    }
}
