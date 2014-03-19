
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
    static Thread recieveThread;
    static int port;
    static String group;

    static DatagramSocket socket = null;

    public static void main(String[] args) {
        try {

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;

            if (args.length == 0) {
                args = new String[2];
                args[0] = "225.4.5.6";  //  group
                System.out.print("Enter Port : ");
                args[1] = stdIn.readLine();       //  port
            }

            port = Integer.parseInt(args[1]);
            group = args[0];

            //electionThreadHandler elc = new electionThreadHandler(port, group);
            recieveThread = new Thread(new electionThreadHandler(port, group));
            recieveThread.start();
            //System.out.println(port + args[0] + args[1] + "\n");

            //Taking care of broadcast socket now
            socket = new DatagramSocket(port);
            System.out.println("Created new DatagramSocket to send broadcasts on port"
                    + "\n\tport: " + port);

            while ((userInput = stdIn.readLine()) != null
                    && userInput.toLowerCase().indexOf("bye") == -1) {

                byte[] buf = new byte[1024];
                ElectionPacket packetToBroadcast = new ElectionPacket();
                packetToBroadcast.type = ElectionPacket.ELECTION_REQUEST;
                packetToBroadcast.message = userInput;

                buf = utils.serialize(packetToBroadcast);

                InetAddress groupAddr = InetAddress.getByName(group);

                DatagramPacket packet = new DatagramPacket(buf, buf.length, groupAddr, port);

                socket.send(packet);

            }
        } catch (IOException ex) {
            Logger.getLogger(election_client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
