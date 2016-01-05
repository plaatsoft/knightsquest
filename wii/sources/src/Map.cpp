/**  
 *  @file 
 *  @brief The file contain the Map class methods
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
 *  Foundation, Inc., 5 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

#include <stdio.h>
#include <stdlib.h>
#include <mxml.h>

#include "General.h"
#include "grrlib.h"
#include "Trace.h"
#include "Map.h"

extern Trace *trace;
extern Game  game; 

// ------------------------------
// Constructor
// ------------------------------

/**
 * Constructor
 * Init all values with default values.
 */
Map::Map()
{
	const char *s_fn="Map::Map";
	trace->event(s_fn,0,"enter");
  
	imageGeneral1=NULL;
	imageGeneral2=NULL;
	imageGeneral3=NULL;
	
	// Clear grid data
	for (int y=0;y<MAX_GRID_Y;y++)
	{
		for (int x=0;x<MAX_GRID_X;x++)
		{
			mapData[y][x]=0x00;
		}
	}
			
	trace->event(s_fn,0,"leave [void]");  
}
	
// ------------------------------
// Destructor
// ------------------------------

/**
 * Destructor
 * Clean up all allocated memory
 */
Map::~Map()
{
	const char *s_fn="Map::~Map";
	trace->event(s_fn,0,"enter");

   trace->event(s_fn,0,"Map [%d] destroyed", index);

	GRRLIB_FreeTexture(imageGeneral1);
   GRRLIB_FreeTexture(imageGeneral2);
	GRRLIB_FreeTexture(imageGeneral3);
	
	trace->event(s_fn,0,"leave [void]");  
}

// ------------------------------
// Others
// ------------------------------

/**
 * loadImage file from sdcard
 *
 * @param filename The filename of the image including path.
 */
GRRLIB_texImg * Map::loadImage(const char *filename)
{
   const char *s_fn="Map::loadImage";
   trace->event(s_fn,0,"enter [filename=%s]",filename);
   
	u8 data[MAX_BUFFER_SIZE];
	memset(data,0x00,MAX_BUFFER_SIZE);
   
	FILE *fp = fopen(filename, "r");
	if (fp!=NULL)
	{  
	   fread(&data, 1, MAX_BUFFER_SIZE, fp);
		fclose(fp);
		trace->event(s_fn,0,"leave [DATA]");
		return GRRLIB_LoadTexture( data );
	}  
	else
	{
		trace->event(s_fn,0,"file %s not found!",filename);
	}
	fclose(fp);
	trace->event(s_fn,0,"leave [NULL]");
	return NULL;
}

/**
 * load map data out xml file.
 *
 * @param filename The filename of the map data in xml format
 */
bool Map::loadMap(const char* filename)
{
   const char *s_fn="Map::loadMap";
   trace->event(s_fn,0,"enter [filename=%s]",filename);
   
   FILE *fp;
   mxml_node_t *tree =NULL;
   mxml_node_t *group;   
   const char  *pointer;
   bool error=false;
	
   int maxLines=0;
   
   /* Load our xml file! */
   fp = fopen(filename, "r");
   if (fp!=NULL)
   {   
      tree = mxmlLoadFile(NULL, fp, MXML_NO_CALLBACK);
      fclose(fp);
    
		// Read Grid Lines
		for (group = mxmlFindElement(tree, tree, "line", NULL, NULL, MXML_DESCEND);
        group != NULL;
        group = mxmlFindElement(group, tree, "line", NULL, NULL, MXML_DESCEND))
		{		 	  	  
			pointer=mxmlElementGetAttr(group,"data");
			if (pointer!=NULL) 
			{
				strcpy(mapData[maxLines],pointer);  
			}
			maxLines++;
		}
   
		mxmlDelete(group);
		mxmlDelete(tree);
	}
	else
	{
		trace->event(s_fn,0,"file %s not found!",filename);
		error=true;
	}
   trace->event(s_fn,0,"leave [error=%d|maxLines=%d]",error, maxLines);
	return error;
}

/**
 * draw. Draw grid on screen.
 *
 * @param xOffset  The xoffset of the grid
 * @param yOffset  The yoffset of the grid
 * @param size     This size (default value is 1 for normal size) 
 */
void Map::draw(int xOffset, int yOffset, float size)
{
   float size1=(32.0/size);
	float size2=(1.0/size);
	
   // Parse grid show correct images 
   for (int y=0; y<MAX_GRID_Y; y++)
   {	
		for (int x=0; x<MAX_GRID_X; x++)
		{
			switch (mapData[y][x])
			{				 
				case '0': 
					// Start point - Draw Grass image
					GRRLIB_DrawImg( 
						(x*size1)+xOffset, 
						(y*size1)+yOffset, 
						imageGeneral1, 0, size2, size2, IMAGE_COLOR );
					break;
					
				case '~':
					// Draw water image 
					GRRLIB_DrawImg( 
						(x*size1)+xOffset, 
						(y*size1)+yOffset, 
						imageGeneral2, 0, size2, size2, IMAGE_COLOR );
					break;
	
				case '=':
					// Draw bridge image
					GRRLIB_DrawImg( 
						(x*size1)+xOffset, 
						(y*size1)+yOffset,
						imageGeneral3, 0, size2, size2, IMAGE_COLOR );
					break;

				case 'H':
					// Draw bridge image
					GRRLIB_DrawImg( 
						(x*size1)+size1+xOffset, 
						(y*size1)+yOffset, 
						imageGeneral3, 90, size2, size2, IMAGE_COLOR );
					break;
			}
		}
	}
}
	
/**
 * Load map data and parse it
 *
 * @param directory   The directory were the map.xml is located
 */
void Map::create(const char* directory)
{
	const char *s_fn="Grid::render";
	trace->event(s_fn,0,"enter [directory=%s]",directory);
   
   char filename[MAX_LEN];
	sprintf(filename,"%s/map.xml",directory);
   loadMap(filename);
	
	// Load images
	sprintf(filename,"%s/general1.png",directory);
   imageGeneral1=loadImage(filename);

	sprintf(filename,"%s/general2.png",directory);
   imageGeneral2=loadImage(filename);
	 	
	sprintf(filename,"%s/general3.png",directory);
   imageGeneral3=loadImage(filename);
	 
	trace->event(s_fn,0,"leave [void]");  
}

/**
 * Init Build map with original map information.
 */
void Map::initBuild(void)
{
   const char *s_fn="Map::initBuild";
   trace->event(s_fn,0,"enter");
	
	for (int y=0; y<MAX_GRID_Y; y++)
   {	
		for (int x=0; x<MAX_GRID_X; x++)
		{	
			mapBuild[y][x]=mapData[y][x]; 
		}
	}
			
	trace->event(s_fn,0,"leave [void]"); 
}

// ------------------------------
// Setters
// ------------------------------

/**
 * setIndex value.
 *
 * @param index1  This index value
 */
void Map::setIndex(int index1)
{
	const char *s_fn="Map::setIndex";
	trace->event(s_fn,0,"%d",index1);
	
   index = index1;	
}

/**
 * setBuild build value in grid. Now nothing else can be build here
 *
 * @param x   The x value 
 * @param y   The y value 
 */
void Map::setBuild(int x, int y)
{
	const char *s_fn="Map::setBuild";
	trace->event(s_fn,0,"x=%d,y=%d",x,y);
	
	mapBuild[y][x]='F';
}

/**
 * setUnBuild. unbuild value in grid. Now something else can be build here
 *
 * @param x   The x value 
 * @param y   The y value 
 */
void Map::setUnBuild(int x, int y)
{
	const char *s_fn="Map::setUnBuild";
	trace->event(s_fn,0,"x=%d,y=%d",x,y);
	
	mapBuild[y][x]='0';
}

// ------------------------------
// Getters
// ------------------------------

/**
 * isBuild. Return if map position is build.
 *
 * @param x   The x position.
 * @param y   The y position.
 *
 * @return boolean (true=build) of (false==not build) 
 */
bool Map::isBuild(int x, int y)
{
	return (mapBuild[y][x]!='0');
}

// ------------------------------
// The End
// ------------------------------