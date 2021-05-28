import java.awt.*;
import java.io.*;

/**
 * The projectiles of the player ship.
 */
public class ShipBullet implements GameObject, Projectile {
    private double xPos, yPos; 
    private double speed;

    public ShipBullet(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        speed = 1000;
    }

    @Override
    public void setCenter(double xPos, double yPos) {
        
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.black);
        g2d.fillRect( (int) xPos, (int) yPos, 4, 4);

    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    @Override
    public void update(double d) {
        xPos += speed*d;
    }

    @Override
    public void sendCompressedData(DataOutputStream dataOut) {
        
    }

    @Override
    public void receiveCompressedData(DataInputStream dataIn) {
        
    }
    
}
