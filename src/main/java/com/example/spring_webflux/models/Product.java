package com.example.spring_webflux.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.annotation.Id
    private Long id;

    @NotBlank
    @Size(max = 45)
    private String name;

    @NotBlank
    @Size(max = 45)
    private String price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank @Size(max = 45) String getName() {
        return name;
    }

    public void setName(@NotBlank @Size(max = 45) String name) {
        this.name = name;
    }

    public @NotBlank @Size(max = 45) String getPrice() {
        return price;
    }

    public void setPrice(@NotBlank @Size(max = 45) String price) {
        this.price = price;
    }

    public Product(Long id, String name, String price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
    }

    public Product() {
        super();
    }

    @Override
    public String toString() {
        return "Product{id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
