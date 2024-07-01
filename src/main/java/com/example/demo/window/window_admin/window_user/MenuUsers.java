package com.example.demo.window.window_admin.window_user;

import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.ShopRepository;
import com.example.demo.window.window_admin.product_create.CreateProduct;
import com.example.demo.window.window_admin.window_product.MenuProduct;
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
public class MenuUsers implements com.example.demo.window.window_admin.Window {
    final JFrame window = new JFrame("user window");
    final ShopRepository shopRepository;
    List<User> userList;

    // features user
    String name;
    String email;
    String password;

    // features product
    String nameProduct;
    String categoryProduct;
    double priceProduct;

    public MenuUsers(ShopRepository shopRepository) {
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
        // setting window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(700, 700);
        window.setResizable(false);
        window.setLocationRelativeTo(null);

        // get users list
        Thread getUsers = new Thread(() -> {
            Thread.currentThread().setName("Thread get users");
            Logger.getLogger(MenuUsers.class.getName()).info(Thread.currentThread().getName() + " start");
            userList = shopRepository.getUserList();
        });
        getUsers.setDaemon(true);
        getUsers.start();
        getUsers.join();

        // create label users
        JLabel labelUsers = createLabel("Пользователи", 20, 250, 30, 255, 100);
        window.add(labelUsers);

        // create label first column in table
        JLabel labelFirstColumn = createLabel("Пользователь", 20, 250, 30, 100, 310);
        window.add(labelFirstColumn);

        // create label second column in table
        JLabel labelSecondColumn = createLabel("Список товаров", 20, 250, 30, 450, 310);
        window.add(labelSecondColumn);

        // create label info user_name
        JLabel labelNameUser = createLabel("Имя:", 20, 200, 30, 30, 400);
        labelNameUser.setVisible(false);
        window.add(labelNameUser);

        // create label value user_name
        JLabel labelNameUserValue = createLabel(" ", 20, 300, 30, 30, 430);
        labelNameUserValue.setVisible(false);
        window.add(labelNameUserValue);

        // create label info user_email
        JLabel labelEmailUser = createLabel("Email:", 20, 200, 30, 30, 470);
        labelEmailUser.setVisible(false);
        window.add(labelEmailUser);

        // create label value user_email
        JLabel labelEmailUserValue = createLabel(" ", 20, 350, 30, 30, 500);
        labelEmailUserValue.setVisible(false);
        window.add(labelEmailUserValue);

        // create label info user_password
        JLabel labelPasswordUser = createLabel("Password:", 20, 200, 30, 30, 540);
        labelPasswordUser.setVisible(false);
        window.add(labelPasswordUser);

        // create label value user_password
        JLabel labelPasswordUserValue = createLabel("77660923050asD", 20, 350, 30, 30, 570);
        labelPasswordUserValue.setVisible(false);
        window.add(labelPasswordUserValue);

        // create label info product name
        JLabel labelNameProduct = createLabel("Название продукта:", 20, 350, 30, 390, 400);
        labelNameProduct.setVisible(false);
        window.add(labelNameProduct);

        // create label value product name
        JLabel labelValueNameProduct = createLabel(" ", 20, 350, 30, 390, 430);
        labelValueNameProduct.setVisible(false);
        window.add(labelValueNameProduct);

        // create label info product category
        JLabel labelCategoryProduct = createLabel("Категория товара:", 20, 400, 30, 390, 480);
        labelCategoryProduct.setVisible(false);
        window.add(labelCategoryProduct);

        // create label value product category
        JLabel labelValueCategoryProduct = createLabel(" ", 20, 400, 30, 390, 510);
        labelValueCategoryProduct.setVisible(false);
        window.add(labelValueCategoryProduct);

        // create label info product price
        JLabel labelPriceProduct = createLabel("Цена товара:", 20, 200, 30, 390, 560);
        labelPriceProduct.setVisible(false);
        window.add(labelPriceProduct);

        // create label value product price
        JLabel labelValuePriceProduct = createLabel(" ", 20, 200, 30, 390, 590);
        labelValuePriceProduct.setVisible(false);
        window.add(labelValuePriceProduct);

        // create product list as component window
        JComboBox<Product> productJComboBox = new JComboBox<>();
        productJComboBox.setBounds(400, 350, 250, 30);
        window.add(productJComboBox);

        // create list users as component
        JComboBox<User> userJComboBox = new JComboBox<>(userList.toArray(new User[0]));
        userJComboBox.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        userJComboBox.setSize(500, 50);
        userJComboBox.setBounds(100, 200, userJComboBox.getWidth(), userJComboBox.getHeight());
        userJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User itemUser = (User) userJComboBox.getSelectedItem();
                name = itemUser.getName();
                email = itemUser.getEmailUser();
                password = itemUser.getPassword();

                // create model for list product
                DefaultComboBoxModel<Product> productModel = new DefaultComboBoxModel<>(
                        itemUser.getProductSet().toArray(new Product[0])
                );
                productJComboBox.setModel(productModel);
                productJComboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Product itemProduct = (Product) productJComboBox.getSelectedItem();
                        nameProduct = itemProduct.getNameProduct();
                        categoryProduct = itemProduct.getCategoryProduct();
                        priceProduct = itemProduct.getPriceProduct();

                        labelNameProduct.setVisible(true);
                        labelValueNameProduct.setText(nameProduct);
                        labelValueNameProduct.setVisible(true);

                        labelCategoryProduct.setVisible(true);
                        labelValueCategoryProduct.setText(categoryProduct);
                        labelValueCategoryProduct.setVisible(true);

                        labelPriceProduct.setVisible(true);
                        labelValuePriceProduct.setText(String.valueOf(priceProduct));
                        labelValuePriceProduct.setVisible(true);
                    }
                });


                // name_user
                labelNameUser.setVisible(true);
                labelNameUserValue.setText(name);
                labelNameUserValue.setVisible(true);

                // email_user
                labelEmailUser.setVisible(true);
                labelEmailUserValue.setText(email);
                labelEmailUserValue.setVisible(true);

                // password_user
                labelPasswordUser.setVisible(true);
                labelPasswordUserValue.setText(password);
                labelPasswordUserValue.setVisible(true);
            }
        });
        window.add(userJComboBox);

        // create button for delete user
        JButton deleteUser = createButton("Удалить", 100, 30, 30, 620);
        deleteUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(deleteUser.getText())) {
                    if (name == null && email == null && password == null) {
                        new NullFieldException("Выберет пользователя").createWindow();
                    } else {
                        Thread deleteUser = new Thread(() -> {
                            Thread.currentThread().setName("delete user");
                            Logger.getLogger(MenuUsers.class.getName()).info(Thread.currentThread().getName() + " start");
                            for (User user : shopRepository.getUserList()) {
                                if (user.getName().equals(name) && user.getEmailUser().equals(email) && user.getPassword().equals(password)) {
                                    shopRepository.deleteUser(user.getId());
                                    Logger.getLogger(MenuUsers.class.getName()).info("Пользователь удален!");

                                    // product
                                    labelValueNameProduct.setText(" ");
                                    labelValueNameProduct.setVisible(false);

                                    labelValueCategoryProduct.setText(" ");
                                    labelValueCategoryProduct.setVisible(false);

                                    labelValuePriceProduct.setText(" ");
                                    labelValuePriceProduct.setVisible(false);

                                    // name_user
                                    labelNameUserValue.setText(" ");
                                    labelNameUserValue.setVisible(false);

                                    // email_user
                                    labelEmailUserValue.setText(" ");
                                    labelEmailUserValue.setVisible(false);

                                    // password_user
                                    labelPasswordUserValue.setText(" ");
                                    labelPasswordUserValue.setVisible(false);

                                    return;
                                }
                            }
                            Logger.getLogger(MenuUsers.class.getName()).info(Thread.currentThread().getName() + " end");
                        });
                        deleteUser.start();
                    }
                }
            }
        });
        window.add(deleteUser);

        // create button check users
        JButton buttonUsers = createButton("Пользователи", 230, 50, 0, 0);
        window.add(buttonUsers);

        // create button check products
        JButton buttonProducts = createButton("Продукты", 230, 50, 230, 0);
        buttonProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(buttonProducts.getText())) {
                    window.dispose();
                    new MenuProduct(shopRepository).createWindow();
                }
            }
        });
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

        window.add(new Panel());
        window.setVisible(true);
    }
}
