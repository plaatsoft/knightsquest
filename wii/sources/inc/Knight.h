/** 
 *  @file 
 *  @brief The file contain the Knight class
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

#ifndef KNIGHT_H
#define KNIGHT_H

#include "grrlib.h"

/**
 * Knight class
 * @param x 			The x-coordinate of the knight.
 * @param y 			The y-coordinate of the knight.
 * @param image		The image of the knight.
 * @param strenght 	The strenght of the knight.
 * @param index 		A unique number of the knight.
 * @param selected 	Indicate if the Knight is selected by the WiiMote.
 */
class Knight
{
  private:
	int x;							
	int y;	
	GRRLIB_texImg *image;
	int strenght;
	int width;
	int index;
	bool selected;
	
  public:
	// Constructor & Destructor
  	Knight();
 	~Knight();

	// Other
	void draw();
	bool onSelect(int pointerX, int pointerY);
	
	// Setters
	void setX(int x);
	void setY(int y);
	void setImage(GRRLIB_texImg *image);
	void setStrenght(int strenght);
	void setIndex(int index);
	
	// Getters
	int getX(void);
	int getY(void);
	int getStrenght(void);
	int getIndex(void);
};

#endif