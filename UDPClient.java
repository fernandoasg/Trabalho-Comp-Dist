import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class UDPClient {

    private static Random rand = new Random();
    private static char[] letras = "0123456789abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public static String stringAleatoria(int tamanho) {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tamanho; i++) {
            int ch = rand.nextInt (letras.length);
            sb.append (letras [ch]);
        }    
        return sb.toString();    
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        // args give message contents and server hostname
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket();
            InetAddress aHost = InetAddress.getByName(args[0]); // Endereço de ip no servidor no segundo argumento

            int tamanhoMensagem = Integer.parseInt(args[1]); // Tamanho da mensagem em bytes que será gerada
            int repeticoes = Integer.parseInt(args[2]); // Número de vezes que a mensagem sera enviada

            byte[] m = new byte[tamanhoMensagem]; // Cria o array de bytes
            m = stringAleatoria(tamanhoMensagem).getBytes(); // Gera mensagem aleatória

            int serverPort = 6789;
            Instant start = Instant.now();
            for(int i = 0; i < repeticoes; i++){
                DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
                aSocket.send(request);
                byte[] buffer = new byte[100];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(reply);
                System.out.println(i + " Reply: " + new String(reply.getData()) + "\n");
            }
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            System.out.println("Time Elapsed: " + timeElapsed);
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