package nl.plaatsoft.knightsquest.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;

public class Player {

	final static Logger log = Logger.getLogger( Player.class);
	
	private int id;		
	private List <Castle> castle = new ArrayList<Castle>();
	
	public Player(int id) {
		super();
		this.id = id;
	}
	
	@Override
	public String toString() {
		return ""+id;
	}

	public void draw(GraphicsContext gc) {
			
		log.info("draw player [id="+id+"]");

		Iterator<Castle> iter1 = castle.iterator();  
		while (iter1.hasNext()) {
			Castle castle = (Castle) iter1.next();
			castle.draw(gc, this);
		}
	}
						
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public List<Castle> getCastle() {
		return castle;
	}
	
	public void setCastle(List<Castle> castle) {
		this.castle = castle;
	}	
}
