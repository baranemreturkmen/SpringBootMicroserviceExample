package com.javaet.inventoryservice.controller;

import com.javaet.inventoryservice.dto.InventoryResponse;
import com.javaet.inventoryservice.service.InventoryService;
import lombok. RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    //With PathVariable
    // http://localhost:8082/api/inventory/iphone-13,iphone13-red
    //With RequestParam
    // http://localhost:8082/api/inventory?skuCode-iphone-13&skuCode-iphone13-red
    @GetMapping
    @ResponseStatus (HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        return inventoryService.isInStock(skuCode);
    }
}
