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
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.collections.ObservableList;
import nl.plaatsoft.knightsquest.network.CloudUser;
import nl.plaatsoft.knightsquest.ui.Constants;

public class UDPServer {

	final static Logger log = Logger.getLogger(UDPServer.class);
	
	private static UUID id = UUID.randomUUID();	
	private static MulticastSocket socket;
	private static InetAddress group;
	
	private static void show(boolean send, DatagramPacket packet) {
		if (packet!=null) {
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
	}
	
	public static void init() {
				
		log.info("server started" );
		
		try {
			socket = new MulticastSocket(20000);		
			socket.setSoTimeout(1000); 
			group = InetAddress.getByName("224.1.2.255");
			socket.joinGroup(group);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	
	public static void sent(byte[] data) {
		
		try {
			DatagramPacket packet = new DatagramPacket(data, data.length, group, 20000);
			show(true, packet);
			socket.send(packet);
						
		} catch (IOException e) {
			log.error(e.getMessage());
		}		
	}
	
	public static String receive() {
				
		String action="";
		while (true) {
			try {		
				byte[] receiveData = new byte[1024];			
				DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
			
				socket.receive(packet);
				
				String data = new String(packet.getData(), StandardCharsets.UTF_8);				
				action = decode(data);
				if (action.length()>0) {				
					show(false, packet);
				}
				
			} catch (IOException e) {
				log.error(e.getMessage());			
				break;
			}			
		}
		return action;
	}
	
	public static void terminate() {
		log.info("server terminated" );
		try {
			socket.leaveGroup(group);
			socket.close();
		} catch (IOException e) {
			log.error(e.getMessage());
		}	
	}	
		
	static private String decode(String data) {
		
		String action;
		
		try {
			JSONObject obj = new JSONObject(data);
			//String product = obj.getString("product");
			//String version = obj.getString("version");
			//Long timestamp = obj.getLong("timestamp");
			String id2 = obj.getString("id");	
			action =  obj.getString("action");
			String name =  obj.getString("name");
			if (action.equals("move")) {			
				int x1 = obj.getInt("x1");
				int y1 = obj.getInt("y1");
				int x2 = obj.getInt("x2");
				int y2 = obj.getInt("y2");
			}
			
			if (id.toString().equals(id2)) {
				// Do not log own broadcast messages;
				return "";
			}			
			
			if (action.equals("ping")) {
				UDPServer.sent(pong());
				return "";
			}
			
			if (action.equals("pong")) {
				return name;
			}
		} 
		catch (JSONException e) {
			log.error(e.getMessage());
		}
		return "";
	}
		
	static public JSONObject createHeader() {
		Date now = new Date();
		JSONObject msg = new JSONObject();
		try {
			msg.put("product", Constants.APP_NAME);
			msg.put("version", Constants.APP_VERSION);
			//msg.put("timestamp", now.getTime());
			msg.put("id", id); 
			msg.put("name", CloudUser.getNickname()); 
		}
		catch (JSONException e) {
			log.error(e.getMessage());
		} 
		return msg;		
	}
	
	static public byte[] ping() {
		JSONObject msg = createHeader();
		try {
			msg.put("action", "ping"); 
		} catch (JSONException e) {
			log.error(e.getMessage());
		} 
		return msg.toString().getBytes();		
	}
	
	static public byte[] pong() {
		JSONObject msg = createHeader();
		try {
			msg.put("action", "pong"); 
		} catch (JSONException e) {
			log.error(e.getMessage());
		} 
		return msg.toString().getBytes();	
	}
		
	static public byte[] join() {
		JSONObject msg = createHeader();
		try {
			msg.put("action", "join"); 
		} catch (JSONException e) {
			log.error(e.getMessage());
		} 
		return msg.toString().getBytes();
	}
	
	static public byte[] abort() {
		JSONObject msg = createHeader();
		try {
			msg.put("action", "abort"); 
		} catch (JSONException e) {
			log.error(e.getMessage());
		} 
		return msg.toString().getBytes();
	}
		
	static public byte[] move(int x1, int y1, int x2, int y2) {
		JSONObject msg = createHeader();
		try {
			msg.put("action", "move");
			msg.put("x1", x1); 
			msg.put("y1", y1); 
			msg.put("x2", x2); 
			msg.put("y2", y2); 
		} catch (JSONException e) {
			log.error(e.getMessage());
		} 
		return msg.toString().getBytes();
	}
	
	static public byte[] turn() {
		JSONObject msg = createHeader();
		try {
			msg.put("action", "turn"); 
		} catch (JSONException e) {
			log.error(e.getMessage());
		} 
		return msg.toString().getBytes();
	}	
	
	
}
