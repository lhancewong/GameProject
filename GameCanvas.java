import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.event.*;

/**
 * This class extends JComponent and overrides the paintComponent method in
 * order to create the custom drawing.
 */
public class GameCanvas extends JComponent {
    private Player p1, p2;
    private Graphics2D g2d;
    private int width, height;

    //temp
    private Rectangle2D.Double bg;

    //Threads
    private GameLoop gameLoop;
    private WriteToServer wtsLoop;
    private ReadFromServer rfsLoop;

    //Game Stuff
    private boolean isRunning;
    private static final int FPS_CAP = 60;
    
    /**
     * Initializes the GameCanvas object.
     */
    public GameCanvas() {
        //Canvas things
        width = 1280;
        height = 720;
        setPreferredSize(new Dimension(width,height));

        //Objects & Shapes
        bg = new Rectangle2D.Double(0,0,width,height);
        initPlayer();
        initBoss();
        initMenuSelection();

        //loops
        gameLoop = new GameLoop(20);


        //variables for the loops
        isRunning = true;

    }

    /**
     * Draws in the order of background, bullet, boss, then player.
     */
    @Override
    protected void paintComponent(Graphics g) {
        g2d = (Graphics2D) g;
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fill(bg);

        //draw player
        p1.drawPlayerShip(g2d);
        p2.drawPlayerShip(g2d);

    }

    /**
     * Initializes the player sprite.
     */
    private void initPlayer() {

        p1 = new Player(210,180,10,4);
        p2 = new Player(210,540,10,4);
    }

    /**
     * Initializes the boss.
     */
    private void initBoss() {

    }

    /**
     * Initializes the menu screen.
     */
    private void initMenuSelection() {

    }

    /**
     * A private class that updates and displays the game.
     */
    private class GameLoop implements Runnable {
        private Thread logicLoop;
        private Timer drawTimer;
        private long sleepTime;

        /**
         * Instantiates the logic loop class.
         * 
         * @param sleepTime delay between loops in milliseconds
         */
        public GameLoop(int sleepTime) {
            logicLoop = new Thread(this);
            this.sleepTime = sleepTime;
        }

        /**
         * Starts the threads.
         */
        public void startThread() {
            logicLoop.start();
            drawTimer.start();
        }

        /**
         * The logic Thread.
         */
        @Override
        public void run() {
            while(isRunning) {
                //TODO update

                try { Thread.sleep(sleepTime); }
                catch(InterruptedException ex) {
                    System.out.println("InterruptedException at GameLoop run()\n\n" + ex);
                }
            }
        }

        /**
         * The display thread. It calculates fps and calls repaint to ideally
         * reach the FPS_CAP;
         */
        public void startDrawLoop() {
            ActionListener displayAnimation = new ActionListener() {
                long previousTime = System.currentTimeMillis()-1;
                int frames = 0;
    
                public void actionPerformed(ActionEvent e) {
                    frames++; 
                    long currentTime = System.currentTimeMillis();
                    /**
                     * Counts how many times the animation as displayed per second (basically fps)
                     * Checks if ~1 sec has passed. If a second has passed and displayFPS_Counter is true,
                     * it resets the value of frames and gets a new previous time.
                     */ 
                    if (currentTime - previousTime >= 1000) {
                        if (isRunning) {
                            //titleFPS.setText("Camping | FPS: " + frames + "   ");
                        } else {
                            //titleFPS.setText("Camping   ");
                        }
                        frames = 0;
                        previousTime = currentTime;
                    }
    
                    repaint();
                }
    
            };
            drawTimer = new javax.swing.Timer((int)Math.round(1000.0/FPS_CAP), displayAnimation);
        }
    }

    /**
     * A private class that writes information to the server.
     */
    private class WriteToServer implements Runnable {
        private long sleepTime;
        private Thread WTSloop;

        /**
         * Initializes the WriteToServer class
         */
        public WriteToServer(int sleepTime) {
            WTSloop = new Thread(this);
            this.sleepTime = sleepTime;
        }

        /**
         * Starts the WTS thread
         */
        public void startThread() {
            WTSloop.start();
        }

        /**
         * The thread that continuously sends data to the server.
         */
        @Override
        public void run() {

            try { Thread.sleep(sleepTime); }
                catch(InterruptedException ex) {
                    System.out.println("InterruptedException at WTS run()\n\n" + ex);
                }
        }

    }

    /**
     * A private class that reads data from the server
     */
    private class ReadFromServer implements Runnable {
        private long sleepTime;
        private Thread RFSloop;

        /**
         * Initializes the ReadFromServer class.
         */
        public ReadFromServer(int sleepTime) {
            RFSloop = new Thread(this);
            this.sleepTime = sleepTime;
        }

        /**
         * Starts the RFS thread.
         */
        public void startThread() {
            RFSloop.start();
        }

        /**
         * The thread that continously recieves data from the server.
         */
        @Override
        public void run() {


            try { Thread.sleep(sleepTime); }
                catch(InterruptedException ex) {
                    System.out.println("InterruptedException at RFS run()\n\n" + ex);
                }
        }

    }
    
}
