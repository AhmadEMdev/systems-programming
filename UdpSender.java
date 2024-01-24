/**
 * Done by Student: Ahmad Mohamed #21808164
 * CMPE 411
 */
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpSender {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();

            InetAddress receiverAddress = InetAddress.getByName("100.100.100.100");
            //DESKTOP-9E392SM/192.168.3.113
//            InetAddress receiverAddress = InetAddress.getByName("192.168.3.113");
            int port = 2024;

//            byte[] data = InetAddress.getLocalHost().getHostAddress().getBytes();
//            byte[] data = InetAddress.getLocalHost().getHostName().getBytes();
            String data = InetAddress.getLocalHost().getHostAddress();



            long endTime = System.currentTimeMillis() + 60 * 1000; // Run for approximately 1 minute

            while (System.currentTimeMillis() < endTime) {
                //DatagramPacket packet = new DatagramPacket(data, data.length, receiverAddress, port);
                DatagramPacket packet = new DatagramPacket(data.getBytes(), data.length(), receiverAddress, port);
                socket.send(packet);

                System.out.println("Sent Datagram to " + receiverAddress.getHostAddress());

                Thread.sleep(5000); // Wait for 5 seconds before sending the next packet
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
