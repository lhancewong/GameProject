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
    public boolean isAlive;
    private boolean mUp, mDown, movingLeft, movingRight;
    private BufferedImage boss1, boss2;
    private Hitbox box;
    private String sDataOut, sDataIn;
    private double hitPoints;


    public Boss(){

        xPos = (GameUtils.get().getWidth()/2)+150;
        yPos = GameUtils.get().getHeight() - 460;
        moveSpeed = 400;
        hitPoints = 70;
        isAlive = true;
        mUp = true;
        mDown = false;
        randomDistance = (int)Math.floor(Math.random()*(yPos-0+1)+0);

        sDataIn = "";
        sDataOut = "";
        box = new Hitbox(xPos + 102, yPos + 98, 198, 160);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (isAlive) {
            if (hitPoints > 30) {
                boss1(g2d);
            }
            else {
                boss2(g2d);
            }
        }
        box.draw(g2d);
        
    }
    
    @Override
    public void update(double d) {
        if(hitPoints <= 0) {
            isAlive = false;
        }
        
        if(isAlive) {
        box = new Hitbox(xPos + 102, yPos + 98, 198, 160);
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
        sDataOut = String.format("Yalin_%.1f_%.1f_%b_%b_%d_%.1f_",xPos,yPos,mUp,mDown,randomDistance,hitPoints);
        readStringData(sDataIn);
        }

    }

    @Override
    public void readStringData(String s) {
        if(!s.equals("")) {
            String[] data = s.split("_");
            xPos = Double.parseDouble(data[1]);
            yPos = Double.parseDouble(data[2]);
            mUp = Boolean.parseBoolean(data[3]);
            mDown = Boolean.parseBoolean(data[4]);
            randomDistance = Integer.parseInt(data[5]);
            hitPoints = Double.parseDouble(data[6]);
        }   
    }

    public void boss1(Graphics2D g2d){
        boss1 = null;
        try {
            boss1 = ImageIO.read(new File("sprites/boss1.png"));
        } catch (IOException e) {
        }
        g2d.drawImage(boss1, (int) xPos, (int) yPos, null, null);
    }

    public void boss2(Graphics2D g2d){
        boss2 = null;
        try {
            boss2 = ImageIO.read(new File("sprites/boss2.png"));
        } catch (IOException e) {
        }
        g2d.drawImage(boss2, (int) xPos, (int) yPos, null, null);
    }

    /**
     * Returns the boss's hitbox
     * 
     * @return hitbox
     */
    public Hitbox getHitbox(){
        return box;
    }

    /**
     * Checks if the bullet is inside the boss's hitbox
     * 
     * @param i the bullet to be tested
     * @return true if the bullet is inside the hitbox and false if it isn't
     */
    public boolean checkHitbox(ShipBullet i){
        if (i.getX() >= box.getX() && 
            i.getX() <= box.getX() + box.getWidth() &&
            i.getY() >= box.getY() &&
            i.getY() <= box.getY() + box.getHeight() ){
                return true;
        }
        else {
            return false;
        }
    }

    public double getX(){
        return xPos;
    }

    public double getY(){
        return yPos;
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

    public void gotHit(){
        hitPoints -= 1;
    }

    public double getBossHP(){
        return hitPoints;
    }

    @Override
    public byte[] getCompressedData() {
        return sDataOut.getBytes();
    }

    @Override
    public void receiveCompressedData(String sDataIn) {
        this.sDataIn = sDataIn;
    }
    
}
