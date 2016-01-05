/**  
 *  @file 
 *  @brief The file contain the Settings class methods
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
 
#include <mxml.h>
#include <ogc/conf.h>

#include "General.h"
#include "Settings.h"
#include "Trace.h"

char validNick[] = "AAAAAA"; // 6 characters + the trailing NULL (game limit)

extern Trace *trace;

// ------------------------------
// Constructor 
// ------------------------------

/**
 * Constructor
 *
 * @author wplaat
 *
 * Init all properties with default values.
 */
Settings::Settings()
{
   const char *s_fn="Settings::Settings";
   trace->event(s_fn,0,"enter");
   
   strcpy(name, nickName());
   musicVolume = 5;
   effectVolume = 9;
   	
   trace->event(s_fn,0,"leave [void]");
}

// ------------------------------
// Destructor
// ------------------------------

/**
 * Destructor
 *
 * @author wplaat
 *
 * Clean up all allocated memory
 */
Settings::~Settings()
{
	const char *s_fn="Settings::~Settings";
	trace->event(s_fn,0,"enter");
  
   trace->event(s_fn,0,"Settings destroyed");
	
	trace->event(s_fn,0,"leave [void]");
}

// ------------------------------
// Others
// ------------------------------

/**
 * Get Wii nickname
 *
 * @author Canyon
 *
 * @return the Wii nickname
 */
const char *Settings::nickName()
{
   const char *s_fn="nickName";
   trace->event(s_fn,0,"enter");
	
	// Name stored on the Wii, size is 0x16 bytes
   unsigned char NickName[22];  
   CONF_GetNickName(NickName);
	
	// Convert to uppercase (game limit)
   char *UpperCaseNick = strupr((char *)NickName); 
 
   unsigned char c, d;
   for(c=0, d=0; c<sizeof(validNick)-1 && d<sizeof(NickName)-1; d++) {
      if((UpperCaseNick[d] >= 0x41 && UpperCaseNick[d] <= 0x5A) ||
         (UpperCaseNick[d] >= 0x30 && UpperCaseNick[d] <= 0x39)) {
			
         // Accept only chars used in the font (uppercase letters + numbers)
         validNick[c++] = UpperCaseNick[d];
      }
   }
	
	trace->event(s_fn,0,"leave [%s]", validNick);
	return validNick;
}

	
/**
 * Load settings of file
 *
 * @author wplaat
 *
 * @param filename	The filename including complete directory path.
 */
void Settings::load(const char *filename)
{
    const char *s_fn="Settings::load";
    trace->event(s_fn,0,"enter");
	
    int i;
    FILE *fp;
    mxml_node_t *tree=NULL;
    mxml_node_t *data=NULL;
    const char *value;
    char temp[MAX_LEN];
   
    /*Load our xml file! */
    fp = fopen(filename, "r");
    if (fp!=NULL)
    {
	    trace->event(s_fn,0,"Load [filename=%s]",filename);
		
		tree = mxmlLoadFile(NULL, fp, MXML_NO_CALLBACK);
		fclose(fp);

		for(i=0; i<MAX_SETTINGS; i++)
		{
			sprintf(temp, "entry%d", i);
			data = mxmlFindElement(tree, tree, temp, NULL, NULL, MXML_DESCEND);
  
			if (data!=NULL)
			{
				value=mxmlElementGetAttr(data,"value"); 
			
				switch (i)
				{
					case 0: 	strcpy(name,value);
								break;
	
					case 1: 	musicVolume=atoi(value);
								break;
					   
					case 2: 	effectVolume=atoi(value);
								break;
				}							
				trace->event(s_fn,0,"Store [id=%d|value=%s]",i,value);
			}
		}
	}
	else
    {
		trace->event(s_fn,0,"Setting file not found, use default values!");
    }

	mxmlDelete(data);
	mxmlDelete(tree);
	trace->event(s_fn,0,"leave [void]");
}

/**
 * Save setting to file
 *
 * @author wplaat
 *
 * @param filename	The setting filename including complete directory path
 */
void Settings::save( const char *filename)
{
   const char *s_fn="Settings::save";
   trace->event(s_fn,0,"enter");
	
   int i;
   mxml_node_t *xml;
   mxml_node_t *group;
   mxml_node_t *data;   
   char temp[MAX_LEN];
      
   xml = mxmlNewXML("1.0");
   
   group = mxmlNewElement(xml, "TowerDefense");
   
   for(i=0; i<MAX_SETTINGS; i++)
   {
      sprintf(temp, "entry%d", i);
      data = mxmlNewElement(group, temp);
  
		switch (i)
		{
			case 0: 	mxmlElementSetAttr(data, "value", name);			  
						break;
				
			case 1: 	sprintf(temp, "%d", musicVolume);
						mxmlElementSetAttr(data, "value", temp);			  
						break;
					
			case 2: 	sprintf(temp, "%d", effectVolume);
						mxmlElementSetAttr(data, "value", temp);			  
						break;
		}
	}
   
	/* now lets save the xml file to a file! */
   FILE *fp;
   fp = fopen(filename, "w");

   mxmlSaveFile(xml, fp, MXML_NO_CALLBACK);
   
   fclose(fp);
	
   mxmlDelete(data);
   mxmlDelete(group);
   mxmlDelete(xml);
   
	trace->event(s_fn,0,"leave [void]");
}

// ------------------------------
// Setters
// ------------------------------
	
/**
 * Set sixth character of player nickname.
 *
 * @author wplaat
 *
 * @param name2 	The nickname of the player
 */
void Settings::setName(const char *name2)
{
	const char *s_fn="Settings::setName";
   trace->event(s_fn,0,"enter [name=%s]",name2);
	
	strcpy(name,name2);
}

/**
 * Set music volume.
 *
 * @author wplaat
 *
 * @param volume 	The music volume value [0..128].
 */
void Settings::setMusicVolume(int volume)
{
	const char *s_fn="Settings::setMusicVolume";
    trace->event(s_fn,0,"%d",volume);
	
	musicVolume=volume;
}

/**
 * Set effect volume.
 *
 * @author wplaat
 *
 * @param volume 	The effect volume value [0..128].
 */
void Settings::setEffectVolume(int volume)
{
	const char *s_fn="Settings::setEffectVolume";
   trace->event(s_fn,0,"%d", volume);
	
	effectVolume=volume;
}
		
// ------------------------------
// Getters
// ------------------------------

/**
 * Get player nickname.
 *
 * @author wplaat
 *
 * @return nickname
 */
char *Settings::getName(void)
{
	return name;
};

/**
 * Get music volume
 *
 * @author wplaat
 *
 * @return volume [0..128]
 */
int Settings::getMusicVolume(void)
{
	return musicVolume;
}

/**
 * Get effect volume
 *
 * @author wplaat
 *
 * @return volume [0..128]
 */
int Settings::getEffectVolume(void)
{
	return effectVolume;
}

// ------------------------------
// The End
// ------------------------------