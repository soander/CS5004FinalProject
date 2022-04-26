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
public class Game extends JPanel {
    //Graphics stuff
    private JButton fireButton;
    private JFrame parent;
    private Game me;

    private Thread thread;
    private String information;
    private String hitResult;
    private static int hitCount;
    private static int missCount;

    //game things
    private Shell shell;
    private Target target;

    //images
    BufferedImage barrelImg;
    BufferedImage wheels;
    BufferedImage background;

    //timers
    private JSpinner powerSpinner;
    private JSpinner angleSpinner;

    //general variables
    private boolean ready; // Did the player ready for next shot?
    private boolean hitCondition; // Did the player hit a target this shot?
    private int launchAngle;	//0 to 90, use Math.toRadians(angle) cuz stuff takes radians
    private float launchPower;	//for use with powerTracker
    private final double GRAVITY = 9.81;

    public Game(JFrame parent, String mapName) {

        //general variables
        launchPower = 100;
        launchAngle = 45;
        ready = true;
        information = "Click Button to fire shell !";
        hitResult = "";
        hitCount = 0;
        missCount = 0;

        //game things
        shell = new Shell(10, 71, 287);
        target = new Target(40);

        //Graphics
        this.parent = parent;
        me = this;
        fireButton = new JButton("Fire");
        powerSpinner = new JSpinner(new SpinnerNumberModel(100, 100, 170, 5));
        angleSpinner = new JSpinner(new SpinnerNumberModel(45, 0, 90, 1));

        //set initial values
        this.setPreferredSize(new Dimension(809,306));


        //images
        try {
            barrelImg = ImageIO.read(new File("images/cannon.png"));
            wheels = ImageIO.read(new File("images/wheel.png"));
            background = ImageIO.read((new File(mapName)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //adding to game panel
        this.add(fireButton);
        this.add(powerSpinner);
        this.add(angleSpinner);

        //Action Listeners
        fireButton.addActionListener(new FireButtonListener());
        powerSpinner.addChangeListener(new PowerSpinnerListener());
        angleSpinner.addChangeListener(new AngleSpinnerListener());
    }


    private class FireButtonListener implements ActionListener, Runnable {
        public void actionPerformed(ActionEvent e) {
            shell = new Shell(10);
            thread = new Thread(this);
            thread.start();
            repaint(1);
        }

        @Override
        public void run() {
            launchShell();
        }
    }

    public class PowerSpinnerListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            launchPower = (int)((JSpinner)e.getSource()).getValue();
        }
    }

    public class AngleSpinnerListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            launchAngle = (int)((JSpinner)e.getSource()).getValue();
            repaint();
        }
    }

    public Shell getShell(){
        return shell;
    }

    public void setBallImg(Image img){
        shell.setImage(img);
    }

    public void setBackgroundImg(BufferedImage background){
        this.background = background;
    }

    private void launchShell() {
        ready = false;
        double radianAngle=Math.toRadians(launchAngle);
        shell.setXSpeed((float) (launchPower * Math.cos(Math.toRadians(launchAngle))));
        shell.setYSpeed((float) (launchPower * Math.sin(Math.toRadians(launchAngle))));
        double t = 0.01;
        double viy=launchPower*Math.sin(radianAngle);

        double sx=0,sy=0;
        double vx=0,vy=0;

        double timeincrement=.065;
        if (!ready) {
            while ((287-sy) < 290){
                vx=launchPower*Math.cos(radianAngle);
                vy=launchPower*Math.sin(radianAngle)- GRAVITY * t;
                try {
                    thread.sleep(20);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                sx=vx*t; // hor acceleation is zero
                sy=vy*t-(0.5 * 9.81 * t * t);
                t+=timeincrement;

                shell.setX(71+(int) sx);
                shell.setY(287 - (int) sy);
                repaint(1);
            }

            if(!target.isHit() && shell.checkCollision(target.getX(), target.getY(), target.getRadius())){
                target.hit();
                hitCondition = true;
                hitResult = "You Hit Target !";
                hitCount++;
                reset();
            } else {
                hitResult = "Missed !";
                missCount++;
                reset();
            }
        }
    }

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
        hitCondition = false;
        repaint(1);
    }

    public void newTarget(){
        target = new Target(40);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);

        //game things
        shell.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        target.paint(g);

        //Graphics
        fireButton.setBounds(70, 100, 57, 20);
        powerSpinner.setBounds(70, 70, 53, 20);
        angleSpinner.setBounds(70, 40, 45, 20);

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

        //draw Images
        double locationX = 51;
        double locationY = 87;
        AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(-launchAngle), locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g2d.drawImage(op.filter(barrelImg, null), 20, 200, null);
        g.drawImage(wheels, 37, 276, null);
    }
}
