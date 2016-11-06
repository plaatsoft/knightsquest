package nl.plaatsoft.knightsquest.ui;

import org.apache.log4j.Logger;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import nl.plaatsoft.knightsquest.tools.Constants;
import nl.plaatsoft.knightsquest.tools.MyPanel;

public class Navigator {
		
	final static Logger log = Logger.getLogger( Navigator.class);
	
	private static Game game;	
	
	private static Scene scene;	
			
	public static final int NONE = 1;
	public static final int GAME = 1;
	public static final int EXIT = 2;
			
	public static Scene getScene() {
		return scene;
	}
	
	//handles mouse scrolling
	private static void setSceneEvents(final Scene scene, MyPanel page) {	    
	    scene.setOnScroll(
	            new EventHandler<ScrollEvent>() {
	              @Override
	              public void handle(ScrollEvent event) {
	                double zoomFactor = 1.05;
	                double deltaY = event.getDeltaY();
	                if (deltaY < 0){
	                  zoomFactor = 2.0 - zoomFactor;
	                }
	                
	                double scale = page.getScaleX() * zoomFactor;
	                
	                if ((scale>=1) && (scale<=2)) {
	                	
	                  	page.setScaleX(scale);
	                	page.setScaleY(scale);
	                	
	                	log.info("scale="+page.getScaleX());
	                	
	                	event.consume();
	                }
	              }
	            });

	  }
	
	public static void go(int page) {
				
		switch (page ) {

			case GAME:
				
				game = new Game();
				game.setScaleX(1.5);
            	game.setScaleY(1.5);
				game.draw();		
				scene = new Scene(game, Constants.WIDTH, Constants.HEIGHT);	
				
				scene.setRoot(game);	
				setSceneEvents(scene, game);
				break;		
								
			case EXIT:
				System.exit(0);
				break;
		}
	}
}
