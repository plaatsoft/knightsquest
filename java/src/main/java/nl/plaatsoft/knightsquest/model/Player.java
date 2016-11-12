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

import javafx.scene.canvas.GraphicsContext;

public class Player {

	final static Logger log = Logger.getLogger( Player.class);
	
	private int id;		
	private List <Region> region = new ArrayList<Region>();
	
	public Player(int id) {
		super();
		this.id = id;
	}
	
	@Override
	public String toString() {
		return ""+id;
	}

	public void draw(GraphicsContext gc) {			
		Iterator<Region> iter = region.iterator();  
		while (iter.hasNext()) {
			Region region = (Region) iter.next();
			region.draw(gc, this);
		}
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
}
