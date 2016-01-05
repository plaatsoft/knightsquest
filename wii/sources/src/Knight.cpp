/** 
 *  @file 
 *  @brief The file contain the Knight class methods
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
#include "Knight.h"
#include "Trace.h"
#include "Sound.h" 

extern Game    game; 
extern Trace   *trace;
extern Sound   *sound;

// ------------------------------
// Constructor
// ------------------------------

/**
 * Constructor
 * Init all properties with default values.
 */
Knight::Knight()
{
	const char *s_fn="Knight::Knight";
	trace->event(s_fn,0,"enter");

	x=0;
	y=0;	
	strenght=0;
	index=0;
   
	trace->event(s_fn,0,"leave [void]");
}
	
// ------------------------------
// Destructor
// ------------------------------

/**
 * Destructor
 * Clean up all allocated memory
 */
Knight::~Knight()
{
   const char *s_fn="Knight::~Knight";
   trace->event(s_fn,0,"enter");

   trace->event(s_fn,0,"Knight [%d] destroyed", index);
   
   trace->event(s_fn,0,"leave [void]");  
}

// ------------------------------
// Others
// ------------------------------

/**
 * Draw Knight on screen.
 */
void Knight::draw()
{
	GRRLIB_DrawImg( x, y, image, 0, 1, 1, 0x000000 );	
}

/**
 * Detect if Knight is selected
 * @param x1 The WiiMote pointer x position.
 * @param y1 The WiiMote pointer y position.
 */
bool Knight::onSelect(int x1, int y1)
{
   const char *s_fn="Button::onSelect";
   trace->event(s_fn,0,"enter [x=%d|y=%d]",x1,y1);

   bool selected=false;
   if ((x1==x) && (y1==y))
   {      
	  trace->event(s_fn,0,"Selected");
	  
	  // Click sound
	  sound->effect(SOUND_CLICK);
	  selected=true;
   }
   else
   {
	  selected=false;
   }
   trace->event(s_fn,0,"leave");
   
   return selected;
}	   
	   
// ------------------------------
// Setters
// ------------------------------

/**
 * Set Knights x postion.
 * @param x1 The X position of the Knight.
 */		
void Knight::setX(int x1)
{
	const char *s_fn="Knight::setX";
	trace->event(s_fn,0,"%d",x1);
	
	x = x1;
}

/**
 * Set Knights y postion.
 * @param y1 The Y position of the Knight.
 */
void Knight::setY(int y1)
{
	const char *s_fn="Knight::setY";
	trace->event(s_fn,0,"%d",y1);

	y = y1;
}

/**
 * Set index of the Knight.
 * @param index1 The index of the Knight.
 */
void Knight::setIndex(int index1)
{
	const char *s_fn="Knight::setIndex";
	trace->event(s_fn,0,"%d",index1);
	
   index=index1;
}

/**
 * Set Knight image.
 * @param image1 The Knight image.
 */
void Knight::setImage(GRRLIB_texImg *image1)
{
	image=image1;
}

// ------------------------------
// Getters
// ------------------------------

/**
 * Get X position of Knight.
 * @return The current X position of the Knight.
 */
int Knight::getX(void)
{
    return x;
}

/**
 * Get Y position of Knight.
 * @return The current Y position of the Knight.
 */
int Knight::getY(void)
{
    return y;
}

/**
 * Get strenght of the Knight.
 * @return The current Knights strenght
 */
int Knight::getStrenght(void)
{
    return strenght;
}

/**
 * Get index of the Knight.
 * @return The current index of the Knight
 */
int Knight::getIndex(void)
{
    return index;
}
// ------------------------------
// The End
// ------------------------------