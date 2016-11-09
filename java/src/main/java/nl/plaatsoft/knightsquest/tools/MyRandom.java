package nl.plaatsoft.knightsquest.tools;

import java.util.List;
import java.util.Random;

import nl.plaatsoft.knightsquest.model.Land;

public class MyRandom {

	private static Random rnd = new Random();
	
	public static int nextInt(int value) {
		return rnd.nextInt(value);
	}
	
	public static Land nextLand(List<Land> list) { 
		
		Land land = null;
		
		if (list.size()>0) {
			land = list.get(rnd.nextInt(list.size()));
		}
		return land;
	}
}
