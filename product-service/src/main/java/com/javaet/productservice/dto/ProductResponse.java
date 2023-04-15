package com.javaet.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse{
    /*Good practise to seperate entities and dtos. In near future maybe we can add
    * some attributes to model which not important for outside world.*/
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
