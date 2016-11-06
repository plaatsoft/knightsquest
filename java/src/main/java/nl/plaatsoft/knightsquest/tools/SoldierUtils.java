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
	
	private static Image tower = new Image("images/tower.png", 12, 16,false, false);
	private static Image soldier = new Image("images/soldier.png", 12, 16,false, false);
	private static Image horse = new Image("images/horse.png", 12, 16,false, false);
	private static Image bishop = new Image("images/bishop.png", 12, 16,false, false);
	private static Image queen = new Image("images/queen.png", 12, 16,false, false);
	private static Image king = new Image("images/king.png", 12, 16,false, false);
	
	public static void createSoldier(Castle castle) {
		
		int armySize = 0;
		Iterator<Land> iter3 = castle.getLand().iterator();  
		while (iter3.hasNext()) {
			Land land = (Land) iter3.next();			
			if (land.getSoldier()!=null) {			
				armySize += SoldierUtils.getFoodNeeds(land.getSoldier().getType());
			}			
		}
		armySize += SoldierUtils.getFoodNeeds(SoldierType.SOLDIER);
					
		/* Create new Soldier if land size allows it */  
		if (armySize < castle.getLandSize()) {
					
			Iterator<Land> iter4 = castle.getLand().iterator();  						
			while (iter4.hasNext()) {				
				Land land = (Land) iter4.next();
				if (land.getSoldier()==null) {
					Soldier soldier = new Soldier(SoldierType.SOLDIER);
					land.setSoldier(soldier);
					log.info("New Soldier [x="+land.getX()+"|y="+land.getY()+"|castle="+castle.getNr()+"] created!");
					break;
				}							
			}
		}
	}
	
	public static void moveSoldier(Castle castle) {
		
		Iterator<Land> iter1 = castle.getLand().iterator();  
		while (iter1.hasNext()) {
			Land land = (Land) iter1.next();
			
			if ((land.getSoldier()!=null) && (land.getSoldier().getType()==SoldierType.SOLDIER)) {
					
				List <Land> list2 = LandUtils.getFreeSegments(land.getX(), land.getY());
								
				log.info(land.getSoldier().getType()+" found [x="+land.getX()+"|y="+land.getY()+"|move option="+list2.size()+"]");
				
				if (list2.size()!=0) {
								
					int nr = rnd.nextInt(list2.size());
					
					Iterator<Land> iter2 = list2.iterator();
					int count=0;
					while (iter2.hasNext()) {
						Land land2 = (Land) iter2.next();
						if (nr==count++) {							
							land2.setSoldier(land.getSoldier());
							land.setSoldier(null);
							iter1=null;
							iter2=null;
							castle.getLand().add(land2);
							log.info("Move soldier from ["+land.getX()+","+land.getY()+"] to ["+land.getX()+","+land.getY()+"]");
							return;
						}
					}																		
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
				
			case HORSE:
				return horse;
				
			case BISHOP:
				return bishop;
				
			case QUEEN:
				return queen;
				
			case KING:
				return king;
			
			default:
				break;
		}
		return null;
	}		
	
	public static int getFoodNeeds(SoldierType army) {
		
		if (army==null) {
			return 0;
		}
	
		switch(army) {
	
			case TOWER: 
				return 0;
		
			case SOLDIER:
				return 5;
				
			case HORSE:
				return 5;
				
			case BISHOP:
				return 5;
				
			case QUEEN:
				return 10;
				
			case KING:
				return 12;
				
			default:
				return 0;			
		}
	}		
}

