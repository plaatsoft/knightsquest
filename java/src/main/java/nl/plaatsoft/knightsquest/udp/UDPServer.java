package nl.plaatsoft.knightsquest.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.apache.log4j.Logger;

public class UDPServer {

	final static Logger log = Logger.getLogger(UDPServer.class);
	
	boolean stop = false;
	
	public void init() throws Exception {
		
		log.info("server started" );
		
		MulticastSocket socket = new MulticastSocket(19876);
		InetAddress group = InetAddress.getByName("224.1.2.3");
		socket.joinGroup(group);
		
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		
		while (!stop) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			socket.receive(receivePacket);
			String sentence = new String(receivePacket.getData());
			log.info("RECEIVED: " + sentence);
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			String capitalizedSentence = sentence.toUpperCase();
			sendData = capitalizedSentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			socket.send(sendPacket);
			stop = true;
			log.info("server terminated" );
		}
		socket.close();
	}
	
	public void terminate() {
		log.info("server terminated" );
		stop = true;
	}	
}
