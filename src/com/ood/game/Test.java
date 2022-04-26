package com.ood.game;

import javax.swing.*;
import java.awt.*;

/**
 * @ClassName Test
 * @Description TODO
 * @Author Yaozheng Wang
 * @Date 2022/4/22 22:24
 **/
public class Test {
    public static void main(String[] args) {
        JFrame gameFrame = new JFrame();
        Game game = new Game(gameFrame, "images/desert.jpg");
        gameFrame.getContentPane().add(game);
        gameFrame.setSize(new Dimension(1000, 1000));
        gameFrame.pack();
        gameFrame.setVisible(true);
        game.setVisible(true);
    }

}
