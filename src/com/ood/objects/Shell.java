package com.ood.objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * @ClassName Shell
 * @Description The shell class
 * @Author Yaozheng Wang
 * @Date 2022/4/16 22:05
 **/
public class Shell {

    private float x; // Declare the shell location of x
    private float y; // Declare the shell location of y
    private float xSpeed; // Declare the shell speed of x
    private float ySpeed; // Declare the shell speed of y
    private float radius; // Declare the shell radius
    private Image icon; // Declare the shell image

    // Constructor with radius
    public Shell(int radius){
        this.x = 0;
        this.y = 0;
        this.xSpeed = 0;
        this.ySpeed = 0;
        setRadius(radius);
        try { // Get the shell image
            icon = ImageIO.read(Objects.requireNonNull(Shell.class.getResource("/com/ood/images/shell.png"))).getScaledInstance(256,256, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Constructor with radius, location of x, location of y
    public Shell(int radius, int x, int y){
        setX(x);
        setY(y);
        this.xSpeed = 0;
        this.ySpeed = 0;
        setRadius(radius);
        try { // Get the shell image
            icon = ImageIO.read(Objects.requireNonNull(Shell.class.getResource("/com/ood/images/shell.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author Yaozheng Wang
     * @Description Check if the shell collision with target
     * @Date 2022/4/22 15:03
     * @Param target location x, location y and radius
     * @Return returns true if shell overlaps with target
     **/
    public boolean checkCollision(float x, float y, float radius){
        if (radius <= 0) {
            throw new IllegalArgumentException("Invalid radius of target!");
        }
        return Math.sqrt(Math.pow(Math.abs(this.x - x),2)+Math.pow(Math.abs(this.y - y), 2)) < this.radius + radius;
    }

    /**
     * @Author Yaozheng Wang
     * @Description Paint the shell
     * @Date 2022/4/22 15:11
     * @Param Graphics
     * @Return null
     **/
    public void paint(Graphics g){
        g.drawImage(icon,(int)(x - radius), (int)(y - radius),(int)(radius * 2),(int)(radius * 2), null);
    }

    //-------------GETTER------------------------
    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float getRadius(){
        return radius;
    }

    public float getXSpeed(){
        return xSpeed;
    }

    public float getYSpeed(){
        return ySpeed;
    }

    public Image getIcon() {
        return icon;
    }

    //-------------SETTERS------------------------
    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public void setRadius(float radius){
        if (radius <= 0) {
            throw new IllegalArgumentException("Invalid radius of shell!");
        }
        this.radius = radius;
    }

    public void setXSpeed(float xSpeed){
        this.xSpeed = xSpeed;
    }

    public void setYSpeed(float ySpeed){
        this.ySpeed = ySpeed;
    }

    public void setImage(Image icon){
        this.icon = icon;
    }
}
