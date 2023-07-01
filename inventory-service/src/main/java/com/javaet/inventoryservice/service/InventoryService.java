package com.javaet.inventoryservice.service;

import com.javaet.inventoryservice.dto.InventoryResponse;
import com.javaet.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    /*Don't use SneakyThrows in production environment because the sneaky throw concept allows us to throw
      any checked exception without defining it explicitly in the method signature. This allows the omission
      of the throws declaration, effectively imitating the characteristics of a runtime exception.*/

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock (List<String> skuCode) {
        //For testing slow connection problems with circuit breaker
        /*log.info("Wait Started");
        Thread.sleep(10000);
        log.info ("Wait Ended");*/
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                     InventoryResponse.builder().skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity() > 0)
                            .build()
                ).toList();
    }
}
