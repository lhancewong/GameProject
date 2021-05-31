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

import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

/**
 * The projectiles of the player ship.
 */
public class ShipBullet {
    private double xPos, yPos; 
    private double speed;
    private BufferedImage bullet;

    public ShipBullet(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        speed = 1000;
    }

    /**
     * 
     * @param deltaTime
     */
    public void update(double deltaTime) {
        xPos += speed *deltaTime;
    }

    /**
     * 
     * 
     * @param g2d
     */
    public void draw(Graphics2D g2d) {
        bullet = null;
        try {
            bullet = ImageIO.read(new File("sprites/bollet.png"));
        } catch (IOException e) {
        }
        g2d.drawImage(bullet, (int) xPos, (int) yPos, null, null);

    }

    /**
     * Returns the x position of the ship bullet.
     * 
     * @return the x position of the ship bullet
     */
    public double getX() {
        return xPos;
    }

    /**
     * Returns the y position of the ship bullet.
     * 
     * @return the y position of the ship bullet
     */
    public double getY() {
        return yPos;
    }
}
