import java.awt.*;
import javax.swing.*;
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
    private WriteToServer wtsLoop;
    private ReadFromServer rfsLoop;

    //Game Stuff
    private Game game;
    public Player p1, p2;
    public Boss Yalin;
    private int pNum;
    private boolean isRunning, isBossFight, isServerSelection, isClassSelection;
    private static final int FPS_CAP = 60;
    private javax.swing.Timer drawTimer;
    
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
        
        //Game Stuff
        game = new Game(false);
        drawLoop();
        
        isRunning = true;
        isServerSelection = false;
        isClassSelection = false;
        isBossFight = true;

        connectToServer();
        game.startThread();

        p1 = game.getPlayer1();
        p2 = game.getPlayer2();
        Yalin = game.getYalin();
        
        drawTimer.start();
        rfsLoop.startThread();
        wtsLoop.startThread();

        
    }

    /**
     * Draws in the order of background, bullet, boss, then player.
     */
    @Override
    protected void paintComponent(Graphics g) {
        g2d = (Graphics2D) g;
        game.draw(g2d);
    }
    

    public Game getGame() {
        return game;
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

    //==================== Networking ================================//


    /**
     * Method to connect to the server
     */
    public void connectToServer() {
        try {
            //System.out.print("Please input the server's IP Address: ");
            //String ipAddress = console.nextLine();

            //System.out.print("Please input the port number: ");
            //int portNum = Integer.parseInt(console.nextLine());

            System.out.println("ATTEMPTING TO CONNECT TO THE SERVER...");
            Socket clientSocket = new Socket("ginks.ml", 25570);

            System.out.println("CONNECTION SUCCESSFUL!");
            wtsLoop = new WriteToServer(new DataOutputStream(clientSocket.getOutputStream()), 60);
            rfsLoop = new ReadFromServer(new DataInputStream(clientSocket.getInputStream()), 60);

            try { 
                pNum = new DataInputStream(clientSocket.getInputStream()).readInt();
                GameUtils.get().setPlayerNum(pNum);
                System.out.println("You are Player " + pNum + "!");
            } catch(IOException ex) {
                System.out.println("IOException when trying to get Player Number");
            }

            
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
            try {
                while (true) {
                    if (pNum == 1) {
                        p1.sendCompressedData(dataOut);
                    } else {
                        p2.sendCompressedData(dataOut);
                    }
                    
                    Thread.sleep(sleepTime);
                }
            } catch(InterruptedException ex) {
                System.out.println("InterruptedException at WTS run()\n\n" + ex);
                System.exit(1);
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
                while (true) {
                    if(pNum == 1) {
                        p2.receiveCompressedData(dataIn);
                    } else {
                        p1.receiveCompressedData(dataIn);
                    }
                    Yalin.receiveCompressedData(dataIn);

                    Thread.sleep(sleepTime);
                }
            } catch(InterruptedException ex) {
                System.out.println("InterruptedException at RFS run()\n\n" + ex);
                System.exit(1);
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
