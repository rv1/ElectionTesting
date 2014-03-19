
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
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
public class electionThreadHandler implements Runnable {

    private static boolean isListening = true;
    static Thread recieveThread;
    private byte[] buf;
    private DatagramPacket packet;
    private MulticastSocket msocket;

    public electionThreadHandler(int port, String group) {
        try {
            msocket = new MulticastSocket(port);
            InetAddress groupAddr = InetAddress.getByName(group);
            msocket.joinGroup(groupAddr);
            System.out.println("Started electionThreadHandler\n"
                    + "\tgroup: " + group + "\n\tport: " + port);
        } catch (IOException ex) {
            Logger.getLogger(electionThreadHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (isListening) {
            try {
                //To effectively exit, you should do a broadcast after anyone exits.
                // get response
                buf = new byte[1024];
                packet = new DatagramPacket(buf, buf.length);
                msocket.receive(packet);

                buf = packet.getData();

                ElectionPacket packetFromServer = (ElectionPacket) utils.deserialize(buf);

                if (packetFromServer.type == ElectionPacket.ELECTION_REQUEST) {
                    System.out.println("echo by electionThreadHandler: " + packetFromServer.message);
                }
            } catch (IOException ex) {
                Logger.getLogger(electionThreadHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(electionThreadHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
