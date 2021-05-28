import java.awt.Graphics2D;
import java.io.*;

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
     * Sends the compressed data.
     */
    void sendCompressedData(DataOutputStream dataOut);

    /**
     * Receives the compressed data.
     */
    void receiveCompressedData(DataInputStream dataIn);
    
}
