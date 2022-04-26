package com.ood.menu;

import com.ood.help.HelpPage;
import com.ood.game.Game;
import com.ood.maps.SelectMaps;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @ClassName Menu
 * @Description TODO
 * @Author Yaozheng Wang
 * @Date 2022/4/24 11:42
 **/
public class MenuPage {
    private BufferedImage playImg;
    private BufferedImage tutImg;
    private BufferedImage exitImg;
    private JLabel startButton = new JLabel();
    private JLabel tutorialButton = new JLabel();
    private JLabel exitButton = new JLabel();

    public static JFrame gameFrame = new JFrame();
    private SelectMaps selectMaps = new SelectMaps(gameFrame);

    private StartPanel panel = new StartPanel();
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	//gets the dimensions of the screen
    private JFrame frame = new JFrame();
    private Container c = frame.getContentPane();

    public MenuPage(){

        startButton.setBounds(190, 100, 195, 145);
        tutorialButton.setBounds(199, 230, 155, 67);
        exitButton.setBounds(199, 298, 155, 67);

        try {
            playImg = ImageIO.read(new File("images/play.png"));
            tutImg = ImageIO.read(new File("images/help.png"));
            exitImg = ImageIO.read(new File("images/exit.png"));

            startButton.setIcon(new ImageIcon(playImg));
            tutorialButton.setIcon(new ImageIcon(tutImg));
            exitButton.setIcon(new ImageIcon(exitImg));
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.addMouseListener(new MouseAdapter(){
            int buttonType;
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

            public void mouseReleased(MouseEvent e){
                if(buttonType == 1){
                    startButton.setIcon(new ImageIcon(playImg));
                    startButton.setBounds(startButton.getX()-5, startButton.getY()-5, startButton.getWidth() + 10, startButton.getHeight() + 10);
                    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	//gets the dimensions of the screen
                    gameFrame.setLocation(dim.width/2-404, dim.height/2 - 153);	//frame is at center of the screen
                    gameFrame.getContentPane().add(selectMaps);
                    gameFrame.pack();
                    selectMaps.setVisible(true);
                    frame.setVisible(false);
                } else if (buttonType == 2){
                    tutorialButton.setIcon(new ImageIcon(tutImg));
                    tutorialButton.setBounds(tutorialButton.getX(), tutorialButton.getY()-5, tutorialButton.getWidth() + 10, tutorialButton.getHeight() + 10);
                    HelpPage tutorial = new HelpPage();
                    tutorial.getFrame().addWindowListener(new WindowAdapter(){
                        public void windowClosing(WindowEvent e){
                            frame.setVisible(true);
                        }
                    });
                    frame.setVisible(false);
                } else if (buttonType == 3){
                    exitButton.setIcon(new ImageIcon(exitImg));
                    exitButton.setBounds(exitButton.getX(), exitButton.getY()-5, exitButton.getWidth() + 10, exitButton.getHeight() + 10);
                    System.exit(0);
                }
            }
        });

        panel.setLayout(null);
        panel.add(startButton);
        panel.add(tutorialButton);
        panel.add(exitButton);
        c.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    static class StartPanel extends JPanel{
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            try {
                g.drawImage(ImageIO.read(new File("images/startPanel.jpg")), 0, 0, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
