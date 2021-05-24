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

    private double xPos, yPos;
    private int xDiff, yDiff;
    private int randomDistance;
    private double xDistance, yDistance;
    private double moveSpeed;
    private boolean isAlive;
    private boolean movingUp, movingDown, movingLeft, movingRight;
    private BufferedImage img;

    private double hitPoints;


    public Boss(){

        xPos = (GameUtils.get().getWidth()/2)+150;
        yPos = GameUtils.get().getHeight() - 460;
        moveSpeed = 100;
        hitPoints = 100;
        movingUp = true;
        movingDown = false;
        randomDistance = (int)Math.floor(Math.random()*(yPos-0+1)+0);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (isAlive)
            img = null;
            try {
                img = ImageIO.read(new File("A:/Admu stuff/Java/CS 22/Finals/Final Proj/GameProject/boss1.png"));
            } catch (IOException e) {
            }
            g2d.drawImage(img, (int) xPos, (int) yPos, null, null);
        
    }

    @Override
    public void update(double d) {


        if(movingUp) {
            if (yPos >= randomDistance){
                yPos -= moveSpeed*d;
            }
            else {
                changeDirection();
            }
        }
        if (movingDown) {
            if (yPos <= randomDistance){
                yPos += moveSpeed*d;
            }
            else {
                changeDirection();
            }
        }

    }


    //encapsulate all movement things under this function
    public void movementPattern(){
        
        
    }

    public void changeDirection(){

        if (movingUp){
            movingDown = !movingDown;
            movingUp =  !movingUp;
            randomDistance = (int)Math.floor(Math.random()*(233-yPos+1)+yPos);
        }
        else{
            movingDown = !movingDown;
            movingUp =  !movingUp;
            randomDistance = (int)Math.floor(Math.random()*(yPos-0+1)+0);
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
