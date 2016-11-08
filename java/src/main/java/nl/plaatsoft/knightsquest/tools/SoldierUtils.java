package nl.plaatsoft.knightsquest.tools;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import javafx.scene.image.Image;
import nl.plaatsoft.knightsquest.model.Castle;
import nl.plaatsoft.knightsquest.model.Land;
import nl.plaatsoft.knightsquest.model.Soldier;
import nl.plaatsoft.knightsquest.model.SoldierType;

public class SoldierUtils {

	final private static Logger log = Logger.getLogger( SoldierUtils.class);		
	final private static Random rnd = new Random();	
	
	private static Image tower = new Image("images/tower.png", 12, 16, false, false);
	private static Image soldier = new Image("images/soldier.png", 12, 16, false, false);
	private static Image horse = new Image("images/horse.png", 12, 16, false, false);
	private static Image bishop = new Image("images/bishop.png", 12, 16, false, false);
	private static Image queen = new Image("images/queen.png", 12, 16, false, false);
	private static Image king = new Image("images/king.png", 12, 16, false, false);
	private static Image cross = new Image("images/cross.png", 12, 16, false, false);
			
	public static void createSoldier(Castle castle) {
	
		//log.info("soldier create start");
		
		/* Create new Soldier if there is enough food */  
		if (castle.foodAvailable()>=SoldierUtils.getFoodNeeds(SoldierType.SOLDIER)) {
				
			/* Create new Soldier if there is room around the castle */  
			List <Land> list = LandUtils.getFreeSegments(castle.getX(), castle.getY(), castle.getPlayer());			
			Iterator<Land> iter = list.iterator();  						
			if (iter.hasNext()) {				
				Land land = (Land) iter.next();
				
				Soldier soldier = new Soldier(SoldierType.SOLDIER, castle.getPlayer());
				land.setSoldier(soldier);
				//log.info("New Soldier [x="+land.getX()+"|y="+land.getY()+"|castleId="+castle.getId()+"] created!");
			}							
		}
		
		//log.info("soldier create end");
	}
	
	
	public static void moveSoldier(Castle castle) {
		
		//log.info("soldier move start");
		
		Iterator<Land> iter1 = castle.getLands().iterator();  
		while (iter1.hasNext()) {
			Land land1 = (Land) iter1.next();
			
			if ((land1.getSoldier()!=null) && 
				!land1.getSoldier().isMoved() && 
				(land1.getSoldier().getType()!=SoldierType.CROSS) && 
				(land1.getSoldier().getType()!=SoldierType.TOWER)) {
					
				//log.info(land1.getSoldier().getType()+" found [x="+land1.getX()+"|y="+land1.getY()+"|move option="+list2.size()+"]");		
				
				/* Upgrade soldier if possible */
				if (land1.getSoldier().getType()!=SoldierType.KING) {
					List <Land> list2 = LandUtils.getUpgradeSoldiers(land1.getX(), land1.getY(), castle.getPlayer());
					Iterator<Land> iter2 = list2.iterator();
					while (iter2.hasNext()) {
						Land land2 = (Land) iter2.next();
											
						land1.getSoldier().setMoved(true);
													
						land2.setSoldier(land1.getSoldier());
						land2.getSoldier().setType(upgrade(land2.getSoldier().getType()));
						land1.setSoldier(null);
						return;				
					}
				}
				
				
				// Move Soldier
				List <Land> list2 = LandUtils.getFreeSegments(land1.getX(), land1.getY(), castle.getPlayer());								
						
				if (list2.size()>0) {
					int nr = rnd.nextInt(list2.size());
					int count=0;
					
					Iterator<Land> iter2 = list2.iterator();
					while (iter2.hasNext()) {
						Land land2 = (Land) iter2.next();
						if (nr==count++) {				
							
							land1.getSoldier().setMoved(true);
							
							if (land2.getPlayer()!=null && !land2.getPlayer().equals(land1.getPlayer())) { 
							
								if ((land2.getSoldier()!=null) && (land1.getSoldier().getType().getValue()<=land2.getSoldier().getType().getValue())) {
									//Enemy solder is strong. Do not attack
									return;
								}
							}
							
							land2.setSoldier(land1.getSoldier());
							land2.setPlayer(castle.getPlayer());
							land1.setSoldier(null);
														
							if (castle.checkNewLand(land2)) {
								
								// Remove land from current owner, if any
								Castle castle2 = PlayerUtils.getPlayer(land2);
								if (castle2!=null) {
									castle2.getLands().remove(land2);
								}	
								
								// Add land to player castle								
								castle.getLands().add(land2);	
							}
							//log.info("Move soldier from ["+land1.getX()+","+land1.getY()+"] to ["+land2.getX()+","+land2.getY()+"]");
							return;
						}
					}
				}																		
			}
		}	
		//log.info("soldier move end");
	}

	public static Image get(SoldierType army) {
	
		switch(army) {
	
			case TOWER: 
				return tower;
		
			case SOLDIER:
				return soldier;
								
			case BISHOP:
				return bishop;
				
			case HORSE:
				return horse;
				
			case QUEEN:
				return queen;
				
			case KING:
				return king;
				
			case CROSS:
				return cross;
			
			default:
				log.error("Unknown soldier found!");
				break;
		}
		return null;
	}		
	
	public static SoldierType upgrade(SoldierType type) {
		
		SoldierType value;
		
		switch(type) {
		
			case SOLDIER:
				value = SoldierType.BISHOP;
				break;
			
			case BISHOP:
				value = SoldierType.HORSE;
				break;
				
			case HORSE:
				value = SoldierType.QUEEN;
				break;
				
			case QUEEN:
				value = SoldierType.KING;
				break;
				
			default:
				value=null;
				break;
		}
			
		return value;
	}
	
	public static int getFoodNeeds(SoldierType type) {
			
		int value=0;
				
		switch(type) {
	
			case SOLDIER:
				value = 2;
				break;
				
			case BISHOP:
				value = 4;
				break;
				
			case HORSE:
				value = 6;
				break;
				
			case QUEEN:
				value = 8;
				break;
				
			case KING:
				value = 10;
				break;
				
			default:
				value = 0;					
				break;
						
		}		
		return value;
	}		
}

