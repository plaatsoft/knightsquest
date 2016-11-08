package nl.plaatsoft.knightsquest.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;
import nl.plaatsoft.knightsquest.tools.SoldierUtils;

public class Castle {

	final static Logger log = Logger.getLogger( Castle.class);
	
	private int id;	
	private int x;
	private int y;
	private Player player;
	private List <Land> lands = new ArrayList<Land>();

	public Castle(int id, int x, int y, Player player) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.player = player;
	}
	
	public void draw(GraphicsContext gc, Player player) {
			
		log.info("draw castle [id="+id+"|landSize="+lands.size()+"]");
		
		Iterator<Land> iter1 = lands.iterator();  
		while (iter1.hasNext()) {
			Land land = (Land) iter1.next();
			land.draw(gc, player);
		}
	}
	
	public int foodAvailable() {
		
		int foodDemand = 0;
		
		Iterator<Land> iter = lands.iterator();						
		while (iter.hasNext()) {				
			Land land = (Land) iter.next();
			if (land.getSoldier()!=null) {					
				foodDemand += SoldierUtils.getFoodNeeds(land.getSoldier().getType());
			}		
		}	
				
		int foodAvailable = lands.size()- foodDemand;	
		
		//log.info("foodDemand="+foodDemand+" foodProduction="+lands.size()+" foodAvailable="+foodAvailable);
		
		return foodAvailable;
	}
	
	public boolean checkNewLand(Land newLand) {

		boolean value = true;
		
		Iterator<Land> iter = lands.iterator();						
		while (iter.hasNext()) {				
			Land land = (Land) iter.next();
			if (land.equals(newLand)) {						
				value = false;
			}		
		}	
		
		//log.info("checkNewLand="+value);
		return value;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public List<Land> getLands() {
		return lands;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
