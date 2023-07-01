package com.javaet.orderservice.controller;

import com.javaet.orderservice.dto.OrderRequest;
import com.javaet.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController()
@RequestMapping("api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    //@LoadBalanced
    //private final WebClient.Builder webClientBuilder;

    //TODO: I think this CircuitBreaker annotation should be in OrderService

    //TODO: Handle better time limitter time out exception
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name="inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){
        /*If you don't use orderService.placeOrder with CompletableFuture.supplyAsync, resilience4j time limiter won't work
        * because with supplyAsync you invoke your method asynchronously also!*/
        return CompletableFuture.supplyAsync(()-> orderService.placeOrder(orderRequest));
    }

    //CompletableFuture -> this will make asynchronous call

    //resilience4j fall back logic -> circuit breaker status will be open

    /*Whatever exception is raised from this particular method will consume the exception into the fallbackMethod
    * fallbackMethod will be executed by circuit breaker*/
    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
        return CompletableFuture.supplyAsync(()-> "Oops! Something went wrong, please order after some time!");
    }

}
