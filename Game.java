import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

//====================== Game Stuff ============================//

    /**
     * class that updates and displays the game.
     */ 
public class Game implements Runnable{

    private Background bg1;
    private Background bg2;
    private Thread logicLoop;
    private long sleepTime;
    public Player p1, p2;
    public Boss Yalin;
    public int pNum;
    private boolean isRunning, isBossFight, isServerSelection, isClassSelection;
    public CopyOnWriteArrayList<ShipBullet> bullets;
    private ArrayList<GameObject> bossFight;
    

    /**
     * Instantiates the logic loop class.
     * 
     * @param sleepTime delay between loops in milliseconds
     */
    public Game() {
        /* bg = new Rectangle2D.Double(0,0,GameUtils.get().getWidth(),GameUtils.get().getHeight()); */
        logicLoop = new Thread(this);
        isRunning = true;
        isBossFight = true;
        initBossFight();
    }

    /**
     * Starts the threads.
     */
    public void startThread() {
        logicLoop.start();
    }

    /**
     * The logic Thread.
     */
    @Override
    public void run() {
        long previousTime = System.currentTimeMillis()-1;
        while(isRunning) {
            long currentTime = System.currentTimeMillis();

            double deltaTime = (currentTime - previousTime)/1000.0;

            for(GameObject i : bossFight) {
                i.update(deltaTime);
            }
            if (bullets.size() > 0){
                for(ShipBullet i: bullets) {
                i.update(deltaTime);
                }  
            }

            previousTime = currentTime;

            try { Thread.sleep(16); }
            catch(InterruptedException ex) {
                System.out.println("InterruptedException at GameLoop run()\n\n" + ex);
            }
        }
    }
    
    
    private void initBossFight() {
        bossFight = new ArrayList<GameObject>();
        bg1 = new Background(1);
        bg2 = new Background(2);
        Yalin = new Boss();
        p1 = new Player(210,180,30,4);
        p2 = new Player(210,540,30,4);
        //bossFight.add(bg);
        bossFight.add(bg1);
        bossFight.add(bg2);
        bossFight.add(p1);
        bossFight.add(p2);
        bossFight.add(Yalin);

        bullets = new CopyOnWriteArrayList<ShipBullet>();

    }

    public void draw(Graphics2D g2d){
        g2d = (Graphics2D) g2d;
        g2d.setColor(new Color(100,150,150));

        if(isBossFight) {
            for(GameObject i: bossFight) {
                i.draw(g2d);
            }
            if (bullets.size() > 0) {
                try {
                    for(ShipBullet i: bullets) {
                        i.draw(g2d);
                        if (i.getX() > 1280){
                            bullets.remove(i);
                        }
                    }  
                } catch (ConcurrentModificationException e) {
                    System.out.println("CME");
                }
            }
            
        }
    }

    public int getPlayerNumber() {
        return pNum;
    }
    

    
}
