package com.example.spring_webflux.servidor;

import com.example.spring_webflux.models.Product;
import com.example.spring_webflux.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private WebClient webClient;

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Mono<Product> getProductByName(String name){
        return productRepository.findByName(name);
    }

    public Mono<Product> createProduct(Product product){
        return productRepository.save(product);
    }

    public Mono<Integer> updateProductById(Long id, Product product){
        return productRepository.findById(id)
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .flatMap(optionalProduct -> {
                    if (optionalProduct.isPresent()) {
                        return productRepository.updateById(id, product.getName(), product.getPrice()).thenReturn(1);
                    }

                    return Mono.just(0); // Returns 0 when the id is not found
                });
    }

    public Mono<Void> deleteProductById(Long id){
        return productRepository.deleteById(id);
    }

    public Mono<Product> getProductsById(Long id) {
        return productRepository.findById(id);
    }

    public Flux<Product> searchAll(List<Long> ids) {

        //WebClient cliente = WebClient.create("http://localhost:8080/api/product");
        String URI_ALL_PRODUCT = "/product/";
        Mono<Product> product = webClient.get().uri(URI_ALL_PRODUCT + ids.get(0)).retrieve().bodyToMono(Product.class);
        Mono<Product> product2 = webClient.get().uri(URI_ALL_PRODUCT + ids.get(1)).retrieve().bodyToMono(Product.class);
        Mono<Product> product3 = webClient.get().uri(URI_ALL_PRODUCT + ids.get(2)).retrieve().bodyToMono(Product.class);

        return Flux.merge(product, product2, product3)
                .sort(Comparator.comparing(Product::getId));// Sort by ID
    }
}
