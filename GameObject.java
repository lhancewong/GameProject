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
     * Updates the GameObject
     */
    void update(double deltaTime);

    /**
     * Compresses the necessary data that will be sent to the server.
     */
    String getCompressedData();

    /**
     * Reads the compressed data received from the server.
     */
    void receiveCompressedData();
    
}
