package nl.plaatsoft.knightsquest.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import nl.plaatsoft.knightsquest.model.Castle;
import nl.plaatsoft.knightsquest.model.Land;
import nl.plaatsoft.knightsquest.model.Player;

public class PlayerUtils {

	final private static Logger log = Logger.getLogger( PlayerUtils.class);		
	final private static List <Player> players = new ArrayList<Player>() ;
	
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
	
	public static Player createPlayer(int id, Pane panel) {
			
		Player player = new Player(id);
		players.add(player);
		log.info("Player [id="+id+"] created");
		
		for (int i=0; i<Constants.START_TOWERS; i++) {
			Castle castle = CastleUtils.createCastle(player);		
			SoldierUtils.createSoldier(castle);	
		}		
		return player;
	}
	

	public static void nextTurn() {
					
		log.info("-----------------");
		
		Iterator<Player> iter1 = players.iterator();  	
		while (iter1.hasNext()) {
			Player player = (Player) iter1.next();			
			
			Iterator<Castle> iter2 = player.getCastle().iterator();  
			while (iter2.hasNext()) {
				Castle castle = (Castle) iter2.next();
				log.info("PlayerId="+player.getId()+" CastleId="+castle.getId());
												
				/* Move Soldiers */
				SoldierUtils.moveSoldier(castle);
				
				/* Create Soldiers */
				//SoldierUtils.createSoldier(castle);				
			}
		}
	}
	
	public static Castle getPlayer(Land newLand) {
		
		log.info("-----------------");
		
		Iterator<Player> iter1 = players.iterator();  	
		while (iter1.hasNext()) {
			Player player = (Player) iter1.next();			
			
			Iterator<Castle> iter2 = player.getCastle().iterator();  
			while (iter2.hasNext()) {
				Castle castle = (Castle) iter2.next();
				
				Iterator<Land> iter3 = castle.getLands().iterator();  
				while (iter3.hasNext()) {
					Land land = (Land) iter3.next();
					
					if (land.equals(newLand)) {
						return castle;
					}
				}
			}
		}
		return null;
	}
		
			
	public static List<Player> getPlayers() {
		return players;
	}

}
