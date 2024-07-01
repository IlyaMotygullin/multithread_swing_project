package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "product")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @Column(name = "id_product")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name_product")
    String nameProduct;

    @Column(name = "price_product")
    double priceProduct;

    @Column(name = "category_product")
    String categoryProduct;

    @ManyToMany
    @JoinTable(
            name = "user_product",
            joinColumns = @JoinColumn(name = "id_product"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    Set<User> userSet;

    public Product(String nameProduct, double priceProduct, String categoryProduct) {
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.categoryProduct = categoryProduct;
    }

    @Override
    public String toString() {
        return "name: " + this.nameProduct + "; " + "price: " + this.priceProduct + "; " + "category: " + this.categoryProduct;
    }
}
