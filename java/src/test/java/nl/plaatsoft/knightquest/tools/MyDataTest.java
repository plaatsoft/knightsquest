package nl.plaatsoft.knightquest.tools;

import static org.junit.Assert.*;

import org.junit.Test;

import nl.plaatsoft.knightsquest.tools.MyData;

public class MyDataTest {

	@Test
	public void nextMapTest1() {
		
		assertEquals("Next map not right!", 2, MyData.getNextMap(1));
	}
		
	@Test
	public void nextMapTest2() {
		
		assertEquals("Next map not right!", 11, MyData.getNextMap(6));
	}
	
	@Test
	public void nextMapTest3() {
		
		assertEquals("Last map not right!", 0, MyData.getNextMap(96));
	}
		
}
