package com.ood.maps;

import com.ood.game.Game;
import com.ood.help.HelpPage;
import com.ood.menu.MenuPage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @ClassName SelectMaps
 * @Description TODO
 * @Author Yaozheng Wang
 * @Date 2022/4/26 14:13
 **/
public class SelectMaps extends JPanel {
    private BufferedImage iceImg;
    private BufferedImage forestImg;
    private BufferedImage mountainImg;
    private BufferedImage desertImg;
    private JLabel iceButton = new JLabel();
    private JLabel forestButton = new JLabel();
    private JLabel mountainButton = new JLabel();
    private JLabel desertButton = new JLabel();

    private JFrame parent;
    public static JFrame selectFrame = new JFrame();
    private static Game game;

    private SelectMapsPanel panel;
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	//gets the dimensions of the screen
    private JFrame frame = new JFrame();
    private Container c = frame.getContentPane();

    private String hint;

    public SelectMaps(JFrame parent) {
        this.parent = parent;
        panel = new SelectMapsPanel();
        iceButton.setBounds(0, 0, 290, 160);
        forestButton.setBounds(0, 200, 290, 160);
        mountainButton.setBounds(300, 0, 290, 160);
        desertButton.setBounds(300, 200, 290, 160);

        try {
            iceImg = ImageIO.read(new File("images/ice.jpg"));
            forestImg = ImageIO.read(new File("images/forest.jpg"));
            mountainImg = ImageIO.read(new File("images/mountains.jpg"));
            desertImg = ImageIO.read(new File("images/desert.jpg"));

            iceButton.setIcon(new ImageIcon(iceImg));
            forestButton.setIcon(new ImageIcon(forestImg));
            mountainButton.setIcon(new ImageIcon(mountainImg));
            desertButton.setIcon(new ImageIcon(desertImg));
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.addMouseListener(new MouseAdapter() {
            int buttonType;
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1 && e.getX() > iceButton.getX()
                        && e.getX() < iceButton.getX() + iceButton.getWidth() && e.getY() > iceButton.getY()
                        && e.getY() < iceButton.getY() + iceButton.getHeight()){
                    buttonType = 1;
                } else if(e.getButton() == MouseEvent.BUTTON1 && e.getX() > forestButton.getX()
                        && e.getX() < forestButton.getX() + forestButton.getWidth() && e.getY() > forestButton.getY()
                        && e.getY() < forestButton.getY() + forestButton.getHeight()) {
                    buttonType = 2;
                } else if(e.getButton() == MouseEvent.BUTTON1 && e.getX() > mountainButton.getX()
                        && e.getX() < mountainButton.getX() + mountainButton.getWidth() && e.getY() > mountainButton.getY()
                        && e.getY() < mountainButton.getY() + mountainButton.getHeight()) {
                    buttonType = 3;
                } else if (e.getButton() == MouseEvent.BUTTON1 && e.getX() > desertButton.getX()
                        && e.getX() < desertButton.getX() + desertButton.getWidth()
                        && e.getY() > desertButton.getY() && e.getY() < desertButton.getY() + desertButton.getHeight()) {
                    buttonType = 4;
                }
            }

            public void mouseReleased(MouseEvent e) {
                if(buttonType == 1) {
                    game = new Game(selectFrame, "images/ice.jpg");
                    selectFrame.setLocation(dim.width/2-404, dim.height/2 - 153);	//frame is at center of the screen
                    selectFrame.getContentPane().add(game);
                    selectFrame.pack();
                    selectFrame.setVisible(true);
                    game.setVisible(true);
                    frame.setVisible(false);
                } else if (buttonType == 2) {
                    game = new Game(selectFrame, "images/forest.jpg");
                    selectFrame.setLocation(dim.width/2-404, dim.height/2 - 153);	//frame is at center of the screen
                    selectFrame.getContentPane().add(game);
                    selectFrame.pack();
                    selectFrame.setVisible(true);
                    game.setVisible(true);
                    frame.setVisible(false);
                    frame.setVisible(false);
                } else if (buttonType == 3) {
                    game = new Game(selectFrame, "images/mountains.jpg");
                    selectFrame.setLocation(dim.width/2-404, dim.height/2 - 153);	//frame is at center of the screen
                    selectFrame.getContentPane().add(game);
                    selectFrame.pack();
                    selectFrame.setVisible(true);
                    game.setVisible(true);
                    frame.setVisible(false);
                } else if (buttonType == 4) {
                    game = new Game(selectFrame, "images/desert.jpg");
                    selectFrame.setLocation(dim.width/2-404, dim.height/2 - 153);	//frame is at center of the screen
                    selectFrame.getContentPane().add(game);
                    selectFrame.pack();
                    selectFrame.setVisible(true);
                    game.setVisible(true);
                    frame.setVisible(false);
                }
            }
        });

        hint = "Please click to select a background!";
        panel.setLayout(null);
        panel.add(iceButton);
        panel.add(desertButton);
        panel.add(forestButton);
        panel.add(mountainButton);
        c.add(panel);
        frame.setTitle("Artillery Game");
        frame.setSize(600,400);
        frame.setLocation(dim.width/2 - 125, dim.height/2 - 110);	//frame is at the center of the screen
        frame.setResizable(false);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
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
