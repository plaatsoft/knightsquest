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
import javafx.scene.paint.Color;
import nl.plaatsoft.knightsquest.utils.LandUtils;
import nl.plaatsoft.knightsquest.utils.PlayerUtils;

public class Land {

	final static Logger log = Logger.getLogger( Land.class);
	
	private int x;
	private int y;
	private LandEnum type; 
	private Soldier soldier;
	private Player player;
	private int region;
		
	public Land(int x, int y, LandEnum type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public void draw(GraphicsContext gc, int size) {
		
		//log.info("draw land ["+x+","+y+"]");
		
		int offset = 0;
		if ((y % 2)==1) {
			offset = size*2;
		} 
		
		LandUtils.getTexture(gc, type);
		
		gc.fillPolygon(
			new double[]{(x*(size*4))+offset, size+(x*(size*4))+offset,	(size*2)+(x*(size*4))+offset, (size*3)+(x*(size*4))+offset, 
					(size*2)+(x*(size*4))+offset, size+(x*(size*4))+offset,	0+(x*(size*4))+offset}, 
			new double[]{size+(y*size), (y*size), (y*size),	size+(y*size), (size*2)+(y*size), (size*2)+(y*size), size+(y*size)}, 7
			);
				
		gc.setFill(Color.BLACK);		
		gc.strokePolyline(
				new double[]{(x*(size*4))+offset, size+(x*(size*4))+offset,	(size*2)+(x*(size*4))+offset, (size*3)+(x*(size*4))+offset, 
						(size*2)+(x*(size*4))+offset, size+(x*(size*4))+offset,	0+(x*(size*4))+offset}, 
				new double[]{size+(y*size), (y*size), (y*size),	size+(y*size), (size*2)+(y*size), (size*2)+(y*size), size+(y*size)}, 7
		);	
		
		if (size>=20) {
			gc.setFill(Color.WHITE);
			gc.fillText("["+x+","+y+"]",(x*(size*4))+offset+15,size+(y*size));
		}
	}
		
	public void draw(GraphicsContext gc, Player player, int size) {
		
		//log.info("draw land player ["+x+","+y+"]");
		
		gc.setGlobalAlpha(0.60);
		
		int offset = 0;
		if ((y % 2)==1) {
			offset = size*2;
		} 
		
		PlayerUtils.getTexture(gc, player.getId());
		
		gc.fillPolygon(
				new double[]{(x*(size*4))+offset, size+(x*(size*4))+offset,	(size*2)+(x*(size*4))+offset, (size*3)+(x*(size*4))+offset, 
						(size*2)+(x*(size*4))+offset, size+(x*(size*4))+offset,	0+(x*(size*4))+offset}, 
				new double[]{size+(y*size), (y*size), (y*size),	size+(y*size), (size*2)+(y*size), (size*2)+(y*size), size+(y*size)}, 7);	
	
		gc.setFill(Color.BLACK);
		
		gc.strokePolyline(
				new double[]{(x*(size*4))+offset, size+(x*(size*4))+offset,	(size*2)+(x*(size*4))+offset, (size*3)+(x*(size*4))+offset, 
						(size*2)+(x*(size*4))+offset, size+(x*(size*4))+offset,	0+(x*(size*4))+offset}, 
				new double[]{size+(y*size), (y*size), (y*size),	size+(y*size), (size*2)+(y*size), (size*2)+(y*size), size+(y*size)}, 7);	
		
		if (size>=20) {
			
			gc.setGlobalAlpha(1);
			gc.setFill(Color.WHITE);
			gc.fillText(""+region,(x*(size*4))+offset+25,size+(y*size)+15);
		}
		
		if (soldier!=null) {
			
			getSoldier().draw(gc, x, y, size);
		}
	}
		
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public LandEnum getType() {
		return type;
	}
	
	public void setType(LandEnum type) {
		this.type = type;
	}
	
	public Soldier getSoldier() {
		return soldier;
	}
	
	public void setSoldier(Soldier soldier) {
		this.soldier = soldier;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getRegion() {
		return region;
	}

	public void setRegion(int region) {
		this.region = region;
	}	
}
