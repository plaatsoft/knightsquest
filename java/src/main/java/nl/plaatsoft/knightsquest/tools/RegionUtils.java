package nl.plaatsoft.knightsquest.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import nl.plaatsoft.knightsquest.model.Region;
import nl.plaatsoft.knightsquest.model.Land;
import nl.plaatsoft.knightsquest.model.LandType;
import nl.plaatsoft.knightsquest.model.Player;
import nl.plaatsoft.knightsquest.model.Soldier;
import nl.plaatsoft.knightsquest.model.SoldierType;

public class RegionUtils {

	final private static Logger log = Logger.getLogger(RegionUtils.class);	
	
	public static void checkFood(Region region) {
		
		log.info("check food [player="+region.getPlayer().getId()+" region="+region.getId()+" size="+region.getLands().size()+" foodAvailable="+region.foodAvailable()+"]");
		
		if (region.foodAvailable()<0) {
				
			/* Too less food for all soldiers of castle, they all die */			
			Iterator<Land> iter = region.getLands().iterator();  						
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

	public static Region createRegion(int regionId, Player player) {
		
		Region region = null;
		
		boolean done = false;
		while (!done) {
			int x = MyRandom.nextInt(Constants.SEGMENT_X);
			int y = MyRandom.nextInt(Constants.SEGMENT_Y);
								
			Land land = LandUtils.getLand()[x][y];
					
			if (land.getType()==LandType.GRASS) {
									
				Soldier soldier = new Soldier(SoldierType.TOWER, player);
				land.setSoldier(soldier);
				land.setPlayer(player);
				
				region = new Region(regionId, player);
				region.getLands().add(land);
				
				player.getRegion().add(region);
										
				List <Land> list2 = LandUtils.getNeigbors(x,y);
				Land land2 = MyRandom.nextLand(list2);
				if (land2!=null) {
					land.setPlayer(player);
					region.getLands().add(land2);					
				}
							
				log.info("New Region [id="+region.getId()+"|x="+x+"|y="+y+"|playerId="+player.getId()+"] created!");
				
				done=true;
			}
		}
		return region;
	}
	
	public static Land getTowerPosition(Region region) {
		
		Iterator<Land> iter = region.getLands().iterator();  						
		while(iter.hasNext()) {			
			Land land = (Land) iter.next();
			if ((land.getSoldier()!=null) && (land.getSoldier().getType()==SoldierType.TOWER)) {
				
				log.info("Tower ["+land.getX()+","+land.getY()+"]");
				return land;
			}
		}
		return null;
	}
	
	/* recursion land search */
	private static void search(Land land2, int regionId) {
		
		land2.setRegion(regionId);
		
		List <Land> list = LandUtils.getRegionLand(land2.getX(), land2.getY(), land2.getPlayer());
		Iterator <Land> iter = list.iterator();	
		while (iter.hasNext()) {						
			Land land = (Land) iter.next();
			search(land, regionId);
		}
	}
	
	public static int detectedRegions() {
		
		int regionId=0;
		
		// Reset all castles
		Land lands [][] = LandUtils.getLand();
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			for (int y=0; y<Constants.SEGMENT_Y; y++) {				
				lands[x][y].setRegion(regionId);
			}
		}
			
		// Find them again
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			for (int y=0; y<Constants.SEGMENT_Y; y++) {	
							
				Land land = lands[x][y];
				if ((land.getRegion()==0) && (land.getPlayer()!=null)) {
					
					regionId++;
					search(land, regionId);
				}
			}
		}
			
		return regionId;
	}
						
	public static void rebuildRegions(int amountOfRegions) {
				
		// Remove all region 
		Iterator<Player> iter1 = PlayerUtils.getPlayers().iterator();  	
		while (iter1.hasNext()) {
			Player player = (Player) iter1.next();
			
			List <Region> list2 = player.getRegion();
			Iterator <Region> iter2 = list2.iterator();	
			while (iter2.hasNext()) {	
				Region region = (Region) iter2.next();
				iter2.remove();
			}
		}
		
		Land lands [][] = LandUtils.getLand();
				
		// Detect new regions with castle and assign them to player again
		for (int regionId=1; regionId<=amountOfRegions; regionId++) {
		
			List <Land> list = new ArrayList<Land>();
			
			int foodAvailable = 0;
			int foodNeeded = 0;
			int castleCount=0;
			for (int x=0; x<Constants.SEGMENT_X; x++) {
				for (int y=0; y<Constants.SEGMENT_Y; y++) {				
					if (lands[x][y].getRegion()==regionId) {
						
						foodAvailable++;
						list.add(lands[x][y]);
						
						if (lands[x][y].getSoldier()!=null) {
							
							foodNeeded += SoldierUtils.getFoodNeeds(lands[x][y].getSoldier().getType());
							
							if (lands[x][y].getSoldier().getType()==SoldierType.TOWER) {								
								if (++castleCount>1) {
									
									/* Remove castle if there are more one castle in one region */
									//log.info("Castle remove");
									lands[x][y].setSoldier(null);
								}								
							}														
						}						
					}
				}				
			}
						
			if (foodAvailable<foodNeeded) {
				
				Iterator <Land> iter = list.iterator();	
				while (iter.hasNext()) {						
					Land land = (Land) iter.next();
					if (land.getSoldier()!=null) {
						
						// No food, soldiers die
						land.getSoldier().setType(SoldierType.CROSS);
					}
				}
			}
			
			//log.info("CastleId="+i+" landSize="+list.size());
			
			if ((castleCount==0) && (list.size()>1)) {
				
				// Create new castle.
				Land land = MyRandom.nextLand(list);
				if (land!=null) {
					//log.info("Castle created");
					land.setSoldier(new Soldier(SoldierType.TOWER,land.getPlayer()));
				}
			}
			
			// Castle is destroyed when region size is one.
			if (list.size()==1) {
				Iterator <Land> iter = list.iterator();	
				while (iter.hasNext()) {	
					Land land = (Land) iter.next();
					if ((land.getSoldier()!=null) && (land.getSoldier().getType()==SoldierType.TOWER)) {
						land.getSoldier().setType(SoldierType.CROSS);
					}
				}
			}
			
			// Create new region and add it to player
			Iterator <Land> iter = list.iterator();	
			if (iter.hasNext()) {	
				Land land = (Land) iter.next();			
				Player player = land.getPlayer();
				Region region = new Region(regionId,player);
				region.setLands(list);
				player.getRegion().add(region);
			}
		}		
	}
}
