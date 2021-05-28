import java.awt.*;
<<<<<<< HEAD
import java.io.DataInputStream;
import java.io.DataOutputStream;
=======
import java.awt.geom.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
>>>>>>> Server-N-Hitboxes

public class Background implements GameObject {

    private int number;
    private Rectangle2D.Double bg1;
    private Rectangle2D.Double bg2;
    private BufferedImage back1;
    private BufferedImage back2;
    private double width;
    private double height;
    private double xPos;
    private double yPos;
    private double moveSpeed;

    public Background(int number){
        width = GameUtils.get().getWidth();
        height = GameUtils.get().getHeight();
        xPos = 0;
        if (number == 2){
            xPos = width;
        }
        yPos = 0;
        moveSpeed = 200;
        this.number = number;

        try {
            back1 = ImageIO.read(new File("sprites/bg.jpg"));
            back2 = ImageIO.read(new File("sprites/bg2.jpg"));
        } catch (IOException e) {
        }
        
    }
    @Override
    public void draw(Graphics2D g2d) {
        if (number == 1){
            g2d.drawImage(back1, (int) xPos, (int) yPos, null, null);
            /* bg1 = new Rectangle2D.Double(xPos, yPos, width + 10, height );
            g2d.setColor(Color.GREEN);
            g2d.draw(bg1);
            g2d.fill(bg1); */
        }
        else if (number == 2){
            g2d.drawImage(back2, (int) xPos, (int) yPos, null, null);
            /* bg2 = new Rectangle2D.Double(xPos, yPos, width + 10, height );
            g2d.setColor(Color.orange);
            g2d.draw(bg2);
            g2d.fill(bg2); */
        }        
    }

    @Override
    public void update(double deltaTime) {  
        if (xPos + width < 0 ){
            xPos = width;
        }
        xPos -= moveSpeed*deltaTime;
        
        
    }

    @Override
    public void sendCompressedData(DataOutputStream dataOut) {
<<<<<<< HEAD
        // TODO Auto-generated method stub
=======
>>>>>>> Server-N-Hitboxes
        
    }

    @Override
<<<<<<< HEAD
    public void recieveCompressedData(DataInputStream dataIn) {
=======
    public void receiveCompressedData(DataInputStream dataIn) {
        
    }
    @Override
    public void readStringData(String s) {
>>>>>>> Server-N-Hitboxes
        // TODO Auto-generated method stub
        
    }
    
}
