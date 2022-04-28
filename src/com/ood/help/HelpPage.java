package com.ood.help;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * @ClassName HelpPage
 * @Description The help page class
 * @Author Yaozheng Wang
 * @Date 2022/4/24 11:55
 **/
public class HelpPage extends JPanel {
    // Declare frame and buttons
    private final JFrame frame = new JFrame();
    private final JButton nextButton = new JButton();
    private final JButton backButton = new JButton();
    private final JButton exitButton = new JButton();
    BufferedImage helpImg;
    private int pageNumber = 1;

    /**
    * @Author Yaozheng Wang
    * @Description The help page constructor
    * @Date 2022/4/26 22:50
    * @Param null
    * @Return null
    **/
    public HelpPage() {

        // Read help image and catch exceptioon
        try {
            helpImg = ImageIO.read(Objects.requireNonNull(HelpPage.class.getResource("/com/ood/images/helppage1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set buttons
        nextButton.setFont(new Font("Arial", Font.PLAIN, 13));
        backButton.setFont(new Font("Arial", Font.PLAIN, 13));
        exitButton.setFont(new Font("Arial", Font.PLAIN, 13));
        nextButton.setText("Next ->");
        backButton.setText("<- Back");
        exitButton.setText("EXIT");
        this.setLayout(null);
        backButton.setBounds(150,270,80,25);
        nextButton.setBounds(370,270,80,25);
        exitButton.setBounds(260,300,80,20);
        checkButton();
        updatePage();

        // Back button add listener
        class BackButton implements ActionListener {
            public void actionPerformed(ActionEvent e){
                pageNumber--;
                checkButton();
                updatePage();
                repaint();
            }
        }
        BackButton back = new BackButton();
        backButton.addActionListener(back);

        // Next button add listener
        class NextButton implements ActionListener{
            public void actionPerformed(ActionEvent e){
                pageNumber++;
                checkButton();
                updatePage();
                repaint();
            }
        }
        NextButton next = new NextButton();
        nextButton.addActionListener(next);

        // Exit button add listener
        WindowEvent close = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
        class ExitButton implements ActionListener{
            public void actionPerformed(ActionEvent e){
                frame.dispatchEvent(close);
            }
        }
        ExitButton exit = new ExitButton();
        exitButton.addActionListener(exit);

        // Add components to the panel
        this.add(nextButton);
        this.add(backButton);
        this.add(exitButton);
        Container c = frame.getContentPane();
        c.add(this);

        // Set the frame
        frame.setTitle("Artillery Game");
        frame.setSize(600,370);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //gets the dimensions of the screen
        frame.setLocation(dim.width/2 - 300, dim.height/2 - 250);	//frame is at the center of the screen
        frame.setVisible(true);
        repaint();
    }

    /**
    * @Author Yaozheng Wang
    * @Description Check the button and page number
    * @Date 2022/4/26 22:51
    * @Param null
    * @Return null
    **/
    public void checkButton() {
        if (pageNumber == 1) {
            backButton.setEnabled(false);
        } else if (pageNumber == 5) {
            nextButton.setEnabled(false);
        }else {
            backButton.setEnabled(true);
            nextButton.setEnabled(true);
        }
    }

    /**
    * @Author Yaozheng Wang
    * @Description Update the help page
    * @Date 2022/4/26 22:52
    * @Param null
    * @Return null
    **/
    public void updatePage(){

        try { // Try to update help page and catch exception
            switch(pageNumber) {
                case 1: helpImg = ImageIO.read(Objects.requireNonNull(HelpPage.class.getResource("/com/ood/images/helppage1.png"))); break;
                case 2: helpImg = ImageIO.read(Objects.requireNonNull(HelpPage.class.getResource("/com/ood/images/helppage2.png"))); break;
                case 3: helpImg = ImageIO.read(Objects.requireNonNull(HelpPage.class.getResource("/com/ood/images/helppage3.png")));break;
                case 4: helpImg = ImageIO.read(Objects.requireNonNull(HelpPage.class.getResource("/com/ood/images/helppage4.png")));break;
                case 5: helpImg = ImageIO.read(Objects.requireNonNull(HelpPage.class.getResource("/com/ood/images/helppage5.png"))); break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * @Author Yaozheng Wang
    * @Description Paint the help page
    * @Date 2022/4/26 22:53
    * @Param The graphics
    * @Return null
    **/
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(helpImg, 0, 0, null);
    }

    // ----------Getter------------
    public  JFrame getFrame() {
        return frame;
    }
}
