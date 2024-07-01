package com.example.demo.window.window_admin.product_create;

import com.example.demo.entity.Product;
import com.example.demo.repository.ShopRepository;
import com.example.demo.window.window_admin.Window;
import com.example.demo.window.window_admin.window_product.MenuProduct;
import com.example.demo.window.window_admin.window_user.MenuUsers;
import com.example.demo.window.window_exception.ExceptionWindow;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateProduct implements Window {
    final JFrame createProduct = new JFrame("create product");
    final ShopRepository shopRepository;
    String nameProducts;
    double priceProduct;
    String categoryProduct;

    public CreateProduct(ShopRepository shopRepository) {
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
    private JTextField createTextField(int sizeFont, int width, int height, int x, int y) {
        JTextField field = new JTextField();
        field.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, sizeFont));
        field.setSize(width, height);
        field.setBounds(x, y, field.getWidth(), field.getHeight());
        return field;
    }

    @Override
    public void createWindow() {
        createProduct.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createProduct.setSize(700, 700);
        createProduct.setResizable(false);
        createProduct.setLocationRelativeTo(null);

        // create main label
        JLabel labelCreateProduct = createLabel("Создать продукт", 20, 350, 30, 250, 120);
        createProduct.add(labelCreateProduct);

        // create label field name
        JLabel labelCreateNameProduct = createLabel("Введите название продукта", 15, 400, 20, 100, 190);
        createProduct.add(labelCreateNameProduct);

        // create field for name product
        JTextField fieldName = createTextField(15, 450, 30, 100, 210);
        createProduct.add(fieldName);

        // create button for input name
        JButton inputNameButton = createButton("Ввести", 100, 30, 560, 210);
        inputNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(inputNameButton.getText())) {
                    nameProducts = fieldName.getText();
                    Logger.getLogger(CreateProduct.class.getName()).info(nameProducts);
                }
            }
        });
        createProduct.add(inputNameButton);

        // create label field price product
        JLabel labelCreatePriceProduct = createLabel("Введите стоимость товара", 15, 400, 20, 100, 270);
        createProduct.add(labelCreatePriceProduct);

        // create field for price product
        JTextField fieldPrice = createTextField(15, 450, 30, 100, 290);
        createProduct.add(fieldPrice);

        // create button for input price
        JButton inputPriceButton = createButton("Ввести", 100, 30, 560, 290);
        inputPriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(inputPriceButton.getText())) {
                    priceProduct = Double.parseDouble(fieldPrice.getText());
                    Logger.getLogger(CreateProduct.class.getName()).info(String.valueOf(priceProduct));
                }
            }
        });
        createProduct.add(inputPriceButton);

        // create label field category product
        JLabel labelCreateCategoryProduct = createLabel("Введите категорию товара", 15, 400, 20, 100, 360);
        createProduct.add(labelCreateCategoryProduct);

        // create field for category product
        JTextField fieldCategory = createTextField(15, 450, 30, 100, 380);
        createProduct.add(fieldCategory);

        // create button for input category
        JButton inputCategoryButton = createButton("Ввести", 100, 30, 560, 380);
        inputCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(inputCategoryButton.getText())) {
                    categoryProduct = fieldCategory.getText();
                    Logger.getLogger(CreateProduct.class.getName()).info(categoryProduct);
                }
            }
        });
        createProduct.add(inputCategoryButton);

        // create button add product in db
        JButton addProduct = createButton("Добавить", 150, 30, 500, 550);
        addProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(addProduct.getText())) {
                    if (nameProducts == null && categoryProduct == null && priceProduct == 0) {
                        new ExceptionWindow().createWindow();
                    } else {
                        Thread threadCreateProduct = new Thread(() -> {
                            Thread.currentThread().setName("Thread create");
                            Logger.getLogger(CreateProduct.class.getName()).info(Thread.currentThread().getName() + " start");

                            Product product = new Product(nameProducts, priceProduct, categoryProduct);
                            shopRepository.addProduct(product);
                            Logger.getLogger(CreateProduct.class.getName()).info("Продукт добавлен");

                            Logger.getLogger(CreateProduct.class.getName()).info(Thread.currentThread().getName() + " end");
                        });
                        threadCreateProduct.start();
                    }
                }
            }
        });
        createProduct.add(addProduct);

        // create button check users
        JButton buttonUsers = createButton("Пользователи", 230, 50, 0, 0);
        buttonUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(buttonUsers.getText())) {
                    new MenuUsers(shopRepository).createWindow();
                }
            }
        });
        createProduct.add(buttonUsers);

        // create button check products
        JButton buttonProducts = createButton("Продукты", 230, 50, 230, 0);
        buttonProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(buttonProducts.getText())) {
                    createProduct.dispose();
                    new MenuProduct(shopRepository).createWindow();
                }
            }
        });
        createProduct.add(buttonProducts);

        // create button create product
        JButton buttonCreateButton = createButton("Создать продукты", 230, 50, 460, 0);
        createProduct.add(buttonCreateButton);

        createProduct.add(new PanelCreate());
        createProduct.setVisible(true);
    }
}
