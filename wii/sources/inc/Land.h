/** 
 *  @file 
 *  @brief The file contain the Land class
 *  @author wplaat
 *
 *  Copyright (C) 2008-2010 PlaatSoft
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 2.
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

#ifndef LAND_H
#define LAND_H

#include "grrlib.h"

/**
 * Land class
 * @param x 			The x-coordinate of the land.
 * @param y 			The y-coordinate of the land.
 * @param owner 		The land owner.
 * @param type			The type of land.
 * @param index 		A unique number of the land.
 */
 
class Land {

  private:
	int x;							
	int y;	
	int owner;
	int type;
	int index;
	
  public:
	// Constructor & Destructor
  	Land();
	Land(int x1, int y1, int type1, int index1);
 	~Land();

	// Other
	void draw();
	
	// Setters
	void setX(int x);
	void setY(int y);
	void setOwner(int owner);
	void setType(int type);
	void setIndex(int index);
	
	// Getters
	int getX(void);
	int getY(void);
	int getOwner(void);
};

#endif