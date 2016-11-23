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

import java.io.Serializable;

import nl.plaatsoft.knightsquest.ui.Constants;

public class Setting implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private double x;
	private double y;
	private boolean musicOn;
	private String resolution;
	private boolean[] mapUnlocked = new boolean[Constants.MAX_LEVELS*10];
	private int[] score = new int[Constants.MAX_LEVELS*10];
		
	public Setting() {		
		x = 0;
		y = 0;		
		musicOn = true;		
		resolution = "640x480";
					
		for(int i=0; i<(Constants.MAX_LEVELS*10); i++) {
			mapUnlocked[i]=new Boolean(false);
		}
		mapUnlocked[1]= new Boolean(true);		
		
		for(int i=0; i<(Constants.MAX_LEVELS*10); i++) {
			score[i]=new Integer(0);
		}
	}

	public int getWidth() {
				
		int value;
		
		if (resolution.equals("1024x768")) {
			value = 1024;
		} else if (resolution.equals("800x600")) {
			value = 800;
		} else {
			value = 640;
		}
		
		return value;
	}

	public int getHeight() {
				
		int value;
		
		if (resolution.equals("1024x768")) {
			value = 768;
		} else if (resolution.equals("800x600")) {
			value = 600;
		} else {
			value = 480;
		}

		return value;		
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public boolean isMusicOn() {
		return musicOn;
	}

	public void setMusicOn(boolean musicOn) {
		this.musicOn = musicOn;
	}

	public boolean getMapUnlocked(int map) {
		return mapUnlocked[map];
	}

	public void setMapUnlocked(int map, boolean value) {
		mapUnlocked[map] = value;
	}
	
	public Integer getScore(int map) {
		return score[map];
	}
	
	public void setScore(int map, int value) {
		score[map] = value;
	}
	
	public int getHighestMap() {
		
		for (int i=(Constants.MAX_LEVELS*10)-1; i>0; i--) {
			if (mapUnlocked[i]==true) {
				return i;
			}
		}
		return 1;
	}
		
}
