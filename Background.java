import java.awt.*;
import java.awt.geom.*;

public class Background implements GameObject {

    private int number;
    private Rectangle2D.Double bg1;
    private Rectangle2D.Double bg2;
    private double width;
    private double height;
    private double xPos;
    private double yPos;
    private double moveSpeed;

    public Background(int number){
        width = GameUtils.get().getWidth() + 100;
        height = GameUtils.get().getHeight();
        xPos = 0;
        if (number == 2){
            xPos = width;
        }
        yPos = 0;
        moveSpeed = 200;
        this.number = number;
        
    }
    @Override
    public void draw(Graphics2D g2d) {
        if (number == 1){
            bg1 = new Rectangle2D.Double(xPos, yPos, width, height );
            g2d.setColor(Color.GREEN);
            g2d.draw(bg1);
            g2d.fill(bg1);
        }
        else if (number == 2){
            bg2 = new Rectangle2D.Double(xPos, yPos, width, height );
            g2d.setColor(Color.orange);
            g2d.draw(bg2);
            g2d.fill(bg2);
        }        
    }

    @Override
    public void update(double deltaTime) {
        // TODO Auto-generated method stub
        if (number == 1){
            if (xPos + width < 0 ){
                xPos = width;
            }
            
        }
        else if (number == 2){
            if (xPos + width < 0 ){
                xPos = width;
            }
        }
        xPos -= moveSpeed*deltaTime;
        
        
    }

    @Override
    public String getCompressedData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void receiveCompressedData(String data) {
        // TODO Auto-generated method stub
        
    }
    
}
