package nl.plaatsoft.knightsquest.tools;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import javafx.scene.image.Image;
import nl.plaatsoft.knightsquest.model.Castle;
import nl.plaatsoft.knightsquest.model.Land;
import nl.plaatsoft.knightsquest.model.Player;
import nl.plaatsoft.knightsquest.model.Soldier;
import nl.plaatsoft.knightsquest.model.SoldierType;

public class SoldierUtils {

	final private static Logger log = Logger.getLogger( SoldierUtils.class);		
	final private static Random rnd = new Random();	
	
	private static Image tower = new Image("images/tower.png", 12, 16,false, false);
	private static Image soldier = new Image("images/soldier.png", 12, 16,false, false);
	private static Image horse = new Image("images/horse.png", 12, 16,false, false);
	private static Image bishop = new Image("images/bishop.png", 12, 16,false, false);
	private static Image queen = new Image("images/queen.png", 12, 16,false, false);
	private static Image king = new Image("images/king.png", 12, 16,false, false);
	
	public static void createSoldier(Castle castle) {
	
		log.info("soldier create start");
		
		/* Create new Soldier if there is enough food */  
		if (castle.foodAvailable()>=SoldierUtils.getFoodNeeds(SoldierType.SOLDIER)) {
				
			/* Create new Soldier if there is room around the castle */  
			List <Land> list = LandUtils.getFreeSegments(castle.getX(), castle.getY());			
			Iterator<Land> iter = list.iterator();  						
			if (iter.hasNext()) {				
				Land land = (Land) iter.next();
				
				Soldier soldier = new Soldier(SoldierType.SOLDIER);
				land.setSoldier(soldier);
				log.info("New Soldier [x="+land.getX()+"|y="+land.getY()+"|castleId="+castle.getId()+"] created!");
			}							
		}
		
		log.info("soldier create end");
	}
	
	public static void moveSoldier(Castle castle) {
		
		log.info("soldier move start");
		
		Iterator<Land> iter1 = castle.getLands().iterator();  
		while (iter1.hasNext()) {
			Land land = (Land) iter1.next();
			
			if ((land.getSoldier()!=null) && (land.getSoldier().getType()==SoldierType.SOLDIER)) {
					
				List <Land> list2 = LandUtils.getFreeSegments(land.getX(), land.getY());
								
				log.info(land.getSoldier().getType()+" found [x="+land.getX()+"|y="+land.getY()+"|move option="+list2.size()+"]");
				
				// Only move when move option are available
				if (list2.size()>0) {
					int nr = rnd.nextInt(list2.size());
					int count=0;
					
					Iterator<Land> iter2 = list2.iterator();
					while (iter2.hasNext()) {
						Land land2 = (Land) iter2.next();
						if (nr==count++) {							
							land2.setSoldier(land.getSoldier());
							land.setSoldier(null);
														
							if (castle.checkNewLand(land2)) {
								
								// Remove land from current owner, if any
								Castle castle2 = PlayerUtils.getPlayer(land2);
								if (castle2!=null) {
									castle2.getLands().remove(land2);
								}	
								
								// Add land to castle
								castle.getLands().add(land2);	
							}
							log.info("Move soldier from ["+land.getX()+","+land.getY()+"] to ["+land2.getX()+","+land2.getY()+"]");
							return;
						}
					}
				}																		
			}
		}	
		log.info("soldier move end");
	}

	public static Image get(SoldierType army) {
	
		switch(army) {
	
			case TOWER: 
				return tower;
		
			case SOLDIER:
				return soldier;
				
			case HORSE:
				return horse;
				
			case BISHOP:
				return bishop;
				
			case QUEEN:
				return queen;
				
			case KING:
				return king;
			
			default:
				log.error("Unknown soldier found!");
				break;
		}
		return null;
	}		
	
	public static int getFoodNeeds(SoldierType army) {
			
		int value = 0;
				
		switch(army) {
	
			case TOWER: 
				break;
						
			case SOLDIER:
				value = 5;
				break;
								
			case HORSE:
				value = 5;
				break;
				
			case BISHOP:
				value = 5;
				break;
				
			case QUEEN:
				value = 10;
				break;
				
			case KING:
				value = 15;
				break;
				
			default:
				log.error("Unknown soldier found!");
				return 0;			
		}
		
		log.info(army+" foodNeeds="+value);
		
		return value;
	}		
}

