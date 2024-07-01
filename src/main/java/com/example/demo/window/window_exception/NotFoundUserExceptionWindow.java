package com.example.demo.window.window_exception;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotFoundUserExceptionWindow implements Exception {
    final JFrame notFound = new JFrame("not_found");

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
        notFound.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        notFound.setSize(400, 400);
        notFound.setResizable(false);
        notFound.setLocationRelativeTo(null);

        // main label
        JLabel notFoundLabel = createLabel("Пользователь не найден", 20, 300, 20, 50, 50);
        notFound.add(notFoundLabel);

        // create main button
        JButton buttonException = createButton("Ok", 100, 30, 140, 150);
        buttonException.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(buttonException.getText())) {
                    notFound.dispose();
                }
            }
        });
        notFound.add(buttonException);

        notFound.add(new PanelException());
        notFound.setVisible(true);
    }
}
