package com.javaet.inventoryservice;

import com.javaet.inventoryservice.model.Inventory;
import com.javaet.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	//Method injection
	//When the program start this objects will created.
	@Bean
	public CommandLineRunner LoadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("iphone_13");
			inventory.setQuantity (100);

			Inventory inventory1 = new Inventory ();
			inventory1.setSkuCode("iphone_13_red");
			inventory1.setQuantity (0);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};
	}

}
