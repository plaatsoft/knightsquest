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

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.scene.image.Image;

import nl.plaatsoft.knightsquest.model.Region;
import nl.plaatsoft.knightsquest.model.Land;
import nl.plaatsoft.knightsquest.model.Player;
import nl.plaatsoft.knightsquest.model.Soldier;
import nl.plaatsoft.knightsquest.model.SoldierEnum;
import nl.plaatsoft.knightsquest.tools.MyRandom;

public class  SoldierUtils {
	
	final private static Logger log = Logger.getLogger(SoldierUtils.class);
		
	private static Image tower = new Image("images/tower.png", Constants.SEGMENT_SIZE+4, Constants.SEGMENT_SIZE+4, false, false);
	private static Image tower2 = new Image("images/tower2.png", Constants.SEGMENT_SIZE+4, Constants.SEGMENT_SIZE+4, false, false);
	private static Image pawn = new Image("images/pawn.png", Constants.SEGMENT_SIZE+4, Constants.SEGMENT_SIZE+4, false, false);
	private static Image pawn2 = new Image("images/pawn2.png", Constants.SEGMENT_SIZE+4, Constants.SEGMENT_SIZE+4, false, false);
	private static Image horse = new Image("images/horse.png", Constants.SEGMENT_SIZE+4, Constants.SEGMENT_SIZE+4, false, false);
	private static Image horse2 = new Image("images/horse2.png", Constants.SEGMENT_SIZE+4, Constants.SEGMENT_SIZE+4, false, false);
	private static Image bishop = new Image("images/bishop.png", Constants.SEGMENT_SIZE+4, Constants.SEGMENT_SIZE+4, false, false);
	private static Image bishop2 = new Image("images/bishop2.png", Constants.SEGMENT_SIZE+4, Constants.SEGMENT_SIZE+4, false, false);
	private static Image queen = new Image("images/queen.png", Constants.SEGMENT_SIZE+4, Constants.SEGMENT_SIZE+4, false, false);
	private static Image queen2 = new Image("images/queen2.png", Constants.SEGMENT_SIZE+4, Constants.SEGMENT_SIZE+4, false, false);
	private static Image king = new Image("images/king.png", Constants.SEGMENT_SIZE+4, Constants.SEGMENT_SIZE+4, false, false);
	private static Image king2 = new Image("images/king2.png", Constants.SEGMENT_SIZE+4, Constants.SEGMENT_SIZE+4, false, false);
	private static Image cross = new Image("images/cross.png", Constants.SEGMENT_SIZE+4, Constants.SEGMENT_SIZE+4, false, false);
			
	public static void createBotSoldier(Region region) {
						
		/* Create new Soldier if there is enough food */  
		if (region.foodAvailable()>=food(SoldierEnum.PAWN)) {
						
			Land land1 = RegionUtils.getTowerPosition(region);
			if (land1!=null) {
				/* Create new Soldier if there is room around the castle */  
				List <Land> list2 = LandUtils.getBotOwnLand(land1);			
				Iterator<Land> iter2 = list2.iterator();  						
				if (iter2.hasNext()) {				
					Land land2 = (Land) iter2.next();
					
					Soldier soldier = new Soldier(SoldierEnum.PAWN, region.getPlayer(), land2);
					land2.setSoldier(soldier);
				}
			}							
		}
	}
		
	public static void newSoldierArrive(Region region) {
		
		/* Inform player that new soldier has arrived if there is enough food */  
		if (region.foodAvailable()>=food(SoldierEnum.PAWN)) {
						
			Land land = RegionUtils.getTowerPosition(region);
			if (land!=null) {
				
				/* Enable castle. So player know new soldier is possible */  
				land.getSoldier().setEnabled(true);
			}							
		}
	}
		
	public static int enableSoldier(Player player) {
		
		int count = 0;
	
		Iterator<Region> iter2 = player.getRegion().iterator();  
		while (iter2.hasNext()) {
			Region region = (Region) iter2.next();
			
			log.info(player+" "+region+" size="+region.getLands().size());
			
			Iterator<Land> iter3 = region.getLands().iterator();  
			while (iter3.hasNext()) {
				Land land = (Land) iter3.next();
				
				if ((land.getSoldier()!=null) && (land.getSoldier().getType()!=SoldierEnum.TOWER)) {
					land.getSoldier().setEnabled(true);
					count++;
				}
			}
		}
		return count;
	}
		
	// Move bots
	public static void moveBotSoldier(Region region) {
		
		//log.info("soldier move start");
		
		Iterator<Land> iter1 = region.getLands().iterator();  
		while (iter1.hasNext()) {
			Land land1 = (Land) iter1.next();
			
			if ((land1.getSoldier()!=null) && 
				land1.getSoldier().isEnabled() && 
				(land1.getSoldier().getType()!=SoldierEnum.CROSS) && 
				(land1.getSoldier().getType()!=SoldierEnum.TOWER)) {
					
				//log.info(land1.getSoldier().getType()+" found [x="+land1.getX()+"|y="+land1.getY()+"]");	
				
				/* --------------------------- */
				/* Upgrade soldier if possible */
				/* --------------------------- */
				
				if (land1.getSoldier().getType()!=SoldierEnum.KING) {
					List <Land> list5 = LandUtils.getUpgradeSoldiers(land1);
					Iterator<Land> iter5 = list5.iterator();
					while (iter5.hasNext()) {
						Land land5 = (Land) iter5.next();

						// Only upgrade if there is enough food
						SoldierEnum nextType = upgrade(land1.getSoldier().getType());	
						
						if (region.foodAvailable()>SoldierUtils.food(nextType)) {
																																																				
							LandUtils.moveSoldier(land1, land5);			
							return;
						}
					}
				}
				
				/* ------------------------------------- */
				/* Conquer enemy land or defend own land */
				/* ------------------------------------- */
				
				List <Land> list2 = LandUtils.getBotEnemyLand(land1);	
				Land land2 = MyRandom.nextLand(list2);
				if (land2!=null) {
									
					if (land2.getSoldier()!=null) {
						
						/* Enemy land is protected with soldier */	
						int attackStrength = land1.getSoldier().getType().getValue();
						int defendStrength = land2.getSoldier().getType().getValue();
						
						if (attackStrength>defendStrength) {
								
							LandUtils.moveSoldier(land1, land2);
							return;
						} 
							
					} else {
						/* Enemy land is unprotected */
						LandUtils.moveSoldier(land1, land2);
						return;				
					}
				}
				
				/* ------------------------ */
				/* Move soldier to new land */		
				/* ------------------------ */
				
				List <Land> list4 = LandUtils.getBotNewLand(land1);					
				Land land4 = MyRandom.nextLand(list4);
				if (land4!=null) {											
					LandUtils.moveSoldier(land1, land4);											
					return;
				}
				
				/* ------------------------ */
				// Move soldier on own land
				/* ------------------------ */
				
				List <Land> list6 = LandUtils.getBotOwnLand(land1);	
				Land land6 = MyRandom.nextLand(list6);
				if (land6!=null) {
					LandUtils.moveSoldier(land1, land6);
					return;
				}		
			}
		}	
	}

	public static Image get(SoldierEnum army, boolean enabled) {
	
		switch(army) {
	
			case TOWER: 
				if (enabled) {
					return tower2;
				} else {
					return tower;
				}
		
			case PAWN:
				if (enabled) {
					return pawn2;
				} else {
					return pawn;
				}
								
			case BISHOP:
				if (enabled) {
					return bishop2;
				} else {
					return bishop;
				}
				
			case HORSE:
				if (enabled) {
					return horse2;
				} else {
					return horse;
				}
				
			case QUEEN:
				if (enabled) {
					return queen2;
				} else {
					return queen;
				}
				
			case KING:
				if (enabled) {
					return king2;
				} else {
					return king;
				}
				
			case CROSS:
				return cross;

			default:
				break;
		}
		return null;
	}		
	
	public static SoldierEnum upgrade(SoldierEnum type) {
		
		SoldierEnum value;
		
		switch(type) {
		
			case PAWN:
				value = SoldierEnum.BISHOP;
				break;
			
			case BISHOP:
				value = SoldierEnum.HORSE;
				break;
				
			case HORSE:
				value = SoldierEnum.QUEEN;
				break;
				
			case QUEEN:
				value = SoldierEnum.KING;
				break;
				
			default:
				value=null;
				break;
		}
			
		return value;
	}
	
	public static int food(SoldierEnum type) {
			
		int value=0;
				
		switch(type) {
	
			case PAWN:
				value = 2;
				break;
				
			case BISHOP:
				value = 5;
				break;
				
			case HORSE:
				value = 10;
				break;
				
			case QUEEN:
				value = 20;
				break;
				
			case KING:
				value = 40;
				break;
				
			default:
				value = 0;					
				break;
						
		}		
		return value;
	}		
}

