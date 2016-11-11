package nl.plaatsoft.knightsquest.model;

import org.apache.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import nl.plaatsoft.knightsquest.tools.Constants;
import nl.plaatsoft.knightsquest.tools.LandUtils;
import nl.plaatsoft.knightsquest.tools.PlayerUtils;

public class Land {

	final static Logger log = Logger.getLogger( Land.class);
	
	private int x;
	private int y;
	private LandType type; 
	private Soldier soldier;
	private Player player;
	private int region;
		
	public Land(int x, int y, LandType type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public void draw(GraphicsContext gc) {
		
		//log.info("draw land ["+x+","+y+"]");
		
		int offset = 0;
		if ((y % 2)==1) {
			offset = Constants.SEGMENT_SIZE*2;
		} 
		
		LandUtils.getTexture(gc, type);
		
		gc.fillPolygon(
			new double[]{(x*(Constants.SEGMENT_SIZE*4))+offset, Constants.SEGMENT_SIZE+(x*(Constants.SEGMENT_SIZE*4))+offset,
					(Constants.SEGMENT_SIZE*2)+(x*(Constants.SEGMENT_SIZE*4))+offset, (Constants.SEGMENT_SIZE*3)+(x*(Constants.SEGMENT_SIZE*4))+offset, 
					(Constants.SEGMENT_SIZE*2)+(x*(Constants.SEGMENT_SIZE*4))+offset, Constants.SEGMENT_SIZE+(x*(Constants.SEGMENT_SIZE*4))+offset, 
					0+(x*(Constants.SEGMENT_SIZE*4))+offset}, 
			new double[]{Constants.SEGMENT_SIZE+(y*Constants.SEGMENT_SIZE), (y*Constants.SEGMENT_SIZE), (y*Constants.SEGMENT_SIZE), 
					Constants.SEGMENT_SIZE+(y*Constants.SEGMENT_SIZE), (Constants.SEGMENT_SIZE*2)+(y*Constants.SEGMENT_SIZE), (Constants.SEGMENT_SIZE*2)+(y*Constants.SEGMENT_SIZE), 
					Constants.SEGMENT_SIZE+(y*Constants.SEGMENT_SIZE)}, 7
			);
				
		gc.setFill(Color.BLACK);		
		gc.strokePolyline(
				new double[]{(x*(Constants.SEGMENT_SIZE*4))+offset, Constants.SEGMENT_SIZE+(x*(Constants.SEGMENT_SIZE*4))+offset,
						(Constants.SEGMENT_SIZE*2)+(x*(Constants.SEGMENT_SIZE*4))+offset, (Constants.SEGMENT_SIZE*3)+(x*(Constants.SEGMENT_SIZE*4))+offset, 
						(Constants.SEGMENT_SIZE*2)+(x*(Constants.SEGMENT_SIZE*4))+offset, Constants.SEGMENT_SIZE+(x*(Constants.SEGMENT_SIZE*4))+offset, 
						0+(x*(Constants.SEGMENT_SIZE*4))+offset}, 
				new double[]{Constants.SEGMENT_SIZE+(y*Constants.SEGMENT_SIZE), (y*Constants.SEGMENT_SIZE), (y*Constants.SEGMENT_SIZE), 
						Constants.SEGMENT_SIZE+(y*Constants.SEGMENT_SIZE), (Constants.SEGMENT_SIZE*2)+(y*Constants.SEGMENT_SIZE), (Constants.SEGMENT_SIZE*2)+(y*Constants.SEGMENT_SIZE), 
						Constants.SEGMENT_SIZE+(y*Constants.SEGMENT_SIZE)}, 7
		);	
		
		if (Constants.SEGMENT_SIZE>=20) {
			gc.setFill(Color.WHITE);
			gc.fillText("["+x+","+y+"]",(x*(Constants.SEGMENT_SIZE*4))+offset+15,Constants.SEGMENT_SIZE+(y*Constants.SEGMENT_SIZE));
		}
	}
		
	public void draw(GraphicsContext gc,Player player) {
		
		//log.info("draw land player ["+x+","+y+"]");
		
		gc.setGlobalAlpha(0.60);
		
		int offset = 0;
		if ((y % 2)==1) {
			offset = Constants.SEGMENT_SIZE*2;
		} 
		
		PlayerUtils.getTexture(gc, player.getId());
		
		gc.fillPolygon(
						new double[]{(x*(Constants.SEGMENT_SIZE*4))+offset, Constants.SEGMENT_SIZE+(x*(Constants.SEGMENT_SIZE*4))+offset,
								(Constants.SEGMENT_SIZE*2)+(x*(Constants.SEGMENT_SIZE*4))+offset, (Constants.SEGMENT_SIZE*3)+(x*(Constants.SEGMENT_SIZE*4))+offset, 
								(Constants.SEGMENT_SIZE*2)+(x*(Constants.SEGMENT_SIZE*4))+offset, Constants.SEGMENT_SIZE+(x*(Constants.SEGMENT_SIZE*4))+offset, 
								0+(x*(Constants.SEGMENT_SIZE*4))+offset}, 
						new double[]{Constants.SEGMENT_SIZE+(y*Constants.SEGMENT_SIZE), (y*Constants.SEGMENT_SIZE), (y*Constants.SEGMENT_SIZE), 
								Constants.SEGMENT_SIZE+(y*Constants.SEGMENT_SIZE), (Constants.SEGMENT_SIZE*2)+(y*Constants.SEGMENT_SIZE), (Constants.SEGMENT_SIZE*2)+(y*Constants.SEGMENT_SIZE), 
								Constants.SEGMENT_SIZE+(y*Constants.SEGMENT_SIZE)}, 7);	
	
		gc.setFill(Color.BLACK);
		
		gc.strokePolyline(
				new double[]{(x*(Constants.SEGMENT_SIZE*4))+offset, Constants.SEGMENT_SIZE+(x*(Constants.SEGMENT_SIZE*4))+offset,
						(Constants.SEGMENT_SIZE*2)+(x*(Constants.SEGMENT_SIZE*4))+offset, (Constants.SEGMENT_SIZE*3)+(x*(Constants.SEGMENT_SIZE*4))+offset, 
						(Constants.SEGMENT_SIZE*2)+(x*(Constants.SEGMENT_SIZE*4))+offset, Constants.SEGMENT_SIZE+(x*(Constants.SEGMENT_SIZE*4))+offset, 
						0+(x*(Constants.SEGMENT_SIZE*4))+offset}, 
				new double[]{Constants.SEGMENT_SIZE+(y*Constants.SEGMENT_SIZE), (y*Constants.SEGMENT_SIZE), (y*Constants.SEGMENT_SIZE), 
						Constants.SEGMENT_SIZE+(y*Constants.SEGMENT_SIZE), (Constants.SEGMENT_SIZE*2)+(y*Constants.SEGMENT_SIZE), (Constants.SEGMENT_SIZE*2)+(y*Constants.SEGMENT_SIZE), 
						Constants.SEGMENT_SIZE+(y*Constants.SEGMENT_SIZE)}, 7);	
		
		if (Constants.SEGMENT_SIZE>=20) {
			
			gc.setGlobalAlpha(1);
			gc.setFill(Color.WHITE);
			gc.fillText(""+region,(x*(Constants.SEGMENT_SIZE*4))+offset+25,Constants.SEGMENT_SIZE+(y*Constants.SEGMENT_SIZE)+15);
		}
		
		if (soldier!=null) {
			
			getSoldier().draw(gc, x, y);
		}
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
	
	public LandType getType() {
		return type;
	}
	
	public void setType(LandType type) {
		this.type = type;
	}
	
	public Soldier getSoldier() {
		return soldier;
	}
	
	public void setSoldier(Soldier soldier) {
		this.soldier = soldier;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getRegion() {
		return region;
	}

	public void setRegion(int region) {
		this.region = region;
	}	
}
