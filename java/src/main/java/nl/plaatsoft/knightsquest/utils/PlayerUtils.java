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
import nl.plaatsoft.knightsquest.model.Land;
import nl.plaatsoft.knightsquest.model.Player;

public class PlayerUtils {

	final private static Logger log = Logger.getLogger( PlayerUtils.class);		
	final private static List <Player> players = new ArrayList<Player>() ;
	private static int turnCount = 0;
	
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
	
	public static Player createPlayer(GraphicsContext gc, int id, Pane pane) {
			
		Boolean bot = true;
		if (id==1) {
			bot = false;
		}
		
		Player player = new Player(id, bot);
		players.add(player);
		//log.info("Player [id="+id+"] created");
		
		for (int i=1; i<=Constants.START_TOWERS; i++) {
						
			RegionUtils.createStartRegion(i, player, pane);		
		}		
		return player;
	}

	public static boolean checkGameOver() {
		
		boolean stop = false;
		int killPlayerCount=0;
		Iterator<Player> iter1 = players.iterator();  	
		while (iter1.hasNext()) {
			Player player = (Player) iter1.next();
			if (player.getRegion().size()==0) {
				killPlayerCount++;				
			}
		}
		
		if (killPlayerCount==(players.size()-1)) {
			stop =true;
		}		
		return stop;		
	}
	
	public static boolean nextTurn() {
		
		log.info("---[ Turn "+(++turnCount)+"]---");
				
		Iterator<Player> iter1 = players.iterator();  	
		while (iter1.hasNext()) {
			
			Player player = (Player) iter1.next();		
			int amount = SoldierUtils.enableMove(player);
				
			Iterator<Region> iter2 = player.getRegion().iterator();  
			while (iter2.hasNext()) {
					
				Region region = (Region) iter2.next();
											
				/* Move all soldiers of bot players */
				if (player.isBot()) {
					for (int i=0; i<amount; i++) {
						SoldierUtils.moveSoldier(region);
					}
				}
								
				/* Create soldier */
				SoldierUtils.createSoldier(region);
			}			
		}
		
		/* Rebuild all regions */
		int regions = RegionUtils.detectedRegions();		
		RegionUtils.rebuildRegions(regions);	
		
		return checkGameOver();
	}
	
	public static Region getRegion(Land newLand) {
				
		Iterator<Player> iter1 = players.iterator();  	
		while (iter1.hasNext()) {
			Player player = (Player) iter1.next();			
			
			Iterator<Region> iter2 = player.getRegion().iterator();  
			while (iter2.hasNext()) {
				Region region = (Region) iter2.next();
				
				Iterator<Land> iter3 = region.getLands().iterator();  
				while (iter3.hasNext()) {
					Land land = (Land) iter3.next();
					
					if (land.equals(newLand)) {
						return region;
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
