package nl.plaatsoft.knightsquest.tools;

import org.apache.log4j.Logger;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import nl.plaatsoft.knightsquest.model.LandType;
import nl.plaatsoft.knightsquest.model.SoldierType;

public class MySegment {

	final static Logger log = Logger.getLogger( MySegment.class);
	
	private LandType type = LandType.NONE;
	private int player;	
	private SoldierType army = SoldierType.NONE;	
	private int x;	
	private int y;
	private int size;
    private MyImageView imageView;
    
	public void draw(GraphicsContext gc1, GraphicsContext gc2) {
					
		int offset = 0;
		if ((y % 2)==1) {
			offset = size*2;
		} 
		
		LandUtils.getTexture(gc1, type);
		gc1.fillPolygon(
			new double[]{0+(x*(size*4))+offset, size+(x*(size*4))+offset, (size*2)+(x*(size*4))+offset, (size*3)+(x*(size*4))+offset, (size*2)+(x*(size*4))+offset, size+(x*(size*4))+offset, 0+(x*(size*4))+offset}, 
			new double[]{size+(y*size), (y*size), (y*size), size+(y*size), (size*2)+(y*size), (size*2)+(y*size), size+(y*size)}, 7
			);
				
		gc1.setFill(Color.BLACK);		
		gc1.strokePolyline(
			new double[]{0+(x*(size*4))+offset, size+(x*(size*4))+offset, (size*2)+(x*(size*4))+offset, (size*3)+(x*(size*4))+offset, (size*2)+(x*(size*4))+offset, size+(x*(size*4))+offset, 0+(x*(size*4))+offset}, 
			new double[]{size+(y*size), (y*size), (y*size), size+(y*size), (size*2)+(y*size), (size*2)+(y*size), size+(y*size)}, 7
			);		
	
		if (player>0) {
			
			PlayerUtils.getTexture(gc2, player);
			
			gc2.setGlobalAlpha(0.7);
			gc2.fillPolygon(
					new double[]{0+(x*(size*4))+offset, size+(x*(size*4))+offset, (size*2)+(x*(size*4))+offset, (size*3)+(x*(size*4))+offset, (size*2)+(x*(size*4))+offset, size+(x*(size*4))+offset, 0+(x*(size*4))+offset}, 
					new double[]{size+(y*size), (y*size), (y*size), size+(y*size), (size*2)+(y*size), (size*2)+(y*size), size+(y*size)}, 7);
			
			gc2.setFill(Color.BLACK);		
			gc2.strokePolyline(
				new double[]{0+(x*(size*4))+offset, size+(x*(size*4))+offset, (size*2)+(x*(size*4))+offset, (size*3)+(x*(size*4))+offset, (size*2)+(x*(size*4))+offset, size+(x*(size*4))+offset, 0+(x*(size*4))+offset}, 
				new double[]{size+(y*size), (y*size), (y*size), size+(y*size), (size*2)+(y*size), (size*2)+(y*size), size+(y*size)}, 7
				);		
		}
		
		if (army!=SoldierType.NONE) {
			
			gc2.setGlobalAlpha(1.0);
			double posX = x*(size*4) + offset+9;
			double posY = (y*size)+1;
						
			gc2.drawImage(SoldierUtils.get(army), posX, posY);
		}
	}
		
	public MySegment( int x, int y, LandType type, int size) {
		
		this.x = x;
		this.y = y;
		this.type = type;
		this.size = size;
	}
	
	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public SoldierType getArmy() {
		return army;
	}

	public void setArmy(SoldierType army) {
		this.army = army;
		Image image=SoldierUtils.get(army);
		
		if (image!=null) {	
			
			int offset = 0;
			if ((y % 2)==1) {
				offset = size*2;
			} 
			
			double scale = Constants.SEGMENT_SCALE;
			double posX = x*(size*4) + offset - (size*1.7);
			double posY = (y*size) - size*2;
			
			imageView = new MyImageView(posX, posY, image, scale);
												
			imageView.setOnMousePressed(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent me) {
					log.info("Pressed x="+x+" y="+y+" type="+type+" player="+player);		
				}
			});
					
			imageView.setOnMouseDragged(new EventHandler<MouseEvent>() {
		    		public void handle(MouseEvent me) {		    	
		    			log.info("Dragged x="+x+" y="+y+" type="+type+" player="+player);			
		    		}
			});
			
			imageView.setOnMouseReleased(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent me) {		    	
		    			log.info("Released x="+x+" y="+y+" type="+type+" player="+player);				
		    		}
			});
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
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public ImageView getImageView() {
		return imageView;
	}	
}
