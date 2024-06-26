/**
	@author Wilbert Meinard L. Chen (201153)
    @author Lhance Christian S. Wong (205467)
	@version May 31, 2021
**/

/*
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
*/

import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

/**
 * This class contains the code that manages the player's appearance and functionality.
 */
public class Player implements GameObject {
    //appearance related
    private double xPos, yPos;
    private double size;
    private int shipType;
    private BufferedImage ship1Image;
    private BufferedImage ship2Image;
    private BufferedImage ship3Image;
    //game related
    private int hitPoints;
    private Hitbox box;
    public boolean isAlive;
    //movements related
    private double moveSpeed;
    public boolean mUp, mDown, mLeft, mRight; //not sure yet
    private String sDataOut, sDataIn;
    private double xBorder, yBorder;

    /**
     * Initializes the player class.
     * 
     * @param xPos x coordinate of ship
     * @param yPos y coordinate of ship
     * @param shipType 1 for offense, 2 for balance, 3 for defense
     */
    public Player(double xPos, double yPos, int shipType) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.shipType = shipType;
        box = new Hitbox(xPos + 30, yPos + 12, 134, 46);

        //window borders
        xBorder = GameUtils.get().getWidth();
        yBorder = GameUtils.get().getHeight();

        //ship stats
        switch (shipType) {
            case 1: //offensive
                hitPoints = 1;
                moveSpeed = 500;
                size = 46;
                break;

            case 2: //balanced
                hitPoints = 9;
                moveSpeed = 400;
                size = 50;
                break;

            case 3: //defensive
                hitPoints = 15;
                moveSpeed = 300;
                size = 50;
                break;
            default:
                hitPoints = 100;
                moveSpeed = 400;
                break;
        } 

        //ship sprites
        ship1Image = null;
        ship2Image = null;
        ship3Image = null;
        try {
            ship1Image = ImageIO.read(new File("sprites/Ship1.png"));
            ship2Image = ImageIO.read(new File("sprites/Ship2.png"));
            ship3Image = ImageIO.read(new File("sprites/Ship3.png"));
        } catch (IOException e) {
        }

        isAlive = true;
        mUp = false;
        mDown = false;
        mLeft = false;
        mRight = false;

        sDataOut = "";
        sDataIn = "";
    }

    /**
     * Updates the player's ship.
     * 
     * @param d this basically makes sure it moves correctly
     */
    @Override
    public void update(double d) {
        if (hitPoints <= 0) {
            isAlive = false;
        }

        switch (shipType) {
            case 1:
                box = new Hitbox(xPos + 30, yPos + 12, 134, 46);
                break;
            case 2:
                box = new Hitbox(xPos + 5, yPos + 6, 112, 50);
                break;
            case 3:
                box = new Hitbox(xPos + 14, yPos + 10, 106, 50);
                break;
        }

        double c = moveSpeed*d;
        if(xPos+size >= xBorder-c) {
            mRight = false;
        }
        if(xPos <= 10) {
            mLeft = false;
        }
        if(yPos+size >= yBorder-c) {
            mDown = false;
        }
        if(yPos <= 10) {
            mUp = false;
        }

        if(mUp) {
            yPos -= moveSpeed*d;
        }
        if(mDown) {
            yPos += moveSpeed*d;
        }
        if(mLeft) {
            xPos -= moveSpeed*d;
        }
        if(mRight) {
            xPos += moveSpeed*d;
        }

        //The data that will be sent over to the server/client.
        sDataOut = String.format("%.1f_%.1f_%b_%b_%b_%b_%d_%d_",xPos,yPos,mUp,mDown,mLeft,mRight,hitPoints,shipType);
        readStringData(sDataIn);
    }

    @Override
    public void readStringData(String s) {
        String[] data = s.split("_");
        /**
         * Checks if the string/array size is suitable for the player.
         * this is to avoid Index out of bounds errors.
         */
        if(!s.equals("") && data.length == 9) {
            xPos = Double.parseDouble(data[1]);
            yPos = Double.parseDouble(data[2]);
            mUp = Boolean.parseBoolean(data[3]);
            mDown = Boolean.parseBoolean(data[4]);
            mLeft = Boolean.parseBoolean(data[5]);
            mRight = Boolean.parseBoolean(data[6]);
            hitPoints = Integer.parseInt(data[7]);
            shipType = Integer.parseInt(data[8]);
        }  
    }

    /**
     * Draws the player's ship based on their ship type.
     * 
     * @param g2d
     */
    @Override
    public void draw(Graphics2D g2d) {
        if (isAlive) {
            switch(shipType) {
                case 1: //offensive
                    offensiveShip(g2d);
                    break;
                case 2: //balanced
                    balancedShip(g2d);
                    break;
                case 3: //defensive
                    defensiveShip(g2d);
                    break;
                default:
                    g2d.setColor(Color.BLACK);
                    g2d.fillRect((int)xPos,(int)yPos,(int)size,(int)size);
            }
        }
    }

    /**
     * Draws the offensive ship's sprite.
     */
    private void offensiveShip(Graphics2D g2d) {
        if (isAlive){
            g2d.drawImage(ship1Image, (int) xPos, (int) yPos, null, null);
        } 
    }

    /**
     * Draws the balanced ship's sprite.
     */
    private void balancedShip(Graphics2D g2d) {
        if (isAlive){
            g2d.drawImage(ship3Image, (int) xPos, (int) yPos, null, null);
        }
    }
    
    /**
     * Draws the defensive ship's sprite.
     */
    private void defensiveShip(Graphics2D g2d) {
        if (isAlive){
            g2d.drawImage(ship2Image, (int) xPos, (int) yPos, null, null);
        }
    }

    /**
     * Sets the ship of the player. It also updates the player's stats 
     * based on that.
     * 
     * @param shipType 1 for offense, 2 for balance, 3 for defense
     */
    public void setShip(int shipType) {
        this.shipType = shipType;
        switch (shipType) {
            case 1: //offensive
                hitPoints = 1;
                moveSpeed = 500;
                break;

            case 2: //balanced
                hitPoints = 9;
                moveSpeed = 400;
                break;

            case 3: //defensive
                hitPoints = 15;
                moveSpeed = 300;
                break;
            default:
                hitPoints = 100;
                moveSpeed = 400;
                break;
        } 
    }

    /**
     * Checks if the player is getting hit by a projectile.
     * 
     * @param i the bullet to be checked
     * @return true if the player is being hit and false if not
     */
    public boolean checkHitbox(BossBullet i){
        if (i.getX() + i.getWidth() >= box.getX() && 
            i.getX() <= box.getX() + box.getWidth() &&
            i.getY() + i.getHeight()>= box.getY() &&
            i.getY() <= box.getY() + box.getHeight() ){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Reduces the player's hitpoints by 1.
     */
    public void gotHit(){
        hitPoints -= 1;
    }
    
    /**
     * Returns the player's x position.
     * 
     * @return the player's x position
     */
    public double getX() {
        return xPos;
    }

    /**
     * Returns the player's y position.
     * 
     * @return the player's x position
     */
    public double getY() {
        return yPos;
    }

    /**
     * Returns the player's hitpoints.
     * 
     * @return the player's hitpoints
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * Returns the player's ship type.
     * 
     * @return the player's ship type
     */
    public int getShipType(){
        return shipType;
    }

    @Override
    public String getCompressedData() {   
        return sDataOut;
    }

    @Override
    public void receiveCompressedData(String sDataIn) {
        this.sDataIn = sDataIn;
    }
}
