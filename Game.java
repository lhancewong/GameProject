import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;

public class Game {
    //Game Stuff
    private double width, height;
    private int pNum;
    private boolean isRunning, isClient;
    private boolean isBossFight, isServerSelection, isClassSelection;
    private static final int FPS_CAP = 60;

    private ArrayList<GameObject> bossFight;
    //private MenuObjects serverSelectionMenu;
    //private MenuObjects classSelectionMenu;

    /**
     * Constructor for clientGame
     */
    public Game(double width, double height) {
        this.width = width;
        this.height = height;
        initBossFight();

    }

    /**
     * Initializes the bossFight ArrayList.
     * This ArrayList is meant to hold what will be 
     * drawn when repaint is called while the
     * boss fight is supposed to be displayed.
     */
    private void initBossFight() {
        bossFight = new ArrayList<GameObject>();
        
        Player p1 = new Player(210,180,30,4);
        Player p2 = new Player(210,540,30,4);
        //bossFight.add(bg);
        bossFight.add(p1);
        bossFight.add(p2);

    }

    /**
     * Updates the game
     * 
     * @param d The time it took to update the game
     */
    public void update(double d) {

    }

    /**
     * Draws the game
     */
    public void draw(Graphics2D g2d) {
        Rectangle2D.Double bg = new Rectangle2D.Double(0,0,width,height);
        g2d.setColor(new Color(100,150,150));
        g2d.fill(bg);

        if(isBossFight) {
            for(GameObject i: bossFight) {
                i.draw(g2d);
            }
        }
    }

    /**
     * Gets all game data 
     */
    public String getGameData() {

        String gameData = "";

        return gameData;
    }

    public void receiveGameData() {

    }

    
}
