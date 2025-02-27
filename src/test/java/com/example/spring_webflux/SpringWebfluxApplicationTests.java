package com.example.spring_webflux;

import com.example.spring_webflux.models.Product;
import com.example.spring_webflux.servidor.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SpringWebfluxApplicationTests {

	@MockBean
	private ProductService productService;

	private Product product1;
	private Product product2;
	private Product product3;
	private WebTestClient webTestClient;

	@BeforeEach
	void setUp() {
		product1 = new Product(1L,"Product 1",  "100.0");
		product2 = new Product(2L,"Product 2",  "500.0");
		product3 = new Product(5L,"Product 5",  "700.0");
		MockitoAnnotations.openMocks(this);
		webTestClient = WebTestClient.bindToServer()
				.baseUrl("http://localhost:8080/api/products")
				.build();
	}

	@Test
	void contextLoads() {
	}
	@Test
	public void testGetAllProducts(){
		// Configure simulated behavior
		when(productService.getAllProducts())
				.thenReturn(Flux.just(product1, product2,product3));

		// Call the getAllProducts method of the service and check the result
		Flux<Product> result = productService.getAllProducts();

        // Use StepVerifier to verify the result
		StepVerifier.create(result)
				.expectNext(product1)
				.expectNext(product2)
				.expectNext(product3)
				.verifyComplete();

	}

	@Test
	public void testGetProductByName(){

		// Configure simulated behavior
		when(productService.getProductByName(product1.getName())).thenReturn(Mono.just(product1));

		// Call the getProductByName method of the service and check the result
		Mono<Product> result = productService.getProductByName(product1.getName());

		// Use StepVerifier to verify the result
		StepVerifier.create(result)
				.expectNextMatches(product -> product.getName().equals(product1.getName()))
				.verifyComplete();
	}

	@Test
	public void TestCreateProduct(){

		// Configure simulated behavior
		when(productService.createProduct(product3)).thenReturn(Mono.just(product3));

		// Call the createProduct method of the service and check the result
		Mono<Product> result = productService.createProduct(product3);

		// Use StepVerifier to verify the result
		StepVerifier.create(result)
				.expectNextMatches(product ->
						product.getId().equals(5L) &&
						product.getName().equals(product3.getName()) &&
						product.getPrice().equals(product3.getPrice()))
				.verifyComplete();

	}

	@Test
	public void testUpdateProductById(){
		Long id = 2L;
		// Configure simulated behavior
		when(productService.updateProductById(id, product2))
				.thenReturn(Mono.just(1));

		// Calling the controller's put updateProductById method
		webTestClient.put().uri("/update/"+ id)
				.bodyValue(product2)
				.exchange()
				.expectStatus().isOk()
				.expectBody(Integer.class)
				.isEqualTo(1);
	}

	@Test
	public void testDeleteProductById(){
		Long id = 1L;
		// Configure simulated behavior
		when(productService.deleteProductById(id)).thenReturn(Mono.empty());

		// Calling the deleteProductById method of the service
		Mono<Void> result = productService.deleteProductById(id);

		// Use StepVerifier to verify the result
		StepVerifier.create(result).verifyComplete();
	}

	@Test
	public void testSearchAll() {
		List<Long> ids = List.of(1L,2L,3L);
		Flux<Product> products = Flux.just(product1,product2,product3);

		// Configure simulated behavior
		when(productService.searchAll(ids)).thenReturn(products);

		// Calling the put searchAll method of the controller
		webTestClient.get().uri(uriBuilder -> uriBuilder
						.path("/searchAll")
						.queryParam("ids", "1,2,3")
						.build())
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(Product.class)
				.consumeWith(response -> {
					List<Product> productList = response.getResponseBody();
					assert productList != null;
					assert productList.size() == 3;
					assert productList.get(0).getId().equals(1L);
					assert productList.get(1).getId().equals(2L);
					assert productList.get(2).getId().equals(3L);
				});
	}
}
