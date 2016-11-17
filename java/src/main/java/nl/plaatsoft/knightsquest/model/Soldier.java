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

import org.apache.log4j.Logger;

import nl.plaatsoft.knightsquest.utils.SoldierUtils;

public class Soldier {

	final static Logger log = Logger.getLogger( Soldier.class);
	
	private SoldierEnum type;
	private boolean enabled;
	private Player player;
	private Land land;	

	@Override
	public String toString() {
		return ""+type;
	}

	public Soldier(SoldierEnum type, Player player, Land land) {

		enabled = false;
		
		this.type = type;
		this.setLand(land);
		this.player = player;
	}
	
	public void draw() {
		
		//log.info("draw "+type+" playerId="+player.getId()+" [x="+land.getX()+"|y="+land.getY()+"]");
				
		int offset = 0;
		if ((land.getY() % 2)==1) {
			offset = land.getSize()*2;
		} 
	             	
		double posX = land.getSize()+(land.getX()*(land.getSize()*4))+offset-2;
		double posY = (land.getY()*land.getSize())+(land.getSize()/2)-2;
		
		land.getGc().setGlobalAlpha(1.0);			
		
		boolean red = false;
		if (!player.isBot()) {
			red = enabled;
		}
		land.getGc().drawImage(SoldierUtils.get(type, red), posX, posY);
	}
		
	public SoldierEnum getType() {
		return type;
	}

	public void setType(SoldierEnum type) {
		this.type = type;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Land getLand() {
		return land;
	}

	public void setLand(Land land) {
		this.land = land;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
