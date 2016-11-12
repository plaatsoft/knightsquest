package nl.plaatsoft.knightsquest.tools;

public class Constants {

	public final static String APP_NAME = "KnightsQuest";
	public final static String APP_VERSION = "0.1";
	public final static String APP_BUILD = "Build (09-11-2016)";
	
	public final static String APP_WS_NAME = "Java-KnightsQuest";
	public final static String APP_WS_URL = "https://service.plaatsoft.nl";
	
	public final static int BOTS_MODE = 1;
	
	public final static int WIDTH = 640; 
	public final static int HEIGHT = 480;		
	public final static double SCALE = 0.55;
	public final static double OFFSET_X = -260;
	public final static double OFFSET_Y = -200;	
	
	public final static int SEGMENT_SIZE = 19;	
	public final static int SEGMENT_X = 15;
	public final static int SEGMENT_Y = 45;
	
	public final static int MAP_WIDTH = SEGMENT_SIZE * 4 * (SEGMENT_X+1);
	public final static int MAP_HEIGHT = SEGMENT_SIZE * 2 * SEGMENT_Y;	
	
	public final static int START_PLAYERS = 2;
	public final static int START_TOWERS = 2;	
	
	public final static int CASTLE_NEW_SOLDIER_TURNS = 4;
}
