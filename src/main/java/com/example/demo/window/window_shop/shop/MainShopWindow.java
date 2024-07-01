package com.example.demo.window.window_shop.shop;

import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.ShopRepository;
import com.example.demo.window.window_exception.NullFieldException;
import com.example.demo.window.window_shop.WindowShop;
import com.example.demo.window.window_shop.card.WindowCard;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Logger;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class MainShopWindow implements WindowShop {
    final JFrame windowShop = new JFrame("window_shop");
    final ShopRepository shopRepository;
    final int ID_USER;
    List<Product> productList;
    String nameProduct;
    String categoryProduct;
    double priceProduct;

    public MainShopWindow(ShopRepository shopRepository, int idUser) {
        this.shopRepository = shopRepository;
        ID_USER = idUser;
    }

    @Override
    public JLabel createLabel(String text, int sizeFont, int width, int height, int x, int y) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, sizeFont));
        label.setSize(width, height);
        label.setBounds(x, y, label.getWidth(), label.getHeight());
        return label;
    }

    @Override
    public JButton createButton(String text, int width, int height, int x, int y) {
        JButton button = new JButton();
        button.setText(text);
        button.setSize(width, height);
        button.setBounds(x, y, button.getWidth(), button.getHeight());
        return button;
    }

    @SneakyThrows
    @Override
    public void createWindow() {
        // thread get product list
        Thread getProductList = new Thread(() -> {
            Thread.currentThread().setName("Thread get products");
            Logger.getLogger(MainShopWindow.class.getName()).info(Thread.currentThread().getName() + " start");
            productList = shopRepository.getProductList();
        });
        getProductList.setDaemon(true);
        getProductList.start();
        getProductList.join();

        productList.forEach(System.out::println);

        // main label in window
        JLabel productLabel = createLabel("Продукты", 20, 100, 50, 290, 130);
        windowShop.add(productLabel);

        // first column label
        JLabel firstColumn = createLabel("Название продукта", 20, 250, 30, 65, 310);
        windowShop.add(firstColumn);

        // second column label
        JLabel secondColumn = createLabel("Цена и категория", 20, 250, 30, 430, 310);
        windowShop.add(secondColumn);

        // create button for shop window
        JButton shopButton = createButton("Продукты", 350, 50, 0, 0);
        windowShop.add(shopButton);

        // create button for card window
        JButton cardButton = createButton("Карзина", 350, 50, 350, 0);
        cardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(cardButton.getText())) {
                    windowShop.dispose();
                    new WindowCard(shopRepository, ID_USER).createWindow();
                }
            }
        });
        windowShop.add(cardButton);

        // create label info name
        JLabel labelInfoNameProduct = createLabel("name product:", 20, 300, 30, 50, 400);
        labelInfoNameProduct.setVisible(false);
        windowShop.add(labelInfoNameProduct);

        // create label name product
        JLabel labelNameProduct = createLabel(" ", 20, 200, 30, 50, 430);
        labelNameProduct.setVisible(false);
        windowShop.add(labelNameProduct);

        // create label info category
        JLabel labelInfoCategoryProduct = createLabel("category:", 20, 200,30, 400, 400);
        labelInfoCategoryProduct.setVisible(false);
        windowShop.add(labelInfoCategoryProduct);

        // create label category product
        JLabel labelCategoryProduct = createLabel(" ", 20, 350, 30, 400, 430);
        labelCategoryProduct.setVisible(false);
        windowShop.add(labelCategoryProduct);

        // create label info price product
        JLabel labelInfoPriceProduct = createLabel("price:", 20, 200, 30, 400, 470);
        labelInfoPriceProduct.setVisible(false);
        windowShop.add(labelInfoPriceProduct);

        // create label price product
        JLabel labelPriceProduct = createLabel(" ", 20, 200,30, 400, 500);
        labelPriceProduct.setVisible(false);
        windowShop.add(labelPriceProduct);

        // add list product as component window
        JComboBox<Product> productJComboBox = new JComboBox<>(productList.toArray(new Product[0]));
        productJComboBox.setSize(500, 50);
        productJComboBox.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        productJComboBox.setBounds(100, 200, productJComboBox.getWidth(), productJComboBox.getHeight());
        // TODO: прописать логику отображения продукта в таблице
        productJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product productItem = (Product) productJComboBox.getSelectedItem();
                nameProduct = productItem.getNameProduct();
                categoryProduct = productItem.getCategoryProduct();
                priceProduct = productItem.getPriceProduct();

                // name product
                labelInfoNameProduct.setVisible(true);
                labelNameProduct.setText(nameProduct);
                labelNameProduct.setVisible(true);

                // category product
                labelInfoCategoryProduct.setVisible(true);
                labelCategoryProduct.setText(categoryProduct);
                labelCategoryProduct.setVisible(true);

                // price product
                labelInfoPriceProduct.setVisible(true);
                labelPriceProduct.setText(String.valueOf(priceProduct));
                labelPriceProduct.setVisible(true);
            }
        });
        windowShop.add(productJComboBox);

        // create button for add product user
        JButton addProduct = createButton("Добавить", 100, 30, 550, 580);
        addProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(addProduct.getText())) {
                    Thread addProductForUser = new Thread(() -> {
                        Thread.currentThread().setName("Add product thread");
                        Logger.getLogger(MainShopWindow.class.getName()).info(Thread.currentThread().getName() + " start");

                        if (nameProduct == null && categoryProduct == null && priceProduct == 0.0) {
                            new NullFieldException("Выберете товар").createWindow();
                        } else {
                            for (User user : shopRepository.getUserList()) {
                                if (user.getId() == ID_USER) {
                                    for (Product product : productList) {
                                        if (product.getCategoryProduct().equals(categoryProduct)
                                                && product.getPriceProduct() == priceProduct
                                                && product.getNameProduct().equals(nameProduct)) {

                                            shopRepository.addProductByIdForUser(ID_USER, product.getId());
                                            Logger.getLogger(MainShopWindow.class.getName()).info("Товар добавлен!");
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    });
                    addProductForUser.start();
                }
            }
        });
        windowShop.add(addProduct);

        // setting window
        windowShop.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowShop.setSize(700, 700);
        windowShop.setResizable(false);
        windowShop.setLocationRelativeTo(null);


        windowShop.add(new ShopPanel());
        windowShop.setVisible(true);
    }
}
