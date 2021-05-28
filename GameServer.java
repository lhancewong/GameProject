import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * This class contains the code that manages the game server's functionality. It also
 * contains the main method that instantiates and starts the server.
 */
public class GameServer {
    
    private int numPlayers;
    private Game gameMaster;

    //GameRelated
    private Player p1, p2;
    private Boss Yalin;

    private static final int MAX_PLAYERS = 2;

    //Network Stuff
    private boolean isRunning;
    private DatagramSocket serverSocket;
    private static final int bufMax = 512;
    private static final int serverPort = 25570;

    //private InetAddress p1Address, p2Address;
    //private int p1Port, p2Port;
    

    public GameServer() {
        System.out.println("===========================================\nShoot and Scoot!");
        numPlayers = 0;

        gameMaster = new Game(true);
        gameMaster.startThread();
        initGameObjects();

        try {
            serverSocket = new DatagramSocket(serverPort);
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
            WriteToClient p1wtcLoop = null;
            WriteToClient p2wtcLoop = null;
            ServerSocket sSoc = new ServerSocket(serverPort);
            while(numPlayers < MAX_PLAYERS) {
                System.out.println("===========================================\nWaiting for players..");
                numPlayers++;
                Socket cSoc = sSoc.accept();

                new DataOutputStream(cSoc.getOutputStream()).writeInt(numPlayers); 
                System.out.println("Player " + numPlayers + " has received a number.");

                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                serverSocket.receive(packet);
                String request = new String(packet.getData(), StandardCharsets.UTF_8);
                request.trim();
                System.out.println(request);
                
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                System.out.println("Player "+ numPlayers+ "'s port is "+ port);
                
                if(numPlayers == 1) {
                    p1wtcLoop = new WriteToClient(numPlayers, address, port, 20);
                } else {
                    p2wtcLoop = new WriteToClient(numPlayers, address, port, 20);
                }
                
                
            }

            ReadFromClient rfcLoop = new ReadFromClient();
            rfcLoop.startThread();
            p1wtcLoop.startThread();
            p2wtcLoop.startThread();

            sSoc.close();
            System.out.println("===========================================\nNo longer accepting connections.");
            System.out.println("TIME TO SHOOT AND SCOOT!");

        } catch(IOException ex) {
            System.out.println("IOException from acceptConnections().");
        }
    }

    /**
     * A private class that writes information to the server.
     */
    private class WriteToClient implements Runnable {
        private int pNum;
        private InetAddress address;
        private int port;
        private long sleepTime;
        private Thread WTCloop;

        /**
         * The thread that continuously sends data to the client.
         */
        @Override
        public void run() {
            while(true) {
                if (pNum == 1) {
                    send(p2.getCompressedData());
                } else if (pNum == 2) {
                    send(p1.getCompressedData());
                }
                send(Yalin.getCompressedData());
                
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
        public WriteToClient(int pNum, InetAddress address, int port, int sleepTime) {
            this.pNum = pNum;
            this.address = address;
            this.port = port;
            this.sleepTime = sleepTime;
            WTCloop = new Thread(this);
        }

        public void send(byte[] buf) {
            DatagramPacket packet = new DatagramPacket(buf,buf.length,address,port);
            try {
                serverSocket.send(packet);
            } catch(IOException ex) {
                System.out.println("IOException from WTC send()");
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
        private Thread RFCloop;

        @Override
        public void run() {
            try {
                while(true) {
                    byte[] buf = new byte[bufMax];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    serverSocket.receive(packet);

                    String sDataIn = new String(packet.getData(), StandardCharsets.UTF_8);
                    sDataIn = sDataIn.trim();

                    if(sDataIn.startsWith("p1")) {
                        p1.receiveCompressedData(sDataIn);
                    } else if(sDataIn.startsWith("p2")) {
                        p2.receiveCompressedData(sDataIn);
                    } else {
                        System.out.println("Data bad: " + sDataIn);
                    }

                }
            } catch(IOException ex) {
                System.out.println("IOException at WTC run()\n\n" + ex);
                System.exit(1);
            }
        }

        public ReadFromClient() {
            RFCloop = new Thread(this);
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
