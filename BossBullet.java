/**
	This is a template for a Java file.
	
	@author Wilbert Meinard L. Chen (201153)
    @author Lhance Christian S. Wong (205467)
	@version May 31, 2021
**/

/*
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
*/

import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 
 */
public class BossBullet implements GameObject{

    //Dimensions
    private double xPos, yPos; 
    private double width, height;
    private double speed;

    //To determine attack
    private int attackType;

    //appearance
    private BufferedImage bullet1, bullet2;

    /**
     * Initializes the bossbullet class. The attacktype determines
     * which sprite to use.
     * 
     * @param xPos the x position of the boss bullet
     * @param yPos the y position of the boss bullet
     * @param attackType the attacktype of the boss bullet
     */
    public BossBullet (double xPos, double yPos, int attackType){
        this.xPos = xPos;
        this.yPos = yPos;
        this.attackType = attackType;
        if (attackType == 1) {
            speed = 700;
        } else {
            speed = 625;
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
        }
    }

    /**
     * Draws the first bullet sprite of boss bullet.
     */
    public void bullet1(Graphics2D g2d){
        bullet1 = null;
        try {
            bullet1 = ImageIO.read(new File("sprites/fireball.png"));
        } catch (IOException e) {
        }
        g2d.drawImage(bullet1, (int) xPos, (int) yPos, null, null);
    }

    /**
     * Draws the second bullet sprite of boss bullet.
     */
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

    /** 
     * Returns the x position of the boss bullet.
     * 
     * @return the x position of the boss bullet
     */
    public double getX() {
        return xPos;
    }

    /**
     * Returns the y position of the boss bullet.
     * 
     * @return the y position of the boss bullet
     */
    public double getY() {
        return yPos;
    }

    /**
     * Returns the width of the boss bullet. The width may
     * also be determined by the current attackType.
     * 
     * @return the width of the boss bullet
     */
    public double getWidth() {
        return width;
    }

    /**
     * 
     * 
     * @return the height of the boss bullet
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the attacktype of the boss bullet.
     * 
     * @return the attack type of the boss bullet
     */
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
