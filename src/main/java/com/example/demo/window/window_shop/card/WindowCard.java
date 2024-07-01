package com.example.demo.window.window_shop.card;

import com.example.demo.entity.Product;
import com.example.demo.repository.ShopRepository;
import com.example.demo.window.window_exception.NullFieldException;
import com.example.demo.window.window_shop.WindowShop;
import com.example.demo.window.window_shop.shop.MainShopWindow;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.logging.Logger;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class WindowCard implements WindowShop {
    final JFrame windowCard = new JFrame("window card");
    final ShopRepository shopRepository;
    final int ID_USER;
    Set<Product> productSet;
    String productName;
    String categoryProduct;
    double priceProduct;
    double resultPriceAllProduct;

    public WindowCard(ShopRepository shopRepository, int idUser) {
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
        // create thread get product by users
        Thread getListProduct = new Thread(() -> {
            Thread.currentThread().setName("Get product list");
            Logger.getLogger(WindowCard.class.getName()).info(Thread.currentThread().getName() + " start");
            productSet = shopRepository.getUserById(ID_USER).getProductSet();
            for (Product product : productSet) {
                resultPriceAllProduct += product.getPriceProduct();
            }
        });
        getListProduct.setDaemon(true);
        getListProduct.start();
        getListProduct.join();

        // setting window
        windowCard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowCard.setSize(700, 700);
        windowCard.setResizable(false);
        windowCard.setLocationRelativeTo(null);

        // result price
        JLabel labelResultPrice = createLabel(String.valueOf(resultPriceAllProduct), 20, 200, 30, 500, 610);
        windowCard.add(labelResultPrice);

        // create button for shop window
        JButton shopButton = createButton("Продукты", 350, 50, 0, 0);
        shopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(shopButton.getText())) {
                    windowCard.dispose();
                    new MainShopWindow(shopRepository, ID_USER).createWindow();
                }
            }
        });
        windowCard.add(shopButton);

        // create button for card window
        JButton cardButton = createButton("Карзина", 350, 50, 350, 0);
        windowCard.add(cardButton);

        // create main label
        JLabel menuCard = createLabel("Мои товары", 20, 200, 30, 290, 130);
        windowCard.add(menuCard);

        // first column label
        JLabel firstColumn = createLabel("Название продукта", 20, 250, 30, 65, 310);
        windowCard.add(firstColumn);

        // second column label
        JLabel secondColumn = createLabel("Цена и категория", 20, 250, 30, 430, 310);
        windowCard.add(secondColumn);

        // result sum label
        JLabel resultSum = createLabel("Общая стоимость товаров: ", 20, 400, 30, 380, 580);
        windowCard.add(resultSum);

        // create info data name product
        JLabel dataNameProduct = createLabel("name product:", 20, 300, 30, 50, 400);
        dataNameProduct.setVisible(false);
        windowCard.add(dataNameProduct);

        // create value name product
        JLabel valueNameProduct = createLabel(" ", 20, 200, 30, 50, 430);
        valueNameProduct.setVisible(false);
        windowCard.add(valueNameProduct);

        // create info data category product
        JLabel dataCategoryProduct = createLabel("category:", 20, 200, 30, 400, 400);
        dataCategoryProduct.setVisible(false);
        windowCard.add(dataCategoryProduct);

        // create value category product
        JLabel valueCategoryProduct = createLabel(" ", 20, 350, 30, 400, 430);
        valueCategoryProduct.setVisible(false);
        windowCard.add(valueCategoryProduct);

        // create info data price product
        JLabel dataPriceProduct = createLabel("price:", 20, 200, 30, 400, 470);
        dataPriceProduct.setVisible(false);
        windowCard.add(dataPriceProduct);

        // create value price product
        JLabel valuePriceProduct = createLabel(" ", 20, 200, 30, 400, 500);
        valuePriceProduct.setVisible(false);
        windowCard.add(valuePriceProduct);

        // add list product as component window
        JComboBox<Product> productJComboBox = new JComboBox<>(productSet.toArray(new Product[0]));
        productJComboBox.setSize(500, 50);
        productJComboBox.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        productJComboBox.setBounds(100, 200, productJComboBox.getWidth(), productJComboBox.getHeight());
        productJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product itemProduct = (Product) productJComboBox.getSelectedItem();
                productName = itemProduct.getNameProduct();
                categoryProduct = itemProduct.getCategoryProduct();
                priceProduct = itemProduct.getPriceProduct();

                // name product
                dataNameProduct.setVisible(true);
                valueNameProduct.setText(productName);
                valueNameProduct.setVisible(true);

                // category product
                dataCategoryProduct.setVisible(true);
                valueCategoryProduct.setText(categoryProduct);
                valueCategoryProduct.setVisible(true);

                // price product
                dataPriceProduct.setVisible(true);
                valuePriceProduct.setText(String.valueOf(priceProduct));
                valuePriceProduct.setVisible(true);

                Product product = new Product(productName, priceProduct, categoryProduct);
                Logger.getLogger(WindowCard.class.getName()).info(String.valueOf(product));
            }
        });
        windowCard.add(productJComboBox);

        // create button delete product by user
        JButton deleteProduct = createButton("Удалить", 100, 30, 30, 580);
        deleteProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(deleteProduct.getText())) {
                    Thread deleteProduct = new Thread(() -> {
                        Thread.currentThread().setName("delete thread");
                        Logger.getLogger(WindowCard.class.getName()).info(Thread.currentThread().getName() + " start");

                        if (productName == null && categoryProduct == null && priceProduct == 0) {
                            Logger.getLogger(WindowCard.class.getName()).info("Не выбран товар");
                            new NullFieldException("Выберете товар").createWindow();
                        } else {
                            for (Product product : productSet) {
                                if (product.getNameProduct().equals(productName)
                                        && product.getCategoryProduct().equals(categoryProduct)
                                        && product.getPriceProduct() == priceProduct) {

                                    // change price label
                                    resultPriceAllProduct -= product.getPriceProduct();
                                    labelResultPrice.setText(String.valueOf(Math.round(resultPriceAllProduct * 100)));

                                    shopRepository.deleteProductById(ID_USER, product.getId());
                                    Logger.getLogger(WindowCard.class.getName()).info("Товар удален!");

                                    // delete name product label
                                    dataNameProduct.setVisible(false);
                                    valueNameProduct.setVisible(false);

                                    // delete category product label
                                    dataCategoryProduct.setVisible(false);
                                    valueCategoryProduct.setVisible(false);

                                    // delete price product label
                                    dataPriceProduct.setVisible(false);
                                    valuePriceProduct.setVisible(false);
                                    return;
                                }
                            }
                        }
                    });
                    deleteProduct.start();
                }
            }
        });
        windowCard.add(deleteProduct);

        windowCard.add(new CardPanel());
        windowCard.setVisible(true);
    }
}
