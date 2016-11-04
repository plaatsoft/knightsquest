package nl.plaatsoft.knightsquest.ui;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import nl.plaatsoft.knightsquest.tools.Constants;
import nl.plaatsoft.knightsquest.tools.MyPanel;

public class Navigator {
		
	private static Game game;	
	
	private static Scene scene;	
			
	public static final int GAME = 1;
	public static final int EXIT = 2;
			
	public static Scene getScene() {
		return scene;
	}
	
	private static void setSceneEvents(final Scene scene, MyPanel page) {
	    //handles mouse scrolling
	    scene.setOnScroll(
	            new EventHandler<ScrollEvent>() {
	              @Override
	              public void handle(ScrollEvent event) {
	                double zoomFactor = 1.05;
	                double deltaY = event.getDeltaY();
	                if (deltaY < 0){
	                  zoomFactor = 2.0 - zoomFactor;
	                }
	                System.out.println(zoomFactor);
	                page.setScaleX(page.getScaleX() * zoomFactor);
	                page.setScaleY(page.getScaleY() * zoomFactor);
	                event.consume();
	              }
	            });

	  }
	
	public static void go(int page) {
				
		switch (page ) {

			case GAME:
				
				game = new Game();
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
