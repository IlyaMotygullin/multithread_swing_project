package com.example.demo.window.window_exception;

import javax.swing.*;

public interface Exception {
    JLabel createLabel(String text, int sizeFont, int width, int height, int x, int y);
    JButton createButton(String text, int width, int height, int x, int y);
    void createWindow();
}
