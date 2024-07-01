package com.example.demo.repository;

import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "shopRepository")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopRepositoryImpl implements ShopRepository {
    final Configuration configuration = new Configuration()
            .addAnnotatedClass(Product.class)
            .addAnnotatedClass(User.class);
    final SessionFactory sessionFactory = configuration.buildSessionFactory();

    @Override
    public List<User> getUserList() {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        List<User> userList = session.createQuery(criteriaQuery.select(userRoot)).getResultList();
        session.getTransaction().commit();
        session.close();
        return userList;
    }

    @Override
    public List<Product> getProductList() {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        List<Product> productList = session.createQuery(criteriaQuery.select(productRoot)).getResultList();
        session.getTransaction().commit();
        session.close();
        return productList;
    }

    @Override
    public void addProduct(Product product) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.persist(product);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void addUser(User user) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.persist(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void addProductByIdForUser(int idUser, int idProduct) {
        // TODO: переделать метод
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        User user = session.get(User.class, idUser);
        Product product = session.get(Product.class, idProduct);
        user.getProductSet().add(product);
        product.getUserSet().add(user);
        session.merge(user);
        session.merge(product);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public User getUserById(int idUser) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        User user = session.get(User.class, idUser);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    @Override
    public void deleteProductById(int idUser, int idProduct) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        User user = session.get(User.class, idUser);
        Product product = session.get(Product.class, idProduct);
        user.getProductSet().remove(product);
        product.getUserSet().remove(user);
        session.merge(user);
        session.merge(product);
        session.getTransaction().commit();
    }

    @Override
    public void deleteUser(int idUser) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        User user = session.get(User.class, idUser);
        for (Product product : user.getProductSet()) {
            product.setUserSet(null);
        }
        session.remove(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteProduct(int idProduct) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Product product = session.get(Product.class, idProduct);
        for (User user : product.getUserSet()) {
            user.setProductSet(null);
        }
        session.remove(product);
        session.getTransaction().commit();
        session.close();
    }
}
