/**	
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

import java.util.ArrayList;
import java.awt.*;

//====================== Game Stuff ============================//

    /**
     * class that updates and displays the game.
     */ 
    public class Game implements Runnable{

    //BulletControllers for the 3 characters in the game
    private BulletController controller1,controller2;
    private BulletController bosscontroller;

    //Images of the background
    private Background bg1;
    private Background bg2;

    //Logic Thread
    private Thread logicLoop;

    //Instance of characters
    public Player p1, p2;
    public Boss Yalin;

    //player number
    public int z = 0;

    //booleans
    private boolean isRunning, isMaster, isBossFight;
    
    //music
    private AudioPlayer ap;

    //arraylist for the objects to be drawn
    private ArrayList<GameObject> bossFight;
    

    /**
     * Instantiates the logic loop class.
     * 
     * @param sleepTime delay between loops in milliseconds
     */
    public Game(boolean isMaster) {
        logicLoop = new Thread(this);
        ap = new AudioPlayer();
        isRunning = true;
        isBossFight = true;
        initBossFight();
        
        GameUtils.get().setIsMaster(isMaster);
        this.isMaster = isMaster;
    }

    /**
     * Initializes the boss fight.
     */
    public void initBossFight() {
        bossFight = new ArrayList<GameObject>();
        bg1 = new Background(1);
        bg2 = new Background(2);
        Yalin = new Boss();
        p1 = new Player(210,280,2);
        p2 = new Player(210,440,2);
        controller1 = new BulletController(p1);
        controller2 = new BulletController(p2);
        bosscontroller = new BulletController(Yalin);

        bossFight.add(bg1);
        bossFight.add(bg2);
        bossFight.add(p1);
        bossFight.add(p2);
        bossFight.add(Yalin);
        bossFight.add(controller1);
        bossFight.add(controller2);
        bossFight.add(bosscontroller);

    }

    public void draw(Graphics2D g2d){
        g2d = (Graphics2D) g2d;
        g2d.setColor(new Color(100,150,150));

        if(isBossFight) {
            for(GameObject i: bossFight) {
                i.draw(g2d);
            }
        }
        
        Font font = new Font("Sans_Serif", Font.PLAIN, 40);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        g2d.drawString("P1 HP:"+String.valueOf(p1.getHitPoints()),10,710);
        g2d.drawString("P2 HP:"+String.valueOf(p2.getHitPoints()),1100,710);

        Font font2 = new Font("Elephant", Font.PLAIN, 100);
        if (!(Yalin.isAlive)){
            g2d.setFont(font2);
            g2d.drawString("YOU WIN",GameUtils.get().getWidth()/2 - g2d.getFontMetrics(font2).stringWidth("YOU WIN") /2 ,360);
        }

        if (!(p1.isAlive) && !(p2.isAlive)){
            g2d.setFont(font2);
            g2d.drawString("LOL YOU DIED",GameUtils.get().getWidth()/2 - g2d.getFontMetrics(font2).stringWidth("LOL YOU DIED") /2 ,360);
            while(z==0){
                ap.stopBossMusic();
                ap.playSound("sounds/losemusic.wav", isMaster);
                z++;
            } 
            
        }
    }

    /**
     * The logic Thread.
     */
    @Override
    public void run() {
        long previousTime = System.currentTimeMillis()-1;
        ap.playSound("sounds/bossmusic.wav", isMaster);
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


    public void changeClass(int shipType){
        p1.setShip(shipType);
        controller1.setNewMax(shipType);
    }

    /**
     * Starts the threads.
     */
    public void startThread() {
        logicLoop.start();
    }

    
    public void checkBulletHitboxes() {
        if(Yalin.isAlive) {
            for(ShipBullet i: controller1.getshipBulletArray()) {
                if (Yalin.checkHitbox(i)){
                    Yalin.gotHit();
                    ap.playSound("sounds/bosshit.wav",isMaster);
                    controller1.removeShipBullet(i);
                }
            }

            for(ShipBullet i: controller2.getshipBulletArray()) {
                if (Yalin.checkHitbox(i)){
                    Yalin.gotHit();
                    ap.playSound("sounds/bosshit.wav", isMaster);
                    controller2.removeShipBullet(i);
                }
            }
        }

        if(p1.isAlive) {
            for(BossBullet i: bosscontroller.getbossBulletArray()) {
                if (p1.checkHitbox(i)){
                    p1.gotHit();
                    ap.playSound("sounds/shiphit.wav", isMaster);
                    bosscontroller.removeBossBullet(i);
                }
            }
        }
        
        if(p2.isAlive) {
            for(BossBullet i: bosscontroller.getbossBulletArray()) {
                if (p2.checkHitbox(i)){
                    p2.gotHit();
                    ap.playSound("sounds/shiphit.wav", isMaster);
                    bosscontroller.removeBossBullet(i);
                }
            }
        }
    }
    
    public BulletController getController(){
        return controller1;
    }
    
    public BulletController getBC1() {
        return controller1;
    }
    
    public BulletController getBC2() {
        return controller2;
    }

    public BulletController getBBC() {
        return bosscontroller;
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
