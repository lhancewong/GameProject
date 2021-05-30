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

/**
 * All drawn game objects will implement this class. It has methods that every game
 * object will need.
 */
public interface GameObject {
    /**
     * Draws the projectile's sprite based on x and y.
     */
    void draw(Graphics2D g2d);

    /**
     * Updates the GameObject.
     * 
     * @param deltaTime
     */
    void update(double deltaTime);

    /**
     * Updates the object using most recent the compressed data.
     * 
     * @param s the compressed data
     */
    void readStringData(String s);

    /**
     * Gets the compressed data of the GameObject.
     */
    String getCompressedData();

    /**
     * Receives the compressed string data.
     */
    void receiveCompressedData(String sDataIn);
    
}
