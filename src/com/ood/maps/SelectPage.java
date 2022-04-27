package com.ood.maps;

import com.ood.game.GamePage;

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
 * @Description The select maps panel
 * @Author Yaozheng Wang
 * @Date 2022/4/26 14:13
 **/
public class SelectPage extends JPanel {
    // Declare the labels
    private final JLabel iceButton = new JLabel();
    private final JLabel forestButton = new JLabel();
    private final JLabel mountainButton = new JLabel();
    private final JLabel desertButton = new JLabel();

    // Declare frames
    private static final JFrame selectFrame = new JFrame();
    private static GamePage game;
    private final JFrame frame = new JFrame();
    // Get the dimensions of the screen
    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    /**
    * @Author Yaozheng Wang
    * @Description The select maps constructor
    * @Date 2022/4/26 20:52
    * @Param null
    * @Return null
    **/
    public SelectPage() {
        // New a select maps panel
        SelectMapsPanel panel = new SelectMapsPanel();
        iceButton.setBounds(0, 0, 290, 160);
        forestButton.setBounds(0, 200, 290, 160);
        mountainButton.setBounds(300, 0, 290, 160);
        desertButton.setBounds(300, 200, 290, 160);

        try { // Try to read images and catch exception
            BufferedImage iceImg = ImageIO.read(new File("images/ice.jpg"));
            BufferedImage forestImg = ImageIO.read(new File("images/forest.jpg"));
            BufferedImage mountainImg = ImageIO.read(new File("images/mountains.jpg"));
            BufferedImage desertImg = ImageIO.read(new File("images/desert.jpg"));

            iceButton.setIcon(new ImageIcon(iceImg));
            forestButton.setIcon(new ImageIcon(forestImg));
            mountainButton.setIcon(new ImageIcon(mountainImg));
            desertButton.setIcon(new ImageIcon(desertImg));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add components to the panel
        panel.setLayout(null);
        panel.add(iceButton);
        panel.add(desertButton);
        panel.add(forestButton);
        panel.add(mountainButton);
        Container c = frame.getContentPane();
        c.add(panel);

        // Set the frame
        frame.setTitle("Artillery Game");
        frame.setSize(600,400);
        frame.setLocation(dim.width/2 - 125, dim.height/2 - 110);	//frame is at the center of the screen
        frame.setResizable(false);
        frame.setVisible(true);

        // Add mouse listener to the frame
        frame.addMouseListener(new MouseAdapter() {
            int buttonType;

            // Check mouse press
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

            // Check the mouse released
            public void mouseReleased(MouseEvent e) {
                if(buttonType == 1) { // The ice map
                    game = new GamePage("images/ice.jpg"); // New game page
                    selectFrame.setTitle("Artillery Game");
                    selectFrame.setLocation(dim.width/2-404, dim.height/2 - 153);	//frame is at center of the screen
                    selectFrame.getContentPane().add(game); // Add game
                    selectFrame.pack();
                    selectFrame.setVisible(true);
                    game.setVisible(true);
                    frame.setVisible(false);
                } else if (buttonType == 2) { // The forest map
                    game = new GamePage("images/forest.jpg"); // New game page
                    selectFrame.setTitle("Artillery Game");
                    selectFrame.setLocation(dim.width/2-404, dim.height/2 - 153);	//frame is at center of the screen
                    selectFrame.getContentPane().add(game); // Add game
                    selectFrame.pack();
                    selectFrame.setVisible(true);
                    game.setVisible(true);
                    frame.setVisible(false);
                    frame.setVisible(false);
                } else if (buttonType == 3) { // The mountain map
                    game = new GamePage("images/mountains.jpg"); // New game page
                    selectFrame.setTitle("Artillery Game");
                    selectFrame.setLocation(dim.width/2-404, dim.height/2 - 153);	//frame is at center of the screen
                    selectFrame.getContentPane().add(game); // Add game
                    selectFrame.pack();
                    selectFrame.setVisible(true);
                    game.setVisible(true);
                    frame.setVisible(false);
                } else if (buttonType == 4) {
                    game = new GamePage("images/desert.jpg"); // New game page
                    selectFrame.setTitle("Artillery Game");
                    selectFrame.setLocation(dim.width/2-404, dim.height/2 - 153);	//frame is at center of the screen
                    selectFrame.getContentPane().add(game); // Add game
                    selectFrame.pack();
                    selectFrame.setVisible(true);
                    game.setVisible(true);
                    frame.setVisible(false);
                }
            }
        });

        // Add window listener to the frame
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                int resp = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?",
                        "Exit?", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                } else if (resp == JOptionPane.NO_OPTION){
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        // Add window listener to the select frame
        selectFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                int resp = JOptionPane.showConfirmDialog(selectFrame, "Are you sure you want to exit?",
                        "Exit?", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    selectFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                } else if (resp == JOptionPane.NO_OPTION){
                    selectFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    /**
     * @ClassName SelectMapsPanel
     * @Description The select maps panel
     * @Author Yaozheng Wang
     * @Date 2022/4/26 14:16
     **/
    public static class SelectMapsPanel extends JPanel {
        // Paint the select maps panel
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            String hint = "Please click to select a background!";
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString(hint, 120, 190);
        }
    }
}
