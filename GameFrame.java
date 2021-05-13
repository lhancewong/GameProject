import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

/**
 *  This class contains the code that sets up the main JFrame for the player.
 */
public class GameFrame {
    //GUI stuff
    private Container mainPanel;
    private JFrame window;

    //Player stuff
    private Player p1;
    private Player p2;

    public GameFrame() {

    }

    public void setUpGUI() {
        mainPanel = window.getContentPane();
        window.setTitle("Shoot and Scoot");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setFocusable(true);


        window.pack();
    }
    
}
