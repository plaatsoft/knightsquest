package nl.plaatsoft.knightquest.udp;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nl.plaatsoft.knightsquest.network.UDPServer;
import nl.plaatsoft.knightsquest.ui.Constants;

public class UDPServerTest {

	final static Logger log = Logger.getLogger(UDPServerTest.class);
	
	private String id;
	private UDPServer server;
	
	@Before
	public void before() {
		
		try {
			server = new UDPServer();
			server.init(Constants.APP_UDP_PORT);
		} catch (Exception e) {
			log.error(e.getMessage());			
		}
	}
	
	@Test
	public void pingTest() {
				
		server.ping();
		String json = server.receive();
		assertEquals("No message", true, json.length()>0);
	}
	
	@Test
	public void joinTest() {
				
		server.join(1,1);
		String json = server.receive();
		assertEquals("No message", true, json.length()>0);
	}
	
	@Test
	public void moveTest() {
				
		server.move(1, 2, 1, 2);
		String json = server.receive();
		assertEquals("No message", true, json.length()>0);
	}
	
	@Test
	public void pongTest() {
				
		server.pong();
		String json = server.receive();
		assertEquals("No message", true, json.length()>0);
	}
	
	@Test
	public void turnTest() {
				
		server.turn();
		String json = server.receive();
		assertEquals("No message", true, json.length()>0);
	}
	
	@Test
	public void filterTest() {
				
		server.turn();
		String json = server.receive();
		json = server.filter(json, id);
		assertEquals("Filter is not working", null, json);
	}	
	
	@After
	public void after() {
		
		server.close();
	}
}
