package com.ood.objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

/**
 * @ClassName Target
 * @Description The target class
 * @Author Yaozheng Wang
 * @Date 2022/4/17 1:10
 **/
public class Target {

    private float x; // Declare the target location of x
    private float y; // Declare the target location of y
    private float previousX; // Declare the target previous location of x
    private float previousY; // Declare the target previous location of x
    private float radius; // Declare the radius of target
    private boolean hit; // Declare the hit condition
    private Random random; // Declare a random number
    private BufferedImage icon; // Declare the buffered image
    private Image img; // Declare the image

    /**
    * @Author Yaozheng Wang
    * @Description Constructor with target radius
    * @Date 2022/4/26 20:46
    * @Param The float radius
    * @Return null
    **/
    public Target(float radius) {
        this.random = new Random();
        this.x = random.nextFloat() * 400 + 400; // Random x location
        this.y = 280;
        setRadius(radius);
        setHit(false); // The hit condition is false
        try { // Get the target image
            this.icon = ImageIO.read(Objects.requireNonNull(Target.class.getResource("/com/ood/images/target.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.img = icon.getScaledInstance(128, 128, Image.SCALE_SMOOTH);
    }

    /**
    * @Author Yaozheng Wang
    * @Description The hit condition
    * @Date 2022/4/26 20:46
    * @Param null
    * @Return null
    **/
    public void hit(){
        this.hit = true;
    }

    /**
    * @Author Yaozheng Wang
    * @Description Use graphics to paint the target
    * @Date 2022/4/26 20:47
    * @Param The Graphics
    * @Return null
    **/
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        if(!hit){ // The target didn't be hit
            g2d.drawImage(icon.getScaledInstance((int)(radius*2), (int)(radius * 2), Image.SCALE_SMOOTH), (int)(x - radius), (int)(y - radius), null);
        }
    }

    //------------GETTERS------------------------
    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float getPreviousX() {
        return previousX;
    }

    public float getPreviousY() {
        return previousY;
    }

    public float getRadius(){
        return radius;
    }

    public boolean isHit(){
        return hit;
    }

    public Random getRandom() {
        return random;
    }

    public BufferedImage getIcon() {
        return icon;
    }

    public Image getImg(){
        return img;
    }

    //-----------SETTERS------------------------
    public void setX(float x){
        if(!hit){ // Not hit
            previousX = this.x;
            this.x = x;
        }
    }

    public void setY(float y){
        if(!hit){ // Not hit
            previousY = this.y;
            this.y = y;
        }
    }

    public void setPreviousX(float previousX) {
        this.previousX = previousX;
    }

    public void setPreviousY(float previousY) {
        this.previousY = previousY;
    }

    public void setRadius(float radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Invalid radius!");
        }
        this.radius = radius;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public void setIcon(BufferedImage icon) {
        this.icon = icon;
    }

    public void setImg(Image img) {
        this.img = img;
    }
}
