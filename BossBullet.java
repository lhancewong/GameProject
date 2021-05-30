import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class BossBullet implements GameObject{

    private double xPos, yPos; 
    private double width, height;
    private double speed;
    private int attackType;
    private BufferedImage bullet1, bullet2;

    public BossBullet (double xPos, double yPos, int attackType){
        this.xPos = xPos;
        this.yPos = yPos;
        this.attackType = attackType;
        if (attackType == 1) {
            speed = 650;
        } else {
            speed = 500;
        }
        
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (attackType == 1) {
            width = 150;
            height = 60;
            bullet1(g2d);
        }
        else if (attackType == 2){
            width = 150;
            height = 60;
            bullet1(g2d);
            /* width = 20;
            height = 10;
            bullet2(g2d); */
        }
    }

    public void bullet1(Graphics2D g2d){
        bullet1 = null;
        try {
            bullet1 = ImageIO.read(new File("sprites/fireball.png"));
        } catch (IOException e) {
        }
        g2d.drawImage(bullet1, (int) xPos, (int) yPos, null, null);
    }

    public void bullet2(Graphics2D g2d){
        bullet2 = null;
        try {
            bullet2 = ImageIO.read(new File("sprites/gordo.png"));
        } catch (IOException e) {
        }
        g2d.drawImage(bullet2, (int) xPos, (int) yPos, null, null);
    }

    @Override
    public void update(double deltaTime) {
        xPos -= speed *deltaTime;
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getAtkType(){
        return attackType;
    }

    @Override
    public void readStringData(String s) {
        
    }

    @Override
    public String getCompressedData() {
        return null;
    }

    @Override
    public void receiveCompressedData(String sDataIn) {
        
    }
    
}
