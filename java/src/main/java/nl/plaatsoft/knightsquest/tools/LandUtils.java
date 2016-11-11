package nl.plaatsoft.knightsquest.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import nl.plaatsoft.knightsquest.model.Land;
import nl.plaatsoft.knightsquest.model.LandType;
import nl.plaatsoft.knightsquest.model.Player;
import nl.plaatsoft.knightsquest.model.SoldierType;

public class LandUtils {
	
	final static Logger log = Logger.getLogger( LandUtils.class);
	
	private static Land[][] land = new Land[Constants.SEGMENT_X][Constants.SEGMENT_Y]; 	
		
	//private static Image water = new Image("images/water.png");
	//private static Image ocean = new Image("images/ocean.png");
	private static Image forest = new Image("images/forest.png");
	private static Image coast = new Image("images/coast.png");
	private static Image rock = new Image("images/rock.png");
	private static Image grass = new Image("images/grass.png");
		
	public static void getTexture(GraphicsContext gc, LandType type) {
		
		switch(type) {
		
			case FOREST:
					gc.setFill(new ImagePattern(forest, 0, 0, 1, 1, true));
					break;	
					
			case GRASS:
					gc.setFill(new ImagePattern(grass, 0, 0, 1, 1, true));
					break;	
					
			case COAST: 
					gc.setFill(new ImagePattern(coast, 0, 0, 1, 1, true));
					break;	
				
			case MOUNTAIN:
					gc.setFill(new ImagePattern(rock, 0, 0, 1, 1, true));
					break;
				
			case WATER:
					gc.setFill(Color.BLUE);
					//gc.setFill(new ImagePattern(water, 0, 0, 1, 1, true));
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
	
	public static List <Land> getUpgradeSoldiers(int x, int y, Player player) {
		
		List <Land> list2 = new ArrayList<Land>();
		
		List <Land> list1 = LandUtils.getNeigbors(x, y);
		Iterator<Land> iter1 = list1.iterator();
						
		while (iter1.hasNext()) {				
			Land land = (Land) iter1.next();
			if ((land.getPlayer()!=null) &&
				land.getPlayer().equals(player) &&
				(land.getSoldier()!=null) && 
				(land.getSoldier().getType()==SoldierType.SOLDIER)) 
			{
					list2.add(land);
			}
		}	

		return list2;
	}

	public static List <Land> getEnemyLand(int x, int y, Player player) {
		
		List <Land> list2 = new ArrayList<Land>();
		
		List <Land> list1 = LandUtils.getNeigbors(x, y);
		Iterator<Land> iter1 = list1.iterator();
						
		while (iter1.hasNext()) {				
			Land land = (Land) iter1.next();
			if ((land.getType()!=LandType.WATER) && (land.getType()!=LandType.OCEAN)) {
				
			 	 if ((land.getPlayer()!=null) && !land.getPlayer().equals(player)) {
			 		if ((land.getSoldier()!=null) && (land.getSoldier().getType()==SoldierType.TOWER)) {
			 			// no nothing
			 		} else {
			 			list2.add(land);
			 		}
				}
			}
		}	
		return list2;
	}

	public static List <Land> getOwnLand(int x, int y, Player player) {
		
		List <Land> list2 = new ArrayList<Land>();
		
		List <Land> list1 = LandUtils.getNeigbors(x, y);
		Iterator<Land> iter1 = list1.iterator();
						
		while (iter1.hasNext()) {				
			Land land = (Land) iter1.next();
			if ((land.getType()!=LandType.WATER) && (land.getType()!=LandType.OCEAN)) {				
				if ((land.getPlayer()!=null) && land.getPlayer().equals(player)) {
					if ((land.getSoldier()!=null) && (land.getSoldier().getType()!=SoldierType.CROSS)) {
						// Soldier on own land, skip it.
					} else {
						// log.info("land [x="+land.getX()+"|y="+land.getY()+"|player="+land.getPlayer()+"|type="+land.getType()+"|soldier="+land.getSoldier()+"]");
						list2.add(land);
					}
				}
			}
		}			
		return list2;
	}

	
	public static List <Land> getNewLand(int x, int y) {
		
		List <Land> list2 = new ArrayList<Land>();
		
		List <Land> list1 = LandUtils.getNeigbors(x, y);
		Iterator<Land> iter1 = list1.iterator();						
		while (iter1.hasNext()) {			
			Land land = (Land) iter1.next();			
			if ((land.getType()!=LandType.WATER) && (land.getType()!=LandType.OCEAN) && (land.getPlayer()==null)) {
				//log.info("land [x="+land.getX()+"|y="+land.getY()+"|player="+land.getPlayer()+"|type="+land.getType()+"|soldier="+land.getSoldier()+"]");
				list2.add(land);				
			}
		}			
		return list2;
	}
	
	public static List <Land> getRegionLand(int x, int y, Player player) {
		
		List <Land> list2 = new ArrayList<Land>();
		
		List <Land> list1 = LandUtils.getNeigbors(x, y);
		Iterator<Land> iter1 = list1.iterator();						
		while (iter1.hasNext()) {			
			Land land = (Land) iter1.next();			
			if ((land.getType()!=LandType.WATER) && (land.getType()!=LandType.OCEAN) && 
				(land.getPlayer()!=null) && land.getPlayer().equals(player) && land.getRegion()==0) {				
					list2.add(land);				
			}
		}			
		return list2;
	}
	
		
	/**
	 * Get Neighers of select x,y coordinate
	 * @param x
	 * @param y
	 * @return
	 */
	public static List <Land> getNeigbors(int x, int y) {
				
		List <Land> list = new ArrayList<Land>();
		
		if (y+1<Constants.SEGMENT_Y) {
			list.add(land[x][y+1]);
		}
		
		if (y-1>=0) {
			list.add(land[x][y-1]);
		}
		
		if (y+2<Constants.SEGMENT_Y) {
			list.add(land[x][y+2]);
		}
		
		if (y-2>=0) {
			list.add(land[x][y-2]);
		}
		
		if (y%2==1) {	
			if ((x+1<Constants.SEGMENT_X) && (y+1<Constants.SEGMENT_Y)) {
				list.add(land[x+1][y+1]);
			}
			
			if ((x+1<Constants.SEGMENT_X) && (y-1>=0)) {
				list.add(land[x+1][y-1]);
			}						
		} else {
			
			if ((x-1>=0) && (y+1<Constants.SEGMENT_Y)) {
				list.add(land[x-1][y+1]);
			}
			
			if ((x-1>=0) && (y-1>=0)) {
				list.add(land[x-1][y-1]);
			}			
		}
		
		//log.info("getNeigbors [x="+x+"|y="+y+"|size="+list.size()+"]");
		return list;
	}
		
	public static List <Land> getNeigbors2(int x, int y) {
		
		List <Land> list = new ArrayList<Land>();
		
		if (y-4>=0) {
			list.add(land[x][y-4]);
		}
		
		if (y-3>=0) {
			list.add(land[x][y-3]);
		}
		
		if ((x+1<Constants.SEGMENT_X) && (y-2>=0)) {
			list.add(land[x+1][y-2]);
		}
				
		if (x+1<Constants.SEGMENT_X) {
			list.add(land[x+1][y]);
		}
		
		if ((x+1<Constants.SEGMENT_X) && (y+2<Constants.SEGMENT_Y)) {
			list.add(land[x+1][y+2]);
		}
		
		if (y+3<Constants.SEGMENT_Y) {
			list.add(land[x][y+3]);
		}
		
		if (y+4<Constants.SEGMENT_Y) {
			list.add(land[x][y+4]);
		}
			
		if ((x-1>=0) && (y+2<Constants.SEGMENT_Y)) {
			list.add(land[x-1][y+2]);
		}
				
		if (x-1>=0) {
			list.add(land[x-1][y]);
		}
		
		if ((x-1>=0) && (y-2>=0)) {
			list.add(land[x-1][y-2]);
		}
				
		if (y%2==1) {	
			
			if ((x+1<Constants.SEGMENT_X) && (y-3>=0)) {
				list.add(land[x+1][y-3]);
			}
			
			if ((x+1<Constants.SEGMENT_X) && (y+3<Constants.SEGMENT_Y)) {
				list.add(land[x+1][y+3]);
			}
		} else {
			if ((x-1>=0) && (y+3<Constants.SEGMENT_Y)) {
				list.add(land[x-1][y+3]);
			}
						
			if ((x-1>=0) && (y-3>=0)) {
				list.add(land[x-1][y-3]);
			}
		}
				
		//log.info("getNeigbors [x="+x+"|y="+y+"|size="+list.size()+"]");
		return list;
	}
	
	
	
	private static void optimizeMap() {
		
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				if (land[x][y].getType()==LandType.COAST) {
					
					int found=0;
					List <Land> list = getNeigbors(x,y);
					Iterator<Land> iter = list.iterator();    	
					while (iter.hasNext()) {
						Land land = (Land) iter.next();
						if (land.getType()==LandType.WATER) {
							found=1;
						}
					}				
					if (found==0) {
					    land[x][y].setType(LandType.GRASS);
					}
				};				
			}
		}		
	}

	private static void createForestMountain() {
				
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				if ((land[x][y].getType()==LandType.GRASS) && (MyRandom.nextInt(2)==1)) {
					
					if (MyRandom.nextInt(2)==1) {
						land[x][y].setType(LandType.FOREST);
					} else {
						land[x][y].setType(LandType.MOUNTAIN);
					}
				};				
			}
		}		
	}


	private static void createOcean() {
		
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				if (land[x][y].getType()==LandType.NONE) {					
					land[x][y].setType(LandType.OCEAN);
				};				
			}
		}		
	}

	private static void createWater() {
		
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				if (land[x][y].getType()==LandType.COAST) {					
					List <Land> list = getNeigbors(x,y);
					Iterator<Land> iter = list.iterator();    	
					while (iter.hasNext()) {
						Land segment = (Land) iter.next();
						if (segment.getType()==LandType.NONE) {
							segment.setType(LandType.WATER);
						}
					}					
				};				
			}
		}		
	}

	private static void createCoast() {
		
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				if (land[x][y].getType()==LandType.GRASS) {					
					List <Land> list = getNeigbors(x,y);
					Iterator<Land> iter = list.iterator();    	
					while (iter.hasNext()) {
						Land segment = (Land) iter.next();
						if (segment.getType()==LandType.NONE) {
							segment.setType(LandType.COAST);
						}
					}
					
				};				
			}
		}		
	}
	
	
	private static void createGrass() {
						
		for (int i=0; i<(Constants.SEGMENT_X*0.75); i++) {
			
			int x = MyRandom.nextInt(Constants.SEGMENT_X);
			int y = MyRandom.nextInt(Constants.SEGMENT_Y);

			for (int j=0; j<Constants.SEGMENT_Y; j++) {
				
				land[x][y].setType(LandType.GRASS);
		 
				List <Land> list = getNeigbors(x,y);
				Iterator<Land> iter = list.iterator();
									
				int next = MyRandom.nextInt(list.size()); 
				int count=0;
				
				while (iter.hasNext()) {				
					Land land = (Land) iter.next();
					land.setType(LandType.GRASS);
					if (count++==next) {
						
						x=land.getX();
						y=land.getY(); 

						break;
					}					
				}
			}
		}
	}
	
	public static void createMap() {
					
		for (int x=0; x<Constants.SEGMENT_X; x++) {	
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
				land[x][y] = new Land(x, y, LandType.NONE);				
			}
		}
		
		createGrass();
		createCoast();	
		createWater();
		createOcean();
		createForestMountain();
		optimizeMap();			
	}		
		
	public static void drawMap(GraphicsContext gc) {
		for (int x=0; x<Constants.SEGMENT_X; x++) {					
			for (int y=0; y<Constants.SEGMENT_Y; y++) {				
				land[x][y].draw(gc);				
			}
		}		
	}
	
	public static Land[][] getLand() {
		return land;
	}	
}
