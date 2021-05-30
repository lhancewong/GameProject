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

/**
 * Ideally the projectile's constructor has a line of code that gives it a unique string id.
 * This makes it easier for the server to access a specific bullet and move it.
 */
public interface Projectile{

    /**
     * Sets both the x and y positions of the projectile.
     * 
     * @param xPos x coordinate of the bullet
     * @param yPos y coordinate of the bullet
     */
    void setCenter(double xPos, double yPos);


}
