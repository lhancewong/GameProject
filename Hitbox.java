/**
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

/**
 * A class to store the hitbox of any gameobject
 * that may need one.
 */
public class Hitbox {
    private double xPos;
    private double yPos;
    private double width;
    private double height;

    /**
     * Initializes the HitBox clas.
     * 
     * @param xPos upper left x value of hitbox
     * @param yPos upper left y value of hitbox
     * @param width width of hitbox
     * @param height height of hitbox
     */
    public Hitbox(double xPos, double yPos, double width, double height){
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the x position of the hitbox.
     * 
     * @return the x position of the hitbox
     */
    public double getX(){
        return xPos;
    }

    /**
     * Returns the y position of the hitbox.
     * 
     * @return the y position of the hitbox
     */
    public double getY(){
        return yPos;
    }

    /**
     * Returns the width of the hitbox.
     * 
     * @return the width of the hitbox
     */
    public double getWidth(){
        return width;
    }

    /**
     * Returns the height of the hitbox.
     * 
     * @return the height of the hitbox
     */
    public double getHeight(){
        return height;
    }
}
