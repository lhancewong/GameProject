/**
 * Ideally the projectile's constructor has a line of code that gives it a unique string id.
 * This makes it easier for the server to access a specific bullet and move it.
 * 
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
