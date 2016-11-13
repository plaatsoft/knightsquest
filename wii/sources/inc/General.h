/**
 *  @file
 *  @brief This file contain all defines
 *  @author wplaat
 *  @note sourcecode tab space is 3
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
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

#ifndef GENERAL_H
#define GENERAL_H

// -----------------------------------------------------------
// Defines
// -----------------------------------------------------------

#define PROGRAM_NAME	   		"KnightsQuest"
#define PROGRAM_VERSION     	"0.30"
#define RELEASE_DATE        	"30-04-2010" 

// Check latest available version 
#define URL1                	"http://www.plaatsoft.nl/service/releasenotes6.html"
#define ID1			        		"UA-6887062-1"

// Fetch Release notes
#define URL2                	"http://www.plaatsoft.nl/service/releasenotes6.html"
#define ID2				   	 	"UA-6887062-1"

// Set Get Today HighScore
#define URL3                	"http://www.plaatsoft.nl/service/score_set_today.php"
#define ID3				    		"UA-6887062-1"

// Set Get Global HighScore
#define URL4                	"http://www.plaatsoft.nl/service/score_set_global.php"
#define ID4				    		"UA-6887062-1"

#define URL_TOKEN           	" Version "
#define HIGHSCORE_FILENAME  	"sd:/apps/KnightsQuest/highscore.xml"
#define SETTING_FILENAME    	"sd:/apps/KnightsQuest/settings.xml"
#define TRACE_FILENAME      	"sd:/apps/KnightsQuest/KnightsQuest.trc"
#define GAME_DIRECTORY      	"sd:/apps/KnightsQuest/"

#define WSP_POINTER_X      	200
#define WSP_POINTER_Y      	250

#define GRRLIB_WHITESMOKE   	0xFFFFFFFF
#define GRRLIB_WHITE_TRANS   	0xFFFFFF44
#define GRRLIB_LIGHTRED     	0x3333FFFF
#define GRRLIB_BLACK 			0x000000FF
#define GRRLIB_BLACK_TRANS		0x00000044
#define GRRLIB_BLACK_TRANS_2	0x000000AA
#define GRRLIB_MAROON  			0x800000FF
#define GRRLIB_GREEN   			0x008000FF
#define GRRLIB_OLIVE   			0x808000FF
#define GRRLIB_NAVY    			0x000080FF
#define GRRLIB_PURPLE  			0x800080FF
#define GRRLIB_TEAL    			0x008080FF
#define GRRLIB_GRAY    			0x808080FF
#define GRRLIB_SILVER  			0xC0C0C0FF
#define GRRLIB_RED     			0xFF0000FF
#define GRRLIB_LIME    			0x00FF00FF
#define GRRLIB_YELLOW  			0xFFFF00FF
#define GRRLIB_BLUE    			0x0000FFFF
#define GRRLIB_FUCHSIA 			0xFF00FFFF
#define GRRLIB_AQUA    			0x00FFFFFF
#define GRRLIB_WHITE   			0xFFFFFFFF

#define IMAGE_COLOR				0xFFFFFFFF
#define IMAGE_COLOR1        	0xEEEEEEEE
#define IMAGE_COLOR2        	0xDDDDDDDD
#define IMAGE_COLOR3        	0x44444444
#define IMAGE_COLOR4        	0xAAAAAAAA

#define MAX_WEAPONS				200
#define MAX_MONSTERS				300
#define MAX_BUTTONS         	15
#define MAX_POINTERS         	4
#define MAX_LOCAL_HIGHSCORE 	100
#define MAX_TODAY_HIGHSCORE	50
#define MAX_GLOBAL_HIGHSCORE	50

#define MAX_RUMBLE				1
#define MAX_HORZ_PIXELS			640
#define MAX_VERT_PIXELS			480
#define MAX_LEN			    	256
#define MAX_ANGLE					360
#define MAX_ALFA					255
#define MAX_SIZE					1

#define MAX_LINES		    		200
#define MAX_BUFFER_SIZE			8192
#define MAX_IDLE_TIME	    	10

#define MAX_GRID_X 				80
#define MAX_GRID_Y 				33

#define IR_X_OFFSET         	40
#define IR_Y_OFFSET         	40

#define SCROLLBAR_x         	600
#define SCROLLBAR_Y_MIN			150
#define SCROLLBAR_Y_MAX     	334

#define MUSIC_MULTIPLER			5
#define EFFECT_MULTIPLER		20

#define AVERAGE_FPS				25

#define BUTTON_A           	(WPAD_BUTTON_A     | WPAD_CLASSIC_BUTTON_A)
#define BUTTON_B           	(WPAD_BUTTON_B     | WPAD_CLASSIC_BUTTON_B)
#define BUTTON_HOME         	(WPAD_BUTTON_HOME  | WPAD_CLASSIC_BUTTON_HOME)
#define BUTTON_1            	(WPAD_BUTTON_1     | WPAD_CLASSIC_BUTTON_X)
#define BUTTON_2            	(WPAD_BUTTON_2     | WPAD_CLASSIC_BUTTON_Y)

#define BUTTON_UP           	(WPAD_BUTTON_UP    | WPAD_CLASSIC_BUTTON_UP)
#define BUTTON_DOWN         	(WPAD_BUTTON_DOWN  | WPAD_CLASSIC_BUTTON_DOWN)
#define BUTTON_LEFT         	(WPAD_BUTTON_LEFT  | WPAD_CLASSIC_BUTTON_LEFT)
#define BUTTON_RIGHT        	(WPAD_BUTTON_RIGHT | WPAD_CLASSIC_BUTTON_RIGHT)
#define BUTTON_PLUS         	(WPAD_BUTTON_PLUS  | WPAD_CLASSIC_BUTTON_PLUS)
#define BUTTON_MINUS         	(WPAD_BUTTON_MINUS | WPAD_CLASSIC_BUTTON_MINUS)

// -----------------------------------------------------------
// ENUMS
// -----------------------------------------------------------

// State machine states
enum
{
	stateNone=0,
	
	stateIntro1=1,   
	stateIntro2=2, 
	stateIntro3=3,
	
	stateMainMenu=4,
	stateGame=7,
	stateGameOver=8,
	stateQuitGame=9,
	
	stateLocalHighScore=10,
	stateTodayHighScore=11,
	stateGlobalHighScore=12,
	
	stateHelp1=13,
	stateHelp2=14,
	
	stateCredits=17,
	stateReleaseNotes=18,
	stateSoundSettings=19,
	stateGameSettings=20,
	stateDonate=21,
	stateQuit=22
};

enum
{
   fontTitle=0,
   fontSubTitle=1,
	fontPanel=2,
   fontParagraph=3,
   fontNew=4,
   fontNormal=5,
   fontSmall=6,
   fontButton=7,
   fontWelcome=8,
	fontBanner=9
};

typedef struct 
{
	
	int stateMachine;			/**< State Machine status */
	int prevStateMachine;	/**< Previous State Machine status */
	
	int event; 					/**< Current event state */
	int prevEvent;				/**< Previous event state */
	
	int maxTodayHighScore;	/**< maximum Today highscore entries [0..40] */	
	int maxGlobalHighScore;	/**< maximum Global highscore entries [0..40] */
	
	int panelXOffset;			/**< game panel X offset */
	int panelYOffset;			/**< game panel Y offset */
	int score;					/**< game score */
	int cash;					/**< game cash */
	int wave;					/**< game wave number */
	
	int alfa;					/**< general graphical image alfa value [0..255] */
	int angle;					/**< general graphical image angle value [0..360] */
	int location;				/**< general graphical location counter */
	float size;					/**< general graphical size [0..1] */	
	float wave1;				/**< general graphical wave1 counter */
	float wave2;				/**< general graphical wave2 counter */
	int scrollIndex;			/**< general graphical scrollbar index counter */

	int frameCounter;			/**< general FPS frame counter */
	int frameDelay;			/**< general sprite animation frame delay counter */
	int frame;					/**< general sprite active frame index */
}
Game;

#endif

/**
 * @mainpage Wii KnightsQuest Documentation
 * @image html KnightsQuest.png
 * Welcome to the KnightsQuest documentation.
 *
 * @section Introduction
 * Wii KnightQuest is a 2D classic game for the Nintendo Wii.
 *
 * @section Links
 * Website: http://www.plaatsoft.nl\n
 * Code: http://code.google.com/p/wii-knightsquest\n
 *
 * @section Credits
 * Documentation  : wplaat\n
 *
 * @section WishList
 * - Scrollable map
 * 
 * @section ReleaseNotes
 * <b>30-04-2010 Version 0.3</b>
 * - GUI:
 *  - Update menu screen information.
 * - Core:
 *  - Improve game icon.
 *  - Improve http library functionality.
 *  - Use GRRLIB 4.3.0 as graphical engine.
 *  - libpng was updated to version 1.4.2
 *  - libjpeg was updated to version 8b
 *  - zlib was updated to version 1.2.5
 *  - FreeType was updated to 2.3.12 
 *  - Solve random freeze problem during startup of game.
 * - General:
 *  - Build game with devkitPPC r21 compiler.
 *
 *  <b>31-03-2010 Version 0.2</b>
 *  - GUI:
 *   - Update menu screen information.
 *  - Core:
 *   - Changed WiiMote idle timeout from 60 to 300 seconds.
 *   - Rewrite settings class (username is now one setting item)
 *   - Improve stability.
 *   - Use libfat 1.0.7 as disk access engine.
 *   - Use libogc 1.8.3 as Wii interface engine
 *  - General:
 *   - Make source code compliant with r21 compiler.
 *   - Build game with devkitPPC r21 compiler.
 *
 *  <b>04-02-2010 Version 0.1</b>
 *  - GUI:
 *   - General GUI basis.
 *  - Core:
 *   - Use GRRLIB 4.2.1 (beta) as graphical render engine.
 *   - Use libfat v1.0.6 as disk access engine
 *   - Use libmxml v2.6 library as xml engine
 *   - Use libogc v1.8.0 library as Wii interface engine
 *  - General:
 *   - Started programming in C++.
 *   - Setup basic directory structure for new project.
 *   - Store source code in Google code SVN repository.
 *   - Build game with devkitPPC r19 compiler.
 *
 * @section Licence
 * Copyright (c) 2008-2010 PlaatSoft
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
 
// -----------------------------------------------------------
// The End
// -----------------------------------------------------------
 