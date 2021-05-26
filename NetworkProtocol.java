import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.ArrayList;
import java.util.Enumeration;
/**
 * NETWORK PROTOCOL by Wong
 * @version N_1.4
 * @author Kingsley Wong
 * 
 * This class finds other instances of itself with the same port within the same local
 * area network and same version. 
 * The only methods you need to know is send, receive and addReceiver.
 * 
 */
public class NetworkProtocol{
	private final int PORT;
	private final Charset CHARSET;
	private final int BUFFER_SIZE;
	private final String VERSION;
	//=========================================
	private DatagramSocket socket;
	private ArrayList<InetAddress> peers;
	private ArrayList<InetAddress> broadcast;
	private ArrayList<InetAddress> self;
	//=========================================
	private Receiver receiver; //network protocol supports other stuff, just change this
	private String channel;
	private Thread receiveThread; //a separate thread to wait for receives.
	private Thread disconnectThread; //shutdown hook
	/**
     * Enables everything.
	 * @param port the desired port
     */
	public NetworkProtocol(int port){
		disconnectThread = new Disconnector();
		Runtime.getRuntime().addShutdownHook(disconnectThread);
		PORT = port;  //change this too, use different port pls
		CHARSET = StandardCharsets.UTF_8;
		BUFFER_SIZE = 1472;
		VERSION = "_N_1.4";
		//=========================================
		try {
			socket = new DatagramSocket(PORT); //best number ever
		} catch (SocketException ex){
			System.out.println("Socket already in use.");
			System.exit(1);
		}
		//=========================================
		peers = new ArrayList<InetAddress>();
		broadcast = new ArrayList<InetAddress>();
		self = new ArrayList<InetAddress>();
		//FURTHER CHECK CODE FOR PROBLEMS
		System.setProperty("java.net.preferIPv4Stack", "true");
		//=========================================
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while(interfaces.hasMoreElements()){
				NetworkInterface temp = interfaces.nextElement();
				if(temp.isLoopback()) continue;
				//=========================================
				for(InterfaceAddress interfaceAddress : temp.getInterfaceAddresses()){
					if(interfaceAddress.getBroadcast() != null) {
						System.out.println("============================");
						broadcast.add(interfaceAddress.getBroadcast());
						System.out.println("Adding Broadcast : " + interfaceAddress.toString());
						self.add(interfaceAddress.getAddress());
						System.out.println("Adding Self : " + interfaceAddress.getAddress().toString());
					}
				}
			}
			System.out.println("============================");
		//=========================================
		} catch (IOException ex){
			System.out.println("Failed to get a broadcast address");
			System.exit(1);
		}
		//=========================================
		receiveThread = new Thread(new ReceiveThread());
		receiveThread.start();
		broadcast();
	}
	/**
     * Sends a string to all peers.
	 * @param in the string to send
     */
	public void send(String in){ 
		if(in.startsWith("SYSTEM_")){
			System.out.println("Blocked");
		} else {
			byte[] buf = in.getBytes(CHARSET);
			uSend(buf);
		}
	}
	//=========================================
	/*public void send(double[] in){ //buffer = 1472 double = 8 max doubles : 183 doubles
		if(double[].length > 183){
			System.out.println("Too many doubles");
			return;
		}
		List<Byte> out = new LinkedList();
		byte[] temp0 = new byte[8];
		
		long temp1;
		for(int i = 0 ; i < double[].length ; i++){
			temp1 = Double.doubleToLongBits(double[i]);
			for(int j = 0 ; j < 8 ; j++) out.add(new Byte((byte)((temp1 >> ((7 - i) * 8)) & 0xff)));
		}
	}
	//=========================================
	public void send(int[] in){
		
	}*/
	/**
     * Sends a raw byte[] to all peers
	 * @param in the byte[] to send to all peers
     */
	public void send(byte[] in){    //byte[].length must be less than or equal 1472
		if(new String(in , CHARSET).startsWith("SYSTEM_")){
			System.out.println("Blocked");
		} else {
			uSend(in);
		}
	}
	/**
     * This is private because it does not filter messages that start with
	 * SYSTEM_ which are special NetworkProtocol commands.
	 * @param in the byte[] to send to all peers
     */
	private void uSend(byte[] in){
		for(InetAddress address : peers){
			try {
				DatagramPacket packet = new DatagramPacket(in, in.length,address,PORT);
				socket.send(packet);
			} catch (UnknownHostException ex) {
			} catch (IOException ex){
				System.out.println("ioerror");
			}
		}
	}
	/**
     * Automatically called by receive thread
	 * @param packet the received packet
     */
	private void receive(DatagramPacket packet){
		String received = new String(packet.getData(), CHARSET);
		received = received.trim(); //removes leading and trailing whitespace
		//=========================================
		if(received.startsWith("SYSTEM_")){
			if(received.equals("SYSTEM_JOIN" + VERSION + channel)){
				if(!peers.contains(packet.getAddress()) && !self.contains(packet.getAddress())){
					System.out.println("Joining : " + packet.getAddress());
					peers.add(packet.getAddress());
					broadcast(packet.getAddress());
				}
				return;
			} else if (received.equals("SYSTEM_LEAVE" + VERSION + channel)){
				System.out.println("Removing : " + packet.getAddress());
				peers.remove(packet.getAddress());
				return;
			}
		} else {
			if (receiver != null){
				receiver.receive(received);
			}
		}		
	}
	/**
     * Disconnects and reconnects with ALL peers.
	 */
	public void refresh(){
		uSend(("SYSTEM_LEAVE" + VERSION + channel).getBytes(CHARSET));
		broadcast();
	}
	/**
     * Connects with all nearby peers. Do not call this,
	 * use refresh() instead
     */
	private void broadcast(){
		byte[] buf = ("SYSTEM_JOIN" + VERSION + channel).getBytes(CHARSET);
		try {
			for (InetAddress broadcast0 : broadcast){
				DatagramPacket packet = new DatagramPacket(buf, buf.length, broadcast0, PORT);
				socket.send(packet);
			}
		} catch (IOException ex){
			System.out.println("ioerror");
		}
	}
	/**
     * Works like broadcast, but only to one person. 
	 * @param target the peer to 'broadcast' to
     */
	private void broadcast(InetAddress target){
		byte[] buf = ("SYSTEM_JOIN" + VERSION + channel).getBytes(CHARSET);
		try {
			DatagramPacket packet = new DatagramPacket(buf, buf.length, target, PORT);
			socket.send(packet);
		} catch (IOException ex){
			System.out.println("ioerror");
		}
	}
	/**
     * Similar to addActionListener
	 * @param r the Receiver Listener thingy
     */
	public void addReceiver(Receiver r){
		receiver = r;
	}
	/**
     * Normally the port you choose will decide who you connect to
	 * but just in case there is this channel feature too
	 * @param in your desired channel
     */
	public void setChannel(String in){
		uSend(("SYSTEM_LEAVE" + VERSION + channel).getBytes(CHARSET));
		channel = in;
		broadcast();
		System.out.println("Set Channel to " + channel);
	}
	//=========================================
	private class ReceiveThread implements Runnable{
		DatagramPacket packet;
		byte[] buf;
		public void run(){
			while(true){
				buf = new byte[BUFFER_SIZE];
				packet = new DatagramPacket(buf, buf.length);
				try {
					socket.receive(packet);
				} catch (IOException ex){}
				if(Thread.interrupted()){
					System.out.println("Receive Thread Interrupted");
					return;
				}
				receive(packet);
			}
		}
	}
	/**
     * Please call this if before you decide to null NetworkProtocol
     */
	public void disconnect(){
		disconnectThread.start();
	}
	/**
     * something like 1/2 of refresh, without the reconnection.
     */
	private class Disconnector extends Thread{ //Disconnector is a shutdown hook that announces
		public void run(){                     //disconnection to all connections.
			receiveThread.interrupt();
			System.out.println("SENDING LEAVE");
			uSend(("SYSTEM_LEAVE" + VERSION + channel).getBytes(CHARSET));
			byte[] buf = new byte[] {0};
			try {
				DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName("127.0.0.1"), PORT);
				socket.send(packet);
				socket.close();
			} catch (IOException ex){}
		}
	}
}