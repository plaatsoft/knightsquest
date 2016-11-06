package nl.plaatsoft.knightsquest.tools;

import org.apache.log4j.Logger;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MySegment {

	final static Logger log = Logger.getLogger( MySegment.class);
	
	private MySegmentEnum type = MySegmentEnum.NONE;
	private int player;	
	private MyArmyEnum army;	
	private int x;	
	private int y;
	private int size;
    private MyImageView imageView;
    
	public void draw(GraphicsContext gc1, GraphicsContext gc2) {
					
		int offset = 0;
		if ((y % 2)==1) {
			offset = size*2;
		} 
		
		MyMap.getTexture(gc1, type);
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
			
			MyPlayers.getTexture(gc2, player);
			
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
	}
		
	public MySegment( int x, int y, MySegmentEnum type, int size) {
		
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

	public MyArmyEnum getArmy() {
		return army;
	}

	public void setArmy(MyArmyEnum army) {
		this.army = army;
		Image image=MyArmy.get(army);
		
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
	
	public MySegmentEnum getType() {
		return type;
	}

	public void setType(MySegmentEnum type) {
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
