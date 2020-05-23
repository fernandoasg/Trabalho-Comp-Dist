import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer{
     public static void main(String[] args){
          DatagramSocket aSocket = null;
          try{
               aSocket = new DatagramSocket(6789);
               byte[] buffer = new byte[10000];
               int contador = 0; // Contador de mensagens recebidas
               while(true){
                    DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                    aSocket.receive(request);
                    DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
                    System.out.println("Mensagem " +contador++ + " recebida do cliente cujo endereco eh: " + request.getAddress() + " pela porta: " + request.getPort());
                    String received = new String(reply.getData(), 0, reply.getLength());
                    System.out.printf("Conteudo: %s\n\n", received);
                    aSocket.send(reply);
               }
          }catch (SocketException e){
               System.out.println("Socket: " + e.getMessage());
          }catch (IOException e){
               System.out.println("IO: " + e.getMessage());
          }finally {
               if(aSocket != null)
                    aSocket.close();
          }
     }
}