package nl.plaatsoft.knightsquest.udp;

import java.util.Date;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import nl.plaatsoft.knightsquest.network.CloudUser;
import nl.plaatsoft.knightsquest.ui.Constants;

public class UDPMessages {
	
	final static Logger log = Logger.getLogger(UDPMessages.class);
	
	static public JSONObject createHeader(String id) {
		Date now = new Date();
		JSONObject msg = new JSONObject();
		try {
			msg.put("product", Constants.APP_NAME);
			msg.put("version", Constants.APP_VERSION);
			msg.put("timestamp", now.getTime());
			msg.put("id", id); 
			msg.put("name", CloudUser.getNickname()); 
		}
		catch (JSONException e) {
			log.error(e.getMessage());
		} 
		return msg;		
	}
	
	static public byte[] ping(String id) {
		JSONObject msg = createHeader(id);
		try {
			msg.put("action", "ping"); 
		} catch (JSONException e) {
			log.error(e.getMessage());
		} 
		return msg.toString().getBytes();		
	}
	
	static public byte[] pong(String id) {
		JSONObject msg = createHeader(id);
		try {
			msg.put("action", "pong"); 
		} catch (JSONException e) {
			log.error(e.getMessage());
		} 
		return msg.toString().getBytes();	
	}
		
	static public byte[] join(String id, int map, int level) {
		JSONObject msg = createHeader(id);
		try {
			msg.put("action", "join");
			msg.put("map", map);
			msg.put("level", level); 
		} catch (JSONException e) {
			log.error(e.getMessage());
		} 
		return msg.toString().getBytes();
	}
	
	static public byte[] abort(String id) {
		JSONObject msg = createHeader(id);
		try {
			msg.put("action", "abort"); 
		} catch (JSONException e) {
			log.error(e.getMessage());
		} 
		return msg.toString().getBytes();
	}
		
	static public byte[] move(String id, int x1, int y1, int x2, int y2) {
		JSONObject msg = createHeader(id);
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
	
	static public byte[] turn(String id) {
		JSONObject msg = createHeader(id);
		try {
			msg.put("action", "turn"); 
		} catch (JSONException e) {
			log.error(e.getMessage());
		} 
		return msg.toString().getBytes();
	}
}
