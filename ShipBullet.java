import java.awt.*;
<<<<<<< HEAD
import java.io.DataInputStream;
import java.io.DataOutputStream;
=======
import java.io.*;
>>>>>>> Server-N-Hitboxes

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
<<<<<<< HEAD
    public void draw(Graphics2D g2d) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(double deltaTime) {
        // TODO Auto-generated method stub
=======
    public void update(double d) {
        xPos += speed*d;
    }

    @Override
    public void sendCompressedData(DataOutputStream dataOut) {
>>>>>>> Server-N-Hitboxes
        
    }

    @Override
<<<<<<< HEAD
    public void sendCompressedData(DataOutputStream dataOut) {
        // TODO Auto-generated method stub
=======
    public void receiveCompressedData(DataInputStream dataIn) {
>>>>>>> Server-N-Hitboxes
        
    }

    @Override
<<<<<<< HEAD
    public void recieveCompressedData(DataInputStream dataIn) {
        // TODO Auto-generated method stub
=======
    public void readStringData(String s) {
>>>>>>> Server-N-Hitboxes
        
    }
    
}
