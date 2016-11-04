package nl.plaatsoft.knightsquest.tools;

import org.apache.log4j.Logger;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Segment {

	final static Logger log = Logger.getLogger( Segment.class);
	
	private int type;
	private int player;	
	private int knight;	
	private int x;	
	private int y;
	private int size;
    private MyImageView imageView;
    
	public void draw(GraphicsContext gc) {
			
		gc.setFill(Color.WHITE);
		
		int offset = 0;
		if ((y % 2)==1) {
			offset = size*2;
		} 
		
		if (player>0){
			
			switch(player) {
				
				case 1: // Player 1
						gc.setFill(Color.YELLOW);
						break;
				
				case 2: // Player 2
						gc.setFill(Color.RED);
						break;
			}	
			
			gc.fillPolygon(
					new double[]{0+(x*(size*4))+offset, size+(x*(size*4))+offset, (size*2)+(x*(size*4))+offset, (size*3)+(x*(size*4))+offset, (size*2)+(x*(size*4))+offset, size+(x*(size*4))+offset, 0+(x*(size*4))+offset}, 
					new double[]{size+(y*size), (y*size), (y*size), size+(y*size), (size*2)+(y*size), (size*2)+(y*size), size+(y*size)}, 7);
			
		} else {
			
			switch(type) {
				case 1: // Forest
						gc.setFill(Color.GREEN);
						break;	
						
				case 2: // Sea
						gc.setFill(Color.BLUE);
						break;	
						
				case 3: // Montain
						gc.setFill(Color.GRAY);
						break;	
			}
			
			gc.fillPolygon(
					new double[]{0+(x*(size*4))+offset, size+(x*(size*4))+offset, (size*2)+(x*(size*4))+offset, (size*3)+(x*(size*4))+offset, (size*2)+(x*(size*4))+offset, size+(x*(size*4))+offset, 0+(x*(size*4))+offset}, 
					new double[]{size+(y*size), (y*size), (y*size), size+(y*size), (size*2)+(y*size), (size*2)+(y*size), size+(y*size)}, 7);
		}
						
		gc.setFill(Color.BLACK);
		
		gc.strokePolyline(
			new double[]{0+(x*(size*4))+offset, size+(x*(size*4))+offset, (size*2)+(x*(size*4))+offset, (size*3)+(x*(size*4))+offset, (size*2)+(x*(size*4))+offset, size+(x*(size*4))+offset, 0+(x*(size*4))+offset}, 
			new double[]{size+(y*size), (y*size), (y*size), size+(y*size), (size*2)+(y*size), (size*2)+(y*size), size+(y*size)}, 7);
				
		if (player>0) {
			Image image=null;
			switch (knight) {
			
				case 1:	image= Pieces.getHorse();
						break;
						
				case 2: image = Pieces.getTower();
						break;
			}		
		
			if (image!=null) {	
				
				double scale = Constants.SEGMENT_SCALE;
				double posX = x*(size*4)+offset+(size*scale);
				double posY = (y*size)+(size/2);
				
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
			
				/*imageView.setOnScroll((ScrollEvent event) -> {
					// 	Adjust the zoom factor as per your requirement
					double zoomFactor = 1.05;
					double deltaY = event.getDeltaY();
					if (deltaY < 0){
						zoomFactor = 2.0 - zoomFactor;
					}
					imageView.setScaleX(imageView.getScaleX() * zoomFactor);
					imageView.setScaleY(imageView.getScaleY() * zoomFactor);
				});*/
			}
		}
		
	public Segment( int x, int y, int type, int player, int knight, int size) {
		
		this.x = x;
		this.y = y;
		this.type = type;
		this.player = player;
		this.knight = knight;
		this.size = size;
	}
	
	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public int getKnight() {
		return knight;
	}


	public void setKnight(int knight) {
		this.knight = knight;
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
	
	public int getType() {
		return type;
	}


	public void setType(int type) {
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
