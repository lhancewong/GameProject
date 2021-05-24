import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

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
    private Scanner console;

    //Threads
    private GameClient gameLoop;
    private WriteToServer wtsLoop;
    private ReadFromServer rfsLoop;

    //Game Stuff
    public Player p1, p2;
    private int pNum;
    private boolean isRunning, isBossFight, isServerSelection, isClassSelection;
    private static final int FPS_CAP = 60;

    private ArrayList<GameObject> bossFight;
    //private MenuObjects serverSelectionMenu;
    //private MenuObjects classSelectionMenu;
    
    /**
     * Initializes the GameCanvas object.
     */
    public GameCanvas() {
        //Canvas things
        width = GameUtils.get().getWidth();
        height = GameUtils.get().getHeight();
        setPreferredSize(new Dimension(width,height));
        console = new Scanner(System.in);
        
        //Objects & Shapes
        bg = new Rectangle2D.Double(0,0,width,height);
        initBossFight();
        initServerSelection();

        //loops
        gameLoop = new GameClient(20);

        isRunning = true;
        isServerSelection = false;
        isClassSelection = false;
        isBossFight = true;

        connectToServer();
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

        if(isBossFight) {
            for(GameObject i: bossFight) {
                i.draw(g2d);
            }
        }

    }

    /**
     * Initializes the bossFight ArrayList.
     * This ArrayList is meant to hold what will be 
     * drawn when repaint is called while the
     * boss fight is supposed to be displayed.
     */
    private void initBossFight() {
        bossFight = new ArrayList<GameObject>();
        //bg = new Background();
        p1 = new Player(210,180,30,4);
        p2 = new Player(210,540,30,4);
        //bossFight.add(bg);
        bossFight.add(p1);
        bossFight.add(p2);

    }

    /**
     * Initializes the serverSelectionMenu ArrayList.
     * Meant to hold what will be drawn when repaint 
     * is called and the serverSelection Menu is supposed
     * to be up
     */
    private void initServerSelection() {

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
     * A private class that updates and displays the game.
     */
    private class GameClient implements Runnable {
        private Thread logicLoop;
        private javax.swing.Timer drawTimer;
        private long sleepTime;

        /**
         * Instantiates the logic loop class.
         * 
         * @param sleepTime delay between loops in milliseconds
         */
        public GameClient(int sleepTime) {
            logicLoop = new Thread(this);
            drawLoop();
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
            long previousTime = System.currentTimeMillis()-1;
            while(isRunning) {
                long currentTime = System.currentTimeMillis();

                double deltaTime = (currentTime - previousTime)/1000.0;

                for(GameObject i : bossFight) {
                    i.update(deltaTime);
                }

                previousTime = currentTime;

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
            ActionListener displayGame = new ActionListener() {
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
                            String titleFPS = "Shoot and Scoot | FPS: " + frames + "   ";
                            GameUtils.get().setJFrameTitle(titleFPS);
                        } else {
                            String titleFPS = "Shoot and Scoot";
                            GameUtils.get().setJFrameTitle(titleFPS);
                        }
                        frames = 0;
                        previousTime = currentTime;
                    }
                    //GAME DRAW
                    repaint();
                }
    
            };
            drawTimer = new javax.swing.Timer((int)Math.round(1000.0/FPS_CAP), displayGame);
        }
    }

    //==================== Networking ================================//


    /**
     * Method to connect to the server
     */
    public void connectToServer() {
        //TODO make it so that the ip and port is taken from a jtextfield or something. called when join is pressed
        try {
            System.out.print("Please input the server's IP Address: ");
            String ipAddress = console.nextLine();

            System.out.print("Please input the port number: ");
            int portNum = Integer.parseInt(console.nextLine());

            System.out.println("ATTEMPTING TO CONNECT TO THE SERVER...");
            Socket clientSocket = new Socket(ipAddress, portNum);

            System.out.println("CONNECTION SUCCESSFUL!");
            wtsLoop = new WriteToServer(new DataOutputStream(clientSocket.getOutputStream()), 20);
            rfsLoop = new ReadFromServer(new DataInputStream(clientSocket.getInputStream()), 20);

            try { 
                pNum = new DataInputStream(clientSocket.getInputStream()).readInt();
                GameUtils.get().setPlayerNum(pNum);
                System.out.println("You are Player " + pNum + "!");
            } catch(IOException ex) {
                System.out.println("IOException when trying to get Player Number");
            }

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
            /* TODO gets the compressed data of every gameobject and sends it to server
             * Example: player sends its moving up down or something
             */
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
                /* try { 
                    pNum = dataIn.readInt();
                    GameUtils.get().setPlayerNum(pNum); 
                    System.out.println("You are Player " + pNum + "!");
                } catch(IOException ex) {
                    System.out.println("IOException at WTC run()");
                } */

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
