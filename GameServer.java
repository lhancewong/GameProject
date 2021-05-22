import java.io.*;
import java.net.*;

/**
 * This class contains the code that manages the game server's functionality. It also
 * contains the main method that instantiates and starts the server.
 */
public class GameServer {
    private ServerSocket ss;
    private int numPlayers;

    //GameRelated
    private boolean isRunning;

    private static final int MAX_PLAYERS = 2;

    public GameServer() {
        System.out.println("( ᴜ ω ᴜ )   |Shoot and Scoot Server| ( ᴜ ω ᴜ )");
        numPlayers = 0;

        try {
            ss = new ServerSocket(25570);
        } catch(IOException ex) {
            System.out.println("IOException from GameServer constructor.");
        }
    }

    public void acceptConnections() {
        try {
            System.out.println("( ᴜ ω ᴜ ) Waiting for connections...");

            while(numPlayers < MAX_PLAYERS) {
                Socket s = ss.accept();
                numPlayers++;
                
                ReadFromClient RFC = new ReadFromClient(s,numPlayers,20);
                WriteToClient WTC = new WriteToClient(s,numPlayers,20);
                System.out.println("( ᴜ ω ᴜ ) Player " + numPlayers + " has connected.");
                RFC.startThread();
                WTC.startThread();
            }

            System.out.println("No longer accepting connections.");

        } catch(IOException ex) {
            System.out.println("IOException from acceptConnections().");
        }
    }

    /**
     * 
     */
    private class GameMaster implements Runnable {
        private Thread logicLoop;
        private long sleepTime;
        
        public GameMaster(int sleepTime) {
            logicLoop = new Thread(this);
            this.sleepTime = sleepTime;
        }

        public void startThread() {
            logicLoop.start();
        }

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
    }
    /**
     * A private class that writes information to the server.
     */
    private class WriteToClient implements Runnable {
        private Socket pSocket;
        private int pNum;
        private long sleepTime;

        private DataOutputStream dataOut; 
        private Thread WTCloop;

        /**
         * Initializes the WriteToServer class
         */
        public WriteToClient(Socket pSocket, int pNum, int sleepTime) {
            this.pSocket = pSocket;
            this.pNum = pNum;
            this.sleepTime = sleepTime;

            WTCloop = new Thread(this);
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

        /**
         * The thread that continuously sends data to the server.
         */
        @Override
        public void run() {
            try { 
                dataOut.writeInt(pNum); 
            } catch(IOException ex) {
                System.out.println("IOException at WTC run()");
            }
            while(true) {
                if (pNum == 1) {
                

                } else {
    
                }
            
                try { Thread.sleep(sleepTime); }
                catch(InterruptedException ex) {
                    System.out.println("InterruptedException at WTC run()\n\n" + ex);
                }
            }
        }

    }



    private class ReadFromClient implements Runnable {
        private Socket pSocket;
        private int pNum;
        private long sleepTime;

        private DataInputStream dataIn;
        private Thread RFCloop;

        public ReadFromClient(Socket pSocket, int pNum, int sleepTime) {
            this.pSocket = pSocket;
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

        @Override
        public void run() {
            while(true) {
                if (pNum == 1) {
                

                } else {
    
                }
            
                try { Thread.sleep(sleepTime); }
                catch(InterruptedException ex) {
                    System.out.println("InterruptedException at WTC run()\n\n" + ex);
                }
            }
            
            
        }

    }

    

    public static void main (String args[]) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
    
}
