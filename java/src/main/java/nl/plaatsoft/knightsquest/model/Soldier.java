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

import javafx.scene.canvas.GraphicsContext;
import nl.plaatsoft.knightsquest.tools.Constants;
import nl.plaatsoft.knightsquest.tools.SoldierUtils;

public class Soldier {

	final static Logger log = Logger.getLogger( Soldier.class);
	
	private SoldierType type;
	private boolean moved = false;
	private Player player;

	@Override
	public String toString() {
		return ""+type;
	}

	public Soldier(SoldierType type, Player player) {

		this.type = type;
		this.player = player;
	}
	
	public void draw(GraphicsContext gc, int x, int y) {
		
		//log.info("draw soldier "+type+" [x="+x+"|y="+y+"]");
		
		gc.setGlobalAlpha(1.0);
			
		int offset = 0;
		if ((y % 2)==1) {
			offset = Constants.SEGMENT_SIZE*2;
		} 
		             
		double posX = Constants.SEGMENT_SIZE+(x*(Constants.SEGMENT_SIZE*4))+offset-2;
		double posY = (y*Constants.SEGMENT_SIZE)+(Constants.SEGMENT_SIZE/2)-2;
	
		gc.drawImage(SoldierUtils.get(type), posX, posY);
	}
		
	public SoldierType getType() {
		return type;
	}

	public void setType(SoldierType type) {
		this.type = type;
	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
