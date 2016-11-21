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

import java.util.List;
import java.util.Random;

import nl.plaatsoft.knightsquest.model.Land;

public class MyRandom {
	
	private static int seed;	
	private static Random rnd;
	
	public static int nextInt(int value) {
		return rnd.nextInt(value);
	}
	
	public static Land nextLand(List<Land> list) { 
		
		Land land = null;
		
		if (list.size()>0) {
			land = list.get(rnd.nextInt(list.size()));
		}
		return land;
	}

	public static int getSeed() {
		return seed;
	}

	public static void setSeed(int seed1) {
		seed = seed1;
		rnd = new Random(seed1);
	}
}
