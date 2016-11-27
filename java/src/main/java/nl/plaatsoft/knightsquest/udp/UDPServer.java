/**
 *  @file
 *  @brief 
 *  @author wplaat
 *
 *  Copyright (C) 2008-2016 PlaatSoft
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package nl.plaatsoft.knightsquest.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

import org.apache.log4j.Logger;
import org.json.JSONObject;

public class UDPServer {

	final static Logger log = Logger.getLogger(UDPServer.class);
	
	//private MulticastSocket socket;
	private DatagramSocket socket;
	private InetAddress group;
	
	public UDPServer(String address, int port) throws Exception {
		
		socket = new DatagramSocket(port);		
		//socket = new MulticastSocket(port);		
		socket.setSoTimeout(2000); 
		group = InetAddress.getByName(address);
		//socket.joinGroup(group);
	}

	private void show(boolean send, DatagramPacket packet) {
		String data = new String(packet.getData());

		String text = "";
		if (send) {
			text +=" TX:";
		} else {
			text +=" RX:";
		}
		text += " ["+packet.getAddress()+":"+packet.getPort()+"] "+data;
		log.info(text);	
	}
		
	public void sent(byte[] data) {
		
		try {
			DatagramPacket packet = new DatagramPacket(data, data.length, group, 20000);
			show(true, packet);
			socket.send(packet);
						
		} catch (IOException e) {
			log.error(e.getMessage());
		}		
	}
	
	public String receive() {
				
		String json = null;
		try {		
			byte[] receiveData = new byte[1024];			
			DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
			show(false, packet);
			
			socket.receive(packet);				
			json = new String(packet.getData(), StandardCharsets.UTF_8);				
				
		} catch (IOException e) {
			log.error(e.getMessage());			
		}			
		return json;
	}
	
	
	public String filter(String json, String id) {
		
		try {
			JSONObject obj = new JSONObject(json);
			String id2 = obj.getString("id");
			
			if (id.toString().equals(id2)) {			
				return null;
			}
			return json;
			
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}			
	}
	
	public void close() {
		socket.close();		
	}
}
