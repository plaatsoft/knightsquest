package nl.plaatsoft.knightsquest.tools;

public class Constants {

	public final static String APP_NAME = "KnightsQuest";
	public final static String APP_VERSION = "0.1";
	public final static String APP_BUILD = "Build (09-11-2016)";
	
	public final static String APP_WS_NAME = "Java-KnightsQuest";
	public final static String APP_WS_URL = "https://service.plaatsoft.nl";
	
	public final static int BOTS_MODE = 1;
	
	public final static int WIDTH = 800; 
	public final static int HEIGHT = 600;		
	public final static double SCALE = 0.42;
	public final static int OFFSET_X = -520;
	public final static int OFFSET_Y = -400;	
	
	public final static int SEGMENT_SIZE = 19;	
	public final static int SEGMENT_X = 24;
	public final static int SEGMENT_Y = 72;
	
	public final static int MAP_WIDTH = SEGMENT_SIZE * 4 * (SEGMENT_X+1);
	public final static int MAP_HEIGHT = SEGMENT_SIZE * 2 * SEGMENT_Y;	
	
	public final static int START_PLAYERS = 6;
	public final static int START_TOWERS = 8;	
	
	public final static int CASTLE_NEW_SOLDIER_TURNS = 4;
}
