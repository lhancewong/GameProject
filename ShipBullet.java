import java.awt.*;

/**
 * The projectiles of the player ship.
 */
public class ShipBullet implements GameObject, Projectile {
    private double xPos, yPos; 
    private double speed;

    public ShipBullet(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        speed = 1000;
    }

    @Override
    public void setCenter(double xPos, double yPos) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.fillRect( (int) xPos, (int) yPos, 4, 4);

    }

    public double getX() {
        return xPos;
    }

    @Override
    public void update(double d) {
        // TODO Auto-generated method stub
        xPos += speed*d;
    }

    @Override
    public String getCompressedData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void receiveCompressedData(String data) {
        // TODO Auto-generated method stub
        
    }
    
}
