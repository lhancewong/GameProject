import java.awt.Color;
import java.awt.Graphics2D;

public class BossBullet implements GameObject{

    private double xPos, yPos; 
    private double width, height;
    private double speed;
    private int attackType;

    public BossBullet (double xPos, double yPos, int attackType){
        this.xPos = xPos;
        this.yPos = yPos;
        this.attackType = attackType;
        speed = 500;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (attackType == 1) {
            width = 150;
            height = 60;
            bullet1(g2d);
        }
        else if (attackType == 2){
            width = 20;
            height = 10;
            bullet2(g2d);
        }
    }

    public void bullet1(Graphics2D g2d){
        g2d.setColor(Color.red);
        g2d.fillRoundRect((int)xPos, (int)yPos, (int) width, (int) height, 10, 10);
    }

    public void bullet2(Graphics2D g2d){
        g2d.setColor(Color.red);
        g2d.fillRoundRect((int)xPos, (int)yPos, (int) width, (int) height, 10, 10);
    }

    @Override
    public void update(double deltaTime) {
        xPos -= 500 *deltaTime;
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getAtkType(){
        return attackType;
    }

    @Override
    public void readStringData(String s) {
        
    }

    @Override
    public byte[] getCompressedData() {
        return null;
    }

    @Override
    public void receiveCompressedData(String sDataIn) {
        
    }
    
}
