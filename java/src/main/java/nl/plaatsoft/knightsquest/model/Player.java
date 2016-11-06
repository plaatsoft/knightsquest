package nl.plaatsoft.knightsquest.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import nl.plaatsoft.knightsquest.tools.Constants;
import nl.plaatsoft.knightsquest.tools.PlayerUtils;

public class Player {

	final static Logger log = Logger.getLogger( Player.class);
	
	private String name;
	private int number;		
	private List <Castle> castle = new ArrayList<Castle>();
	
	public Player(String name, int number) {
		super();
		this.name = name;
		this.number = number;
	}
	
	public void draw(GraphicsContext gc) {
			
		log.info("draw player");
		
		gc.setGlobalAlpha(0.7);
		
		Iterator<Castle> iter1 = castle.iterator();  
		while (iter1.hasNext()) {
			Castle castle = (Castle) iter1.next();
						
			Iterator<Land> iter2 =  castle.getLand().iterator();  
			while (iter2.hasNext()) {
				Land land = (Land) iter2.next();
				
				int x = land.getX();
				int y = land.getY();
				
				int offset = 0;
				if ((y % 2)==1) {
					offset = Constants.SEGMENT_SIZE*2;
				} 
					
				PlayerUtils.getTexture(gc, number);
				
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
				
				if (land.getSoldier()!=null) {
					land.getSoldier().draw(gc, x, y);
				}
			}
			
			castle.draw(gc);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public List<Castle> getCastle() {
		return castle;
	}
	
	public void setCastle(List<Castle> castle) {
		this.castle = castle;
	}	
}
