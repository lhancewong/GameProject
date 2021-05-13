/**
 * Ideally the projectile's constructor has a line of code that gives it a unique string id.
 * This makes it easier for the server to access a specific bullet and move it.
 * 
 */
public interface Projectile {

    /**
     * Draws the projectile's sprite based on x and y.
     */
    void draw();

    /**
     * Sets both the x and y positions of the projectile.
     * 
     * @param xPos x coordinate of the bullet
     * @param yPos y coordinate of the bullet
     */
    void setCenter(double xPos, double yPos);

    /**
     * Moves the projectile left or right.
     * 
     * @param amnt the distance to move
     */
    void moveH(double amnt);

    /**
     * Moves the projectile up or down.
     * 
     * @param amnt the distance to move
     */
    void moveV(double amnt);

}
