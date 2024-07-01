package com.example.demo.window.window_user.window_register;

import com.example.demo.entity.User;
import com.example.demo.repository.ShopRepository;
import com.example.demo.window.window_exception.ExceptionWindow;
import com.example.demo.window.window_user.Window;
import com.example.demo.window.window_user.window_entrance.EntranceWindow;
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
public class RegisterWindow implements Window {
    final ShopRepository shopRepository;
    final JFrame windowRegister = new JFrame("window register");
    List<User> userList;

    String nameUser;
    String emailUser;
    String passwordUser;

    public RegisterWindow(ShopRepository shopRepository) {
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

        // create thread get users
        Thread threadGetUsers = new Thread(() -> {
            Thread.currentThread().setName("Thread get users");
            Logger.getLogger(RegisterWindow.class.getName()).info(Thread.currentThread().getName() + " start");
            userList = shopRepository.getUserList();
        });
        threadGetUsers.setDaemon(true);
        threadGetUsers.start();
        threadGetUsers.join();

        //setting window
        windowRegister.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowRegister.setSize(700,700);
        windowRegister.setResizable(false);
        windowRegister.setLocationRelativeTo(null);

        // create main label
        JLabel registerLabel = createLabel("Регистрация", 20, 150, 50, 260, 50);
        windowRegister.add(registerLabel);

        // create label input name user
        JLabel inputName = createLabel("Введите имя", 15, 200, 30, 100, 170);
        windowRegister.add(inputName);

        // create form name users
        JTextField fieldNameUsers = createTextField(15, 450, 30, 100, 200);
        windowRegister.add(fieldNameUsers);

        // create button input name user
        JButton buttonInputName = createButton("Ввести", 100, 30, 570, 200);
        buttonInputName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(buttonInputName.getText())) {
                    nameUser = fieldNameUsers.getText();
                    if (nameUser.isEmpty()) {
                        Logger.getLogger(RegisterWindow.class.getName()).info("field is empty");
                        new ExceptionWindow().createWindow();
                    }
                    Logger.getLogger(RegisterWindow.class.getName()).info(nameUser);
                }
            }
        });
        windowRegister.add(buttonInputName);

        // create label input email users
        JLabel inputEmail = createLabel("Введите email", 15, 200, 30, 100, 250);
        windowRegister.add(inputEmail);

        // create form email users
        JTextField fieldEmailUsers = createTextField(15, 450, 30, 100, 280);
        windowRegister.add(fieldEmailUsers);

        // create button input email users
        JButton buttonInputEmail = createButton("Ввести", 100, 30, 570, 280);
        buttonInputEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(buttonInputEmail.getText())) {
                    emailUser = fieldEmailUsers.getText();
                    if (emailUser.isEmpty()) {
                        Logger.getLogger(RegisterWindow.class.getName()).info("field is empty");
                        new ExceptionWindow().createWindow();
                    }
                    Logger.getLogger(RegisterWindow.class.getName()).info(emailUser);
                }
            }
        });
        windowRegister.add(buttonInputEmail);

        // create label input password users
        JLabel inputPassword = createLabel("Введите пароль", 15, 250, 30, 100, 330);
        windowRegister.add(inputPassword);

        // create form password user
        JTextField fieldPasswordUsers = createTextField(15, 450, 30, 100, 360);
        windowRegister.add(fieldPasswordUsers);

        // create button input password users
        JButton buttonInputPassword = createButton("Ввести", 100, 30, 570, 360);
        buttonInputPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(buttonInputPassword.getText())) {
                    passwordUser = fieldPasswordUsers.getText();
                    if (passwordUser.isEmpty()) {
                        Logger.getLogger(RegisterWindow.class.getName()).info("field is empty");
                        new ExceptionWindow().createWindow();
                    }
                    Logger.getLogger(RegisterWindow.class.getName()).info(passwordUser);
                }
            }
        });
        windowRegister.add(buttonInputPassword);

        // create button register user
        JButton registerButton = createButton("Регистрация", 150, 30, 500, 550);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(registerButton.getText())) {
                    Thread checkUsersInDB = new Thread(() -> {
                        Thread.currentThread().setName("CheckUser");
                        Logger.getLogger(RegisterWindow.class.getName()).info(Thread.currentThread().getName() + " start");

                        if ((nameUser == null) && (emailUser == null) && (passwordUser == null)) {
                            new ExceptionWindow().createWindow();
                        } else {
                            User newUser = new User(nameUser, emailUser, passwordUser);
                            for (User user : userList) {
                                if (!(user.getName().equals(nameUser)) && !(user.getEmailUser().equals(emailUser)) && !(user.getPassword().equals(passwordUser))) {
                                    shopRepository.addUser(newUser);
                                    windowRegister.dispose();
                                    new EntranceWindow(shopRepository).createWindow();
                                    return;
                                }
                            }
                        }

                        Logger.getLogger(RegisterWindow.class.getName()).info(Thread.currentThread().getName() + " end");
                    });
                    checkUsersInDB.start();
                }
            }
        });
        windowRegister.add(registerButton);

        windowRegister.add(new PanelRegister());
        windowRegister.setVisible(true);
    }
}
