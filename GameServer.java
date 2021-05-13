import java.io.*;
import java.net.*;

/**
 * This class contains the code that manages the game server's functionality. It also
 * contains the main method that instantiates and starts the server.
 */
public class GameServer {
    private ServerSocket ss;
    private int numPlayers;

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
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                numPlayers++;
                out.writeInt(numPlayers);
                System.out.println("( ᴜ ω ᴜ ) Player " + numPlayers + " has connected.");
            }

            System.out.println("No longer accepting connections.");

        } catch(IOException ex) {
            System.out.println("IOException from acceptConnections().");
        }
    }

    public static void main (String args[]) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
    
}
