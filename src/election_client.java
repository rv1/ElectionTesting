
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rahul
 */
public class election_client {

    // Globals
    static final int gamePort = 3000;
    static final String gameGroup = "225.4.5.7";
    static InetAddress groupAddr;
    
    static Thread recieveThread;
    static int port = gamePort;
    
    static DatagramSocket socket = null;

    public static void main(String[] args) {
        try {

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;

            while (port == gamePort){
                socket = new DatagramSocket();
                port = socket.getLocalPort();
                socket.close();
            }
            System.out.println("Port for datagramSocket : "+ port);
            socket = new DatagramSocket(port);

            recieveThread = new Thread(new electionThreadHandler(gamePort, gameGroup));
            recieveThread.start();
            
            System.out.println("Created new DatagramSocket to send broadcasts on port"
                    + "\n\tport: " + port);

            while ((userInput = stdIn.readLine()) != null
                    && userInput.toLowerCase().indexOf("bye") == -1) {

                byte[] buf = new byte[1024];
                ElectionPacket packetToBroadcast = new ElectionPacket();
                packetToBroadcast.type = ElectionPacket.ELECTION_REQUEST;
                packetToBroadcast.message = userInput;

                buf = utils.serialize(packetToBroadcast);

                groupAddr = InetAddress.getByName(gameGroup);

                DatagramPacket packet = new DatagramPacket(buf, buf.length, groupAddr, gamePort);

                socket.send(packet);

            }
        } catch (IOException ex) {
            Logger.getLogger(election_client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
