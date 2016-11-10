package nl.plaatsoft.knightsquest.tools;

public class Constants {

	public final static String APP_NAME = "KnightsQuest";
	public final static String APP_VERSION = "0.1";
	public final static String APP_BUILD = "Build (09-11-2016)";
	
	public final static String APP_WS_NAME = "Java-KnightsQuest";
	public final static String APP_WS_URL = "https://service.plaatsoft.nl";
	
	public final static int BOTS_MODE = 0;
	
	public final static int WIDTH = 1200; 
	public final static int HEIGHT = 800;	
	
	public final static double SEGMENT_SCALE = 0.20;
	public final static int SEGMENT_SIZE = 20;
	
	public final static int SEGMENT_X = 8;
	public final static int SEGMENT_Y = 20;
	
	public final static int MAP_WIDTH = SEGMENT_SIZE * 4 * (SEGMENT_X+1);
	public final static int MAP_HEIGHT = SEGMENT_SIZE * 2 * SEGMENT_Y;	
	
	public final static int START_PLAYERS = 4;
	public final static int START_TOWERS = 1;	
	
	public final static int CASTLE_NEW_SOLDIER_TURNS = 4;
}
