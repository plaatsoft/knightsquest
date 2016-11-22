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

public class Player {

	// Player Generic 
	private int id;				
	private boolean bot = false;
	private List <Region> region = new ArrayList<Region>();

	// Player Statistics
	private int conquer=0;
	private int upgrades=0;
	private int moves=0;
	private int creates=0;
		
	public Player(int id, boolean bot) {
		this.bot = bot;
		this.id = id;
	}
	
	public void draw() {		
		Iterator<Region> iter = region.iterator();  
		while (iter.hasNext()) {
			Region region = (Region) iter.next();
			region.draw();
		}
	}
		
	public String toString() {
		return "playerId="+id;
	}
		
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public List<Region> getRegion() {
		return region;
	}
	
	public void setRegion(List<Region> region) {
		this.region = region;
	}

	public boolean isBot() {
		return bot;
	}

	public void setBot(boolean bot) {
		this.bot = bot;
	}	
	
	public int getUpgrades() {
		return upgrades;
	}

	public void setUpgrades(int upgrades) {
		this.upgrades = upgrades;
	}

	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

	public int getCreates() {
		return creates;
	}

	public void setCreates(int creates) {
		this.creates = creates;
	}

	public int getConquer() {
		return conquer;
	}

	public void setConquer(int conquer) {
		this.conquer = conquer;
	}	
}
