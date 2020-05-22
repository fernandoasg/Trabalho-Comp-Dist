import java.net.*;
import java.io.*;

public class UDPClient{
	public static void main(String[] args){
		// args give message contents and server hostname
		DatagramSocket aSocket = null;
		try{
			aSocket = new DatagramSocket();
			byte[] m = args[0].getBytes();
			InetAddress aHost = InetAddress.getByName(args[1]);
			int quantidade = Integer.parseInt(args[2]);
			int tamanhoBuffer = Integer.parseInt(args[3]);
			int serverPort = 6789;
			DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
			for (int i = 0; i < quantidade; i++) {
				System.out.print(i +"º request ");
				aSocket.send(request);
				byte[] buffer = new byte[tamanhoBuffer];
				DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(reply);
				System.out.println("Reply: " + new String(reply.getData()) +"\n");
			}
		}catch (SocketException e){
			System.out.println("SocketException: " + e.getMessage());
		}catch (IOException e){
			System.out.println("IOException: " + e.getMessage());
		}finally {
			if(aSocket != null)
				aSocket.close();
		}
	}
}