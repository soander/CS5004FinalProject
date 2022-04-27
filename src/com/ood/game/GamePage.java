package com.ood.game;

import com.ood.objects.Shell;
import com.ood.objects.Target;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @ClassName Game
 * @Description The game panel
 * @Author Yaozheng Wang
 * @Date 2022/4/17 1:05
 **/
public class GamePage extends JPanel {
    private final JButton fireButton; // Declare a fire button

    private final String information; // Declare information String
    private String hitResult; // Declare hit result
    private static int hitCount; // Declare static hit count
    private static int missCount; // Declare static miss count

    private Shell shell; // The game object shell
    private Target target; // The game object target

    private BufferedImage barrelImg; // The barrel image
    private BufferedImage wheels; // The wheels image
    private BufferedImage background; // The background image

    private final JSpinner powerSpinner; // The power spinner
    private final JSpinner angleSpinner; // The angle spinner

    private boolean ready; // Ready for next shot
    private int launchAngle; // The launch Angle 0 to 90
    private float launchPower; // The launch power 100 to 170

    /**
    * @Author Yaozheng Wang
    * @Description The game panel constructor
    * @Date 2022/4/26 20:07
    * @Param The String map name
    * @Return null
    **/
    public GamePage(String mapName) {
        // The information
        launchPower = 100;
        launchAngle = 45;
        ready = true;
        information = "Click Button to fire shell !";
        hitResult = "";
        hitCount = 0;
        missCount = 0;

        // New shell and target
        shell = new Shell(10, 71, 287);
        target = new Target(40);

        // New fireButton, powerSpinner and angleSpinner
        fireButton = new JButton("Fire");
        powerSpinner = new JSpinner(new SpinnerNumberModel(100, 100, 170, 5));
        angleSpinner = new JSpinner(new SpinnerNumberModel(45, 0, 90, 1));

        // Set the game window size
        this.setPreferredSize(new Dimension(809,306));

        // Try to read images and catch exception
        try {
            barrelImg = ImageIO.read(new File("images/cannon.png"));
            wheels = ImageIO.read(new File("images/wheel.png"));
            background = ImageIO.read((new File(mapName)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add fireButton, powerSpinner and angleSpinner to game panel
        this.add(fireButton);
        this.add(powerSpinner);
        this.add(angleSpinner);

        // Add action listeners
        fireButton.addActionListener(new FireButtonListener());
        powerSpinner.addChangeListener(new PowerSpinnerListener());
        angleSpinner.addChangeListener(new AngleSpinnerListener());
    }

    /**
    * @Author Yaozheng Wang
    * @Description The FireButtonListener to launch a shell
    * @Date 2022/4/26 20:11
    * @Param The action event
    **/
    private class FireButtonListener implements ActionListener, Runnable {
        public void actionPerformed(ActionEvent e) {
            shell = new Shell(10);
            // Declare a thread
            Thread thread = new Thread(this);
            thread.start();
            repaint(1);
        }

        @Override
        public void run() { // Use thread run method to launch shell
            launchShell();
        }
    }

    /**
    * @Author Yaozheng Wang
    * @Description The PowerSpinnerListener set the launch power
    * @Date 2022/4/26 20:12
    * @Param The ChangeEvent
    * @Return null
    **/
    public class PowerSpinnerListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            launchPower = (int)((JSpinner)e.getSource()).getValue();
        }
    }

    /**
    * @Author Yaozheng Wang
    * @Description The AngleSpinnerListener set the launch angle
    * @Date 2022/4/26 20:13
    * @Param The ChangeEvent
    * @Return null
    **/
    public class AngleSpinnerListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            launchAngle = (int)((JSpinner)e.getSource()).getValue();
            repaint();
        }
    }

    /**
    * @Author Yaozheng Wang
    * @Description The launch shell method
    * @Date 2022/4/26 20:17
    * @Param null
    * @Return null
    **/
    private void launchShell() {
        ready = false;
        double radianAngle = Math.toRadians(launchAngle); // Get the radian angle
        shell.setXSpeed((float) (launchPower * Math.cos(radianAngle))); // Set shell X speed
        shell.setYSpeed((float) (launchPower * Math.sin(radianAngle))); // Set shell Y speed
        double t = 0.01; // Set the time
        double GRAVITY = 9.81;

        double sx,sy = 0; // The shell's x and y distance
        double vx,vy; // The shell's x and y speed

        double timeIncrement =.065; // Set increment time
        if (!ready) {
            while ((287-sy) < 290){ // The shell didn't touch earth
                vx = shell.getXSpeed();
                vy = shell.getYSpeed()- GRAVITY * t;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                sx=vx*t; // The change of shell's x distance after time increment
                sy=vy*t-(0.5 * 9.81 * t * t); // The change of shell's y distance after time increment
                t+=timeIncrement;

                shell.setX(71+(int) sx); // New x position
                shell.setY(287 - (int) sy); // New y position
                repaint(1);
            }

            // Check if shell collision with target
            if(!target.isHit() && shell.checkCollision(target.getX(), target.getY(), target.getRadius())){
                target.hit();
                hitResult = "You Hit Target !";
                hitCount++;
                reset();
            } else {
                hitResult = "Missed !";
                missCount++;
                reset(); // Call reset method
            }
        }
    }

    /**
    * @Author Yaozheng Wang
    * @Description The reset method to start new game round
    * @Date 2022/4/26 20:28
    * @Param null
    * @Return null
    **/
    public void reset(){
        shell.setXSpeed(0);
        shell.setYSpeed(0);
        shell.setX(71);
        shell.setY(287);
        newTarget();
        launchPower = 100;
        launchAngle = 45;
        powerSpinner.setValue(100);
        angleSpinner.setValue(45);
        ready = true;
        repaint(1);
    }

    /**
    * @Author Yaozheng Wang
    * @Description The method to new a random target
    * @Date 2022/4/26 20:30
    * @Param null
    * @Return null
    **/
    public void newTarget(){
        target = new Target(40);
    }

    /**
    * @Author Yaozheng Wang
    * @Description The method to paint game panel
    * @Date 2022/4/26 20:30
    * @Param The Graphics
    * @Return null
    **/
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);

        // Paint shell and target
        shell.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        target.paint(g);

        // Paint fireButton, powerSpinner and angleSpinner
        fireButton.setBounds(70, 100, 57, 20);
        powerSpinner.setBounds(70, 70, 53, 20);
        angleSpinner.setBounds(70, 40, 45, 20);

        // Paint the information and hit result
        Font f = new Font("Calibri", Font.BOLD, 30);
        g.setFont(f);
        g.drawString(hitResult, 370, 306/2);
        f = new Font("Helvetica", Font.BOLD, 15);
        g.setFont(f);
        g.drawString(information, 37/2, 276/2);
        g.drawString("Angle", 25, 55);
        g.drawString("Power", 25, 85);
        f = new Font("Helvetica", Font.BOLD, 25);
        g.setFont(f);
        g.drawString("HIT MISS", 365, 75);
        g.drawString(Integer.toString(hitCount), 380, 100);
        g.drawString(Integer.toString(missCount), 430, 100);

        // Paint the barrel and wheels
        double locationX = 51;
        double locationY = 87;
        AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(-launchAngle), locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g2d.drawImage(op.filter(barrelImg, null), 20, 200, null);
        g.drawImage(wheels, 37, 276, null);
    }
}
