package nl.plaatsoft.knightsquest.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.log4j.Logger;

public class UDPClient {

	final static Logger log = Logger.getLogger(UDPClient.class);
	
	public void init(String message) throws Exception {
		
		log.info("client started" );
		
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress group = InetAddress.getByName("224.1.2.3");
		byte[] sendData = message.getBytes();
		byte[] receiveData = new byte[1024];
	
		
		for (int i=0; i<10; i++) {
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, group, 19876);
			log.info("client sent message" );
			clientSocket.send(sendPacket);
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			String modifiedSentence = new String(receivePacket.getData());
			log.info("FROM SERVER:" + modifiedSentence);
		}
		clientSocket.close();
		log.info("client terminated" );
	}
}
