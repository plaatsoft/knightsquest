package nl.plaatsoft.knightsquest.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MyGraphics {
	
	public static void getTypeColor(GraphicsContext gc, Types type) {
	
		switch(type) {
		
		case FOREST:
				gc.setFill(Color.DARKGREEN);
				break;	
				
		case GRASS:
				gc.setFill(Color.GREEN);
				break;	
				
		case COAST: 
				gc.setFill(Color.YELLOW);
				break;	
			
		case MOUNTAIN:
				gc.setFill(Color.GRAY);
				break;
			
		case WATER:
				gc.setFill(Color.BLUE);
				break;
				
		case OCEAN:
				gc.setFill(Color.DARKBLUE);
				break;
				
		default:
				gc.setFill(Color.BLACK);
				break;
		}
	}
}
