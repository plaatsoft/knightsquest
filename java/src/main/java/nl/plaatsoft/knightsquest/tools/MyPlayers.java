package nl.plaatsoft.knightsquest.tools;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class MyPlayers {

	final static Logger log = Logger.getLogger( MyPlayers.class);	
	final private static Random rnd = new Random();
	
	public static void getTexture(GraphicsContext gc, int player) { 
	
		switch(player) {
		
			case 1: // Player 1
					gc.setFill(Color.YELLOW);
					break;
				
			case 2: // Player 2
					gc.setFill(Color.RED);
					break;
			
			case 3: // Player 3
					gc.setFill(Color.CYAN);
					break;
		
			case 4: // Player 4
					gc.setFill(Color.MAGENTA);
					break;
		}
	}	
	
	public static void createPlayer(int player, Pane panel) {
			
		for (int i=0; i<Constants.START_TOWERS; i++) {
			boolean done = false;
			while (!done) {
				int x = rnd.nextInt(Constants.SEGMENT_X);
				int y = rnd.nextInt(Constants.SEGMENT_Y);
				
				if (MyMap.getSegment()[x][y].getType()==MySegmentEnum.GRASS) {
															
					List <MySegment> list = MyMap.getNeigbors(x,y);
					Iterator<MySegment> iter = list.iterator();
					
					boolean good=true;
					while (iter.hasNext()) {				
						MySegment segment = (MySegment) iter.next();
						if (segment.getPlayer()>0) {
							good=false;
						}
					}
					
					if (good==true) {
						
						MyMap.getSegment()[x][y].setPlayer(player);
						MyMap.getSegment()[x][y].setArmy(MyArmyEnum.TOWER);
						panel.getChildren().add(MyMap.getSegment()[x][y].getImageView());
						
						list = MyMap.getNeigbors(x,y);
						iter = list.iterator();						
						while (iter.hasNext()) {				
							MySegment segment = (MySegment) iter.next();
							segment.setPlayer(player);							
						}
						done=true;
					}
				}
			}
		}
	}
}
