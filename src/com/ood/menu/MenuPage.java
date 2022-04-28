package com.ood.menu;

import com.ood.help.HelpPage;
import com.ood.maps.SelectPage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;

/**
 * @ClassName Menu
 * @Description TOD
 * @Author Yaozheng Wang
 * @Date 2022/4/24 11:42
 **/
public class MenuPage {
    // Declare buffered images and labels
    private BufferedImage playImg;
    private BufferedImage tutImg;
    private BufferedImage exitImg;
    private final JLabel startButton = new JLabel();
    private final JLabel tutorialButton = new JLabel();
    private final JLabel exitButton = new JLabel();
    private SelectPage selectMaps;

    // Declare frames
    public JFrame gameFrame = new JFrame();
    private final JFrame frame = new JFrame();

    /**
    * @Author Yaozheng Wang
    * @Description The menu page constructor
    * @Date 2022/4/26 21:20
    * @Param null
    * @Return null
    **/
    public MenuPage(){

        // Set buttons
        startButton.setBounds(190, 100, 195, 145);
        tutorialButton.setBounds(199, 230, 155, 67);
        exitButton.setBounds(199, 300, 155, 67);

        try { // Try to read images and catch exception
            playImg = ImageIO.read(Objects.requireNonNull(MenuPage.class.getResource("/com/ood/images/play.png")));
            tutImg = ImageIO.read(Objects.requireNonNull(MenuPage.class.getResource("/com/ood/images/help.png")));
            exitImg = ImageIO.read(Objects.requireNonNull(MenuPage.class.getResource("/com/ood/images/exit.png")));
            startButton.setIcon(new ImageIcon(playImg));
            tutorialButton.setIcon(new ImageIcon(tutImg));
            exitButton.setIcon(new ImageIcon(exitImg));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add components to the start panel
        StartPanel panel = new StartPanel();
        panel.setLayout(null);
        panel.add(startButton);
        panel.add(tutorialButton);
        panel.add(exitButton);
        Container c = frame.getContentPane();
        c.add(panel);

        // Set the frame
        frame.setTitle("Artillery Game");
        frame.setSize(600,400);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //gets the dimensions of the screen
        frame.setLocation(dim.width/2 - 125, dim.height/2 - 110);	//frame is at the center of the screen
        frame.setResizable(false);
        frame.setVisible(true);

        // Add mouse listener to the frame
        frame.addMouseListener(new MouseAdapter(){
            int buttonType;
            // Check the mouse press
            public void mousePressed(MouseEvent e){
                if (e.getButton() == MouseEvent.BUTTON1 && e.getX() > startButton.getX()
                        && e.getX() < startButton.getX() + startButton.getWidth() && e.getY() > startButton.getY()
                        && e.getY() < startButton.getY() + startButton.getHeight()) {
                    startButton.setIcon(new ImageIcon(playImg.getScaledInstance(startButton.getWidth() - 10, startButton.getHeight() - 10, Image.SCALE_SMOOTH)));
                    startButton.setBounds(startButton.getX()+5, startButton.getY()+5, startButton.getWidth() - 10, startButton.getHeight() - 10);
                    buttonType = 1;
                } else if (e.getButton() == MouseEvent.BUTTON1 && e.getX() > tutorialButton.getX()
                        && e.getX() < tutorialButton.getX() + tutorialButton.getWidth() && e.getY() > tutorialButton.getY()
                        && e.getY() < tutorialButton.getY() + tutorialButton.getHeight()) {
                    tutorialButton.setIcon(new ImageIcon(tutImg.getScaledInstance(tutorialButton.getWidth() - 10, tutorialButton.getHeight() - 10, Image.SCALE_SMOOTH)));
                    tutorialButton.setBounds(tutorialButton.getX(), tutorialButton.getY()+5, tutorialButton.getWidth() - 10, tutorialButton.getHeight() - 10);
                    buttonType = 2;
                } else if (e.getButton() == MouseEvent.BUTTON1 && e.getX() > exitButton.getX()
                        && e.getX() < exitButton.getX() + exitButton.getWidth() && e.getY() > exitButton.getY()
                        && e.getY() < exitButton.getY() + exitButton.getHeight()) {
                    exitButton.setIcon(new ImageIcon(exitImg.getScaledInstance(exitButton.getWidth() - 10, exitButton.getHeight() - 10, Image.SCALE_SMOOTH)));
                    exitButton.setBounds(exitButton.getX(), exitButton.getY()+5, exitButton.getWidth() - 10, exitButton.getHeight() - 10);
                    buttonType = 3;
                }
            }

            // Check the mouse release
            public void mouseReleased(MouseEvent e) {
                if(buttonType == 1) { // The play button
                    selectMaps = new SelectPage(); // New select maps
                    startButton.setIcon(new ImageIcon(playImg));
                    startButton.setBounds(startButton.getX()-5, startButton.getY()-5, startButton.getWidth() + 10, startButton.getHeight() + 10);
                    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	//gets the dimensions of the screen
                    gameFrame.setLocation(dim.width/2-404, dim.height/2 - 153);	//frame is at center of the screen
                    gameFrame.getContentPane().add(selectMaps); // Add select maps panel
                    gameFrame.pack();
                    selectMaps.setVisible(true);
                    frame.setVisible(false);
                } else if (buttonType == 2) { // The help button
                    tutorialButton.setIcon(new ImageIcon(tutImg)); // New tutorial page
                    tutorialButton.setBounds(tutorialButton.getX(), tutorialButton.getY()-5, tutorialButton.getWidth() + 10, tutorialButton.getHeight() + 10);
                    HelpPage tutorial = new HelpPage();
                    tutorial.getFrame().addWindowListener(new WindowAdapter(){ // Add window listener
                        public void windowClosing(WindowEvent e){
                            frame.setVisible(true);
                        }
                    });
                    frame.setVisible(false);
                } else if (buttonType == 3) { // The exit button
                    exitButton.setIcon(new ImageIcon(exitImg));
                    exitButton.setBounds(exitButton.getX(), exitButton.getY()-5, exitButton.getWidth() + 10, exitButton.getHeight() + 10);
                    System.exit(0);
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

        // Add window listener to the game frame
        gameFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                int resp = JOptionPane.showConfirmDialog(gameFrame, "Are you sure you want to exit?",
                        "Exit?", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                } else if (resp == JOptionPane.NO_OPTION){
                    gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    // The static start panel class
    static class StartPanel extends JPanel{
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            try {
                g.drawImage(ImageIO.read(Objects.requireNonNull(MenuPage.class.getResource("/com/ood/images/startPanel.jpg"))), 0, 0, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
