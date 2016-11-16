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

package nl.plaatsoft.knightsquest.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import nl.plaatsoft.knightsquest.utils.SoldierUtils;

public class Region {

	final static Logger log = Logger.getLogger( Region.class);
	
	private int id;	
	private Player player;
	private List <Land> lands = new ArrayList<Land>();

	public Region(int id, Player player) {
		this.id = id;
		this.player = player;
	}
	
	public void draw() {
			
		//log.info("draw region [id="+id+"|landSize="+lands.size()+"]");
		
		Iterator<Land> iter1 = lands.iterator();  
		while (iter1.hasNext()) {
			Land land = (Land) iter1.next();
			land.draw();
		}
	}
	
	public int foodAvailable() {
		
		int foodDemand = 0;
		
		Iterator<Land> iter = lands.iterator();						
		while (iter.hasNext()) {				
			Land land = (Land) iter.next();
			if (land.getSoldier()!=null) {					
				foodDemand += SoldierUtils.getFoodNeeds(land.getSoldier().getType());
			}		
		}	
				
		int foodAvailable = lands.size()- foodDemand;	
		
		//log.info("regionId="+id+" foodDemand="+foodDemand+" foodProduction="+lands.size()+" foodAvailable="+foodAvailable);
		
		return foodAvailable;
	}
	
	public boolean checkNewLand(Land newLand) {

		boolean value = true;
		
		Iterator<Land> iter = lands.iterator();						
		while (iter.hasNext()) {				
			Land land = (Land) iter.next();
			if (land.equals(newLand)) {						
				value = false;
			}		
		}	
		
		//log.info("checkNewLand="+value);
		return value;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setLands(List<Land> lands) {
		this.lands = lands;
	}
	
	public List<Land> getLands() {
		return lands;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
