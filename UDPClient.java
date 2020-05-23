import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class UDPClient {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // args give message contents and server hostname
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket();
            InetAddress aHost = InetAddress.getByName(args[0]); // Endereço de ip no servidor no segundo argumento

            int tamanhoMensagem = Integer.parseInt(args[1]); // Tamanho da mensagem em bytes que será gerada
            int repeticoes = Integer.parseInt(args[2]); // Número de vezes que a mensagem sera enviada

            int serverPort = 6789;
            for(int i = 0; i < repeticoes; i++){

                byte[] m = new byte[tamanhoMensagem]; // Cria o array de bytes
                SecureRandom.getInstanceStrong().nextBytes(m); // Gera mensagem aleatória

                DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
                aSocket.send(request);
                byte[] buffer = new byte[100];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(reply);
                System.out.println("Reply: " + new String(reply.getData()) + "\n");
            }
        } catch (SocketException e) {
            System.out.println("SocketException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            if (aSocket != null)
                aSocket.close();
        }
    }
}