package com.javaet.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {
    /*We duplicated this class because we can not reach inside the InventoryService
    * in real time projects we should reach this class with jars or some design patterns
    * like adapter design pattern etc.*/
    private String skuCode;
    private boolean isInStock;
}
