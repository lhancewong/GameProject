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
    private boolean isRunning, isMaster, isBossFight, isServerSelection, isClassSelection;
    private BulletController controller1,controller2;
    public CopyOnWriteArrayList<ShipBullet> bullets;
    private ArrayList<GameObject> bossFight;
    

    /**
     * Instantiates the logic loop class.
     * 
     * @param sleepTime delay between loops in milliseconds
     */
    public Game(boolean isMaster) {
        logicLoop = new Thread(this);
        isRunning = true;
        isBossFight = true;
        pNum = GameUtils.get().getPlayerNum();
        initBossFight();
        initMenuSelection();

        this.isMaster = isMaster;
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

            checkBulletHitboxes();
            
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
        p1 = new Player("p1",210,180,30,1);
        p2 = new Player("p2",210,540,30,3);
        controller1 = new BulletController(p1);
        controller2 = new BulletController(p2);

        bossFight.add(bg1);
        bossFight.add(bg2);
        bossFight.add(p1);
        bossFight.add(p2);
        bossFight.add(Yalin);
        bossFight.add(controller1);
        bossFight.add(controller2);

        bullets = new CopyOnWriteArrayList<ShipBullet>();

    }

    /**
     * Initializes the menu screen.
     */
    private void initMenuSelection() {

    }

    public void draw(Graphics2D g2d){
        g2d = (Graphics2D) g2d;
        g2d.setColor(new Color(100,150,150));

        if(isBossFight) {
            for(GameObject i: bossFight) {
                i.draw(g2d);
            }
        }
    }

    public int getPlayerNumber() {
        return pNum;
    }

    public void checkBulletHitboxes() {
        for(ShipBullet i: controller1.getshipBulletArray()) {
            if (i.getX() >= Yalin.getHitbox().getX() && 
                i.getX() <= Yalin.getHitbox().getX() + Yalin.getHitbox().getWidth() &&
                i.getY() >= Yalin.getHitbox().getY() &&
                i.getY() <= Yalin.getHitbox().getY() + Yalin.getHitbox().getHeight() ){
                //System.out.println("HIT");
                Yalin.gotHit();
                controller1.removeBullet(i);
            }
        }
        for(ShipBullet i: controller2.getshipBulletArray()) {
            if (i.getX() >= Yalin.getHitbox().getX() && 
                i.getX() <= Yalin.getHitbox().getX() + Yalin.getHitbox().getWidth() &&
                i.getY() >= Yalin.getHitbox().getY() &&
                i.getY() <= Yalin.getHitbox().getY() + Yalin.getHitbox().getHeight() ){
                //System.out.println("HIT");
                Yalin.gotHit();
                controller2.removeBullet(i);
            }
        }
    }

    public boolean checkBulletHitboxes2() {
        
        return false;
    }

    public BulletController getController(){
        if(pNum == 1) {
            return controller1;
        } else {
            return controller2;
        } 
    }

    public BulletController getBC1() {
        return controller1;
    }

    public BulletController getBC2() {
        return controller2;
    }

    public Player getPlayer1() {
        return p1;
    }

    public Player getPlayer2() {
        return p2;
    }
    
    public Boss getYalin() {
        return Yalin;
    }

    
}
