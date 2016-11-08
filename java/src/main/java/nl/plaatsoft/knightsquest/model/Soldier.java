package nl.plaatsoft.knightsquest.model;

import org.apache.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;
import nl.plaatsoft.knightsquest.tools.Constants;
import nl.plaatsoft.knightsquest.tools.SoldierUtils;

public class Soldier {

	final static Logger log = Logger.getLogger( Soldier.class);
	
	private SoldierType type;
	private boolean moved = false;
	private Player player;

	public Soldier(SoldierType type, Player player) {

		this.type = type;
		this.player = player;
	}
	
	public void draw(GraphicsContext gc, int x, int y) {
		
		//log.info("draw "+type+" [x="+x+"|y="+y+"]");
		
		gc.setGlobalAlpha(1.0);
			
		int offset = 0;
		if ((y % 2)==1) {
			offset = Constants.SEGMENT_SIZE*2;
		} 
			
		double posX = x*(Constants.SEGMENT_SIZE*4) + offset + 9;
		double posY = (y*Constants.SEGMENT_SIZE)+1;
	
		gc.drawImage(SoldierUtils.get(type), posX, posY);
	}
		
	public SoldierType getType() {
		return type;
	}

	public void setType(SoldierType type) {
		this.type = type;
	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
