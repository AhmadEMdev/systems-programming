/**
 * Done by Student: Ahmad Mohamed #21808164
 * CMPE 411
 */

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpReceiver {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(2024);

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String receivedData = new String(packet.getData(), 0, packet.getLength());

                System.out.println("Received Datagram from " + packet.getAddress().getHostAddress() +
                        " with data: " + receivedData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
