package nl.plaatsoft.knightsquest.ui;

import java.util.Iterator;
import java.util.Random;

import org.apache.log4j.Logger;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import nl.plaatsoft.knightsquest.tools.Constants;
import nl.plaatsoft.knightsquest.tools.MyPanel;
import nl.plaatsoft.knightsquest.tools.Segment;
import nl.plaatsoft.knightsquest.tools.MyButton;
import nl.plaatsoft.knightsquest.tools.MyImageView;

public class Game extends MyPanel {

	final static Logger log = Logger.getLogger( Game.class);
	
	private GraphicsContext gc;
	private double offsetX = 0;
	private double offsetY = 0;
	private Canvas canvas;
	
	private void map( GraphicsContext gc) {
		
		Random rnd = new Random();
			
		for (int x=0; x<Constants.SEGMENT_X; x++) {
		
			Segment segment;
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
				
				segment = new Segment(x, y, rnd.nextInt(4), rnd.nextInt(8), rnd.nextInt(10), Constants.SEGMENT_SIZE);
				segment.draw(gc);

				if(segment.getImageView() !=null ) {					
					getChildren().add(segment.getImageView());
				}
			}
		}
	}		
		
	public void draw() {
		
		canvas = new Canvas(Constants.MAP_WIDTH,Constants.MAP_HEIGHT);
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
	    		canvas.setLayoutX(me.getSceneX()-offsetX);
	    		canvas.setLayoutY(me.getSceneY()-offsetY);
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
	    map(gc);		    
		getChildren().add(new MyButton(Constants.WIDTH-200, Constants.HEIGHT-60, "Turn", 20, Navigator.EXIT));
	}
}
