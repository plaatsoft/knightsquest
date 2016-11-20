package nl.plaatsoft.knightsquest.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import nl.plaatsoft.knightsquest.tools.MyFactory;
import nl.plaatsoft.knightsquest.tools.MyRandom;
import nl.plaatsoft.knightsquest.ui.Constants;

public class LandDAO {

	final static Logger log = Logger.getLogger( LandDAO.class);
	
	private Land[][] lands = new Land[Constants.SEGMENT_X][Constants.SEGMENT_Y];
	
	private Image water = new Image("images/water.png");
	//private static Image ocean = new Image("images/ocean.png");
	private Image forest = new Image("images/forest.png");
	private Image coast = new Image("images/coast.png");
	private Image rock = new Image("images/rock.png");
	private Image grass = new Image("images/grass.png");
			
	public void getTexture(GraphicsContext gc, LandEnum type) {
		
		switch(type) {
		
			case FOREST:
					gc.setFill(new ImagePattern(forest, 0, 0, 1, 1, true));
					break;	
					
			case GRASS:
					//gc.setFill(Color.DARKGREEN);
					gc.setFill(new ImagePattern(grass, 0, 0, 1, 1, true));
					break;	
					
			case COAST: 
					//gc.setFill(Color.BURLYWOOD);
					gc.setFill(new ImagePattern(coast, 0, 0, 1, 1, true));
					break;	
				
			case MOUNTAIN:
					gc.setFill(new ImagePattern(rock, 0, 0, 1, 1, true));
					break;
				
			case WATER:
					//gc.setFill(Color.BLUE);
					gc.setFill(new ImagePattern(water, 0, 0, 1, 1, true));
					break;
					
			case OCEAN:
					gc.setFill(Color.DARKBLUE);
					//gc.setFill(new ImagePattern(ocean, 0, 0, 1, 1, true));
					break;
					
			default:
					gc.setFill(Color.BLACK);
					break;
		}
	}
	
	public List <Land> getUpgradeSoldiers(Land land) {
		
		List <Land> list2 = new ArrayList<Land>();
		
		List <Land> list1 = getNeigbors(land);
		Iterator<Land> iter1 = list1.iterator();
						
		while (iter1.hasNext()) {				
			Land land1 = (Land) iter1.next();
			if ((land1.getPlayer()!=null) &&
				land1.getPlayer().equals(land.getPlayer()) &&
				(land1.getSoldier()!=null) && 
				(land1.getSoldier().getType()==SoldierEnum.PAWN)) 
			{
					list2.add(land1);
			}
		}	

		return list2;
	}

	public List <Land> getBotEnemyLand(Land land) {
		
		List <Land> list2 = new ArrayList<Land>();
		
		List <Land> list1 =getNeigbors(land);
		Iterator<Land> iter1 = list1.iterator();
						
		while (iter1.hasNext()) {				
			Land land1 = (Land) iter1.next();
			if ((land1.getType()!=LandEnum.WATER) && (land1.getType()!=LandEnum.OCEAN)) {
				
			 	 if ((land1.getPlayer()!=null) && !land1.getPlayer().equals(land.getPlayer())) {
			 		if ((land1.getSoldier()!=null) && (land1.getSoldier().getType()==SoldierEnum.TOWER)) {
			 			// no nothing
			 		} else {
			 			list2.add(land1);
			 		}
				}
			}
		}	
		return list2;
	}

	public List <Land> getBotOwnLand(Land land) {
		
		List <Land> list2 = new ArrayList<Land>();
		
		List <Land> list1 = getNeigbors(land);
		Iterator<Land> iter1 = list1.iterator();
						
		while (iter1.hasNext()) {				
			Land land1 = (Land) iter1.next();
			if ((land1.getType()!=LandEnum.WATER) && (land1.getType()!=LandEnum.OCEAN)) {				
				if ((land1.getPlayer()!=null) && land1.getPlayer().equals(land.getPlayer())) {
					if ((land1.getSoldier()!=null) && (land1.getSoldier().getType()!=SoldierEnum.TOWER)) {
						// Soldier on own land, skip it.
					} else {
						// log.info("land [x="+land.getX()+"|y="+land.getY()+"|player="+land.getPlayer()+"|type="+land.getType()+"|soldier="+land.getSoldier()+"]");
						list2.add(land1);
					}
				}
			}
		}			
		return list2;
	}
	
	public List <Land> getBotNewLand(Land land) {
		
		List <Land> list2 = new ArrayList<Land>();
		
		List <Land> list1 = getNeigbors(land);
		Iterator<Land> iter1 = list1.iterator();						
		while (iter1.hasNext()) {			
			Land land1 = (Land) iter1.next();			
			if ((land1.getType()!=LandEnum.WATER) && (land1.getType()!=LandEnum.OCEAN) && (land1.getPlayer()==null)) {
				//log.info("land [x="+land.getX()+"|y="+land.getY()+"|player="+land.getPlayer()+"|type="+land.getType()+"|soldier="+land.getSoldier()+"]");
				list2.add(land1);				
			}
		}			
		return list2;
	}

	public List <Land> getBotRegionLand(Land land) {
		
		List <Land> list2 = new ArrayList<Land>();
		
		List <Land> list1 = getNeigbors(land);
		Iterator<Land> iter1 = list1.iterator();						
		while (iter1.hasNext()) {			
			Land land1 = (Land) iter1.next();			
			if ((land1.getType()!=LandEnum.WATER) && (land1.getType()!=LandEnum.OCEAN) && 
				(land1.getPlayer()!=null) && land1.getPlayer().equals(land.getPlayer()) && land1.getRegion()==0) {				
					list2.add(land1);				
			}
		}			
		return list2;
	}
	
	public Land getPlayerSelectedLand(double mouseX, double mouseY) {
		
		for (int x=0; x<Constants.SEGMENT_X; x++) {			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			    if (lands[x][y].getPolygon().contains(mouseX, mouseY)) {
			       return lands[x][y];
			    }		
			}
		}	
		return null;
	}
	
	public void moveSoldier(Land source, Land destination) {
		
		Region srcRegion = MyFactory.getRegionDAO().getRegion(source);
		Region dstRegion = MyFactory.getRegionDAO().getRegion(destination);
		
		//log.info("Move soldier ["+source.getX()+","+source.getY()+"]->["+destination.getX()+","+destination.getY()+"]");
		
		if ( (destination.getSoldier()!=null) && (destination.getPlayer()!=null) && destination.getPlayer().equals(source.getPlayer())) {
			
			// Soldier upgrade			
			if (destination.getSoldier().getType()!=SoldierEnum.PAWN) {
				// upgrade only allowed with Pawn, so skip upgrade
				return;
			}
			
			SoldierEnum nextType = MyFactory.getSoldierDAO().upgrade(source.getSoldier().getType());				
			if (srcRegion.foodAvailable()<=MyFactory.getSoldierDAO().food(nextType)) {
				//log.info("Not food enough, upgrade skipped");
				// Not enough food, so skip upgrade.
				return;
			}
			source.getSoldier().setType(nextType);
		}
		
		destination.setSoldier(source.getSoldier());
		destination.setPlayer(source.getPlayer());		
		destination.getSoldier().setLand(destination);		
		destination.getSoldier().setEnabled(false);
			
		source.setSoldier(null);
				
		// Remove land from old region		
		if (dstRegion!=null) {
			dstRegion.getLands().remove(destination);
		}
		
		// Add land to new region
		srcRegion.getLands().add(destination);		
	}
	
	public void createSoldier(Land source, Land destination) {
		
		Region srcRegion = MyFactory.getRegionDAO().getRegion(source);
		Region dstRegion = MyFactory.getRegionDAO().getRegion(destination);
		
		Soldier soldier = new Soldier(SoldierEnum.PAWN, source.getPlayer(), destination);
		
		source.getSoldier().setEnabled(false);
		
		destination.setSoldier(soldier);
		destination.setPlayer(source.getPlayer());		
		destination.getSoldier().setLand(destination);		
		destination.getSoldier().setEnabled(false);
		
		// Remove land from old region		
		if (dstRegion!=null) {
			dstRegion.getLands().remove(destination);			
		}
		
		// Add land to new region
		srcRegion.getLands().add(destination);
	}
		
	public void resetSelected() {

		for (int x=0; x<Constants.SEGMENT_X; x++) {			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
				lands[x][y].setDestination(false);
				lands[x][y].setSource(false);
			}		
		}
	}
		
	public boolean getPlayerLandHasFriendlyNeigbor(Land land, Player player) {
			
		List <Land> list1 = getNeigbors(land);
		Iterator<Land> iter1 = list1.iterator();						
		while (iter1.hasNext()) {			
			Land land1 = (Land) iter1.next();			
			if ((land1.getPlayer()!=null) && land1.getPlayer().equals(player)) {				
			   return true;		
			}
		}			
		return false;
	}

	public void setPlayerSoldierMoveDestinations(Land land) {
				
		List <Land> list1;
		
		if (land.getSoldier().getType()==SoldierEnum.PAWN) {
			// Pawn can move to two land tills per turn
			list1 = getNeigbors2(land);
		} else {
			// All other soldier types can only move one land till per turn
			list1 = getNeigbors(land);
		}
		Iterator<Land> iter1 = list1.iterator();
						
		while (iter1.hasNext()) {				
			Land land1 = (Land) iter1.next();
			if ( (land1.getType()!=LandEnum.WATER) && 
				 (land1.getType()!=LandEnum.OCEAN)) {
			
				if (land.getSoldier().getType()==SoldierEnum.PAWN) {
					if (!getPlayerLandHasFriendlyNeigbor(land1, land.getPlayer())) {
						continue;
					}
				}
				
				// Target land is free
				if (land1.getPlayer()==null) {
					 land1.setDestination(true);
					 continue;
				 }
				 
				 // Target land has no soldier
				 if (land1.getSoldier()==null) {
					 land1.setDestination(true);
					 continue;
				 }
				
				 // Target land has cross
				 if ((land1.getSoldier()!=null) && (land1.getSoldier().getType()==SoldierEnum.CROSS)) { 
					 land1.setDestination(true);
					 continue;
				 }
				 
				 // Target land is owner by bot but soldier is less strong
				 if ((land1.getPlayer()!=null) && (!land1.getPlayer().equals(land.getPlayer())) && (land1.getSoldier()!=null) && 
			    		 (land1.getSoldier().getType().getValue()<land.getSoldier().getType().getValue())) {
					 land1.setDestination(true);
					 continue;
				 }
				 				 
				 // Target land is owned, Pawn soldier available and there is food available for upgrade. 
				 Region region = MyFactory.getRegionDAO().getRegion(land);
				 SoldierEnum nextType = MyFactory.getSoldierDAO().upgrade(land.getSoldier().getType());	
				 
				 if ((land1.getPlayer()!=null) && land1.getPlayer().equals(land.getPlayer()) && 
					 (land1.getSoldier()!=null) && (land1.getSoldier().getType()==SoldierEnum.PAWN) &&
					 (region.foodAvailable()>MyFactory.getSoldierDAO().food(nextType))) {
					 land1.setDestination(true);
					 continue;				 
				 }
			}
		}	
	}
	
	public void setPlayerNewSoldierDestinations(Land land) {
		
		List <Land> list1 = getNeigbors(land);
		Iterator<Land> iter1 = list1.iterator();
						
		while (iter1.hasNext()) {				
			Land land1 = (Land) iter1.next();
			if ( (land1.getType()!=LandEnum.WATER) && 
				 (land1.getType()!=LandEnum.OCEAN)) {
			
				 // Target land is free
				 if (land1.getPlayer()==null) {
					 land1.setDestination(true);
					 continue;
				 }
				
				// Target land without soldier
				 if (land1.getSoldier()==null) {
					 land1.setDestination(true);
					 continue;
				 }
				 
				// Target land has cross
				 if ((land1.getSoldier()!=null) && (land1.getSoldier().getType()==SoldierEnum.CROSS)) { 
					 land1.setDestination(true);
					 continue;
				 }
			}
		}	
	}
	
	public void doPlayerActions(Land land3, Player player) {
	
		if (land3.isDestination()) {
						
			// Search source land
			List <Land> list1 = getNeigbors2(land3);
			Iterator<Land> iter1 = list1.iterator();						
			while (iter1.hasNext()) {			
				Land land2 = (Land) iter1.next();	
				if (land2.isSource()) {
				
					if (land2.getSoldier().getType()==SoldierEnum.TOWER) { 
													
						createSoldier(land2, land3);
						
					} else {
					
						moveSoldier(land2, land3);
					}
					
					/* Rebuild all regions */
					int regions = MyFactory.getRegionDAO().detectedRegions();		
					MyFactory.getRegionDAO().rebuildRegions(regions);	
					break;
				}
			}	
			
			resetSelected();
			
		} else {
						
			if ( (land3.getPlayer()!=null) && 
			     (land3.getPlayer().equals(player)) && 
				 (land3.getSoldier()!=null) && 
				 land3.getSoldier().isEnabled() &&			     
				 (land3.getSoldier().getType()!=SoldierEnum.CROSS))
			{		
				
				if (land3.isSource()) {
				
					// Deselect soldier
					resetSelected();
					
				} else {
					
					// Select soldier
					resetSelected();
					
					/* Select source */
					land3.setSource(true);
					
					/* Select destination */				
					if (land3.getSoldier().getType()==SoldierEnum.TOWER) {
						setPlayerNewSoldierDestinations(land3);
					} else {
						setPlayerSoldierMoveDestinations(land3);
					}
				}
			}
		}	
	}
		
	/**
	 * Get Neighers of select x,y coordinate
	 * @param x
	 * @param y
	 * @return
	 */
	public List <Land> getNeigbors(Land land) {
				
		int x = land.getX();
		int y = land.getY();
		
		List <Land> list = new ArrayList<Land>();
		
		if (y+1<Constants.SEGMENT_Y) {
			list.add(lands[x][y+1]);
		}
		
		if (y-1>=0) {
			list.add(lands[x][y-1]);
		}
		
		if (y+2<Constants.SEGMENT_Y) {
			list.add(lands[x][y+2]);
		}
		
		if (y-2>=0) {
			list.add(lands[x][y-2]);
		}
		
		if (y%2==1) {	
			if ((x+1<Constants.SEGMENT_X) && (y+1<Constants.SEGMENT_Y)) {
				list.add(lands[x+1][y+1]);
			}
			
			if ((x+1<Constants.SEGMENT_X) && (y-1>=0)) {
				list.add(lands[x+1][y-1]);
			}						
		} else {
			
			if ((x-1>=0) && (y+1<Constants.SEGMENT_Y)) {
				list.add(lands[x-1][y+1]);
			}
			
			if ((x-1>=0) && (y-1>=0)) {
				list.add(lands[x-1][y-1]);
			}			
		}
		
		return list;
	}
		
	public List <Land> getNeigbors2(Land land) {
		
		int x = land.getX();
		int y = land.getY();
		
		List <Land> list = new ArrayList<Land>();
		
		if (y+1<Constants.SEGMENT_Y) {
			list.add(lands[x][y+1]);
		}
		
		if (y-1>=0) {
			list.add(lands[x][y-1]);
		}
		
		if (y+2<Constants.SEGMENT_Y) {
			list.add(lands[x][y+2]);
		}
		
		if (y-2>=0) {
			list.add(lands[x][y-2]);
		}
		
		if (y-4>=0) {
			list.add(lands[x][y-4]);
		}
		
		if (y-3>=0) {
			list.add(lands[x][y-3]);
		}
		
		if ((x+1<Constants.SEGMENT_X) && (y-2>=0)) {
			list.add(lands[x+1][y-2]);
		}
				
		if (x+1<Constants.SEGMENT_X) {
			list.add(lands[x+1][y]);
		}
		
		if ((x+1<Constants.SEGMENT_X) && (y+2<Constants.SEGMENT_Y)) {
			list.add(lands[x+1][y+2]);
		}
		
		if (y+3<Constants.SEGMENT_Y) {
			list.add(lands[x][y+3]);
		}
		
		if (y+4<Constants.SEGMENT_Y) {
			list.add(lands[x][y+4]);
		}
			
		if ((x-1>=0) && (y+2<Constants.SEGMENT_Y)) {
			list.add(lands[x-1][y+2]);
		}
				
		if (x-1>=0) {
			list.add(lands[x-1][y]);
		}
		
		if ((x-1>=0) && (y-2>=0)) {
			list.add(lands[x-1][y-2]);
		}
				
		if (y%2==1) {	
			
			if ((x+1<Constants.SEGMENT_X) && (y-3>=0)) {
				list.add(lands[x+1][y-3]);
			}
			
			if ((x+1<Constants.SEGMENT_X) && (y+3<Constants.SEGMENT_Y)) {
				list.add(lands[x+1][y+3]);
			}
			
			if ((x+1<Constants.SEGMENT_X) && (y+1<Constants.SEGMENT_Y)) {
				list.add(lands[x+1][y+1]);
			}
			
			if ((x+1<Constants.SEGMENT_X) && (y-1>=0)) {
				list.add(lands[x+1][y-1]);
			}			
			
		} else {
			if ((x-1>=0) && (y+3<Constants.SEGMENT_Y)) {
				list.add(lands[x-1][y+3]);
			}
						
			if ((x-1>=0) && (y-3>=0)) {
				list.add(lands[x-1][y-3]);
			}
			
			if ((x-1>=0) && (y+1<Constants.SEGMENT_Y)) {
				list.add(lands[x-1][y+1]);
			}
			
			if ((x-1>=0) && (y-1>=0)) {
				list.add(lands[x-1][y-1]);
			}		
		}
				
		return list;
	}
	
	public void scaleMap(double scale) {
		
		//log.info("scale="+scale);
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				lands[x][y].setScale(scale);	
			}
		}		
	}

	private void optimizeMap() {
		
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				if (lands[x][y].getType()==LandEnum.COAST) {
					
					int found=0;
					List <Land> list = getNeigbors(lands[x][y]);
					Iterator<Land> iter = list.iterator();    	
					while (iter.hasNext()) {
						Land land = (Land) iter.next();
						if (land.getType()==LandEnum.WATER) {
							found=1;
						}
					}				
					if (found==0) {
					    lands[x][y].setType(LandEnum.GRASS);
					}
				};				
			}
		}		
	}

	private void createForestMountain() {
				
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				if ((lands[x][y].getType()==LandEnum.GRASS) && (MyRandom.nextInt(2)==1)) {
					
					if (MyRandom.nextInt(2)==1) {
						lands[x][y].setType(LandEnum.FOREST);
					} else {
						lands[x][y].setType(LandEnum.MOUNTAIN);
					}
				};				
			}
		}		
	}

	private  void createWater() {
		
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				if (lands[x][y].getType()==LandEnum.COAST) {					
					List <Land> list = getNeigbors(lands[x][y]);
					Iterator<Land> iter = list.iterator();    	
					while (iter.hasNext()) {
						Land segment = (Land) iter.next();
						if (segment.getType()==LandEnum.OCEAN) {
							segment.setType(LandEnum.WATER);
						}
					}					
				};				
			}
		}		
	}

	private void createCoast() {
		
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				if (lands[x][y].getType()==LandEnum.GRASS) {					
					List <Land> list = getNeigbors(lands[x][y]);
					Iterator<Land> iter = list.iterator();    	
					while (iter.hasNext()) {
						Land segment = (Land) iter.next();
						if (segment.getType()==LandEnum.OCEAN) {
							segment.setType(LandEnum.COAST);
						}
					}
					
				};				
			}
		}		
	}
	
	
	private void createGrass() {
						
		for (int i=0; i<(Constants.SEGMENT_X*0.70); i++) {
			
			int x = MyRandom.nextInt(Constants.SEGMENT_X);
			int y = MyRandom.nextInt(Constants.SEGMENT_Y);

			for (int j=0; j<Constants.SEGMENT_Y; j++) {
				
				lands[x][y].setType(LandEnum.GRASS);
		 
				List <Land> list = getNeigbors(lands[x][y]);
				Iterator<Land> iter = list.iterator();
									
				int next = MyRandom.nextInt(list.size()); 
				int count=0;
				
				while (iter.hasNext()) {				
					Land land = (Land) iter.next();
					land.setType(LandEnum.GRASS);
					if (count++==next) {
						
						x=land.getX();
						y=land.getY(); 

						break;
					}					
				}
			}
		}
	}
	
	public void createMap(GraphicsContext gc, int size) {
					
		for (int x=0; x<Constants.SEGMENT_X; x++) {	
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
				lands[x][y] = new Land(gc, x, y, size, LandEnum.OCEAN);				
			}
		}
		
		createGrass();
		createCoast();	
		createWater();
		createForestMountain();
		optimizeMap();			
	}		
		
	public void drawMap() {
		for (int x=0; x<Constants.SEGMENT_X; x++) {					
			for (int y=0; y<Constants.SEGMENT_Y; y++) {				
				lands[x][y].draw();				
			}
		}		
	}
	
	public Land[][] getLands() {
		return lands;
	}	
}
