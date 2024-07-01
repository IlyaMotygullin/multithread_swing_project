package com.example.demo.window.window_shop;

import javax.swing.*;

public interface WindowShop {
    JLabel createLabel(String text, int sizeFont, int width, int height, int x, int y);
    JButton createButton(String text, int width, int height, int x, int y);
    void createWindow();
}
