package com.ood;

import com.ood.menu.MenuPage;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @ClassName Main
 * @Description TODO
 * @Author Yaozheng Wang
 * @Date 2022/4/22 16:09
 **/
public class Main {
    public static void main(String[] args) {
        new MenuPage();
        MenuPage.gameFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                int resp = JOptionPane.showConfirmDialog(MenuPage.gameFrame, "Are you sure you want to exit?",
                        "Exit?", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    MenuPage.gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                } else {
                    MenuPage.gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

    }
}
