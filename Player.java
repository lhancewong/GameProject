import java.awt.geom.*;
import java.awt.*;
import java.io.*;
<<<<<<< HEAD
=======
import javax.imageio.*;
import java.awt.image.*;
>>>>>>> Server-N-Hitboxes

/**
 * This class contains the code that manages the player's appearance and functionality.
 */
public class Player implements GameObject {
    private int pNum;

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
<<<<<<< HEAD
        //System.out.printf("[U_%.1f_%.1f_%b_%b_%b_%b]\n",xPos,yPos,movingUp,movingDown,movingLeft,movingRight);
        
        double c = moveSpeed*d;
        if(xPos+size >= xBorder-c) {
            movingRight = false;
=======
        //System.out.printf("[U_%.1f_%.1f_%b_%b_%b_%b]\n",xPos,yPos,mUp,mDown,mLeft,mRight);
        
        double c = moveSpeed*d;
        if(xPos+size >= xBorder-c) {
            mRight = false;
>>>>>>> Server-N-Hitboxes
        }
        if(xPos <= 10) {
            mLeft = false;
        }
        if(yPos+size >= yBorder-c) {
<<<<<<< HEAD
            movingDown = false;
=======
            mDown = false;
>>>>>>> Server-N-Hitboxes
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
    public void sendCompressedData(DataOutputStream dataOut) {
<<<<<<< HEAD
        //String data = String.format("p%d_%b_%b_%b_%b_",pNum,movingUp,movingDown,movingLeft,movingRight);
        //String data = String.format("%.2f_%.2f_",xPos,yPos);
        try {
            dataOut.writeDouble(xPos);
            dataOut.writeDouble(yPos);
            //dataOut.writeBoolean(movingUp);
            //dataOut.writeBoolean(movingDown);
            //dataOut.writeBoolean(movingLeft);
            //dataOut.writeBoolean(movingRight);
            dataOut.flush();
        } catch(IOException ex) {
            System.out.println("IOException send" + ex);
        }
        


    }

    @Override
    public void recieveCompressedData(DataInputStream dataIn) {
        /* //movingUp_movingDown_movingLeft_movingRight
        String[] dataList = data.split("_");

        if(dataList[0].equals("true")) {
            movingUp = true;
        } else {
            movingUp = false;
        }

        if(dataList[1].equals("true")) {
            movingDown = true;
        } else {
            movingDown = false;
        }

        if(dataList[2].equals("true")) {
            movingLeft = true;
        } else {
            movingLeft = false;
        }

        if(dataList[3].equals("true")) {
            movingRight = true;
        } else {
            movingRight = false;
        } */

        
        //String[] dataList = data.split("_");
        //System.out.println("RECIEVING"+dataList[0] + " " + dataList[1]);
        //xPos = Double.parseDouble(dataList[0]);
        //yPos = Double.parseDouble(dataList[1]);

        try {
            xPos = dataIn.readDouble();
            yPos = dataIn.readDouble();
            //movingUp = dataIn.readBoolean();
            //movingDown = dataIn.readBoolean();
            //movingLeft = dataIn.readBoolean();
            //movingRight = dataIn.readBoolean();
        } catch(IOException ex) {
            System.out.println("IOException received" + ex);
        }

=======
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
    public void receiveCompressedData(DataInputStream dataIn) {
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

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
>>>>>>> Server-N-Hitboxes
    }

}
