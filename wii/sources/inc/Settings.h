/** 
 *  @file 
 *  @brief The file contain the Settings class
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

#ifndef SETTINGS_H
#define SETTINGS_H

#define MAX_NAME_SIZE 	7
#define MAX_SETTINGS		9

class Settings
{
  private:	
	char name[MAX_NAME_SIZE];
	int  musicVolume;
	int  effectVolume;
	
	const char *nickName();

  public:
  	// Constructor & Destructor
	Settings();
 	~Settings();
	
	// Methodes
	void load(const char *filename);
	void save( const char *filename);
	
	// Setters
	void setName(const char *name);
	void setMusicVolume(int volume);
	void setEffectVolume(int volume);

	// Getters
	char *getName(void);
	int  getMusicVolume(void);
	int  getEffectVolume(void);
};

#endif
