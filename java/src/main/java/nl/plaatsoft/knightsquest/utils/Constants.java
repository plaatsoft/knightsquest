/**
 *  @file
 *  @brief 
 *  @author wplaat
 *
 *  Copyright (C) 2008-2016 PlaatSoft
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package nl.plaatsoft.knightsquest.utils;

public class Constants {

	public final static String APP_NAME = "KnightsQuest";
	public final static String APP_VERSION = "0.2";
	public final static String APP_BUILD = "Build (17-11-2016)";
	
	public final static String APP_WS_NAME = "Java-KnightsQuest";
	public final static String APP_WS_URL = "service.plaatsoft.nl";
	public final static String APP_DONATE_URL = "http://www.plaatsoft.nl/donate";
	
	public final static int MUSIC_MODE = 1;
	
	public final static int WIDTH = 640; 
	public final static int HEIGHT = 480;		
	public final static double SCALE = 1; //0.55;
	public final static double OFFSET_X = 20; //-260;
	public final static double OFFSET_Y = 10; //-200;	
	
	public final static int SEGMENT_SIZE = 10;	
	public final static int SEGMENT_X = 15;
	public final static int SEGMENT_Y = 45;
	 
	public final static int MAP_WIDTH = SEGMENT_SIZE * 4 * (SEGMENT_X+1);
	public final static int MAP_HEIGHT = SEGMENT_SIZE * 2 * SEGMENT_Y;	
	
	public final static int START_PLAYERS = 6;
	public final static int START_TOWERS = 2;	
}
