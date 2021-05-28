import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
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

    //Network Stuff
    private DatagramSocket clientSocket;
    private static final int bufMax = 512;
    
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

        findServer();
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
    public void findServer() {
        try {
            /* Random random = new Random();
            int ranPort = 1023 + random.nextInt(64331);//gets a random int from 1023 to 65353. */
            clientSocket = new DatagramSocket();
            
            InetAddress ip = InetAddress.getByName("localhost");
            int port = 25570;

            Socket cSoc = new Socket("ginks.ml",port);
            
            wtsLoop = new WriteToServer(ip, port, 20);
            rfsLoop = new ReadFromServer();

            System.out.println("Requesting for Player number from server...");

            pNum = new DataInputStream(cSoc.getInputStream()).readInt();
            GameUtils.get().setPlayerNum(pNum);
            System.out.println("You are Player " + pNum + "!");

            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf,buf.length,ip,port);
            clientSocket.send(packet);

            System.out.println("Waiting for other Player...");
            byte[] start = new byte[bufMax];
            DatagramPacket startPacket = new DatagramPacket(start, start.length);
            clientSocket.receive(startPacket);

            cSoc.close();
        } catch(IOException ex) {
          System.out.println("IOException from connectToServer() method.");
        }

      }
    
      /**
     * A private class that writes information to the server.
     */
    private class WriteToServer implements Runnable {
        private InetAddress address;
        private int port;
        private long sleepTime;
        private Thread WTSloop;

         /**
         * The thread that continuously sends data to the server.
         */
        @Override
        public void run() {
            while (true) {
                if (pNum == 1) {
                    send(p1.getCompressedData());
                } else {
                    send(p2.getCompressedData());
                }

                try { Thread.sleep(sleepTime); }
                catch(InterruptedException ex) {
                    System.out.println("InterruptedException at WTC run()\n\n" + ex);
                    System.exit(1);
                }
            }
        }

        public void send(byte[] buf) {
            DatagramPacket packet = new DatagramPacket(buf,buf.length,address,port);
            try {
                clientSocket.send(packet);
            } catch(IOException ex) {
                System.out.println("IOException from WTC send()");
            }
        }

        /**
         * Initializes the WriteToServer class
         */
        public WriteToServer(InetAddress address, int port, int sleepTime) {
            this.address = address;
            this.port = port;
            this.sleepTime = sleepTime;
            WTSloop = new Thread(this);
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
        private Thread RFSloop;

        @Override
        public void run() {
            try {
                while(true) {
                    byte[] buf = new byte[bufMax];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    clientSocket.receive(packet);

                    String sDataIn = new String(packet.getData(), StandardCharsets.UTF_8);
                    sDataIn = sDataIn.trim();

                    if(sDataIn.startsWith("p1")) {
                        p1.receiveCompressedData(sDataIn);
                    } else if(sDataIn.startsWith("p2")) {
                        p2.receiveCompressedData(sDataIn);
                    } else if(sDataIn.startsWith("Yalin")){
                        Yalin.receiveCompressedData(sDataIn);
                    } else {
                        System.out.println("Data bad: " + sDataIn);
                    }
                        
                    
                }
            } catch(IOException ex) {
                System.out.println("IOException at RFC run()" + ex);
                System.exit(1);
            }
        }

        /**
         * Initializes the ReadFromServer class.
         */
        public ReadFromServer() {
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
