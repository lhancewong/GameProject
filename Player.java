import java.awt.geom.*;
import java.awt.*;

/**
 * This class contains the code that manages the player's appearance and functionality.
 */
public class Player implements GameObject{
    private int playerID;

    //appearance related
    private double xPos, yPos;
    private double size;
    private int shipType;

    //game related
    private int hitPoints;
    private double moveSpeed;
    private int projectileDamage; //?
    private boolean isAlive;
    private boolean movingUp, movingDown, movingLeft, movingRight; //not sure yet
    

    /**
     * Initializes the some variables.
     * 
     * @param xPos x coordinate of ship
     * @param yPos y coordinate of ship
     * @param shipType 1 for offense, 2 for balance, 3 for defense
     */
    public Player(double xPos, double yPos, double size, int shipType) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.size = size;
        this.shipType = shipType;

        //ship stats
        switch (shipType) {
            case 1: //offensive
                hitPoints = 1;
                moveSpeed = 5;
                projectileDamage = 5;
                break;

            case 2: //balanced
                hitPoints = 3;
                moveSpeed = 5;
                projectileDamage = 3;
                break;

            case 3: //defensive
                hitPoints = 5;
                moveSpeed = 4;
                projectileDamage = 1;
                break;
        } 

        isAlive = true;
        movingUp = false;
        movingDown = false;
        movingLeft = false;
        movingRight = false;
    }

    /**
     * Draws the player's ship.
     * 
     * @param g2d
     */
    public void draw(Graphics2D g2d) {
        if (isAlive)
            switch(shipType) {
                case 1: //offensive
                    offensiveShip();
                    break;
                case 2: //balanced
                    balancedShip();
                    break;
                case 3: //defensive
                    defensiveShip();
                    break;
                default:
                    g2d.setColor(Color.BLACK);
                    g2d.fillRect((int)xPos,(int)yPos,(int)size,(int)size);
            }

    }

    /**
     * Updates the player's ship.
     * 
     * @param d this basically makes sure it moves correctly
     */
    public void update(double d) {
        if(movingUp) {
            yPos -= moveSpeed*d;
        }
        if(movingDown) {
            yPos += moveSpeed*d;
        }
        if(movingLeft) {
            xPos -= moveSpeed*d;
        }
        if(movingRight) {
            xPos += moveSpeed*d;
        }

        //then another if about getting hit and taking damage.

    }

    /**
     * Increments the player's amount of hitpoints.
     * 
     * @param amnt
     */
    public void incrementHitPoints(double amnt) {
        hitPoints += amnt;
    }

    /**
     * Draws the offensive ship's sprite.
     */
    private void offensiveShip() {
        if(movingUp && !movingDown) {

        } else if(movingDown && !movingUp) {

        } else {

        }
        
    }

    /**
     * Draws the balanced ship's sprite.
     */
    private void balancedShip() {

    }
    
    /**
     * Draws the defensive ship's sprite.
     */
    private void defensiveShip() {

    }

    /**
     * Ideally this can only be called once the player
     * dies.
     * 
     * @param shipType 1 for offense, 2 for balance, 3 for defense
     */
    public void setShip(int shipType) {
        this.shipType = shipType;
    }

    public void setMovement(String letter, boolean value) {
        switch(letter) {
            case "W":
                movingUp = value;
                break;
            case "A":
                movingLeft = value;
                break;
            case "S":
                movingDown = value;
                break;
            case "D":
                movingRight = value;
                break;
        }

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
