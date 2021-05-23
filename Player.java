import java.awt.geom.*;
import java.awt.*;

/**
 * This class contains the code that manages the player's appearance and functionality.
 */
public class Player implements GameObject {
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
    //movements related
    public boolean movingUp, movingDown, movingLeft, movingRight; //not sure yet
    public double xBorder, yBorder;
    

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

        xBorder = GameUtils.get().getWidth();
        yBorder = GameUtils.get().getHeight();

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
            default:
                hitPoints = 100;
                moveSpeed = 400;
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
    @Override
    public void draw(Graphics2D g2d) {
        //System.out.println(xPos + " " + yPos + " " + movingUp + " " + movingDown + " " + movingLeft + " " + movingRight);
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
    @Override
    public void update(double d) {
        //System.out.println("U" + xPos + " " + yPos + " " + movingUp + " " + movingDown + " " + movingLeft + " " + movingRight);
        
        /**
         * Note:It would be nice if the -10's will instead be based on the 
         * speed of the ship and deltatime to optimize the x and y borders
         * of the ship's movements.
         */
        if(xPos+size >= xBorder-10) {
            movingRight = false;
        }
        if(xPos <= 10) {
            movingLeft = false;
        }
        if(yPos+size >= yBorder-10) {
            movingDown = false;
        }
        if(yPos <= 10) {
            movingUp = false;
        }

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

    @Override
    public String getCompressedData() {
        String data = "";
        return null;
    }

    @Override
    public void recieveCompressedData() {
        // TODO Auto-generated method stub
        
    }
}
