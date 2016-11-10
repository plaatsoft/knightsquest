package nl.plaatsoft.knightsquest.tools;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.scene.image.Image;

import nl.plaatsoft.knightsquest.model.Region;
import nl.plaatsoft.knightsquest.model.Land;
import nl.plaatsoft.knightsquest.model.Soldier;
import nl.plaatsoft.knightsquest.model.SoldierType;

public class SoldierUtils {

	final private static Logger log = Logger.getLogger( SoldierUtils.class);		
	
	private static Image tower = new Image("images/tower.png", Constants.SEGMENT_SIZE+4, Constants.SEGMENT_SIZE+4, false, false);
	private static Image soldier = new Image("images/soldier.png", Constants.SEGMENT_SIZE+4, Constants.SEGMENT_SIZE+4, false, false);
	private static Image horse = new Image("images/horse.png", Constants.SEGMENT_SIZE+4, Constants.SEGMENT_SIZE+4, false, false);
	private static Image bishop = new Image("images/bishop.png", Constants.SEGMENT_SIZE+4, Constants.SEGMENT_SIZE+4, false, false);
	private static Image queen = new Image("images/queen.png", Constants.SEGMENT_SIZE+4, Constants.SEGMENT_SIZE+4, false, false);
	private static Image king = new Image("images/king.png", Constants.SEGMENT_SIZE+4, Constants.SEGMENT_SIZE+4, false, false);
	private static Image cross = new Image("images/cross.png", Constants.SEGMENT_SIZE+4, Constants.SEGMENT_SIZE+4, false, false);
			
	public static void createSoldier(Region region) {
	
		//log.info("soldier create start");
					
		/* Create new Soldier if there is enough food */  
		if (region.foodAvailable()>=SoldierUtils.getFoodNeeds(SoldierType.SOLDIER)) {
						
			Land land1 = RegionUtils.getTowerPosition(region);
			
			/* Create new Soldier if there is room around the castle */  
			List <Land> list2 = LandUtils.getOwnLand(land1.getX(), land1.getY(), region.getPlayer());			
			Iterator<Land> iter2 = list2.iterator();  						
			if (iter2.hasNext()) {				
				Land land2 = (Land) iter2.next();
				
				Soldier soldier = new Soldier(SoldierType.SOLDIER, region.getPlayer());
				land2.setSoldier(soldier);
				log.info("New Soldier [x="+land2.getX()+"|y="+land2.getY()+"|regionId="+region.getId()+"] created!");
			}							
		}
		
		//log.info("soldier create end");
	}
	
	
	public static void moveSoldier(Region region) {
		
		//log.info("soldier move start");
		
		Iterator<Land> iter1 = region.getLands().iterator();  
		while (iter1.hasNext()) {
			Land land1 = (Land) iter1.next();
			
			if ((land1.getSoldier()!=null) && 
				!land1.getSoldier().isMoved() && 
				(land1.getSoldier().getType()!=SoldierType.CROSS) && 
				(land1.getSoldier().getType()!=SoldierType.TOWER)) {
					
				//log.info(land1.getSoldier().getType()+" found [x="+land1.getX()+"|y="+land1.getY()+"]");	
				
				/* --------------------------- */
				/* Upgrade soldier if possible */
				/* --------------------------- */
				
				if (land1.getSoldier().getType()!=SoldierType.KING) {
					List <Land> list2 = LandUtils.getUpgradeSoldiers(land1.getX(), land1.getY(), region.getPlayer());
					Iterator<Land> iter2 = list2.iterator();
					while (iter2.hasNext()) {
						Land land2 = (Land) iter2.next();

						// Only upgrade if there is enough food
						if (region.foodAvailable()>SoldierUtils.getFoodNeeds(upgrade(land1.getSoldier().getType()))) {
						
							land1.getSoldier().setMoved(true);
							
							SoldierType currentType = land1.getSoldier().getType();
							SoldierType nextType = upgrade(land1.getSoldier().getType());
														
							land2.setSoldier(land1.getSoldier());
							land2.getSoldier().setType(nextType);
							//log.info("Move ["+land1.getX()+","+land1.getY()+"]->["+land2.getX()+","+land2.getY()+"] Upgrade soldier ["+currentType+"->"+nextType+"]");
						
							land1.setSoldier(null);						
							return;
						}
					}
				}
				
				/* ------------------------------------- */
				/* Conquer enemy land or defend own land */
				/* ------------------------------------- */
				
				List <Land> list2 = LandUtils.getEnemyLand(land1.getX(), land1.getY(), region.getPlayer());	
				Land land2 = MyRandom.nextLand(list2);
				if (land2!=null) {
									
					if (land2.getSoldier()!=null) {
						
						/* Enemy land is protected with soldier */	
						int attackStrength = land1.getSoldier().getType().getValue();
						int defendStrength = land2.getSoldier().getType().getValue();
						
						if (attackStrength>defendStrength) {
								
							land1.getSoldier().setMoved(true);
							
							// log.info(land1.getSoldier().getType()+" ["+land1.getX()+","+land1.getY()+"] attack and kills "+land2.getSoldier().getType()+" ["+land2.getX()+","+land2.getY()+"]");
							
							land2.setSoldier(land1.getSoldier());
							land2.setPlayer(region.getPlayer());
							land1.setSoldier(null);
															
							// Remove land from current owner, if any
							Region region2 = PlayerUtils.getPlayer(land2);
							if (region2!=null) {
								region2.getLands().remove(land2);
							}	
							
							// Add land to player castle								
							region.getLands().add(land2);
							return;
						} 
							
					} else {
						/* Enemy land is unprotected */
					
						land1.getSoldier().setMoved(true);
									
						// log.info("land1.getSoldier().getType()+" ["+land1.getX()+","+land1.getY()+"]->["+land2.getX()+","+land2.getY()+"]");
					
						land2.setSoldier(land1.getSoldier());
						land2.setPlayer(region.getPlayer());
						land1.setSoldier(null);
															
						//	Remove land from current owner, if any
						Region region2 = PlayerUtils.getPlayer(land2);
						if (region!=null) {
							region2.getLands().remove(land2);
						}	
							
						// 	Add land to player castle								
						region.getLands().add(land2);		
						return;				
					}
				}
								
				/* ------------------------ */
				/* Move soldier to new land */		
				/* ------------------------ */
				
				List <Land> list4 = LandUtils.getNewLand(land1.getX(), land1.getY());					
				Land land4 = MyRandom.nextLand(list4);
				if (land4!=null) {
											
					land1.getSoldier().setMoved(true);
							
					land4.setSoldier(land1.getSoldier());
					land4.setPlayer(region.getPlayer());
					land1.setSoldier(null);
							
					// Add land to player castle								
					region.getLands().add(land4);
							
					// log.info("land1.getSoldier().getType()+" ["+land1.getX()+","+land1.getY()+"]->["+land4.getX()+","+land4.getY()+"]");
					return;
				}
						
				/* ------------------------ */
				// Move soldier on own land
				/* ------------------------ */
				
				List <Land> list5 = LandUtils.getOwnLand(land1.getX(), land1.getY(), region.getPlayer());	
				Land land5 = MyRandom.nextLand(list5);
				if (land5!=null) {

					land1.getSoldier().setMoved(true);
													
					land5.setSoldier(land1.getSoldier());
					land5.setPlayer(region.getPlayer());
					land1.setSoldier(null);

					//log.info("land1.getSoldier().getType()+" ["+land1.getX()+","+land1.getY()+"]->["+land5.getX()+","+land5.getY()+"]");
					return;
				}		
			}
		}	
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

