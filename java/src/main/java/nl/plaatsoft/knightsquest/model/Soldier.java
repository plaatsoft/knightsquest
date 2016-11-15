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
import nl.plaatsoft.knightsquest.tools.MyImageView;
import nl.plaatsoft.knightsquest.utils.SoldierUtils;

public class Soldier {

	final static Logger log = Logger.getLogger( Soldier.class);
	
	private SoldierEnum type;
	private boolean moved = false;
	private Player player;
	private MyImageView imageView;

	@Override
	public String toString() {
		return ""+type;
	}

	public Soldier(SoldierEnum type, Player player) {

		this.type = type;
		this.player = player;
		imageView = new MyImageView(0,0,SoldierUtils.get(type),1);
	}
	
	public void draw(GraphicsContext gc, int x, int y, int size) {
		
		log.info("draw "+type+" playerId="+player.getId()+" [x="+x+"|y="+y+"]");
				
		if(imageView!=null) {
			imageView.setPosition(x, y);			
		}
		
		/* else {		
			int offset = 0;
			if ((y % 2)==1) {
			offset = size*2;
			} 
	             	
			double posX = size+(x*(size*4))+offset-2;
			double posY = (y*size)+(size/2)-2;
		
			gc.setGlobalAlpha(1.0);			
				gc.drawImage(SoldierUtils.get(type), posX, posY);
		} */
	}
		
	public SoldierEnum getType() {
		return type;
	}

	public void setType(SoldierEnum type) {
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
	
	public MyImageView getImageView() {
		return imageView;
	}
}
