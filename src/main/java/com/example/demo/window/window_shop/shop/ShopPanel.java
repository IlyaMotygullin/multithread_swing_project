package com.example.demo.window.window_shop.shop;

import javax.swing.*;
import java.awt.*;

public class ShopPanel extends JPanel {
    // draw table for product

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawLine(0, 300, 700, 300);
        g2.drawLine(0, 350, 700, 350);
        g2.drawLine(350, 300, 350, 700);
    }
}
