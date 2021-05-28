import java.io.*;
import java.net.*;

/**
 * This class contains the code that manages the game server's functionality. It also
 * contains the main method that instantiates and starts the server.
 */
public class GameServer {
    private ServerSocket ss;
    private int numPlayers;
    private Game gameMaster;

    //GameRelated
    private Player p1, p2;
    private Boss Yalin;

    private static final int MAX_PLAYERS = 2;

    public GameServer() {
        System.out.println("=================\nShoot and Scoot Server");
        numPlayers = 0;

        gameMaster = new Game(true);
        gameMaster.startThread();
        initGameObjects();
        

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
            System.out.println("\nWaiting for connections...");
            ReadFromClient RFC = null;
            WriteToClient WTC = null;

            while(numPlayers < MAX_PLAYERS) {
                Socket s = ss.accept();
                numPlayers++;
                
                RFC = new ReadFromClient(s,numPlayers,60);
                WTC = new WriteToClient(s,numPlayers,60);
                System.out.println("===============\nPlayer " + numPlayers + " has connected.");
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
     * A private class that writes information to the server.
     */
    private class WriteToClient implements Runnable {
        //private Socket pSocket;
        private int pNum;
        private long sleepTime;

        private DataOutputStream dataOut; 
        private Thread WTCloop;

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
                    Yalin.sendCompressedData(dataOut);

            
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
                        p1.receiveCompressedData(dataIn);
                    } else {
                        p2.receiveCompressedData(dataIn);
                    }

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
