package com.example.demo.window.window_admin.window_product;

import javax.swing.*;
import java.awt.*;

public class PanelProduct extends JPanel {

    // draw table
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawLine(0, 250, 700, 250);
        g2.drawLine(350, 250, 350, 700);
        g2.drawLine(0, 300, 700, 300);
    }
}
