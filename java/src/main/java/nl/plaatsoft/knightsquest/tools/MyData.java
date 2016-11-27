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

package nl.plaatsoft.knightsquest.tools;

import java.util.UUID;

import org.apache.log4j.Logger;

import nl.plaatsoft.knightsquest.ui.Constants;

public class MyData {
	
	final static Logger log = Logger.getLogger( MyData.class);
	
	public static final int MODE_1P = 1;
	public static final int MODE_2P = 2;
	
	private static String id = UUID.randomUUID().toString();
	
	private static int level = 0;
	private static int map = 1;
	private static int mode = 1;
		
	public static int getPlayers() {
		
		int amount=getBots() + 1;
			
		if (mode==MODE_2P) {
			
			amount = 2;
		}	
		return amount; 
	}
	
	public static int getSeed() {
		
		switch (map) {
		
			case 1: return 1234;
			case 2: return 64;
			case 3: return 4341;
			case 4: return 66;
			case 5: return 70;
			case 6: return 72;
			
			default: return map;
		}
	}
	
	
	public static int getBots() {
		
		int value = level+1;
		if (value>5) {
			value = 5;
		}
		
		if (mode==MODE_2P) {
			value=0;
		}
		
		return value;
	}
	
	public static int getLands() {		
		int value = 3;		
		return value;
	}
	
	public static int getTowers() {
		
		int value = Math.round(level/3)+1;
		if (value>3) {
			value = 3;
		}
		
		if (mode==MODE_2P) {
			value=1;
		}
		
		return value;
	}

	public static int getChanceNewSoldier() {
		
		int value=1;
		
		if (level==0) {
			// 25%			
			value = 4;
		}
		
		if (level==1) {
			// 33%		
			value = 3;
		}
		
		if (level==2) {
			// 50%
			value = 2;
		}
		
		if (level>2) {
			// 100%
			value = 1;
		}		
		return value;
	}
	
	public static int getHarbors() {
		
		int value = 0;
		
		if (level>0) {
			value = 4;
		}
		
		if (level>1) {
			value = 6;
		}
		
		if (level>2) {
			value = 8;
		}
		
		if (level>3) {
			value = 10;
		}		
		return value;
	}
	
	public static int getLevel() {
		return level;
	}
	
	public static void setLevel(int level) {
		MyData.level = level;
	}

	public static void setMode(int mode) {
		MyData.mode = mode;
	}
	
	public static int getMode() {
		return mode;
	}
	
	public static int getMap() {
		return map;
	}

	public static void setMap(int map) {
		
		MyRandom.clear();
		MyData.map = map;
	}
	
	public static int getNextMap(int map) {
		
		int value = 0;
		
		int tmp1 = (map/10) * 10;
		int tmp2 = (map%10);
		tmp2++;
		if (tmp2>6) {
			tmp2=1;
			tmp1+=10;			
		}
		value = tmp1+tmp2;
				
		/* Maximum map reached */
		if (tmp1>Constants.MAX_LEVELS) {
			value = 0;
		}
		
		return value;
	}

	public static String getId() {
		return id;
	}
}
