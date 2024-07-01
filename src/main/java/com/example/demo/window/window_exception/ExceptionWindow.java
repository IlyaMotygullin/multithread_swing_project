package com.example.demo.window.window_exception;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExceptionWindow implements Exception {
    final JFrame windowException = new JFrame("window exception");

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

    @Override
    public void createWindow() {
        // setting window
        windowException.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowException.setSize(400, 400);
        windowException.setResizable(false);
        windowException.setLocationRelativeTo(null);

        // create main label
        JLabel labelException = createLabel("Пустые поля", 20, 250, 30, 120, 50);
        windowException.add(labelException);

        // create main button
        JButton buttonException = createButton("Ok", 100, 30, 140, 150);
        buttonException.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(buttonException.getText())) {
                    windowException.dispose();
                }
            }
        });
        windowException.add(buttonException);

        windowException.add(new PanelException());
        windowException.setVisible(true);
    }
}
