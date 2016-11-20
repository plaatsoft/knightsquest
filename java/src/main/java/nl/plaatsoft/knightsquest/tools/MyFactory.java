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

import nl.plaatsoft.knightsquest.model.LandDAO;
import nl.plaatsoft.knightsquest.model.PlayerDAO;
import nl.plaatsoft.knightsquest.model.RegionDAO;
import nl.plaatsoft.knightsquest.model.ScoreDAO;
import nl.plaatsoft.knightsquest.model.SoldierDAO;

public class MyFactory {

	static MyConfig config;
	static PlayerDAO playerDAO;
	static ScoreDAO scoreDAO;
	static LandDAO landDAO;
	static SoldierDAO soldierDAO;
	static RegionDAO regionDAO;
				
	public static MyConfig getConfig() {
		
		if (config==null) {
			config = new MyConfig();
		}
		return config;
	}
	
	public static PlayerDAO getPlayerDAO() {
		
		if (playerDAO==null) {
			playerDAO = new PlayerDAO();
		}
		return playerDAO;
	}
	
	public static ScoreDAO getScoreDAO() {
		
		if (scoreDAO==null) {
			scoreDAO = new ScoreDAO();
		}
		return scoreDAO;
	}
	
	public static LandDAO getLandDAO() {
		
		if (landDAO==null) {
			landDAO = new LandDAO();
		}
		return landDAO;
	}
	
	public static SoldierDAO getSoldierDAO() {
		
		if (soldierDAO==null) {
			soldierDAO = new SoldierDAO();
		}
		return soldierDAO;
	}
	
	public static RegionDAO getRegionDAO() {
		
		if (regionDAO==null) {
			regionDAO = new RegionDAO();
		}
		return regionDAO;
	}
	
	public static void clearFactory() {
		
		playerDAO = null;
		scoreDAO = null;
		landDAO = null;
		soldierDAO = null;
		regionDAO = null;
	}
}
