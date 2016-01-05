/** 
 *  @file 
 *  @brief The file contain the Land class methods
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

#include <wiiuse/wpad.h>

#include "General.h"
#include "grrlib.h"
#include "Land.h"
#include "Trace.h"

extern Game    game; 
extern Trace   *trace;

// ------------------------------
// Constructor
// ------------------------------

/**
 * Constructor
 * Init all properties with default values.
 */
Land::Land() {

	const char *s_fn="Land::Land";
	trace->event(s_fn,0,"enter");

	x=0;
	y=0;	
	owner=0;
	type=0;
	index=0;
   
	trace->event(s_fn,0,"leave [void]");
}

/**
 * Constructor
 * Init all properties
 */
Land::Land(int x1, int y1, int type1, int index1) {

	const char *s_fn="Land::Land";
	trace->event(s_fn,0,"enter");

	x=x1;
	y=y1;	
	type=type1;
	index=index1;
   
	trace->event(s_fn,0,"leave [void]");
}
	
// ------------------------------
// Destructor
// ------------------------------

/**
 * Destructor
 * Clean up all allocated memory
 */
Land::~Land() {

   const char *s_fn="Land::~Land";
   trace->event(s_fn,0,"enter");

   trace->event(s_fn,0,"Land [%d] destroyed", index);
   
   trace->event(s_fn,0,"leave [void]");  
}

// ------------------------------
// Others
// ------------------------------

/**
 * Draw Land on screen.
 */
void Land::draw() {

	int xSize=10;
	int ySize=10;
	u32 sixtentColor[6];
		
	switch (type) {
	  
		case 0: sixtentColor[0] = GRRLIB_GREEN;
				  sixtentColor[1] = GRRLIB_GREEN;
				  sixtentColor[2] = GRRLIB_GREEN;
				  sixtentColor[3] = GRRLIB_GREEN;
				  sixtentColor[4] = GRRLIB_GREEN;
				  sixtentColor[5] = GRRLIB_GREEN;
				  break;
				  
		case 1: sixtentColor[0] = GRRLIB_BLUE;
				  sixtentColor[1] = GRRLIB_BLUE;
				  sixtentColor[2] = GRRLIB_BLUE;
				  sixtentColor[3] = GRRLIB_BLUE;
				  sixtentColor[4] = GRRLIB_BLUE;
				  sixtentColor[5] = GRRLIB_BLUE;
				  break;

		case 2: sixtentColor[0] = GRRLIB_RED;
				  sixtentColor[1] = GRRLIB_RED;
				  sixtentColor[2] = GRRLIB_RED;
				  sixtentColor[3] = GRRLIB_RED;
				  sixtentColor[4] = GRRLIB_RED;
				  sixtentColor[5] = GRRLIB_RED;
				  break;
	}
				  
	guVector sixtent[] = {
				{((1+x)*xSize),(1+y)*ySize,0.0f}, 
				{((2+x)*xSize),(1+y)*ySize,0.0f}, 
				{((3+x)*xSize),(2+y)*ySize,0.0f},
				{((2+x)*xSize),(3+y)*ySize,0.0f},
				{((1+x)*xSize),(3+y)*ySize,0.0f},
				{((0+x)*xSize),(2+y)*ySize,0.0f},
	};
	
	GRRLIB_NGoneFilled( sixtent,  sixtentColor, 6);	
}   
	   
// ------------------------------
// Setters
// ------------------------------

/**
 * Set Lands x postion.
 * @param x1 The X position of the Land.
 */		
void Land::setX(int x1) {

	const char *s_fn="Land::setX";
	trace->event(s_fn,0,"%d",x1);
	
	x = x1;
}

/**
 * Set Lands y postion.
 * @param y1 The Y position of the Land.
 */
void Land::setY(int y1) {

	const char *s_fn="Land::setY";
	trace->event(s_fn,0,"%d",y1);

	y = y1;
}

/**
 * Set index of the Land.
 * @param index1 The index of the Land.
 */
void Land::setIndex(int index1) {

	const char *s_fn="Land::setIndex";
	trace->event(s_fn,0,"%d",index1);
	
   index=index1;
}


/**
 * Set type of the Land.
 * @param type1 The type of the Land.
 */
void Land::setType(int type1) {

	const char *s_fn="Land::setType";
	trace->event(s_fn,0,"%d",type1);
	
   type=type1;
}

/**
 * Set owner of the Land.
 * @param owner1 	The owner of the Land.
 */
void Land::setOwner(int owner1) {

	const char *s_fn="Land::setOwner";
	trace->event(s_fn,0,"%d",owner1);
	
   owner=owner1;
}

// ------------------------------
// Getters
// ------------------------------

/**
 * Get X position of Land.
 * @return The current X position of the Land.
 */
int Land::getX(void) {

    return x;
}

/**
 * Get Y position of Land.
 * @return The current Y position of the Land.
 */
int Land::getY(void) {

    return y;
}

/**
 * Get owner of the Land.
 * @return The current Lands owner
 */
int Land::getOwner(void) {

    return owner;
}

// ------------------------------
// The End
// ------------------------------