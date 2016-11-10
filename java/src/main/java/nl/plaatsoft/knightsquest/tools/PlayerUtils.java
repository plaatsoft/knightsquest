package nl.plaatsoft.knightsquest.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import nl.plaatsoft.knightsquest.model.Region;
import nl.plaatsoft.knightsquest.model.Land;
import nl.plaatsoft.knightsquest.model.Player;
import nl.plaatsoft.knightsquest.model.Soldier;
import nl.plaatsoft.knightsquest.model.SoldierType;

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
		
		for (int i=1; i<=Constants.START_TOWERS; i++) {
						
			Region region = RegionUtils.createRegion(i, player);		
			SoldierUtils.createSoldier(region);	
		}		
		return player;
	}

	public static int activateMoveSoldier(Player player) {
			
		int count = 0;
		Iterator<Region> iter2 = player.getRegion().iterator();  
		while (iter2.hasNext()) {
			Region region = (Region) iter2.next();
			
			Iterator<Land> iter3 = region.getLands().iterator();  
			while (iter3.hasNext()) {
				Land land = (Land) iter3.next();
				
				if ((land.getSoldier()!=null) && (land.getSoldier().getType()!=SoldierType.TOWER)) {
					land.getSoldier().setMoved(false);
					 count++;
				}
			}
		}
		return count;
	}
	

		
	public static void nextTurn() {
					
		log.info("-------------");
		
		Iterator<Player> iter1 = players.iterator();  	
		while (iter1.hasNext()) {
			Player player = (Player) iter1.next();			
						
			Iterator<Region> iter2 = player.getRegion().iterator();  
			while (iter2.hasNext()) {
				Region region = (Region) iter2.next();
				//log.info("###### PlayerId="+player.getId()+" CastleId="+castle.getId());
												
				/* Activate Soldiers to Move */
				int amount = activateMoveSoldier(player);
				
				/* Move all soldiers of bot players */
				for (int i=0; i<amount; i++) {
					SoldierUtils.moveSoldier(region);
				}
							
				/* Create soldier */
				SoldierUtils.createSoldier(region);							
			}
		}
		
		int regions = RegionUtils.detectedRegions();
		RegionUtils.rebuildRegions(regions);		
	}
	
	public static Region getPlayer(Land newLand) {
				
		Iterator<Player> iter1 = players.iterator();  	
		while (iter1.hasNext()) {
			Player player = (Player) iter1.next();			
			
			Iterator<Region> iter2 = player.getRegion().iterator();  
			while (iter2.hasNext()) {
				Region castle = (Region) iter2.next();
				
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
