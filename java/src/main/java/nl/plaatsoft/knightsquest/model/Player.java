package nl.plaatsoft.knightsquest.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;

public class Player {

	final static Logger log = Logger.getLogger( Player.class);
	
	private int id;		
	private List <Region> region = new ArrayList<Region>();
	
	public Player(int id) {
		super();
		this.id = id;
	}
	
	@Override
	public String toString() {
		return ""+id;
	}

	public void draw(GraphicsContext gc) {			
		Iterator<Region> iter = region.iterator();  
		while (iter.hasNext()) {
			Region region = (Region) iter.next();
			region.draw(gc, this);
		}
	}
						
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public List<Region> getRegion() {
		return region;
	}
	
	public void setRegion(List<Region> region) {
		this.region = region;
	}	
}
