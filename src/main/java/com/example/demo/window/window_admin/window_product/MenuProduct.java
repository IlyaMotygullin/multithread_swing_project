package com.example.demo.window.window_admin.window_product;

import com.example.demo.entity.Product;
import com.example.demo.repository.ShopRepository;
import com.example.demo.window.window_admin.Window;
import com.example.demo.window.window_admin.product_create.CreateProduct;
import com.example.demo.window.window_admin.window_user.MenuUsers;
import com.example.demo.window.window_exception.NullFieldException;
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
public class MenuProduct implements Window {
    //TODO: реализовать удаление товара
    final JFrame window = new JFrame("window product");
    final ShopRepository shopRepository;
    List<Product> productList;

    // features product
    String nameProduct;
    double priceProduct;
    String categoryProduct;

    public MenuProduct(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
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
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(700, 700);
        window.setResizable(false);
        window.setLocationRelativeTo(null);

        // create product list
        Thread getProductList = new Thread(() -> {
            Thread.currentThread().setName("Thread get product list");
            Logger.getLogger(MenuProduct.class.getName()).info(Thread.currentThread().getName() + " start");
            productList = shopRepository.getProductList();
        });
        getProductList.setDaemon(true);
        getProductList.start();
        getProductList.join();

        // print list
        productList.forEach(System.out::println);

        // create main label product
        JLabel labelProduct = createLabel("Список продуктов", 20, 250, 30, 250, 100);
        window.add(labelProduct);

        // create label name product in table
        JLabel labelFirstColumn = createLabel("Название продукта", 20, 400, 30, 60, 260);
        window.add(labelFirstColumn);

        // create label price and category product in table
        JLabel labelSecondColumn = createLabel("Категория и цена товара", 20, 450, 30, 380, 260);
        window.add(labelSecondColumn);

        // name product info data
        JLabel labelInfoNameProduct = createLabel("Название товара:", 20, 350, 30, 60, 350);
        labelInfoNameProduct.setVisible(false);
        window.add(labelInfoNameProduct);

        // name product value
        JLabel labelNameProductValue = createLabel(" ", 20, 300, 30, 60, 390);
        labelNameProductValue.setVisible(false);
        window.add(labelNameProductValue);

        // price product info data
        JLabel labelInfoPriceProduct = createLabel("Цена товара:", 20, 350, 30, 400, 350);
        labelInfoPriceProduct.setVisible(false);
        window.add(labelInfoPriceProduct);

        // price product value
        JLabel labelPriceProductValue = createLabel(" ", 20, 200, 30, 400, 390);
        labelPriceProductValue.setVisible(false);
        window.add(labelPriceProductValue);

        // category product info data
        JLabel labelInfoCategoryProduct = createLabel("Категория товара:", 20, 400, 30, 400, 450);
        labelInfoCategoryProduct.setVisible(false);
        window.add(labelInfoCategoryProduct);

        // category product value
        JLabel labelCategoryProductValue = createLabel(" ", 20, 400, 30, 400, 490);
        labelCategoryProductValue.setVisible(false);
        window.add(labelCategoryProductValue);

        // list product as window component
        JComboBox<Product> productJComboBox = new JComboBox<>(productList.toArray(new Product[0]));
        productJComboBox.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, 15));
        productJComboBox.setSize(500, 50);
        productJComboBox.setBounds(100, 150, productJComboBox.getWidth(), productJComboBox.getHeight());
        productJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product productItem = (Product) productJComboBox.getSelectedItem();
                nameProduct = productItem.getNameProduct();
                priceProduct = productItem.getPriceProduct();
                categoryProduct = productItem.getCategoryProduct();

                // set value name product
                labelInfoNameProduct.setVisible(true);
                labelNameProductValue.setText(nameProduct);
                labelNameProductValue.setVisible(true);

                // set value price product
                labelInfoPriceProduct.setVisible(true);
                labelPriceProductValue.setText(String.valueOf(priceProduct));
                labelPriceProductValue.setVisible(true);

                // set value category product
                labelInfoCategoryProduct.setVisible(true);
                labelCategoryProductValue.setText(categoryProduct);
                labelCategoryProductValue.setVisible(true);
            }
        });
        window.add(productJComboBox);

        // create button check users
        JButton buttonUsers = createButton("Пользователи", 230, 50, 0, 0);
        buttonUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(buttonUsers.getText())) {
                    window.dispose();
                    new MenuUsers(shopRepository).createWindow();
                }
            }
        });
        window.add(buttonUsers);

        // create button check products
        JButton buttonProducts = createButton("Продукты", 230, 50, 230, 0);
        window.add(buttonProducts);

        // create button create product
        JButton buttonCreateButton = createButton("Создать продукты", 230, 50, 460, 0);
        buttonCreateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(buttonCreateButton.getText())) {
                    window.dispose();
                    new CreateProduct(shopRepository).createWindow();
                }
            }
        });
        window.add(buttonCreateButton);

        // create delete product
        JButton deleteProduct = createButton("Удалить", 100, 30, 30, 620);
        deleteProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(deleteProduct.getText())) {
                    if (nameProduct == null && categoryProduct == null && priceProduct == 0) {
                        new NullFieldException("Выберете товар").createWindow();
                    } else {
                        Thread deleteProduct = new Thread(() -> {
                            Thread.currentThread().setName("delete thread");
                            Logger.getLogger(MenuProduct.class.getName()).info(Thread.currentThread().getName() + " start");
                            for (Product product : shopRepository.getProductList()) {
                                if (product.getNameProduct().equals(nameProduct)
                                        && product.getCategoryProduct().equals(categoryProduct)
                                        && product.getPriceProduct() == priceProduct) {

                                    shopRepository.deleteProduct(product.getId());

                                    labelNameProductValue.setText(" ");
                                    labelNameProductValue.setVisible(false);

                                    // set value price product
                                    labelPriceProductValue.setText(" ");
                                    labelPriceProductValue.setVisible(false);

                                    // set value category product
                                    labelCategoryProductValue.setText(" ");
                                    labelCategoryProductValue.setVisible(false);

                                    return;
                                }
                            }
                        });
                        deleteProduct.start();
                    }
                }
            }
        });
        window.add(deleteProduct);

        window.add(new PanelProduct());
        window.setVisible(true);
    }
}
