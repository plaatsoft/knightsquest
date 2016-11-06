package nl.plaatsoft.knightsquest.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;
import nl.plaatsoft.knightsquest.tools.Constants;
import nl.plaatsoft.knightsquest.tools.SoldierUtils;

public class Castle {

	final static Logger log = Logger.getLogger( Castle.class);
	
	private int nr;	
	private int x;
	private int y;
	private List <Land> land = new ArrayList<Land>();

	public Castle(int nr, int x, int y) {
		this.nr = nr;
		this.x = x;
		this.y = y;
	}
	
	public void draw(GraphicsContext gc) {
			
		log.info("draw castle");
		gc.setGlobalAlpha(1.0);
		
		int offset = 0;
		if ((y % 2)==1) {
			offset = Constants.SEGMENT_SIZE*2;
		} 
				
		double posX = x*(Constants.SEGMENT_SIZE*4) + offset + 9;
		double posY = (y*Constants.SEGMENT_SIZE)+1;
		
		gc.drawImage(SoldierUtils.get(SoldierType.TOWER), posX, posY);
	}

	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
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

	public int getLandSize() {
		return land.size()-1;
	}
	
	public List<Land> getLand() {
		return land;
	}

	public void setLand(List<Land> land) {
		this.land = land;
	}
}
