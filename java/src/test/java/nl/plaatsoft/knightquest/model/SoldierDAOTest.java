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

package nl.plaatsoft.knightquest.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nl.plaatsoft.knightsquest.model.Land;
import nl.plaatsoft.knightsquest.model.LandDAO;
import nl.plaatsoft.knightsquest.model.LandEnum;
import nl.plaatsoft.knightsquest.model.Player;
import nl.plaatsoft.knightsquest.model.Region;
import nl.plaatsoft.knightsquest.model.SoldierDAO;
import nl.plaatsoft.knightsquest.tools.MyData;

public class SoldierDAOTest {
		
	@Test
	public void test1() {
		
		int size = 10;				
							
		MyData.setLevel(1);
		MyData.setMap(1);
		
		LandDAO landDAO = new LandDAO();				
		Land[][] lands = landDAO.getLands();
		
		Player player = new Player(size, true);		
		Region region = new Region(1, player);
		
		for (int x=0; x<5;x++) {
			for (int y=0; y<5;y++) {
				lands[x][y] = new Land(null, x, y, size, LandEnum.GRASS);
				region.getLands().add(lands[x][y]);
			}			
		}		
						
		SoldierDAO soldierDAO = new SoldierDAO();
		soldierDAO.createBotSoldier(region);
		
		assertEquals("Region contain 25 land tills", region.getLands().size(), 25);
	}
}
