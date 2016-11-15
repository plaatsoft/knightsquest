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

package nl.plaatsoft.knightsquest.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.scene.layout.Pane;
import nl.plaatsoft.knightsquest.model.Region;
import nl.plaatsoft.knightsquest.model.Land;
import nl.plaatsoft.knightsquest.model.LandEnum;
import nl.plaatsoft.knightsquest.model.Player;
import nl.plaatsoft.knightsquest.model.Soldier;
import nl.plaatsoft.knightsquest.model.SoldierEnum;
import nl.plaatsoft.knightsquest.tools.MyRandom;

public class RegionUtils {

	final private static Logger log = Logger.getLogger(RegionUtils.class);

	public static void checkFood(Region region) {

		log.info("check food [player=" + region.getPlayer().getId() + " region=" + region.getId() + " size="
				+ region.getLands().size() + " foodAvailable=" + region.foodAvailable() + "]");

		if (region.foodAvailable() < 0) {

			/* Too less food for all soldiers of castle, they all die */
			Iterator<Land> iter = region.getLands().iterator();
			while (iter.hasNext()) {
				Land land = (Land) iter.next();
				if (land.getSoldier() != null) {
					Soldier soldier = land.getSoldier();
					if (soldier.getType() != SoldierEnum.TOWER) {
						soldier.setType(SoldierEnum.CROSS);
						// log.info("Soldier
						// [x="+land.getX()+"|y="+land.getY()+"|castleId="+castle.getId()+"]
						// died!");
					}
				}
			}
		}

		// log.info("check food end");
	}

	public static Region createStartRegion(int regionId, Player player, Pane pane) {

		Region region = null;

		boolean done = false;
		while (!done) {

			boolean bad = false;

			int x = MyRandom.nextInt(Constants.SEGMENT_X);
			int y = MyRandom.nextInt(Constants.SEGMENT_Y);

			// Each start region must have two lands between each other)
			List<Land> list1 = LandUtils.getNeigbors(x, y);
			Iterator<Land> iter1 = list1.iterator();
			while (iter1.hasNext()) {
				Land land1 = (Land) iter1.next();
				if (land1.getPlayer() != null) {
					bad = true;
				}
			}

			List<Land> list2 = LandUtils.getNeigbors2(x, y);
			Iterator<Land> iter2 = list2.iterator();
			while (iter2.hasNext()) {
				Land land2 = (Land) iter2.next();
				if (land2.getPlayer() != null) {
					bad = true;
				}
			}

			if (!bad) {

				Land land3 = LandUtils.getLand()[x][y];
				if (land3.getType() == LandEnum.GRASS) {

					// Clain land
					land3.setPlayer(player);

					// Place Tower
					Soldier soldier3 = new Soldier(SoldierEnum.TOWER, player);		
					soldier3.getImageView().setPosition(land3.getX(), land3.getY());
					pane.getChildren().add(soldier3.getImageView());
					land3.setSoldier(soldier3);
					
					log.info("New "+soldier3.getType()+" [x="+land3.getX()+"|y="+land3.getY()+"] created!");

					// Create new region
					region = new Region(player.getId(), player);
					region.getLands().add(land3);
					// log.info("New Region
					// [id="+region.getId()+"|playerId="+player.getId()+"]
					// created!");

					player.getRegion().add(region);

					// Add some more lands to region
					List<Land> list4 = LandUtils.getNewLand(x, y);
					Land land4 = MyRandom.nextLand(list4);
					if (land4 != null) {
						land4.setPlayer(player);
						region.getLands().add(land4);
						
						Soldier soldier4 = new Soldier(SoldierEnum.PAWN, player);
						soldier4.getImageView().setPosition(land4.getX(), land4.getY());
						pane.getChildren().add(soldier4.getImageView());
						land4.setSoldier(soldier4);
						
						log.info("New "+soldier4.getType()+" [x="+land4.getX()+"|y="+land4.getY()+"|regionId="+region.getId()+"] created!");
					}

					done = true;
				}
			}
		}
		return region;
	}

	public static Land getTowerPosition(Region region) {

		Iterator<Land> iter = region.getLands().iterator();
		while (iter.hasNext()) {
			Land land = (Land) iter.next();
			if ((land.getSoldier() != null) && (land.getSoldier().getType() == SoldierEnum.TOWER)) {

				// log.info("Tower ["+land.getX()+","+land.getY()+"]");
				return land;
			}
		}
		return null;
	}

	/* recursion land search */
	private static void search(Land land2, int regionId) {

		land2.setRegion(regionId);

		List<Land> list = LandUtils.getRegionLand(land2.getX(), land2.getY(), land2.getPlayer());
		Iterator<Land> iter = list.iterator();
		while (iter.hasNext()) {
			Land land = (Land) iter.next();
			search(land, regionId);
		}
	}

	public static int detectedRegions() {

		int regionId = 0;

		// Reset all castles
		Land lands[][] = LandUtils.getLand();
		for (int x = 0; x < Constants.SEGMENT_X; x++) {
			for (int y = 0; y < Constants.SEGMENT_Y; y++) {
				lands[x][y].setRegion(regionId);
			}
		}

		// Find them again
		for (int x = 0; x < Constants.SEGMENT_X; x++) {
			for (int y = 0; y < Constants.SEGMENT_Y; y++) {

				Land land = lands[x][y];
				if ((land.getType() != LandEnum.WATER) && (land.getType() != LandEnum.OCEAN) && (land.getRegion() == 0)
						&& (land.getPlayer() != null)) {
					regionId++;
					search(land, regionId);
				}
			}
		}

		return regionId;
	}

	public static void rebuildRegions(int amountOfRegions, Pane pane) {

		// Remove all region
		Iterator<Player> iter1 = PlayerUtils.getPlayers().iterator();
		while (iter1.hasNext()) {
			Player player = (Player) iter1.next();

			List<Region> list2 = player.getRegion();
			Iterator<Region> iter2 = list2.iterator();
			while (iter2.hasNext()) {
				@SuppressWarnings("unused")
				Region region = (Region) iter2.next();
				iter2.remove();
			}
		}

		Land lands[][] = LandUtils.getLand();

		// Detect new regions with castle and assign them to player again
		for (int regionId = 1; regionId <= amountOfRegions; regionId++) {

			List<Land> list = new ArrayList<Land>();

			int foodAvailable = 0;
			int foodNeeded = 0;
			int castleCount = 0;
			for (int x = 0; x < Constants.SEGMENT_X; x++) {
				for (int y = 0; y < Constants.SEGMENT_Y; y++) {
					if (lands[x][y].getRegion() == regionId) {

						foodAvailable++;
						list.add(lands[x][y]);

						if (lands[x][y].getSoldier() != null) {

							foodNeeded += SoldierUtils.getFoodNeeds(lands[x][y].getSoldier().getType());

							if (lands[x][y].getSoldier().getType() == SoldierEnum.TOWER) {
								if (++castleCount > 1) {

									/*
									 * Remove castle if there are more one
									 * castle in one region
									 */
									// log.info("Castle remove");
									lands[x][y].setSoldier(null);
								}
							}
						}
					}
				}
			}

			if (foodAvailable < foodNeeded) {

				Iterator<Land> iter = list.iterator();
				while (iter.hasNext()) {
					Land land = (Land) iter.next();
					if (land.getSoldier() != null) {

						// No food, soldiers die
						land.getSoldier().setType(SoldierEnum.CROSS);
					}
				}
			}

			// log.info("CastleId="+i+" landSize="+list.size());

			if ((castleCount == 0) && (list.size() > 1)) {

				// Create new castle.
				Land land = MyRandom.nextLand(list);
				if (land != null) {
					// log.info("Castle created");
					Soldier soldier = new Soldier(SoldierEnum.TOWER, land.getPlayer());
					pane.getChildren().add(soldier.getImageView());
					land.setSoldier(soldier);
				}
			}

			// Castle is destroyed when region size is one.
			if (list.size() == 1) {
				Iterator<Land> iter = list.iterator();
				while (iter.hasNext()) {
					Land land = (Land) iter.next();
					if ((land.getSoldier() != null) && (land.getSoldier().getType() == SoldierEnum.TOWER)) {
						land.getSoldier().setType(SoldierEnum.CROSS);
					}
				}
			}

			// Create new region and add it to player
			Iterator<Land> iter = list.iterator();
			if (iter.hasNext()) {
				Land land = (Land) iter.next();
				Player player = land.getPlayer();
				Region region = new Region(regionId, player);
				region.setLands(list);
				player.getRegion().add(region);
			}
		}
	}
}