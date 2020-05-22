import java.net.*;
import java.io.*;

public class UDPServer{
     public static void main(String[] args){
          DatagramSocket aSocket = null;
          try{
               aSocket = new DatagramSocket(6789);
               byte[] buffer = new byte[10000];

               System.out.println("Vou entrar no loop infinito");
               System.out.println(aSocket.getInetAddress());
               System.out.println(InetAddress.getLocalHost());
               while(true){
                    DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                    aSocket.receive(request);
                    DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
                    System.out.println("Mensagem recebida do cliente cujo endereco eh: " + request.getAddress() + " pela porta: " + request.getPort());
                    String received = new String(reply.getData(), 0, reply.getLength());
                    System.out.println("Conteudo: " + received);
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