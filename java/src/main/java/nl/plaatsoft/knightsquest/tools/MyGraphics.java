package nl.plaatsoft.knightsquest.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class MyGraphics {
	
	//private static Image water = new Image("images/water.png");
	//private static Image ocean = new Image("images/ocean.png");
	private static Image forest = new Image("images/forest.png");
	private static Image coast = new Image("images/coast.png");
	private static Image rock = new Image("images/rock.png");
	private static Image grass = new Image("images/grass.png");
	
	public static void getTypeColor(GraphicsContext gc, Types type) {
	
		switch(type) {
		
		case FOREST:
				gc.setFill(new ImagePattern(forest, 0, 0, 1, 1, true));
				break;	
				
		case GRASS:
				gc.setFill(new ImagePattern(grass, 0, 0, 1, 1, true));
				break;	
				
		case COAST: 
				gc.setFill(new ImagePattern(coast, 0, 0, 1, 1, true));
				break;	
			
		case MOUNTAIN:
				gc.setFill(new ImagePattern(rock, 0, 0, 1, 1, true));
				break;
			
		case WATER:
				gc.setFill(Color.BLUE);
				//gc.setFill(new ImagePattern(water, 0, 0, 1, 1, true));
				break;
				
		case OCEAN:
				gc.setFill(Color.DARKBLUE);
				//gc.setFill(new ImagePattern(ocean, 0, 0, 1, 1, true));
				break;
				
		default:
				gc.setFill(Color.BLACK);
				break;
		}
	}
}
