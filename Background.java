/**
	This is a template for a Java file.
	
	@author Wilbert Meinard L. Chen (201153)
    @author Lhance Christian S. Wong (205467)
	@version May 31, 2021
**/

/*
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
*/

import java.awt.*;
import java.awt.geom.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Background implements GameObject {

    private int number;
    private BufferedImage back1;
    private BufferedImage back2;
    private double width;
    private double xPos;
    private double yPos;
    private double moveSpeed;

    public Background(int number){
        width = GameUtils.get().getWidth();
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
        }
        else if (number == 2){
            g2d.drawImage(back2, (int) xPos, (int) yPos, null, null);
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
    public void readStringData(String s) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public String getCompressedData() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void receiveCompressedData(String sDataIn) {
        // TODO Auto-generated method stub
        
    }
    
}
