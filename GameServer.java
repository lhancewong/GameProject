import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This class contains the code that manages the game server's functionality. It also
 * contains the main method that instantiates and starts the server.
 */
public class GameServer {
    private ServerSocket ss;
    private int numPlayers;
    private Game gameMaster;

    private String clientInput;

    //GameRelated
<<<<<<< HEAD
    private ArrayList<GameObject> bossFight;
    private Player p1, p2;
=======
    private Player p1, p2;
    private Boss Yalin;
>>>>>>> Server-N-Hitboxes

    private static final int MAX_PLAYERS = 2;

    public GameServer() {
<<<<<<< HEAD
        System.out.println("Shoot and Scoot Server");
        numPlayers = 0;

        GameMaster gameMaster = new GameMaster(20);
        initBossFight();
        gameMaster.startThread();
=======
        System.out.println("=================\nShoot and Scoot Server");
        numPlayers = 0;

        gameMaster = new Game(true);
        gameMaster.startThread();
        initGameObjects();
        
>>>>>>> Server-N-Hitboxes

        try {
            ss = new ServerSocket(25570);
        } catch(IOException ex) {
            System.out.println("IOException from GameServer constructor.");
        }
    }

    private void initGameObjects() {
        p1 = gameMaster.getPlayer1();
        p2 = gameMaster.getPlayer2();
        Yalin = gameMaster.getYalin();
    }

    public void acceptConnections() {
        try {
<<<<<<< HEAD
            System.out.println("Waiting for connections...");
=======
            System.out.println("\nWaiting for connections...");
>>>>>>> Server-N-Hitboxes
            ReadFromClient RFC = null;
            WriteToClient WTC = null;

            while(numPlayers < MAX_PLAYERS) {
                Socket s = ss.accept();
                numPlayers++;
                
                RFC = new ReadFromClient(s,numPlayers,60);
                WTC = new WriteToClient(s,numPlayers,60);
<<<<<<< HEAD
                System.out.println("Player " + numPlayers + " has connected.");
=======
                System.out.println("===============\nPlayer " + numPlayers + " has connected.");
>>>>>>> Server-N-Hitboxes
                try { 
                    new DataOutputStream(s.getOutputStream()).writeInt(numPlayers); 
                } catch(IOException ex) {
                    System.out.println("IOException at WTC run()");
                }
                RFC.startThread();
                WTC.startThread();
            }
            System.out.println("No longer accepting connections.");

        } catch(IOException ex) {
            System.out.println("IOException from acceptConnections().");
        }
    }

    /**
<<<<<<< HEAD
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
     * 
=======
     * A private class that writes information to the server.
>>>>>>> Server-N-Hitboxes
     */
    private class WriteToClient implements Runnable {
        //private Socket pSocket;
        private int pNum;
        private long sleepTime;
<<<<<<< HEAD
        
        public GameMaster(int sleepTime) {
            logicLoop = new Thread(this);
            this.sleepTime = sleepTime;
        }

        

        public void startThread() {
            initBossFight();
            logicLoop.start();
        }
=======

        private DataOutputStream dataOut; 
        private Thread WTCloop;
>>>>>>> Server-N-Hitboxes

        /**
         * The thread that continuously sends data to the client.
         */
        @Override
        public void run() {
<<<<<<< HEAD
            long previousTime = System.currentTimeMillis()-1;
            while(true) {
                long currentTime = System.currentTimeMillis();

                double deltaTime = (currentTime - previousTime)/1000.0;

                for(GameObject i : bossFight) {
                    i.update(deltaTime);
                }

                previousTime = currentTime;

=======
            while(true) {
                    if (pNum == 1) {
                        p2.sendCompressedData(dataOut);
                    } else {
                        p1.sendCompressedData(dataOut);
                    }
                    Yalin.sendCompressedData(dataOut);
>>>>>>> Server-N-Hitboxes

            
                try { Thread.sleep(sleepTime); }
                catch(InterruptedException ex) {
                    System.out.println("InterruptedException at WTC run()\n\n" + ex);
                    System.exit(1);
                }
            }
        }
<<<<<<< HEAD
    }
    /**
     * A private class that writes information to the server.
     */
    private class WriteToClient implements Runnable {
        //private Socket pSocket;
        private int pNum;
        private long sleepTime;

        private DataOutputStream dataOut; 
        private Thread WTCloop;
=======
>>>>>>> Server-N-Hitboxes

        /**
         * The thread that continuously sends data to the client.
         */
        @Override
        public void run() {
            while(true) {
                    if (pNum == 1) {
                        p2.sendCompressedData(dataOut);
                    } else {
                        p1.sendCompressedData(dataOut);
                    }
            
                try { Thread.sleep(sleepTime); }
                catch(InterruptedException ex) {
                    System.out.println("InterruptedException at WTC run()\n\n" + ex);
                    System.exit(1);
                }
            }
        }

        /**
         * Initializes the WriteToServer class
         */
        public WriteToClient(Socket pSocket, int pNum, int sleepTime) {
            //this.pSocket = pSocket;
            this.pNum = pNum;
            this.sleepTime = sleepTime;

            WTCloop = new Thread(this);

            //gets the DataOutputStream
            try {
                dataOut = new DataOutputStream(pSocket.getOutputStream());
            } catch(IOException ex) {
                System.out.println("IOException from WTC constructor");
            }
        }

        /**
         * Starts the WTC thread
         */
        public void startThread() {
            WTCloop.start();
        }

    }

    private class ReadFromClient implements Runnable {
        private int pNum;
        private long sleepTime;

        private DataInputStream dataIn;
        private Thread RFCloop;

        @Override
        public void run() {
            try {
                while(true) {
                    if(pNum == 1) {
<<<<<<< HEAD
                        p1.recieveCompressedData(dataIn);
                    } else {
                        p2.recieveCompressedData(dataIn);
                    }

                    //processInput(clientInput.split("_"));

=======
                        p1.receiveCompressedData(dataIn);
                    } else {
                        p2.receiveCompressedData(dataIn);
                    }

>>>>>>> Server-N-Hitboxes
                    Thread.sleep(sleepTime); 
                }
            } catch(InterruptedException ex) {
                System.out.println("InterruptedException at WTC run()\n\n" + ex);
                System.exit(1);
            }
        }

        public ReadFromClient(Socket pSocket, int pNum, int sleepTime) {
            this.pNum = pNum;
            this.sleepTime = sleepTime;

            RFCloop = new Thread(this);
            try { 
                dataIn = new DataInputStream(pSocket.getInputStream()); 
            } catch(IOException ex) {
                System.out.println("IOException from RFC constructor");
            }
        }

        public void startThread() {
            RFCloop.start();
        }
           
    }

    public static void main(String args[]) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
    
}
