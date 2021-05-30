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
