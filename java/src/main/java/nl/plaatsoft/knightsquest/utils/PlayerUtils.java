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

package nl.plaatsoft.knightsquest.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import nl.plaatsoft.knightsquest.model.Region;
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
					
			case 5: // Player 5
					gc.setFill(Color.BROWN);
					break;
					
			case 6: // Player 6
					gc.setFill(Color.LIGHTBLUE);
					break;
		}		
	}	
	
	public static String getColor(int player) { 
		
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
	
	public static Player createPlayer(GraphicsContext gc, int id, Pane pane) {
			
		Boolean bot = true;
		if (id==1) {
			bot = false;
		}
		
		Player player = new Player(id, bot);
		players.add(player);
		
		for (int i=1; i<=Constants.START_TOWERS; i++) {
						
			RegionUtils.createStartRegion(i, player, pane);		
		}		
		return player;
	}
	
	public static void nextTurn() {
	
		log.info("-------");
		
		LandUtils.resetSelected();
		
		Iterator<Player> iter1 = players.iterator();  	
		while (iter1.hasNext()) {
			
			Player player = (Player) iter1.next();		
						
			int amount = SoldierUtils.enableSoldier(player);
				
			Iterator<Region> iter2 = player.getRegion().iterator();  
			while (iter2.hasNext()) {					
				Region region = (Region) iter2.next();
				
				if (!player.isBot()) {
					
					// Human Player
					SoldierUtils.newSoldierArrive(region);
				
				} else {
					
					// Bot player
					for (int i=0; i<amount; i++) {
												
						/* Move bot soldiers */
						SoldierUtils.moveBotSoldier(region);						
					}
					
					/* Create bot soldier, if possible */
					SoldierUtils.createBotSoldier(region);
				}
			}			
		}
		
		/* Rebuild all regions */
		int regions = RegionUtils.detectedRegions();		
		RegionUtils.rebuildRegions(regions);	
	}
	
	public static List<Player> getPlayers() {
		return players;
	}

}
