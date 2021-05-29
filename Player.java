import java.awt.geom.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

/**
 * This class contains the code that manages the player's appearance and functionality.
 */
public class Player implements GameObject {
    private String pName;

    //appearance related
    private double xPos, yPos;
    private double size;
    private double width, height;
    private int shipType;
    private BufferedImage ship1Image;
    private BufferedImage ship2Image;
    private BufferedImage ship3Image;
    private Hitbox box;

    //game related
    private double hitPoints;
    private double moveSpeed;
    private int projectileDamage; //?
    private boolean isAlive;
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
    public Player(String pName, double xPos, double yPos, double size, int shipType) {
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
                moveSpeed = 500;
                projectileDamage = 5;
                break;

            case 2: //balanced
                hitPoints = 3;
                moveSpeed = 400;
                projectileDamage = 3;
                break;

            case 3: //defensive
                hitPoints = 5;
                moveSpeed = 300;
                projectileDamage = 1;
                break;
            default:
                hitPoints = 100;
                moveSpeed = 400;
                projectileDamage = 1;
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

        this.pName = pName;

        sDataOut = "dud";
        sDataIn = "";
    }

    /**
     * Draws the player's ship.
     * 
     * @param g2d
     */
    @Override
    public void draw(Graphics2D g2d) {
        if (isAlive)
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

    /**
     * Updates the player's ship.
     * 
     * @param d this basically makes sure it moves correctly
     */
    @Override
    public void update(double d) {
        //System.out.printf("[U_%.1f_%.1f_%b_%b_%b_%b]\n",xPos,yPos,mUp,mDown,mLeft,mRight);
        
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


        sDataOut = String.format("%s_%.1f_%.1f_%b_%b_%b_%b_%.1f_",pName,xPos,yPos,mUp,mDown,mLeft,mRight,hitPoints);
        readStringData(sDataIn);
    }

    public void readStringData(String s) {
        if(!s.equals("")) {
            String[] data = s.split("_");
            xPos = Double.parseDouble(data[1]);
            yPos = Double.parseDouble(data[2]);
            mUp = Boolean.parseBoolean(data[3]);
            mDown = Boolean.parseBoolean(data[4]);
            mLeft = Boolean.parseBoolean(data[5]);
            mRight = Boolean.parseBoolean(data[6]);
            hitPoints = Double.parseDouble(data[7]);
        }  
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
    private void offensiveShip(Graphics2D g2d) {
        if (isAlive){
            g2d.drawImage(ship1Image, (int) xPos, (int) yPos, null, null);
            box = new Hitbox(xPos + 30, yPos + 12, 134, 46);
            box.draw(g2d);
        }
        
    }
    
    /**
     * Draws the defensive ship's sprite.
     */
    private void defensiveShip(Graphics2D g2d) {
        if (isAlive){
            g2d.drawImage(ship2Image, (int) xPos, (int) yPos, null, null);
            box = new Hitbox(xPos + 2, yPos + 6, 112, 72);
            box.draw(g2d);
        }
    }

    /**
     * Draws the balanced ship's sprite.
     */
    private void balancedShip(Graphics2D g2d) {
        if (isAlive){
            g2d.drawImage(ship3Image, (int) xPos, (int) yPos, null, null);
            box = new Hitbox(xPos + 14, yPos + 10, 106, 50);
            box.draw(g2d);
        }
    }
    
    /**
     * Creates instance of ShipBullet
     */
    public void shoot(){
        /* ShipBullet bullet = new ShipBullet(xPos, yPos);
        bullet.draw(g2d); */

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

    public Hitbox getHitbox(){
        return box;
    }

    public int getShipType(){
        return shipType;
    }

    @Override
    public byte[] getCompressedData() {   
        return sDataOut.getBytes();
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

    public String getPlayerName() {
        return pName;
    }

}
