import java.awt.*;
import javax.swing.*;
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

    private int xPos, yPos;
    private int xDiff, yDiff;
    private int randomDistance;
    private double xDistance, yDistance;
    private double size;
    private double moveSpeed;
    private double chgDirTimer;
    private boolean isAlive;
    private boolean movingUp, movingDown, movingLeft, movingRight;
    private BufferedImage img;
    private JLabel picLabel;

    private double hitPoints;


    public Boss(){

        xPos = GameUtils.get().getWidth()/2;
        yPos = GameUtils.get().getHeight()*1/5;
        moveSpeed = 5;
        hitPoints = 100;
        chgDirTimer = 0;
        movingUp = true;
        movingDown = false;
        randomDistance = (int)Math.floor(Math.random()*(yPos-0+1)+0);
        Timer timer = new Timer();
        /* timer.scheduleAtFixedRate(new chkDirection(), 0, 3000); */
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (isAlive)
            img = null;
            try {
                img = ImageIO.read(new File("A:/Admu stuff/Java/CS 22/Finals/Final Proj/GameProject/boss1.png"));
            } catch (IOException e) {
            }
            g2d.drawImage(img, xPos, yPos, null, null);
        
    }

    @Override
    public void update(double d) {

        System.out.println(randomDistance);
        if(movingUp) {
            if (yPos > randomDistance){
                yPos -= moveSpeed*d;
            }
            else {
                changeDirection();
            }
        }
        if(movingDown) {
            if (yPos < randomDistance){
                yPos += moveSpeed*d;
            }
            else {
                changeDirection();
            }
        }

    }

    /* public class chkDirection extends TimerTask {
        @Override
        public void run() {
            changeDirection();
        }
    } */

    //encapsulate all movement things under this function
    public void movementPattern(){
        
        
    }

    public void changeDirection(){
        if (movingUp){
            /* yDiff = yPos;
            yDistance = yDiff * Math.random(); */
            randomDistance = (int)Math.floor(Math.random()*(720-yPos+1)+yPos);
            System.out.println(randomDistance);
            movingUp = false;
            movingDown = true;
        }
        else{
            randomDistance = (int)Math.floor(Math.random()*(yPos-0+1)+0);
            System.out.println(randomDistance);
            movingDown = false;
            movingUp = true;
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
    public void receiveCompressedData(String data) {
        // TODO Auto-generated method stub
        
    }
    
}
