/**
 *  @file
 *  @brief 
 *  @author wplaat
 *
 *  Copyright (C) 2008-2016 PlaatSoft
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */


package nl.plaatsoft.knightsquest.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import nl.plaatsoft.knightsquest.tools.MyData;
import nl.plaatsoft.knightsquest.tools.MyFactory;

public class PlayerDAO {

	private static Logger log = Logger.getLogger( PlayerDAO.class);		
	
	private List <Player> players = new ArrayList<Player>();
	
	public void getTexture(GraphicsContext gc, int player) { 
		
		switch(player) {
		
			case 0: // Player 1
					gc.setFill(Color.YELLOW);
					break;
				
			case 1: // Player 2
					gc.setFill(Color.RED);
					break;
			
			case 2: // Player 3
					gc.setFill(Color.CYAN);
					break;
		
			case 3: // Player 4
					gc.setFill(Color.MAGENTA);
					break;
					
			case 4: // Player 5
					gc.setFill(Color.BROWN);
					break;
					
			case 5: // Player 6
					gc.setFill(Color.LIGHTBLUE);
					break;
		}		
	}	
	
	public String getColor(int player) { 
		
		switch(player) {
		
			case 0: // Player 1
					return "yellow";

			case 1: // Player 2
					return "red";
			
			case 2: // Player 3
					return "cyan";
		
			case 3: // Player 4
					return "magenta";
					
			case 4: // Player 5
					return "brown";
					
			case 5: // Player 6
					return "lightblue";
		}		
		return "";
	}	
	
	public Player createPlayer(GraphicsContext gc, int id, Pane pane) {
			
		/* Create player, with region / land and one soldier */
		Boolean bot = true;
		if (id==0) {
			bot = false;
		}
		
		Player player = new Player(id, bot);
		MyFactory.getPlayerDAO().getPlayers().add(player);
		
		for (int i=0; i<MyData.getTowers(); i++) {
						
			MyFactory.getRegionDAO().createStartRegion(i+1, player, pane);		
		}				
		return player;
	}
	
	public Player getHumanPlayer() {
		
		Iterator<Player> iter = players.iterator();  
		while (iter.hasNext()) {
			Player player = (Player) iter.next();
			if (!player.isBot()) {
				return player;
			}
		}
		return null;
	}
		
	public boolean hasPlayerNoMoves(Player player) {
	
		/* Check if human player has moves lefts in this turn */
		Iterator<Region> iter2 = player.getRegion().iterator();  
		while (iter2.hasNext()) {
			Region region = (Region) iter2.next();
				
			Iterator<Land> iter3 = region.getLands().iterator();  
			while (iter3.hasNext()) {
				Land land = (Land) iter3.next();
					
				if ((land.getSoldier()!=null) && land.getSoldier().isEnabled()) {
					return false;
				}
			}
		}
		
		return true;
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
				
				if (player.isBot()) {
										
					// Bot player
					for (int i=0; i<amount; i++) {
												
						/* Move bot soldiers */
						MyFactory.getSoldierDAO().moveBotSoldier(region);						
					}
					
					/* Create bot soldier, if possible */
					MyFactory.getSoldierDAO().createBotSoldier(region);
					
				} else {
				
					// Human Player
					MyFactory.getSoldierDAO().newSoldierArrive(region);
				}			
			}			
		}
		
		/* Rebuild all regions */
		int regions = MyFactory.getRegionDAO().detectedRegions();		
		MyFactory.getRegionDAO().rebuildRegions(regions);	
	}
	
	public List <Player> getPlayers() {
		return players;
	}

	public void setPlayers(List <Player> players) {
		this.players = players;
	}
}
