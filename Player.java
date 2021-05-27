import java.awt.geom.*;
import java.awt.*;
import java.io.*;

/**
 * This class contains the code that manages the player's appearance and functionality.
 */
public class Player implements GameObject {
    private int pNum;

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
    public Player(double xPos, double yPos, double size, int shipType) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.size = size;
        this.shipType = shipType;

        pNum = GameUtils.get().getPlayerNum();

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
        mUp = false;
        mDown = false;
        mLeft = false;
        mRight = false;

        sDataOut = String.format("%.1f_%.1f_%b_%b_%b_%b",xPos,yPos,mUp,mDown,mLeft,mRight);
        sDataIn = "";
    }

    /**
     * Draws the player's ship.
     * 
     * @param g2d
     */
    @Override
    public void draw(Graphics2D g2d) {
        //System.out.println(xPos + " " + yPos + " " + mUp + " " + mDown + " " + mLeft + " " + mRight);
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

        sDataOut = String.format("%.1f_%.1f_%b_%b_%b_%b",xPos,yPos,mUp,mDown,mLeft,mRight);
        readStringData(sDataIn);
    }

    public void readStringData(String s) {
        if(!s.equals("")) {
            String[] data = s.split("_");
            xPos = Double.parseDouble(data[0]);
            yPos = Double.parseDouble(data[1]);
            mUp = Boolean.parseBoolean(data[2]);
            mDown = Boolean.parseBoolean(data[3]);
            mLeft = Boolean.parseBoolean(data[4]);
            mRight = Boolean.parseBoolean(data[5]);
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
    private void offensiveShip() {
        if(mUp && !mDown) {

        } else if(mDown && !mUp) {

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
    public void sendCompressedData(DataOutputStream dataOut) {
        //String data = String.format("p%d_%b_%b_%b_%b_",pNum,mUp,mDown,mLeft,mRight);
        try {
            /* dataOut.writeDouble(xPos);
            dataOut.writeDouble(yPos);
            dataOut.writeBoolean(mUp);
            dataOut.writeBoolean(mDown);
            dataOut.writeBoolean(mLeft);
            dataOut.writeBoolean(mRight); */
            dataOut.writeUTF(sDataOut);
            dataOut.flush();
        } catch(IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void recieveCompressedData(DataInputStream dataIn) {
        try {
            /* xPos = dataIn.readDouble();
            yPos = dataIn.readDouble();
            mUp = dataIn.readBoolean();
            mDown = dataIn.readBoolean();
            mLeft = dataIn.readBoolean();
            mRight = dataIn.readBoolean(); */
            sDataIn = dataIn.readUTF();
        } catch(IOException ex) {
            System.out.println(ex);
        }
    }
}
