/**
	@author Wilbert Meinard L. Chen (201153)
    @author Lhance Christian S. Wong (205467)
	@version May 31, 2021
**/

/*
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
*/

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * This class contains the code that manages the game server's functionality. It also
 * contains the main method that instantiates and starts the server.
 * 
 * <p> Since there is no real benefit to using TCP for this type
 * of application, UDP was used instead. GameServer also has its own instance of
 * Game to sync up both players. It updates its own game and sends over the necessary data 
 * to the two GameCanvas clients. Note that the server will not draw the game, it will only
 * update it.
 */
public class GameServer {
    //Game Object
    private Game gameMaster;
    private Player p1, p2;
    private Boss Yalin;
    private BulletController controller1, controller2,bossController;
    //Network Stuff
    private DatagramSocket serverSocket;
    private int numPlayers;
    private static final int MAX_PLAYERS = 2;
    private static final int bufMax = 8192;
    private static final int serverPort = 25570;

    /**
     * Initializes the GameServer class.
     */
    public GameServer() {
        System.out.println("===========================================\nShoot and Scoot!");
        numPlayers = 0;
        gameMaster = new Game(true);
        initGame();
        
        //Creates a DatagramSocket using a specific port
        try {
            serverSocket = new DatagramSocket(serverPort);
        } catch(IOException ex) {}
    }

    /**
     * Initializes important game objects.
     */
    private void initGame() {
        p1 = gameMaster.getPlayer1();
        p2 = gameMaster.getPlayer2();
        Yalin = gameMaster.getYalin();
        controller1 = gameMaster.getBC1();
        controller2 = gameMaster.getBC2();
        bossController = gameMaster.getBBC();
    }

    /**
     * This method first waits for connections. Once someone has connected, the method gives the player client
     * their player number and waits for the next player. After that it passes both player's
     * InetAddress and port to a class that sends them data.
     */
    public void acceptConnections() {
        try {
            //WTC thread of p1 and p2
            WriteToClient p1wtcLoop = null;
            WriteToClient p2wtcLoop = null;
            ServerSocket sSoc = new ServerSocket(serverPort);
            while(numPlayers < MAX_PLAYERS) {
                System.out.println("===========================================\nWaiting for players..");
                numPlayers++;
                //Accepts a TCP connection request from a client
                Socket cSoc = sSoc.accept();

                //Used tcp to ensure the clients get their player number
                new DataOutputStream(cSoc.getOutputStream()).writeInt(numPlayers); 
                System.out.println("Player " + numPlayers + " has received a number.");

                //Receives an DatagramPacket to save their InetAddress and port.
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                serverSocket.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();

                //Some console output
                System.out.println("Player "+ numPlayers+ "'s port is "+ port);
                System.out.println("Player "+ numPlayers+ "'s IP Address is "+ address.toString());
                
                //Initializes the correct WTC loop
                if(numPlayers == 1) {
                    p1wtcLoop = new WriteToClient(numPlayers, address, port, 16);
                } else {
                    p2wtcLoop = new WriteToClient(numPlayers, address, port, 16);
                }
            }
            //Initializes one RFC thread and starts the other two WTC threads of both players
            ReadFromClient rfcLoop = new ReadFromClient();
            rfcLoop.startThread();
            p1wtcLoop.startThread();
            p2wtcLoop.startThread();

            //Closes an unused socket
            sSoc.close();
            System.out.println("===========================================\nNo longer accepting connections.");
            System.out.println("TIME TO SHOOT AND SCOOT!");
            //start game update thread
            gameMaster.startThread();

        } catch(IOException ex) {
            System.out.println("IOException from acceptConnections().");
        }
    }

    /**
     * A private class that writes information to the server.
     * 
     * <p>Currently sending via UDP. Based on the player number(the number that
     * determines who this thread is sending data to), it will
     * send over the correct information.
     */
    private class WriteToClient implements Runnable {
        private int pNum;
        private InetAddress address;
        private int port;
        private long sleepTime;
        private Thread WTCloop;

        @Override
        public void run() {
            while(true) {
                if (pNum == 1) {
                    //Sends p2's data and bullets to p1
                    send(("p_"+p2.getCompressedData()));
                    send(controller2.getCompressedData()); 
                } else if (pNum == 2) {
                    //Sends p1's data and bullets to p2
                    send(("p_"+p1.getCompressedData()));
                    send(controller1.getCompressedData());
                }
                //Sends the boss's data and bullets to both players
                send(Yalin.getCompressedData());
                send(bossController.getCompressedData());
                
                //waits for sleepTime amount of time in millseconds in between loops
                try { Thread.sleep(sleepTime); }
                catch(InterruptedException ex) {
                    System.out.println("IOException at WTC run()\n\n" + ex);
                    System.exit(1);
                }
            }
        }

        /**
         * A method that sends over a byte array to a specific
         * address and port given by WriteToClient's constructor.
         * 
         * @param buf the byte array
         */
        public void send(String data) {
            byte[] buf = data.getBytes();
            DatagramPacket packet = new DatagramPacket(buf,buf.length,address,port);
            try {
                serverSocket.send(packet);
            } catch(IOException ex) {
                System.out.println("IOException from WTC send()");
            }
        }

       /**
        * Initializes the WritetoClient class.
        *
        * @param pNum The player that receives whatever is sent.
        * @param address The ip address of the player
        * @param port The port of the player
        * @param sleepTime The delay in milliseconds in between the sending of data
        */
        public WriteToClient(int pNum, InetAddress address, int port, int sleepTime) {
            this.pNum = pNum;
            this.address = address;
            this.port = port;
            this.sleepTime = sleepTime;
            WTCloop = new Thread(this);
        }

        /**
         * Starts the WriteToClient thread.
         */
        public void startThread() {
            WTCloop.start();
        }

    }

    /**
     * A private class that contionuously accepts/receives data
     * as fast as they arrive.
     * 
     * <p>Currently receiving via UDP. Since clients constantly bombard the server with data,
     * it receives packets as fast as it can. It then segregates the data into their
     * respective game objects to be processed.
     */
    private class ReadFromClient implements Runnable {
        private Thread RFCloop;

        @Override
        public void run() {
            try {
                while(true) {
                    byte[] buf = new byte[bufMax];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    serverSocket.receive(packet);

                    //turns the byte array into string
                    String sDataIn = new String(packet.getData(), StandardCharsets.UTF_8);
                    sDataIn = sDataIn.trim();

                    //passes the string into the correct game object
                    if(sDataIn.startsWith("p1_")) {
                        p1.receiveCompressedData(sDataIn);
                    } else if(sDataIn.startsWith("p2_")) {
                        p2.receiveCompressedData(sDataIn);
                    } else if(sDataIn.startsWith("p1BC_")) {
                        controller1.receiveCompressedData(sDataIn);
                    } else if(sDataIn.startsWith("p2BC_")) {
                        controller2.receiveCompressedData(sDataIn);
                    } else {
                        System.out.println("Data bad: " + sDataIn);
                    }

                }
            } catch(IOException ex) {
                System.out.println("IOException at RFC run()\n\n" + ex);
                System.exit(1);
            }
        }

        /**
         * Initializes the ReadFromClientClass.
         */
        public ReadFromClient() {
            RFCloop = new Thread(this);
        }

        /**
         * Starts the ReadFromClient's Thread.
         */
        public void startThread() {
            RFCloop.start();
        }
           
    }

    /**
     * Starts the server
     * 
     * @param args
     */
    public static void main(String args[]) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
    
}
