package nl.plaatsoft.knightsquest.ui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import nl.plaatsoft.knightsquest.tools.Constants;
import nl.plaatsoft.knightsquest.tools.LandUtils;
import nl.plaatsoft.knightsquest.tools.MyButton;
import nl.plaatsoft.knightsquest.tools.MyLabel;
import nl.plaatsoft.knightsquest.tools.MyPanel;
import nl.plaatsoft.knightsquest.tools.MyRandom;

public class MapSelector extends MyPanel {
	
	private void createMiniWorld(int x, int y, int seek) {
				
		MyRandom.setSeek(seek);
		
		Canvas canvas = new Canvas((Constants.SEGMENT_X*Constants.SEGMENT_SIZE*4),(Constants.SEGMENT_Y*Constants.SEGMENT_SIZE*2));
		canvas.setLayoutX(x-480);
		canvas.setLayoutY(y-720);
		canvas.setScaleX(0.15);
		canvas.setScaleY(0.15);
					
		GraphicsContext gc = canvas.getGraphicsContext2D();		
		getChildren().add(canvas);
	    		
	    LandUtils.createMap();
	    LandUtils.drawMap(gc);
	    
	    canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent me) {
		    	
		    	MyRandom.setSeek(seek);
		    	Navigator.go(Navigator.GAME);
		    }
		});
	}
	
	public void draw() {
		setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    	  	    	
    	int y=20;
    	int x;
    	getChildren().add( new MyLabel(0, y, "Map Selector", 60, "white", "-fx-font-weight: bold;"));
    	
    	y+=90;
    	x=25;
    	createMiniWorld(x, y, 1);
    	
    	x+=200;
    	createMiniWorld(x, y, 8);
    	
    	x+=200;
    	createMiniWorld(x, y, 30);
    	
    	y+=150;
    	
    	x=25;
    	createMiniWorld(x, y, 4);
    	
    	x+=200;
    	createMiniWorld(x, y, 10);
    	
    	x+=200;
      	createMiniWorld(x, y, 6);
    	       		
    	getChildren().add( new MyButton(230, 420, "Close", 18, Navigator.HOME));		
	}
}
