package com.example.demo.window.window_user.window_entrance;

import com.example.demo.entity.User;
import com.example.demo.repository.ShopRepository;
import com.example.demo.window.window_admin.window_user.MenuUsers;
import com.example.demo.window.window_exception.ExceptionWindow;
import com.example.demo.window.window_exception.NotFoundUserExceptionWindow;
import com.example.demo.window.window_shop.shop.MainShopWindow;
import com.example.demo.window.window_user.Window;
import com.example.demo.window.window_user.window_register.RegisterWindow;
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
public class EntranceWindow implements Window {
    final ShopRepository shopRepository;
    final JFrame windowEntrance = new JFrame("window entrance");
    List<User> userList;
    String emailUser;
    String passwordUser;

    public EntranceWindow(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public JTextField createTextField(int sizeFont, int width, int height, int x, int y) {
        JTextField field = new JTextField();
        field.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, sizeFont));
        field.setSize(width, height);
        field.setBounds(x, y, field.getWidth(), field.getHeight());
        return field;
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
        // create get user thread
        Thread getUser = new Thread(() -> {
            Thread.currentThread().setName("Thread get user list");
            Logger.getLogger(EntranceWindow.class.getName()).info(Thread.currentThread().getName() + " start");
            userList = shopRepository.getUserList();
        });
        getUser.setDaemon(true);
        getUser.start();
        getUser.join();

        userList.forEach(System.out::println);

        // setting window
        windowEntrance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowEntrance.setSize(700, 700);
        windowEntrance.setResizable(false);
        windowEntrance.setLocationRelativeTo(null);

        // create main label
        JLabel labelEntrance = createLabel("Войти", 20, 150, 50, 300, 50);
        windowEntrance.add(labelEntrance);

        // create label for input email
        JLabel inputLabel = createLabel("Введите email", 15, 350, 30, 100, 170);
        windowEntrance.add(inputLabel);

        // create text field for email users
        JTextField emailTextField = createTextField(15, 450, 30, 100, 200);
        windowEntrance.add(emailTextField);

        // create button input email user
        JButton buttonEmail = createButton("Ввести", 100, 30, 570, 200);
        buttonEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(buttonEmail.getText())) {
                    emailUser = emailTextField.getText();
                    if (emailUser.isEmpty()) {
                        new ExceptionWindow().createWindow();
                    }
                    Logger.getLogger(EntranceWindow.class.getName()).info(emailUser);
                }
            }
        });
        windowEntrance.add(buttonEmail);

        // create label for input password
        JLabel inputPassword = createLabel("Введите пароль", 15, 350, 30, 100, 270);
        windowEntrance.add(inputPassword);

        // create text field for password users
        JTextField passwordTextField = createTextField(15, 450, 30, 100, 300);
        windowEntrance.add(passwordTextField);

        // create button input password user
        JButton buttonPassword = createButton("Ввести", 100, 30, 570, 300);
        buttonPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(buttonPassword.getText())) {
                    passwordUser = passwordTextField.getText();
                    if (passwordUser.isEmpty()) {
                        new ExceptionWindow().createWindow();
                    }
                    Logger.getLogger(EntranceWindow.class.getName()).info(passwordUser);
                }
            }
        });
        windowEntrance.add(buttonPassword);

        // create button register user
        JButton buttonRegister = createButton("Регистрация", 150, 40, 30, 500);
        buttonRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowEntrance.dispose();
                new RegisterWindow(shopRepository).createWindow();
            }
        });
        windowEntrance.add(buttonRegister);
        for (User user : userList) {
            System.out.println(user.getPassword() + " " + user.getEmailUser());
        }

        // create button entrance user
        JButton buttonEntrance = createButton("Войти", 150, 40, 500, 500);
        buttonEntrance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(buttonEntrance.getText())) {
                    Thread threadEntrance = new Thread(() -> {
                        Thread.currentThread().setName("Thread entrance");
                        Logger.getLogger(EntranceWindow.class.getName()).info(Thread.currentThread().getName() + " start");
                        if (emailUser == null && passwordUser == null) {
                            new ExceptionWindow().createWindow();
                        } else {
                            for (User user : userList) {
                                if (user.getEmailUser().equals(emailUser) && user.getPassword().equals(passwordUser)) {
                                    Logger.getLogger(EntranceWindow.class.getName()).info("Пользователь найден");
                                    windowEntrance.dispose();
                                    new MainShopWindow(shopRepository, user.getId()).createWindow();
                                    return;
                                }
                            }
                            if (emailUser.equals("admin") && passwordUser.equals("12354")) {
                                windowEntrance.dispose();
                                new MenuUsers(shopRepository).createWindow();
                                return;
                            }
                            Logger.getLogger(EntranceWindow.class.getName()).info("Пользователь не найден");
                            new NotFoundUserExceptionWindow().createWindow();
                            return;
                        }
                        Logger.getLogger(EntranceWindow.class.getName()).info(Thread.currentThread().getName() + " end");
                    });
                    threadEntrance.start();
                }
            }
        });
        windowEntrance.add(buttonEntrance);

        windowEntrance.add(new PanelEntrance());
        windowEntrance.setVisible(true);
    }
}
