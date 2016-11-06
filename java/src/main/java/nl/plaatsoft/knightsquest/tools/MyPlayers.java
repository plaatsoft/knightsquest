package nl.plaatsoft.knightsquest.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class MyPlayers {

	final private static Logger log = Logger.getLogger( MyPlayers.class);	
	final private static Random rnd = new Random();	
	final private static List <MyPlayer> players = new ArrayList<MyPlayer>() ;
	
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
	
	public static void createPlayer(int nr, Pane panel) {
			
		MyPlayer player = new MyPlayer();
		player.setNumber(nr);		
		players.add(player);
		
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
						
						MyTown town = new MyTown("",x,y);
						player.getTowns().add(town);
						
						MyMap.getSegment()[x][y].setPlayer(nr);
						MyMap.getSegment()[x][y].setArmy(MyArmyEnum.TOWER);
						panel.getChildren().add(MyMap.getSegment()[x][y].getImageView());
						
						town.getSegments().add(MyMap.getSegment()[x][y]);
						
						list = MyMap.getNeigbors(x,y);
						iter = list.iterator();						
						while (iter.hasNext()) {				
							MySegment segment = (MySegment) iter.next();
							segment.setPlayer(nr);			
							town.getSegments().add(segment);											
						}
						done=true;
					}
				}
			}
		}
	}
	
	
	public static void nextTurn() {
					
		Iterator<MyPlayer> iter1 = players.iterator();  	
		while (iter1.hasNext()) {
			MyPlayer player = (MyPlayer) iter1.next();			
			
			Iterator<MyTown> iter2 = player.getTowns().iterator();  
			while (iter2.hasNext()) {
				MyTown town = (MyTown) iter2.next();
				
								
				int armySize = 0;
				Iterator<MySegment> iter3 = town.getSegments().iterator();  
				while (iter3.hasNext()) {
					MySegment segment = (MySegment) iter3.next();			
					armySize += MyArmy.getFoodNeeds(segment.getArmy());
				}
				log.info("Player="+player.getNumber()+" Size="+town.getSize()+" armySize="+armySize);
					
				/* Check if next soldier can live */  
				if (armySize<=town.getSize()) {
							
					Iterator<MySegment> iter4 = town.getSegments().iterator();  						
					while (iter4.hasNext()) {				
						MySegment segment = (MySegment) iter4.next();
						if (segment.getArmy()==null) {
							segment.setArmy(MyArmyEnum.SOLDIER);
							log.info("Player="+player.getNumber()+" New Soldier");
							break;
						}							
					}
				}					
			}
		}
	}
}
