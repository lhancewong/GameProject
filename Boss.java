import java.awt.*;
import java.awt.geom.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



/**
 * This class contains the code that manages the boss' appearance and functionality.
 */
public class Boss implements GameObject{

    private double xPos, yPos;
    private int randomDistance;
    private double moveSpeed;
    private boolean isAlive;
    private boolean movingUp, movingDown, movingLeft, movingRight;
    private BufferedImage img;
    private Hitbox box;

    private double hitPoints;


    public Boss(){

        xPos = (GameUtils.get().getWidth()/2)+150;
        yPos = GameUtils.get().getHeight() - 460;
        moveSpeed = 400;
        hitPoints = 100;
        isAlive = true;
        movingUp = true;
        movingDown = false;
        randomDistance = (int)Math.floor(Math.random()*(yPos-0+1)+0);
        
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (isAlive) {
            img = null;
            try {
                img = ImageIO.read(new File("A:/Admu stuff/Java/CS 22/Finals/Final Proj/GameProject/boss1.png"));
            } catch (IOException e) {
            }
            g2d.drawImage(img, (int) xPos, (int) yPos, null, null);
        }
        box = new Hitbox(xPos + 102, yPos + 98, 198, 160);
        box.draw(g2d);
        
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

    public Hitbox getHitbox(){
        return box;
    }

    //encapsulate all movement things under this function
    public void movementPattern(){
        
        
    }

    public void changeDirection(){

        if (movingUp){
            movingDown = !movingDown;
            movingUp =  !movingUp;
            randomDistance = (int)Math.floor(Math.random()*(383-yPos+1)+yPos);
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
        return null;
    }

    @Override
    public void receiveCompressedData(String data) {
        
    }
    
}
