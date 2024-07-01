package com.example.demo.repository;

import com.example.demo.entity.Product;
import com.example.demo.entity.User;

import java.util.List;

public interface ShopRepository {
    List<User> getUserList();
    List<Product> getProductList();
    void addProduct(Product product);
    void addUser(User user);
    void addProductByIdForUser(int idUser, int idProduct);
    User getUserById(int idUser);
    void deleteProductById(int idUser, int idProduct);
    void deleteUser(int idUser);
    void deleteProduct(int idProduct);
}
