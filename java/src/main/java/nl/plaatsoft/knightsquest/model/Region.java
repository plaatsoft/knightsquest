package nl.plaatsoft.knightsquest.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;
import nl.plaatsoft.knightsquest.tools.SoldierUtils;

public class Region {

	final static Logger log = Logger.getLogger( Region.class);
	
	private int id;	
	private Player player;
	private List <Land> lands = new ArrayList<Land>();

	public Region(int id, Player player) {
		this.id = id;
		this.player = player;
	}
	
	public void draw(GraphicsContext gc, Player player) {
			
		//log.info("draw region [id="+id+"|landSize="+lands.size()+"]");
		
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
		
		//log.info("regionId="+id+" foodDemand="+foodDemand+" foodProduction="+lands.size()+" foodAvailable="+foodAvailable);
		
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
	
	public void setLands(List<Land> lands) {
		this.lands = lands;
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
