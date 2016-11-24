package nl.plaatsoft.knightsquest.udp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class UDPServer {

	final static Logger log = Logger.getLogger(UDPServer.class);
	private SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");	
	
	boolean stop = false;
	
	private void show(TextArea area, DatagramPacket packet) {
		
		String data = new String(packet.getData());
		
		String text = formatter.format(new Date())+" RX: ["+packet.getAddress()+":"+packet.getPort()+"] "+data+"\n"+area.getText();
		area.setText(text);
	}
	
	public void init(TextArea area ) throws Exception {
		
		log.info("server started" );
		
		MulticastSocket socket = new MulticastSocket(20000);
		InetAddress group = InetAddress.getByName("224.1.2.255");
		socket.joinGroup(group);
		
		byte[] receiveData = new byte[1024];
		//byte[] sendData = new byte[1024];
		
		while (!stop) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			socket.receive(receivePacket);			
			Platform.runLater ( () -> show(area, receivePacket));
			 			 
			//InetAddress IPAddress = receivePacket.getAddress();
			//int port = receivePacket.getPort();
			//String capitalizedSentence = data.toUpperCase();
			//sendData = capitalizedSentence.getBytes();
			//DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			//socket.send(sendPacket);
			//stop = true;
			//log.info("server terminated" );
		}
		
		socket.leaveGroup(group);
		socket.close();
	}
	
	public void terminate() {
		log.info("server terminated" );
		stop = true;
	}	
}
