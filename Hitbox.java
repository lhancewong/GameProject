import java.awt.*;
import java.awt.geom.*;

public class Hitbox implements GameObject{
    private double xPos;
    private double yPos;
    private double width;
    private double height;
    private Rectangle2D box;


    public Hitbox(double xPos, double yPos, double width, double height){
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }


    @Override
    public void draw(Graphics2D g2d) {
        box = new Rectangle2D.Double(xPos, yPos, width, height);
        g2d.setColor(Color.RED);
        g2d.draw(box);
    }

    @Override
    public void update(double deltaTime) {
        
    }

    public double getX(){
        return xPos;
    }

    public double getY(){
        return yPos;
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }

    @Override
    public void readStringData(String s) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public byte[] getCompressedData() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void receiveCompressedData(String sDataIn) {
        // TODO Auto-generated method stub
        
    }

}
