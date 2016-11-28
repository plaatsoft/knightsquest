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
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import nl.plaatsoft.knightsquest.network.CloudUser;
import nl.plaatsoft.knightsquest.ui.Constants;

public class UDPServer {

	final static Logger log = Logger.getLogger(UDPServer.class);
	
	private DatagramSocket socket = null;
	private InetAddress group = null;
	private String id = UUID.randomUUID().toString();
	
	private String getBroadcastAddress() throws SocketException {
		
		@SuppressWarnings("rawtypes")
		Enumeration e = NetworkInterface.getNetworkInterfaces();		
		while(e.hasMoreElements()) {
			NetworkInterface n = (NetworkInterface) e.nextElement();
			Iterator<InterfaceAddress> iter1 = n.getInterfaceAddresses().iterator();
			while (iter1.hasNext()) {
				InterfaceAddress address = (InterfaceAddress) iter1.next();
				String ip = address.getAddress().toString();
					
				int n1 = ip.indexOf(".", 4);
				if (n1>0) {			
					if (!ip.equals("/127.0.0.1") && (!ip.startsWith("/169.254"))) {
						return (address.getBroadcast().toString().substring(1));
					}
				}
			}
		}
		return null;
	}
	
	public void init(int port) {
		
		try {
			if (socket==null) {
				socket = new DatagramSocket(port);		
				socket.setSoTimeout(2000); 
				group = InetAddress.getByName(getBroadcastAddress());
			}			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	private void show(boolean send, DatagramPacket packet) {
		String data = new String(packet.getData());

		String text = "";
		if (send) {
			text +=" TX:";
		} else {
			text +=" RX:";
		}
		text += " ["+packet.getAddress().toString().substring(1)+":"+packet.getPort()+"] "+data;
		log.info(text);	
	}
		
	private void sent(byte[] data) {
		
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
	
	private JSONObject createHeader() throws JSONException {
		Date now = new Date();
		JSONObject msg = new JSONObject();
		
		msg.put("product", Constants.APP_NAME);
		msg.put("version", Constants.APP_VERSION);
		msg.put("timestamp", now.getTime());
		msg.put("id", id); 
		msg.put("name", CloudUser.getNickname()); 
		
		return msg;		
	}
	
	public void ping() {
		
		JSONObject msg = null; 
		
		try {
			msg = createHeader();
			msg.put("action", "ping"); 
		} catch (JSONException e) {
			log.error(e.getMessage());
		} 
		sent(msg.toString().getBytes());		
	}
	
	 public void pong() {
		 JSONObject msg = null; 
		try {
			msg = createHeader();
			msg.put("action", "pong"); 
		} catch (JSONException e) {
			log.error(e.getMessage());
		} 
		sent( msg.toString().getBytes());	
	}
		
	public void join(int map, int level) {
		JSONObject msg = null; 
		try {
			msg = createHeader();
			msg.put("action", "join");
			msg.put("map", map);
			msg.put("level", level); 
		} catch (JSONException e) {
			log.error(e.getMessage());
		} 
		sent(msg.toString().getBytes());
	}
	
	public void abort() {
		JSONObject msg = null; 
		try {
			msg = createHeader();
			msg.put("action", "abort"); 
		} catch (JSONException e) {
			log.error(e.getMessage());
		} 
		sent(msg.toString().getBytes());
	}
		
	public void move(int x1, int y1, int x2, int y2) {
		JSONObject msg = null; 
		try {
			msg = createHeader();
			msg.put("action", "move");
			msg.put("x1", x1); 
			msg.put("y1", y1); 
			msg.put("x2", x2); 
			msg.put("y2", y2); 
		} catch (JSONException e) {
			log.error(e.getMessage());
		} 
		sent(msg.toString().getBytes());
	}
	
	public void create(int x2, int y2) {
		JSONObject msg = null; 
		try {
			msg = createHeader();
			msg.put("action", "create");
			msg.put("x2", x2); 
			msg.put("y2", y2); 
		} catch (JSONException e) {
			log.error(e.getMessage());
		} 
		sent(msg.toString().getBytes());
	}
	
	public void turn() {
		JSONObject msg = null; 
		try {
			msg = createHeader();
			msg.put("action", "turn"); 
		} catch (JSONException e) {
			log.error(e.getMessage());
		} 
		sent(msg.toString().getBytes());
	}
	
}
