package nl.plaatsoft.knightsquest.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import nl.plaatsoft.knightsquest.tools.MyFactory;
import nl.plaatsoft.knightsquest.ui.Constants;

public class PlayerDAO {

	private static Logger log = Logger.getLogger( PlayerDAO.class);		
	
	private List <Player> players = new ArrayList<Player>();

	public List <Player> getPlayers() {
		return players;
	}

	public void setPlayers(List <Player> players) {
		this.players = players;
	}
	
	public void getTexture(GraphicsContext gc, int player) { 
		
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
					
			case 5: // Player 5
					gc.setFill(Color.BROWN);
					break;
					
			case 6: // Player 6
					gc.setFill(Color.LIGHTBLUE);
					break;
		}		
	}	
	
	public String getColor(int player) { 
		
		switch(player) {
		
			case 1: // Player 1
					return "yellow";

			case 2: // Player 2
					return "red";
			
			case 3: // Player 3
					return "cyan";
		
			case 4: // Player 4
					return "magenta";
					
			case 5: // Player 5
					return "brown";
					
			case 6: // Player 6
					return "lightblue";
		}		
		return "";
	}	
	
	public Player createPlayer(GraphicsContext gc, int id, Pane pane) {
			
		Boolean bot = true;
		if (id==1) {
			bot = false;
		}
		
		Player player = new Player(id, bot);
		MyFactory.getPlayerDAO().getPlayers().add(player);
		
		for (int i=1; i<=Constants.START_TOWERS; i++) {
						
			MyFactory.getRegionDAO().createStartRegion(i, player, pane);		
		}		
		return player;
	}
	
	public boolean hasPlayerNoMoves(Player player) {
	
		int amount=0;
		
		Iterator<Region> iter2 = player.getRegion().iterator();  
		while (iter2.hasNext()) {
			Region region = (Region) iter2.next();
				
			Iterator<Land> iter3 = region.getLands().iterator();  
			while (iter3.hasNext()) {
				Land land = (Land) iter3.next();
					
				if ((land.getSoldier()!=null) && land.getSoldier().isEnabled()) {
					amount++;
				}
			}
		}
		
		log.info("amount="+amount);
		if (amount==0) {
			return true;
		}
		return false;
	}
	
	public void nextTurn() {
	
		log.info("-------");
		
		MyFactory.getLandDAO().resetSelected();
		
		Iterator<Player> iter1 = MyFactory.getPlayerDAO().getPlayers().iterator();  	
		while (iter1.hasNext()) {
			
			Player player = (Player) iter1.next();		
						
			int amount = MyFactory.getSoldierDAO().enableSoldier(player);
				
			Iterator<Region> iter2 = player.getRegion().iterator();  
			while (iter2.hasNext()) {					
				Region region = (Region) iter2.next();
				
				if (!player.isBot()) {
					
					// Human Player
					MyFactory.getSoldierDAO().newSoldierArrive(region);
				
				} else {
					
					// Bot player
					for (int i=0; i<amount; i++) {
												
						/* Move bot soldiers */
						MyFactory.getSoldierDAO().moveBotSoldier(region);						
					}
					
					/* Create bot soldier, if possible */
					MyFactory.getSoldierDAO().createBotSoldier(region);
				}
			}			
		}
		
		/* Rebuild all regions */
		int regions = MyFactory.getRegionDAO().detectedRegions();		
		MyFactory.getRegionDAO().rebuildRegions(regions);	
	}
}
