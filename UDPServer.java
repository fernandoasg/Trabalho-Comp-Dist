import java.net.*;
import java.io.*;

public class UDPServer{
     public static void main(String[] args){
          DatagramSocket aSocket = null;
          try{
               aSocket = new DatagramSocket(6789);
               byte[] buffer = new byte[100];
               System.out.println("Vou entrar no loop infinito");
               System.out.println(aSocket.getInetAddress());
               System.out.println(InetAddress.getLocalHost());
               while(true){
                    DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                    aSocket.receive(request);
                    DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
                    if(reply != null)
                         System.out.println("Vou responder: " + request.getAddress());
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