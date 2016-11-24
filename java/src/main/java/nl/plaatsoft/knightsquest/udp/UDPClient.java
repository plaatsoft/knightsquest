package nl.plaatsoft.knightsquest.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class UDPClient {

	private final static SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");		
	private final static Logger log = Logger.getLogger(UDPClient.class);

	private DatagramSocket socket;	
	private InetAddress group;
	private TextArea area;

	private void show(DatagramPacket packet) {
		
		String data = new String(packet.getData());
		
		String text = formatter.format(new Date())+" TX: ["+packet.getAddress()+":"+packet.getPort()+"] "+data+"\n"+area.getText();
		area.setText(text);
	}

	
	public void init(TextArea area) {
		
		log.info("client started" );
		
		this.area = area;
						
		try {
								
			socket = new DatagramSocket();			
			log.info("socket ="+socket.getInetAddress());
			group = InetAddress.getByName("224.1.2.255");
			
		} catch (IOException e) {
			log.error(e.getMessage());
		}	
	}
	
	public void sent(String data) {
			
		try {
			byte[] sendData = data.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, group, 20000);
			Platform.runLater ( () -> show(sendPacket));
			
			socket.send(sendPacket);
						
		} catch (IOException e) {
			log.error(e.getMessage());
		}		
	}
	
	public void receive() {
		
		try {
			
			byte[] receiveData = new byte[1024];		
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);		
			socket.receive(receivePacket);
			String data = new String(receivePacket.getData());
			log.info("Receive " +receivePacket.getAddress()+" "+receivePacket.getPort()+" ["+data+"]");
			
		} catch (IOException e) {
			log.error(e.getMessage());
		}		
	}
	
	public void close() {
		log.info("client terminated" );
		socket.close();		
	}
}
