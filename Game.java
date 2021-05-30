import java.util.ArrayList;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

//====================== Game Stuff ============================//

    /**
     * class that updates and displays the game.
     */ 
    public class Game implements Runnable{

    private Background bg1;
    private Background bg2;
    private Thread logicLoop;
    public Player p1, p2;
    public Boss Yalin;
    public int pNum;
    private boolean isRunning, isMaster, isBossFight, isServerSelection, isClassSelection;
    private BulletController controller1,controller2;
    private BulletController bosscontroller;

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
        initBossFight();
        initServerSelection();

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
        p1 = new Player(210,280,1);
        p2 = new Player(210,440,3);
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

        bullets = new CopyOnWriteArrayList<ShipBullet>();
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


    public void changeClass(int shipType){
        p1.setShip(shipType);
    }

    /**
     * Initializes the server selection screen.
     */
    private void initServerSelection() {

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
                    //System.out.println("HIT");
                    Yalin.gotHit();
                    controller1.removeShipBullet(i);
                }
            }

            for(ShipBullet i: controller2.getshipBulletArray()) {
                if (Yalin.checkHitbox(i)){
                    //System.out.println("HIT");
                    Yalin.gotHit();
                    controller2.removeShipBullet(i);
                }
            }
        }

        if(p1.isAlive) {
            for(BossBullet i: bosscontroller.getbossBulletArray()) {
                if (p1.checkHitbox(i)){
                    //System.out.println("HIT");
                    p1.gotHit();
                    bosscontroller.removeBossBullet(i);
                }
            }
        }
        
        if(p2.isAlive) {
            for(BossBullet i: bosscontroller.getbossBulletArray()) {
                if (p2.checkHitbox(i)){
                    //System.out.println("HIT");
                    p2.gotHit();
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
