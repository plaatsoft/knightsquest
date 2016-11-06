package nl.plaatsoft.knightsquest.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class MyMap {
	
	final static Logger log = Logger.getLogger( MyMap.class);
	
	private static MySegment[][] segment = new MySegment[Constants.SEGMENT_X][Constants.SEGMENT_Y]; 	
	private static Random rnd = new Random();
	
	//private static Image water = new Image("images/water.png");
	//private static Image ocean = new Image("images/ocean.png");
	private static Image forest = new Image("images/forest.png");
	private static Image coast = new Image("images/coast.png");
	private static Image rock = new Image("images/rock.png");
	private static Image grass = new Image("images/grass.png");
		
	public static void getTexture(GraphicsContext gc, MySegmentEnum type) {
		
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
		
		
	/**
	 * Get Neighers of select x,y coordinate
	 * @param x
	 * @param y
	 * @return
	 */
	public static List <MySegment> getNeigbors(int x, int y) {
		
		List <MySegment> list = new <MySegment> ArrayList();
		
		if (y+1<Constants.SEGMENT_Y) {
			list.add(segment[x][y+1]);
		}
		
		if (y-1>=0) {
			list.add(segment[x][y-1]);
		}
		
		if (y+2<Constants.SEGMENT_Y) {
			list.add(segment[x][y+2]);
		}
		
		if (y-2>=0) {
			list.add(segment[x][y-2]);
		}
		
		if (y%2==1) {	
			if ((x+1<Constants.SEGMENT_X) && (y+1<Constants.SEGMENT_Y)) {
				list.add(segment[x+1][y+1]);
			}
			
			if ((x+1<Constants.SEGMENT_X) && (y-1>=0)) {
				list.add(segment[x+1][y-1]);
			}						
		} else {
			
			if ((x-1>=0) && (y+1<Constants.SEGMENT_Y)) {
				list.add(segment[x-1][y+1]);
			}
			
			if ((x-1>=0) && (y-1>=0)) {
				list.add(segment[x-1][y-1]);
			}			
		}
		return list;
	}
	
	private static void optimizeMap() {
		
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				if (segment[x][y].getType()==MySegmentEnum.COAST) {
					
					int found=0;
					List <MySegment> list = getNeigbors(x,y);
					Iterator<MySegment> iter = list.iterator();    	
					while (iter.hasNext()) {
						MySegment segment = (MySegment) iter.next();
						if (segment.getType()==MySegmentEnum.WATER) {
							found=1;
						}
					}				
					if (found==0) {
					 segment[x][y].setType(MySegmentEnum.GRASS);
					}
				};				
			}
		}		
	}

	private static void createForestMountain() {
				
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				if ((segment[x][y].getType()==MySegmentEnum.GRASS) && (rnd.nextInt(2)==1)) {
					
					if (rnd.nextInt(2)==1) {
						segment[x][y].setType(MySegmentEnum.FOREST);
					} else {
						segment[x][y].setType(MySegmentEnum.MOUNTAIN);
					}
				};				
			}
		}		
	}


	private static void createOcean() {
		
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				if (segment[x][y].getType()==MySegmentEnum.NONE) {					
					segment[x][y].setType(MySegmentEnum.OCEAN);
				};				
			}
		}		
	}

	private static void createWater() {
		
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				if (segment[x][y].getType()==MySegmentEnum.COAST) {					
					List <MySegment> list = getNeigbors(x,y);
					Iterator<MySegment> iter = list.iterator();    	
					while (iter.hasNext()) {
						MySegment segment = (MySegment) iter.next();
						if (segment.getType()==MySegmentEnum.NONE) {
							segment.setType(MySegmentEnum.WATER);
						}
					}					
				};				
			}
		}		
	}

	private static void createCoast() {
		
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				if (segment[x][y].getType()==MySegmentEnum.GRASS) {					
					List <MySegment> list = getNeigbors(x,y);
					Iterator<MySegment> iter = list.iterator();    	
					while (iter.hasNext()) {
						MySegment segment = (MySegment) iter.next();
						if (segment.getType()==MySegmentEnum.NONE) {
							segment.setType(MySegmentEnum.COAST);
						}
					}
					
				};				
			}
		}		
	}
	
	
	private static void createGrass() {
						
		for (int i=0; i<=Constants.SEGMENT_X/2; i++) {
			
			int x = rnd.nextInt(Constants.SEGMENT_X-(Constants.BORDER/2));
			int y = rnd.nextInt(Constants.SEGMENT_Y-(Constants.BORDER*2));

			for (int j=0; j<Constants.SEGMENT_Y; j++) {
				
				segment[x][y].setType(MySegmentEnum.GRASS);
		 
				List <MySegment> list = getNeigbors(x,y);
				Iterator<MySegment> iter = list.iterator();
						
				int next = rnd.nextInt(list.size()); 
				int count=0;
				
				while (iter.hasNext()) {				
					MySegment segment = (MySegment) iter.next();
					segment.setType(MySegmentEnum.GRASS);
					if (count++==next) {
						
						x=segment.getX();
						y=segment.getY(); 

						break;
					}					
				}
			}
		}
	}
	
	public static void createMap() {
					
		for (int x=0; x<Constants.SEGMENT_X; x++) {	
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
				segment[x][y] = new MySegment(x, y, MySegmentEnum.NONE, Constants.SEGMENT_SIZE);				
			}
		}
		
		createGrass();
		createCoast();	
		createWater();
		createOcean();
		createForestMountain();
		optimizeMap();			
	}		
		
	public static void drawMap(GraphicsContext gc1, GraphicsContext gc2) {
		for (int x=0; x<Constants.SEGMENT_X; x++) {					
			for (int y=0; y<Constants.SEGMENT_Y; y++) {				
				segment[x][y].draw(gc1, gc2);				
			}
		}		
	}
	
	public static MySegment[][] getSegment() {
		return segment;
	}	
}
