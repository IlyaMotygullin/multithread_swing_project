package com.example.demo.window.window_user;

import javax.swing.*;

public interface Window {
    JTextField createTextField(int sizeFont, int width, int height, int x, int y);
    JLabel createLabel(String text, int sizeFont, int width, int height, int x, int y);
    JButton createButton(String text, int width, int height, int x, int y);
    void createWindow();
}
