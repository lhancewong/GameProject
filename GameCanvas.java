import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/**
 * This class extends JComponent and overrides the paintComponent method in
 * order to create the custom drawing.
 */
public class GameCanvas extends JComponent {
    //Graphics
    private Graphics2D g2d;
    private int width, height;
    //temp
    private Rectangle2D.Double bg;

    //Threads
    private GameClient gameLoop;
    private WriteToServer wtsLoop;
    private ReadFromServer rfsLoop;

    //Game Stuff
    private Player p1, p2;
    private int pNum;
    private boolean isRunning, isServer;
    private static final int FPS_CAP = 60;
    private String titleFPS; 
    
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
        gameLoop = new GameClient(20);


        //variables for the loops
        isRunning = true;

        gameLoop.startThread();

    }

    /**
     * Draws in the order of background, bullet, boss, then player.
     */
    @Override
    protected void paintComponent(Graphics g) {
        g2d = (Graphics2D) g;
        g2d.setColor(new Color(100,150,150));
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
     * Returns the number of the player.
     * 
     * @return The player's number
     */
    public int getPlayerNumber() {
        return pNum;
    }

    //====================== Game Stuff ============================//


    /**
     * This method will display the server connection menu.
     */
    public void startConnectionMenu() {

    }

    /**
     * This method will display the class selection menu.
     */
    public void startClassSelect() {

    }

    /**
     * This method will display the boss fight.
     */
    public void startGame() {

    }

    /**
     * A private class that updates and displays the game.
     */
    private class GameClient implements Runnable {
        private Thread logicLoop;
        private Timer drawTimer;
        private long sleepTime;

        /**
         * Instantiates the logic loop class.
         * 
         * @param sleepTime delay between loops in milliseconds
         */
        public GameClient(int sleepTime) {
            logicLoop = new Thread(this);
            this.sleepTime = sleepTime;
            drawLoop();
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
                p1.updatePlayerShip(0.1);
                p2.updatePlayerShip(0.1);

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
        public void drawLoop() {
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
                            //titleFPS = "Camping | FPS: " + frames + "   ";
                            System.out.println("FPS: " + frames);
                        } else {
                            //titleFPS = "Camping   ";
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

    //==================== Networking ================================//


    /**
     * Method to connect to the server
     */
    public void connectToServer() {
        //TODO make it so that the ip and port is taken from a jtextfield or something
        try {
            System.out.print("Please input the server's IP Address: ");
            String ipAddress = "192.168.1.152";

            System.out.print("Please input the port number: ");
            int portNum = Integer.parseInt("25570");
            System.out.println("ATTEMPTING TO CONNECT TO THE SERVER...");
            Socket clientSocket = new Socket(ipAddress, portNum);
            System.out.println("CONNECTION SUCCESSFUL!");
            wtsLoop = new WriteToServer(new DataOutputStream(clientSocket.getOutputStream()), 20);
            rfsLoop = new ReadFromServer(new DataInputStream(clientSocket.getInputStream()), 20);
            wtsLoop.startThread();
            rfsLoop.startThread();
        } catch(IOException ex) {
          System.out.println("IOException from connectToServer() method.");
        }
      }
    /**
     * A private class that writes information to the server.
     */
    private class WriteToServer implements Runnable {
        private DataOutputStream dataOut;
        private long sleepTime;
        private Thread WTSloop;

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

        /**
         * Initializes the WriteToServer class
         */
        public WriteToServer(DataOutputStream dataOut, int sleepTime) {
            WTSloop = new Thread(this);
            this.sleepTime = sleepTime;
            this.dataOut = dataOut;
        }

        /**
         * Starts the WTS thread
         */
        public void startThread() {
            WTSloop.start();
        }

    }

    /**
     * A private class that reads data from the server
     */
    private class ReadFromServer implements Runnable {
        private DataInputStream dataIn;
        private long sleepTime;
        private Thread RFSloop;

        /**
         * The thread that continously recieves data from the server.
         */
        @Override
        public void run() {
            try {
                try { pNum = dataIn.readInt(); } 
                catch(IOException ex) {
                    System.out.println("IOException at WTC run()");
                }

                while (true) {
                    //TODO read data from server.
                    Thread.sleep(sleepTime);
                }
            } catch(InterruptedException ex) {
                System.out.println("InterruptedException at RFS run()\n\n" + ex);
            }
        }

        /**
         * Initializes the ReadFromServer class.
         */
        public ReadFromServer(DataInputStream dataIn, int sleepTime) {
            this.dataIn = dataIn;
            this.sleepTime = sleepTime;
            RFSloop = new Thread(this);
        }

        /**
         * Starts the RFS thread.
         */
        public void startThread() {
            RFSloop.start();
        }

    }
    
}
