import java.awt.geom.*;
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
    private Hitbox box;
    //game related
    private int hitPoints;
    private double moveSpeed;
    public boolean isAlive;
    //movements related
    public boolean mUp, mDown, mLeft, mRight; //not sure yet
    private String sDataOut, sDataIn;
    private double xBorder, yBorder;
    

    /**
     * Initializes the some variables.
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

        xBorder = GameUtils.get().getWidth();
        yBorder = GameUtils.get().getHeight();

        //ship stats
        switch (shipType) {
            case 1: //offensive
                hitPoints = 1;
                moveSpeed = 500;
                size = 30;
                break;

            case 2: //balanced
                hitPoints = 3;
                moveSpeed = 400;
                size = 2;
                break;

            case 3: //defensive
                hitPoints = 5;
                moveSpeed = 300;
                size = 14;
                break;
            default:
                hitPoints = 100;
                moveSpeed = 400;
                break;
        } 

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
     * Draws the player's ship.
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
                box = new Hitbox(xPos + 2, yPos + 6, 112, 72);
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


        sDataOut = String.format("%.1f_%.1f_%b_%b_%b_%b_%d_%d_",xPos,yPos,mUp,mDown,mLeft,mRight,hitPoints,shipType);
        readStringData(sDataIn);
    }

    public void readStringData(String s) {
        String[] data = s.split("_");
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
     * Draws the offensive ship's sprite.
     */
    private void offensiveShip(Graphics2D g2d) {
        if (isAlive){
            g2d.drawImage(ship1Image, (int) xPos, (int) yPos, null, null);
            box.draw(g2d);
        }
        
    }
    
    /**
     * Draws the defensive ship's sprite.
     */
    private void defensiveShip(Graphics2D g2d) {
        if (isAlive){
            g2d.drawImage(ship2Image, (int) xPos, (int) yPos, null, null);
            box.draw(g2d);
        }
    }

    /**
     * Draws the balanced ship's sprite.
     */
    private void balancedShip(Graphics2D g2d) {
        if (isAlive){
            g2d.drawImage(ship3Image, (int) xPos, (int) yPos, null, null);
            box.draw(g2d);
        }
    }
    
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

    public void gotHit(){
        hitPoints -= 1;
    }

    /**
     * Ideally this can only be called once the player
     * dies.
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
                hitPoints = 3;
                moveSpeed = 400;
                break;

            case 3: //defensive
                hitPoints = 5;
                moveSpeed = 300;
                break;
            default:
                hitPoints = 100;
                moveSpeed = 400;
                break;
        } 
    }

    public int getHitPoints() {
        return hitPoints;
    }

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

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }
}
