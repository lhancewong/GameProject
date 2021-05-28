import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;


/**
 * This class contains the code that manages the boss' appearance and functionality.
 */
public class Boss implements GameObject{

    private double xPos, yPos;
    private int randomDistance;
    private double moveSpeed;
    private boolean isAlive;
    private boolean mUp, mDown, movingLeft, movingRight;
    private BufferedImage img;
    private Hitbox box;
    private String sDataOut, sDataIn;
    private double hitPoints;


    public Boss(){

        xPos = (GameUtils.get().getWidth()/2)+150;
        yPos = GameUtils.get().getHeight() - 460;
        moveSpeed = 400;
        hitPoints = 100;
        isAlive = true;
        mUp = true;
        mDown = false;
        randomDistance = (int)Math.floor(Math.random()*(yPos-0+1)+0);

        sDataIn = "";
        sDataOut = "";
        
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (isAlive) {
            img = null;
            try {
                img = ImageIO.read(new File("sprites/boss1.png"));
            } catch (IOException e) {
            }
            g2d.drawImage(img, (int) xPos, (int) yPos, null, null);
        }
        box = new Hitbox(xPos + 102, yPos + 98, 198, 160);
        box.draw(g2d);
        
    }

    @Override
    public void update(double d) {

        if(mUp) {
            if (yPos >= randomDistance){
                yPos -= moveSpeed*d;
            }
            else {
                changeDirection();
            }
        }
        if (mDown) {
            if (yPos <= randomDistance){
                yPos += moveSpeed*d;
            }
            else {
                changeDirection();
            }
        }

        //mUp_mDown_randomDistance_hitPoints_
        sDataOut = String.format("%.2f_%.2f_%b_%b_%d_%f_",xPos,yPos,mUp,mDown,randomDistance,hitPoints);
        readStringData(sDataIn);

    }

    @Override
    public void readStringData(String s) {
        if(!s.equals("")) {
            String[] data = s.split("_");
            xPos = Double.parseDouble(data[0]);
            yPos = Double.parseDouble(data[1]);
            mUp = Boolean.parseBoolean(data[2]);
            mDown = Boolean.parseBoolean(data[3]);
            randomDistance = Integer.parseInt(data[4]);
            hitPoints = Double.parseDouble(data[5]);
        }   
    }

    public Hitbox getHitbox(){
        return box;
    }

    //encapsulate all movement things under this function
    public void movementPattern(){
        
        
    }

    public void changeDirection(){

        if (mUp){
            mDown = !mDown;
            mUp =  !mUp;
            randomDistance = (int)Math.floor(Math.random()*(383-yPos+1)+yPos);
        }
        else{
            mDown = !mDown;
            mUp =  !mUp;
            randomDistance = (int)Math.floor(Math.random()*(yPos-0+1)+0);
        }
    }

    public void incrementHitPoints(double amnt) {
        hitPoints += amnt;
    }

    @Override
    public void sendCompressedData(DataOutputStream dataOut) {
        try {
            dataOut.writeUTF(sDataOut);
            dataOut.flush();
        } catch(IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void receiveCompressedData(DataInputStream dataIn) {
        try {
            sDataIn = dataIn.readUTF();
        } catch(IOException ex) {
            System.out.println(ex);
        }
    }
    
}
