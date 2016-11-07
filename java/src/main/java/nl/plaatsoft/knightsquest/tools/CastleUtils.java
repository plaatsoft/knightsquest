package nl.plaatsoft.knightsquest.tools;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import nl.plaatsoft.knightsquest.model.Castle;
import nl.plaatsoft.knightsquest.model.Land;
import nl.plaatsoft.knightsquest.model.LandType;
import nl.plaatsoft.knightsquest.model.Player;
import nl.plaatsoft.knightsquest.model.Soldier;
import nl.plaatsoft.knightsquest.model.SoldierType;

public class CastleUtils {

	final private static Logger log = Logger.getLogger(CastleUtils.class);	
	
	final private static Random rnd = new Random();	
	
	public static Castle createCastle(Player player) {
		
		Castle castle = null;
		
		boolean done = false;
		while (!done) {
			int x = rnd.nextInt(Constants.SEGMENT_X);
			int y = rnd.nextInt(Constants.SEGMENT_Y);
				
			if (LandUtils.getLand()[x][y].getType()==LandType.GRASS) {
																	
				List <Land> list = LandUtils.getNeigbors(x,y);
				Iterator<Land> iter = list.iterator();
												
				castle = new Castle(player.getCastle().size(),x,y);
				castle.getLands().add(LandUtils.getLand()[x][y]);
				Soldier soldier = new Soldier(SoldierType.TOWER);
				LandUtils.getLand()[x][y].setSoldier(soldier);
				
				player.getCastle().add(castle);
				
				list = LandUtils.getNeigbors(x,y);
				iter = list.iterator();						
				while (iter.hasNext()) {				
					Land land = (Land) iter.next();
					castle.getLands().add(land);											
				}
				
				log.info("New Castle [id="+castle.getId()+"|x="+x+"|y="+y+"|playerId="+player.getId()+"] created!");
				
				done=true;
			}
		}
		return castle;
	}
}
