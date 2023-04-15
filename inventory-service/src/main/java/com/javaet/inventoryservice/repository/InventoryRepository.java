package com.javaet.inventoryservice.repository;

import com.javaet.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    /*We have to find all the inventories with this given skuCode*/
    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
