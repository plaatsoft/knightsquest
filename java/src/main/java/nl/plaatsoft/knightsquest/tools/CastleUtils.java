package nl.plaatsoft.knightsquest.tools;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import nl.plaatsoft.knightsquest.model.Castle;
import nl.plaatsoft.knightsquest.model.Land;
import nl.plaatsoft.knightsquest.model.LandType;
import nl.plaatsoft.knightsquest.model.Player;
import nl.plaatsoft.knightsquest.model.Soldier;
import nl.plaatsoft.knightsquest.model.SoldierType;

public class CastleUtils {

	final private static Logger log = Logger.getLogger(CastleUtils.class);	
	
	public static void checkFood(Castle castle) {
		
		log.info("check food [player="+castle.getPlayer().getId()+" castle="+castle.getId()+" size="+castle.getLands().size()+" foodAvailable="+castle.foodAvailable()+"]");
		
		if (castle.foodAvailable()<0) {
				
			/* Too less food for all soldiers of castle, they all die */			
			Iterator<Land> iter = castle.getLands().iterator();  						
			while(iter.hasNext()) {			
				Land land = (Land) iter.next();
				if (land.getSoldier()!=null) {
					Soldier soldier = land.getSoldier();
					if (soldier.getType()!=SoldierType.TOWER) {						
						soldier.setType(SoldierType.CROSS);								
						//log.info("Soldier [x="+land.getX()+"|y="+land.getY()+"|castleId="+castle.getId()+"] died!");
					}
				}									
			}							
		}
		
		//log.info("check food end");		
	}

	public static Castle createCastle(Player player) {
		
		Castle castle = null;
		
		boolean done = false;
		while (!done) {
			int x = MyRandom.nextInt(Constants.SEGMENT_X);
			int y = MyRandom.nextInt(Constants.SEGMENT_Y);
				
			if (LandUtils.getLand()[x][y].getType()==LandType.GRASS) {
																	
				castle = new Castle(player.getCastle().size(), x, y, player);
				castle.getLands().add(LandUtils.getLand()[x][y]);
				Soldier soldier = new Soldier(SoldierType.TOWER, player);
				LandUtils.getLand()[x][y].setSoldier(soldier);
				LandUtils.getLand()[x][y].setPlayer(player);
				
				player.getCastle().add(castle);
								
				List <Land> list1 = LandUtils.getNeigbors(x,y);
				Land land = MyRandom.nextLand(list1);
				if (land!=null) {
					land.setPlayer(player);
					castle.getLands().add(land);					
				}
							
				log.info("New Castle [id="+castle.getId()+"|x="+x+"|y="+y+"|playerId="+player.getId()+"] created!");
				
				done=true;
			}
		}
		return castle;
	}
}
