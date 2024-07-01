package com.example.demo.window.window_exception;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class NullFieldException implements Exception {
    final JFrame nullExceptionWindow = new JFrame("null field");
    String msgException;

    public NullFieldException(String msgException) {
        this.msgException = msgException;
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

    @Override
    public void createWindow() {
        // setting window
        nullExceptionWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nullExceptionWindow.setSize(400,400);
        nullExceptionWindow.setResizable(false);
        nullExceptionWindow.setLocationRelativeTo(null);

        // create main label
        JLabel labelMainException = createLabel(this.msgException, 20, 400, 30, 100, 50);
        nullExceptionWindow.add(labelMainException);

        // create main button
        JButton buttonMain = createButton("Ok", 100,30, 150, 150);
        buttonMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(buttonMain.getText())) {
                    nullExceptionWindow.dispose();
                }
            }
        });
        nullExceptionWindow.add(buttonMain);

        nullExceptionWindow.add(new PanelException());
        nullExceptionWindow.setVisible(true);
    }
}
