package nl.plaatsoft.knightquest.udp;

import static org.junit.Assert.*;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nl.plaatsoft.knightsquest.udp.UDPMessages;
import nl.plaatsoft.knightsquest.udp.UDPServer;

public class UDPServerTest {

	final static Logger log = Logger.getLogger(UDPServerTest.class);
	
	private String id;
	private UDPServer server;
	
	@Before
	public void before() {
		
		id = UUID.randomUUID().toString();		
		try {
			server = new UDPServer("192.168.2.255", 20000);
		} catch (Exception e) {
			log.error(e.getMessage());			
		}
	}
	
	@Test
	public void pingTest() {
				
		server.sent(UDPMessages.ping(id));
		String json = server.receive();
		assertEquals("No message", true, json.length()>0);
	}
	
	@Test
	public void joinTest() {
				
		server.sent(UDPMessages.join(id));
		String json = server.receive();
		assertEquals("No message", true, json.length()>0);
	}
	
	@Test
	public void moveTest() {
				
		server.sent(UDPMessages.move(id, 1, 2, 1, 2));
		String json = server.receive();
		assertEquals("No message", true, json.length()>0);
	}
	
	@Test
	public void pongTest() {
				
		server.sent(UDPMessages.pong(id));
		String json = server.receive();
		assertEquals("No message", true, json.length()>0);
	}
	
	@Test
	public void turnTest() {
				
		server.sent(UDPMessages.turn(id));
		String json = server.receive();
		assertEquals("No message", true, json.length()>0);
	}
	
	@Test
	public void filterTest() {
				
		server.sent(UDPMessages.turn(id));
		String json = server.receive();
		json = server.filter(json, id);
		assertEquals("Filter is not working", null, json);
	}	
	
	@After
	public void after() {
		
		server.close();
	}
}
