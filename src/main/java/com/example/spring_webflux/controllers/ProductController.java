package com.example.spring_webflux.controllers;

import com.example.spring_webflux.models.Product;
import com.example.spring_webflux.servidor.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/allproduct") //Return all products
    public Flux<Product> getAllProducts() {
        return productService.getAllProducts().map(Product::new);
    }

    @GetMapping("/{name}") //Product query by Name
    public Mono<Product> getProductByName(@PathVariable String name){
        return productService.getProductByName(name);
    }

    @PostMapping("/create") //Create a product
    public Mono<Product> createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }

    @PutMapping("/update/{id}") //Update a product
    public Mono<Integer> updateProductById(@PathVariable Long id, @RequestBody Product product){
        return productService.updateProductById(id, product);
    }

    @DeleteMapping("/delete/{id}") //Delete a product
    public Mono<Void> deleteProductById(@PathVariable Long id){
        return productService.deleteProductById(id);
    }

    @GetMapping("/product/{id}") //Consult a product by id
    public Mono<Product> getProductsByIds(@PathVariable Long id) {
        return productService.getProductsById(id);
    }

    @GetMapping("/searchAll") //Get a list of products by their id
    public ResponseEntity<Flux<Product>> searchAll(@RequestParam List<Long> ids) {
        if (ids.size() != 3) { //We validate that ids contains 3 values
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Flux.error(new IllegalArgumentException("The list of IDs must contain exactly 3 elements.")));
        }
        Flux<Product> products = productService.searchAll(ids);
        return ResponseEntity.ok(products);
    }
}
