package nl.plaatsoft.knightsquest.ui;

import java.util.Iterator;

import org.apache.log4j.Logger;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import nl.plaatsoft.knightsquest.tools.Constants;

public class Navigator {
		
	final static Logger log = Logger.getLogger( Navigator.class);
	
	private static Intro1 intro1;
	private static Intro2 intro2;
	private static Game game;	
	
	private static Scene scene;	
			
	public static final int NONE = 1;
	public static final int INTRO1 = 2;
	public static final int INTRO2 = 3;
	public static final int GAME = 4;
	public static final int EXIT = 5;
			
	public static Scene getScene() {
		return scene;
	}
	
	//handles mouse scrolling
	private static void setSceneEvents(final Scene scene, Pane page) {	    
	    scene.setOnScroll(
	            new EventHandler<ScrollEvent>() {
	              @Override
	              public void handle(ScrollEvent event) {
	                double zoomFactor = 1.10;
	                double deltaY = event.getDeltaY();
	                if (deltaY < 0){
	                  zoomFactor = 2.0 - zoomFactor;
	                }
	                	               	               
	               Iterator <Node> iter =  page.getChildren().iterator();			
	    		   while(iter.hasNext()) {		
	    			 	    
	    				Node node = (Node) iter.next();
	    				if(node instanceof Pane){
	    			         Pane pane = (Pane) node;
	    			         if (pane.getId().equals("map")) {
	    			        	 
	    			        	 double scale = pane.getScaleX() * zoomFactor;
	    			        	 
	    			        	 //if (scale>(Constants.SCALE-0.05)) 
	    			        	 {	                	
	    			              	pane.setScaleX(scale);
	    			               	pane.setScaleY(scale);
	    			               	event.consume();
	    			             }
	    			         }
	    			    }      
	    		   }
	           }
	      });
	  }
	
	public static void go(int page) {
				
	  switch (page ) {

		case INTRO1:
			intro1 = new Intro1();
			intro1.draw();
			scene = new Scene(intro1, Constants.WIDTH, Constants.HEIGHT);	
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			    public void handle(KeyEvent key) {
			    	Navigator.go(Navigator.INTRO2);			
			    }
			});						
			break;
		
		case INTRO2:
			intro2 = new Intro2();				
			intro2.draw();
			scene.setRoot(intro2);
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			    public void handle(KeyEvent key) {
			    	Navigator.go(Navigator.GAME);			
			    }
			});		
			break;		
			
		case GAME:
			game = new Game();				
			game.draw();						
			scene.setRoot(game);	
			setSceneEvents(scene, game);
			break;		
								
		case EXIT:
			System.exit(0);
			break;
		}
	}
}
