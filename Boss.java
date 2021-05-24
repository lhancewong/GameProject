import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * This class contains the code that manages the boss' appearance and functionality.
 */
public class Boss implements GameObject{

    private double xPos, yPos;
    private double size;
    private double moveSpeed;
    private boolean isAlive;
    private boolean movingUp, movingDown, movingLeft, movingRight;

    private double hitPoints;


    public Boss(){

        moveSpeed = 5;
        hitPoints = 100;
        movingUp = false;
        movingDown = false;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new chkDirection(), 0, 20);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (isAlive)
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("A:/Admu stuff/Java/CS 22/Finals/Final Proj/GameProject/boss1.jpg"));
            } catch (IOException e) {
            }
            g2d.drawImage(img, xPos, yPos, Color.white);
        
    }

    @Override
    public void update(double d) {
        // TODO Auto-generated method stub
        if(movingUp) {
            yPos -= moveSpeed*d;
        }
        if(movingDown) {
            yPos += moveSpeed*d;
        }

    }

    public class chkDirection extends TimerTask {
        @Override
        public void run() {
            changeDirection();
        }
    }

    public void changeDirection(){
        if (movingUp){
            movingDown = true;
            movingUp = false;
        }
        if (movingDown){
            movingUp = true;
            movingDown = false;
        }
    }

    public void incrementHitPoints(double amnt) {
        hitPoints += amnt;
    }


    @Override
    public String getCompressedData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void receiveCompressedData() {
        // TODO Auto-generated method stub
        
    }
    
}
