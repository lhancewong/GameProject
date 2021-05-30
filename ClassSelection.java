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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class ClassSelection extends MouseAdapter {
    
    public boolean active;
    private Font font;
    private Font font2;
    private Game game;
    public boolean readyState;
    private String sDataOut;
    private BufferedImage ship1Image;
    private BufferedImage ship2Image;
    private BufferedImage ship3Image;
    private BufferedImage bg;

    //Class 1: Offensive
    private Rectangle ofsBtn;
    private String ofsTxt = "Offensive";
    private boolean ofsHighlight = false;

    //Class 2: Balanced
    private Rectangle balBtn;
    private String balTxt = "Balanced";
    private boolean balHighlight = false;

    //Class 3: Defensive
    private Rectangle defBtn;
    private String defTxt = "Defensive";
    private boolean defHighlight = false;

    //Ready Button
    private Rectangle rdyBtn;
    private String rdyTxt = "Ready";
    private boolean rdyHighlight = false;

    //Quit Button
    private Rectangle quitBtn;
	private String quitTxt = "Quit";
	private boolean qHighlight = false;

    private String health;
    private String speed;
    private String maxBullets;

    public ClassSelection(Game game) {
        active = true;
        this.game = game;
        int x, y, w, h;
        
        ship1Image = null;
        ship2Image = null;
        ship3Image = null;
        bg = null;
        try {
            ship1Image = ImageIO.read(new File("sprites/Ship1.png"));
            ship2Image = ImageIO.read(new File("sprites/Ship2.png"));
            ship3Image = ImageIO.read(new File("sprites/Ship3.png"));
            bg = ImageIO.read(new File("sprites/bg3.jpg"));
        } catch (IOException e) {
        }
        
        w = 300;
		h = 500;

        y = GameUtils.get().getHeight() / 2 - h / 2;

        x = GameUtils.get().getWidth() / 4 - w / 2; 
        ofsBtn = new Rectangle(x, y, w, h);

        x = GameUtils.get().getWidth() / 2 - w / 2; 
        balBtn = new Rectangle(x, y, w, h);

        x = GameUtils.get().getWidth() * 3 / 4 - w / 2; 
        defBtn = new Rectangle(x, y, w, h);

        w = 180;
		h = 80;
        x = GameUtils.get().getWidth() * 3 / 4; 
        y = GameUtils.get().getHeight() - 50 - h / 2 ;
        quitBtn = new Rectangle(x, y, w, h);

        x = GameUtils.get().getWidth() * 3 / 4 - 200; 
        rdyBtn = new Rectangle(x, y, w, h);

        font = new Font("Comic Sans", Font.PLAIN, 40);
        font2 = new Font("Comic Sans", Font.PLAIN, 20);
    }
    
    
    public void draw(Graphics2D g2d) {

        g2d.drawImage(bg, 0, 0, null, null);
        g2d.setFont(font);

        // draw buttons
        g2d.setColor(Color.black);
		if (ofsHighlight)
			g2d.setColor(Color.white);
		g2d.fill(ofsBtn);

        g2d.setColor(Color.black);
		if (balHighlight)
			g2d.setColor(Color.white);
		g2d.fill(balBtn);

        g2d.setColor(Color.black);
		if (defHighlight)
			g2d.setColor(Color.white);
		g2d.fill(defBtn);

		g2d.setColor(Color.black);
		if (rdyHighlight)
			g2d.setColor(Color.white);
		g2d.fill(rdyBtn);

		g2d.setColor(Color.black);
		if (qHighlight)
			g2d.setColor(Color.white);
		g2d.fill(quitBtn);

        // draw button borders
		g2d.setColor(Color.white);
        g2d.draw(ofsBtn);
		g2d.draw(balBtn);
        g2d.draw(defBtn);
		g2d.draw(rdyBtn);
		g2d.draw(quitBtn);

        int strWidth, strHeight;

        //Offensive class text
        health = "Health: Low";
        speed = "Speed: Fast";
        maxBullets = "Max Bullets: 5";
        
        strWidth = g2d.getFontMetrics(font).stringWidth(ofsTxt);
		strHeight = g2d.getFontMetrics(font).getHeight();
        
        g2d.drawImage(ship1Image, (int) (ofsBtn.getX() + ofsBtn.getWidth() / 2 - 94), 
        (int) (ofsBtn.getY() + ofsBtn.getHeight() / 4 - 50), null, null);
        g2d.setColor(Color.blue);
		g2d.drawString(ofsTxt, (int) (ofsBtn.getX() + ofsBtn.getWidth() / 2 - strWidth / 2),
				(int) (ofsBtn.getY() + ofsBtn.getHeight() / 2 + strHeight / 4)); 


        g2d.setFont(font2);
        g2d.setColor(Color.ORANGE);
        strWidth = g2d.getFontMetrics(font2).stringWidth(health);
        g2d.drawString(health, (int) (ofsBtn.getX() + ofsBtn.getWidth() / 2 - strWidth / 2),
				(int) (ofsBtn.getY() + ofsBtn.getHeight() * 3 / 4 )); 

        strWidth = g2d.getFontMetrics(font2).stringWidth(speed);
        g2d.drawString(speed, (int) (ofsBtn.getX() + ofsBtn.getWidth() / 2 - strWidth / 2),
				(int) (ofsBtn.getY() + ofsBtn.getHeight() * 3 / 4 + 50)); 
        
        strWidth = g2d.getFontMetrics(font2).stringWidth(maxBullets);
        g2d.drawString(maxBullets, (int) (ofsBtn.getX() + ofsBtn.getWidth() / 2 - strWidth / 2),
				(int) (ofsBtn.getY() + ofsBtn.getHeight() * 3 / 4 + 100)); 


        //Balanced class text
        health = "Health: Normal";
        speed = "Speed: Normal";
        maxBullets = "Max Bullets: 3";

        g2d.setFont(font);
        strWidth = g2d.getFontMetrics(font).stringWidth(balTxt);
        g2d.drawImage(ship3Image, (int) (balBtn.getX() + balBtn.getWidth() / 2 - 69), 
        (int) (balBtn.getY() + balBtn.getHeight() / 4 - 50), null, null);
        g2d.setColor(Color.blue);
		g2d.drawString(balTxt, (int) (balBtn.getX() + balBtn.getWidth() / 2 - strWidth / 2),
				(int) (balBtn.getY() + balBtn.getHeight() / 2 + strHeight / 4));

        g2d.setFont(font2);
        g2d.setColor(Color.ORANGE);
        strWidth = g2d.getFontMetrics(font2).stringWidth(health);
        g2d.drawString(health, (int) (balBtn.getX() + balBtn.getWidth() / 2 - strWidth / 2),
				(int) (balBtn.getY() + balBtn.getHeight() * 3 / 4));

        strWidth = g2d.getFontMetrics(font2).stringWidth(speed);
        g2d.drawString(speed, (int) (balBtn.getX() + balBtn.getWidth() / 2 - strWidth / 2),
				(int) (balBtn.getY() + balBtn.getHeight() * 3 / 4 + 50));

        strWidth = g2d.getFontMetrics(font2).stringWidth(maxBullets);
        g2d.drawString(maxBullets, (int) (balBtn.getX() + balBtn.getWidth() / 2 - strWidth / 2),
				(int) (balBtn.getY() + balBtn.getHeight() * 3 / 4 + 100));

        //Defensive class text
        health = "Health: High";
        speed = "Speed: Slow";
        maxBullets = "Max Bullets: 1";

        g2d.setFont(font);
        strWidth = g2d.getFontMetrics(font).stringWidth(defTxt);
        g2d.drawImage(ship2Image, (int) (defBtn.getX() + defBtn.getWidth() / 2 - 68), 
        (int) (defBtn.getY() + defBtn.getHeight() / 4 - 50), null, null);
        g2d.setColor(Color.blue);
		g2d.drawString(defTxt, (int) (defBtn.getX() + defBtn.getWidth() / 2 - strWidth / 2),
				(int) (defBtn.getY() + defBtn.getHeight() / 2 + strHeight / 4));

        g2d.setFont(font2);
        g2d.setColor(Color.ORANGE);
        strWidth = g2d.getFontMetrics(font2).stringWidth(health);
        g2d.drawString(health, (int) (defBtn.getX() + defBtn.getWidth() / 2 - strWidth / 2),
				(int) (defBtn.getY() + defBtn.getHeight() * 3 / 4));

        strWidth = g2d.getFontMetrics(font2).stringWidth(speed);
        g2d.drawString(speed, (int) (defBtn.getX() + defBtn.getWidth() / 2 - strWidth / 2),
				(int) (defBtn.getY() + defBtn.getHeight() * 3 / 4 + 50));

        strWidth = g2d.getFontMetrics(font2).stringWidth(maxBullets);
        g2d.drawString(maxBullets, (int) (defBtn.getX() + defBtn.getWidth() / 2 - strWidth / 2),
				(int) (defBtn.getY() + defBtn.getHeight() * 3 / 4 + 100));

        //Ready Button text
        g2d.setFont(font);
        strWidth = g2d.getFontMetrics(font).stringWidth(rdyTxt);

        g2d.setColor(Color.green);
		g2d.drawString(rdyTxt, (int) (rdyBtn.getX() + rdyBtn.getWidth() / 2 - strWidth / 2),
				(int) (rdyBtn.getY() + rdyBtn.getHeight() / 2 + strHeight / 4));

        //Quit Button text
        strWidth = g2d.getFontMetrics(font).stringWidth(quitTxt);

        g2d.setColor(Color.red);
		g2d.drawString(quitTxt, (int) (quitBtn.getX() + quitBtn.getWidth() / 2 - strWidth / 2),
				(int) (quitBtn.getY() + quitBtn.getHeight() / 2 + strHeight / 4));

    }

    @Override
	public void mouseClicked(MouseEvent e) {
        if (active) {

            Point p = e.getPoint();
    
            if (rdyBtn.contains(p)){
                System.out.println("rdy");
                GameUtils.get().getGameCanvas().joinServer();
                rdyHighlight = true;
                active = false;
            }
            else if (quitBtn.contains(p)){
                qHighlight = true;
                System.exit(0);
            }
            else if (ofsBtn.contains(p)){
                System.out.println("Offense");
                game.changeClass(1);
                ofsHighlight = true;
                balHighlight = false;
                defHighlight = false;
            }
            else if (balBtn.contains(p)){
                System.out.println("bal");
                game.changeClass(2);
                ofsHighlight = false;
                balHighlight = true;
                defHighlight = false;
            }
            else if (defBtn.contains(p)){
                System.out.println("def");
                game.changeClass(3);
                ofsHighlight = false;
                balHighlight = false;
                defHighlight = true;
            }
        }

	}

    @Override
	public void mouseMoved(MouseEvent e) {

		Point p = e.getPoint();

		// determine if mouse is hovering inside one of the buttons
        ofsHighlight = ofsBtn.contains(p);
        balHighlight = balBtn.contains(p);
        defHighlight = defBtn.contains(p);
		rdyHighlight = rdyBtn.contains(p);
		qHighlight = quitBtn.contains(p);

	}

    public byte[] getCompressedData() {   
        return sDataOut.getBytes();
    }

}
