package com.javaet.orderservice.dto;

import com.javaet.orderservice.entity.OrderLineItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest{
    private List<OrderLineItemsDto> orderLineItemsDtoList;
}

