package nl.plaatsoft.knightsquest.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import nl.plaatsoft.knightsquest.model.Castle;
import nl.plaatsoft.knightsquest.model.Land;
import nl.plaatsoft.knightsquest.model.LandType;
import nl.plaatsoft.knightsquest.model.Player;
import nl.plaatsoft.knightsquest.model.Soldier;
import nl.plaatsoft.knightsquest.model.SoldierType;

public class PlayerUtils {

	final private static Logger log = Logger.getLogger( PlayerUtils.class);		
	final private static Random rnd = new Random();	
	final private static List <Player> players = new ArrayList<Player>() ;
	
	public static void getTexture(GraphicsContext gc, int player) { 
	
		switch(player) {
		
			case 1: // Player 1
					gc.setFill(Color.YELLOW);
					break;
				
			case 2: // Player 2
					gc.setFill(Color.RED);
					break;
			
			case 3:
					gc.setFill(Color.CYAN);
					break;
		
			case 4: // Player 4
					gc.setFill(Color.MAGENTA);
					break;
		}		
	}	
	
	public static Player createPlayer(int nr, Pane panel) {
			
		Player player = new Player("Player"+nr, nr);
		players.add(player);
		log.info("Player"+nr+" created");
		
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
				log.info("Player="+player.getNumber()+" Castle="+castle.getNr());
												
				/* Move Soldiers */
				SoldierUtils.moveSoldier(castle);
				
				/* Create Soldiers */
				SoldierUtils.createSoldier(castle);				
			}
		}
	}
	
	public static List<Player> getPlayers() {
		return players;
	}

}
