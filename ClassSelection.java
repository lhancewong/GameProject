import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class ClassSelection extends MouseAdapter{
    
    public boolean active;
    private Font font;
    private Game game;
    private int pNum;
    public boolean readyState;
    private DataOutputStream dataOut;

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

    public ClassSelection(Game game,DataOutputStream dataOut, GameCanvas gameCanvas) {
        active = true;
        this.game = game;
        this.dataOut = dataOut;
        pNum = GameUtils.get().getPlayerNum();
        int x, y, w, h;
         
        w = 150;
		h = 300;

        y = GameUtils.get().getHeight() / 2 - h / 2;

        x = GameUtils.get().getWidth() / 4 - w / 2; 
        ofsBtn = new Rectangle(x, y, w, h);

        x = GameUtils.get().getWidth() / 2 - w / 2; 
        balBtn = new Rectangle(x, y, w, h);

        x = GameUtils.get().getWidth() * 3 / 4 - w / 2; 
        defBtn = new Rectangle(x, y, w, h);

        w = 200;
		h = 100;
        x = GameUtils.get().getWidth() * 3 / 4; 
        y = GameUtils.get().getHeight() - 50 - h / 2 ;
        quitBtn = new Rectangle(x, y, w, h);

        x = GameUtils.get().getWidth() * 3 / 4 - 200; 
        rdyBtn = new Rectangle(x, y, w, h);

        font = new Font("Comic Sans", Font.PLAIN, 100);
    }
    
    
    public void draw(Graphics2D g2d) {
        
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
        strWidth = g2d.getFontMetrics(font).stringWidth(ofsTxt);
		strHeight = g2d.getFontMetrics(font).getHeight();

        g2d.setColor(Color.blue);
		g2d.drawString(ofsTxt, (int) (ofsBtn.getX() + ofsBtn.getWidth() / 2 - strWidth / 2),
				(int) (ofsBtn.getY() + ofsBtn.getHeight() / 2 + strHeight / 4));

        //Balanced class text
        strWidth = g2d.getFontMetrics(font).stringWidth(balTxt);
		strHeight = g2d.getFontMetrics(font).getHeight();

        g2d.setColor(Color.blue);
		g2d.drawString(balTxt, (int) (balBtn.getX() + balBtn.getWidth() / 2 - strWidth / 2),
				(int) (balBtn.getY() + balBtn.getHeight() / 2 + strHeight / 4));

        //Defensive class text
        strWidth = g2d.getFontMetrics(font).stringWidth(defTxt);
		strHeight = g2d.getFontMetrics(font).getHeight();

        g2d.setColor(Color.blue);
		g2d.drawString(defTxt, (int) (defBtn.getX() + defBtn.getWidth() / 2 - strWidth / 2),
				(int) (defBtn.getY() + defBtn.getHeight() / 2 + strHeight / 4));

        //Ready Button text
        strWidth = g2d.getFontMetrics(font).stringWidth(rdyTxt);
		strHeight = g2d.getFontMetrics(font).getHeight();

        g2d.setColor(Color.green);
		g2d.drawString(rdyTxt, (int) (rdyBtn.getX() + rdyBtn.getWidth() / 2 - strWidth / 2),
				(int) (rdyBtn.getY() + rdyBtn.getHeight() / 2 + strHeight / 4));

        //Quit Button text
        strWidth = g2d.getFontMetrics(font).stringWidth(quitTxt);
		strHeight = g2d.getFontMetrics(font).getHeight();

        g2d.setColor(Color.red);
		g2d.drawString(quitTxt, (int) (quitBtn.getX() + quitBtn.getWidth() / 2 - strWidth / 2),
				(int) (quitBtn.getY() + quitBtn.getHeight() / 2 + strHeight / 4));

    }

    public void isReady(){
        readyState = true;
    }

    @Override
	public void mouseClicked(MouseEvent e) {
        if (active) {

            Point p = e.getPoint();
    
            if (rdyBtn.contains(p)){
                System.out.println("rdy");
                game.initBossFight();
                isReady();
                try {
                    if(ofsHighlight) {
                        dataOut.writeInt(1);
                    } else if(balHighlight) {
                        dataOut.writeInt(2);
                    } else if (defHighlight) {
                        dataOut.writeInt(3);
                    }
                } catch(IOException ex) {}

                rdyHighlight = true;
                active = false;
            }
            else if (quitBtn.contains(p)){
                qHighlight = true;
                System.exit(0);
            }
            else if (ofsBtn.contains(p)){
                System.out.println("Offense");
                game.changeClass(game.getPlayerNumber(), 1);
                ofsHighlight = true;
                balHighlight = false;
                defHighlight = false;
            }
            else if (balBtn.contains(p)){
                System.out.println("bal");
                game.changeClass(game.getPlayerNumber(), 2);
                ofsHighlight = false;
                balHighlight = true;
                defHighlight = false;
            }
            else if (defBtn.contains(p)){
                System.out.println("def");
                game.changeClass(game.getPlayerNumber(), 3);
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
}
