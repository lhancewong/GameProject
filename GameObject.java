/**
 * All drawn game objects will implement this class. It had methods that every game
 * object will need.
 */
public interface GameObject {
    /**
     * Draws the projectile's sprite based on x and y.
     */
    void draw();

    /**
     * Updates the GameObject
     */
    void update();

    /**
     * Compresses the necessary data that will be sent to the server.
     */
    String getCompressedData();

    /**
     * Reads the compressed data received from the server.
     */
    void recieveCompressedData();
    
}
