package com.ood.maps;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @ClassName SelectMapsPanel
 * @Description TODO
 * @Author Yaozheng Wang
 * @Date 2022/4/26 14:16
 **/
public class SelectMapsPanel extends JPanel {
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        String hint = "Please click to select a background!";
        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.drawString(hint, 120, 190);
    }
}
