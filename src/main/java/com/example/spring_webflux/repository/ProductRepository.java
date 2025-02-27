package com.example.spring_webflux.repository;

import com.example.spring_webflux.models.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.r2dbc.repository.Query;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
    Mono<Product> findByName(String name);
    Mono<Product> findById(Long id);
    @NotNull Flux<Product> findAll();

    @NotNull Mono<Void> deleteById(Long id);

    @Query("UPDATE product SET name = :name, price = :price WHERE id = :id")
    Mono<Integer> updateById(Long id, String name, String price);
}
