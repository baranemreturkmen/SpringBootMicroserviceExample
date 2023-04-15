package com.javaet.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaet.productservice.dto.ProductRequest;
import com.javaet.productservice.repository.ProductRepository;
import com.mongodb.assertions.Assertions;
import org.junit.jupiter.api. Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers. containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	/*At the start of the integration test first the test will start the mongo db container by downloading the
	* mongo 4.4.2 image then after starting the container it will get replicaSetUrl and it to the spring data
	* mongodb uri property dynamically at the time of creating the test. We have already configuratiıons in the
	* yml file but that configurations are for our local. When we use docker we need this configurations.
	* So all of this we use Container and DynamicPropertySource annotations. DynamicPropertySource will add
	* the property to spring context when we run the test.*/
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

	@Autowired
	private MockMvc mockMvc;

	/*POJO to JSON or vice versa. You can't autowire ObjectMapper directly.*/
	@Autowired
	private MappingJackson2HttpMessageConverter springMvcJacksonConverter;;

	@Autowired
	private ProductRepository productRepository;

	@DynamicPropertySource
	static void setProperties (DynamicPropertyRegistry dymDynamicPropertyRegistry) {
		dymDynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		ObjectMapper objectMapper = springMvcJacksonConverter.getObjectMapper();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON).content(productRequestString))
				.andExpect(status().isCreated());

		Assertions.assertTrue(productRepository.findAll().size() == 1);
	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder ()
				.name("iPhone 13")
				.description("iPhone 13")
				.price(BigDecimal.valueOf(1200))
				.build();
	}

}
