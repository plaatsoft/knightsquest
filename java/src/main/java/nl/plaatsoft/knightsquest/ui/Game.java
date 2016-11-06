package nl.plaatsoft.knightsquest.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import nl.plaatsoft.knightsquest.tools.Army;
import nl.plaatsoft.knightsquest.tools.Constants;
import nl.plaatsoft.knightsquest.tools.MyPanel;
import nl.plaatsoft.knightsquest.tools.Segment;
import nl.plaatsoft.knightsquest.tools.Types;
import nl.plaatsoft.knightsquest.tools.MyButton;
import nl.plaatsoft.knightsquest.tools.MyImageView;

public class Game extends MyPanel {

	final static Logger log = Logger.getLogger( Game.class);
	
	private GraphicsContext gc;
	private double offsetX = 0;
	private double offsetY = 0;
	private Canvas canvas;
	private Segment[][] segment = new Segment[Constants.SEGMENT_X][Constants.SEGMENT_Y]; 
	
	/**
	 * Get Neighers of select x,y coordinate
	 * @param x
	 * @param y
	 * @return
	 */
	private List <Segment> getNeigbors(int x, int y) {
		
		List <Segment> list = new <Segment> ArrayList();
		
		if (y+1<Constants.SEGMENT_Y) {
			list.add(segment[x][y+1]);
		}
		
		if (y-1>=0) {
			list.add(segment[x][y-1]);
		}
		
		if (y+2<Constants.SEGMENT_Y) {
			list.add(segment[x][y+2]);
		}
		
		if (y-2>=0) {
			list.add(segment[x][y-2]);
		}
		
		if (y%2==1) {	
			if ((x+1<Constants.SEGMENT_X) && (y+1<Constants.SEGMENT_Y)) {
				list.add(segment[x+1][y+1]);
			}
			
			if ((x+1<Constants.SEGMENT_X) && (y-1>=0)) {
				list.add(segment[x+1][y-1]);
			}						
		} else {
			
			if ((x-1>=0) && (y+1<Constants.SEGMENT_Y)) {
				list.add(segment[x-1][y+1]);
			}
			
			if ((x-1>=0) && (y-1>=0)) {
				list.add(segment[x-1][y-1]);
			}			
		}
		return list;
	}
	
	private void cleanup() {
				
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				if (segment[x][y].getType()==Types.COAST) {
					
					int found=0;
					List <Segment> list = getNeigbors(x,y);
					Iterator<Segment> iter = list.iterator();    	
					while (iter.hasNext()) {
						Segment segment = (Segment) iter.next();
						if (segment.getType()==Types.WATER) {
							found=1;
						}
					}				
					if (found==0) {
					 segment[x][y].setType(Types.GRASS);
					}
				};				
			}
		}		
	}

	private void createForestMountain() {
		
		Random rnd = new Random();
		
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				if ((segment[x][y].getType()==Types.GRASS) && (rnd.nextInt(2)==1)) {
					
					if (rnd.nextInt(2)==1) {
						segment[x][y].setType(Types.FOREST);
					} else {
						segment[x][y].setType(Types.MOUNTAIN);
					}
				};				
			}
		}		
	}


	private void createOcean() {
		
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				if (segment[x][y].getType()==Types.NONE) {					
					segment[x][y].setType(Types.OCEAN);
				};				
			}
		}		
	}

	private void createWater() {
		
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				if (segment[x][y].getType()==Types.COAST) {					
					List <Segment> list = getNeigbors(x,y);
					Iterator<Segment> iter = list.iterator();    	
					while (iter.hasNext()) {
						Segment segment = (Segment) iter.next();
						if (segment.getType()==Types.NONE) {
							segment.setType(Types.WATER);
						}
					}					
				};				
			}
		}		
	}

	private void createCoast() {
		
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				if (segment[x][y].getType()==Types.GRASS) {					
					List <Segment> list = getNeigbors(x,y);
					Iterator<Segment> iter = list.iterator();    	
					while (iter.hasNext()) {
						Segment segment = (Segment) iter.next();
						if (segment.getType()==Types.NONE) {
							segment.setType(Types.COAST);
						}
					}
					
				};				
			}
		}		
	}
	
	
	private void createGrass() {
		
		Random rnd = new Random();
				
		for (int i=0; i<=Constants.SEGMENT_X/2; i++) {
			
			int x = rnd.nextInt(Constants.SEGMENT_X-(Constants.BORDER/2));
			if ( x <(Constants.BORDER/2)) {
				x+=(Constants.BORDER/2);
			}
			int y = rnd.nextInt(Constants.SEGMENT_Y-(Constants.BORDER*2));
			if ( y<Constants.BORDER*2) {
				y+=Constants.BORDER*2;
			}

			for (int j=0; j<Constants.SEGMENT_Y; j++) {
				
				segment[x][y].setType(Types.GRASS);
		 
				List <Segment> list = getNeigbors(x,y);
				Iterator<Segment> iter = list.iterator();
						
				int next = rnd.nextInt(list.size()); 
				int count=0;
				
				while (iter.hasNext()) {				
					Segment segment = (Segment) iter.next();
					segment.setType(Types.GRASS);
					if (count++==next) {
						
						x=segment.getX();
						y=segment.getY(); 

						break;
					}					
				}
			}
		}
	}
	
	private void map() {
					
		for (int x=0; x<Constants.SEGMENT_X; x++) {
	
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				segment[x][y] = new Segment(x, y, Types.NONE, 0, Army.NONE, Constants.SEGMENT_SIZE);				
								
				if(segment[x][y].getImageView() !=null ) {					
					getChildren().add(segment[x][y].getImageView());
				}
			}
		}
		
		createGrass();
		createCoast();	
		createWater();
		createOcean();
		createForestMountain();
		cleanup();		
		
		for (int x=0; x<Constants.SEGMENT_X; x++) {					
			for (int y=0; y<Constants.SEGMENT_Y; y++) {				
				segment[x][y].draw(gc);				
			}
		}		
	}		
		
	public void draw() {
		
		setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
			
		canvas = new Canvas(Constants.MAP_WIDTH,Constants.MAP_HEIGHT);
		//canvas.setLayoutX(Constants.SEGMENT_SIZE*-1);
		//canvas.setLayoutY(Constants.SEGMENT_SIZE*-1);
		gc = canvas.getGraphicsContext2D();
		 				
		canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
		   		offsetX = me.getSceneX() - canvas.getLayoutX();
		   		offsetY = me.getSceneY() - canvas.getLayoutY();
		   		getScene().setCursor(Cursor.HAND);
		   	}
		});
				
		canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
	    	public void handle(MouseEvent me) {		 
	    		
	    		
	    		double tmpX = me.getSceneX() - offsetX;
	    		double tmpY = me.getSceneY() - offsetY;

	    		canvas.setLayoutX(tmpX);
	    		canvas.setLayoutY(tmpY);
	    		log.info(canvas.getLayoutX()+" "+canvas.getLayoutY());
	    		
	    		Iterator<Node> iter = getChildren().iterator();	    				
	    		while (iter.hasNext()) {	    			
	    			Node node = (Node) iter.next();
	    			if (node.getClass()==MyImageView.class) {
	    				MyImageView imageView = (MyImageView) node;
	    				imageView.move(canvas.getLayoutX(),canvas.getLayoutY());
	    			}
	    		}   		
	    	}
		});
				
		canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {		    	
				getScene().setCursor(Cursor.DEFAULT);
	    	}
		});
				
		getChildren().add(canvas);	    
	    map();
	    
	    MyButton btn = new MyButton(Constants.WIDTH-200, Constants.HEIGHT-60, "Map", 20, Navigator.NONE);
	    btn.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent event) {
            	map();
            }
        });
	   	    
		getChildren().add(btn);
	}
}
