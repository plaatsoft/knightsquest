/** 
 *  @file 
 *  @brief The file contain the Pointer class methods 
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

#include "General.h"
#include "grrlib.h"
#include "Pointer.h"
#include "Settings.h"
#include "Trace.h"
#include "Button.h"
#include "Sound.h"
  
extern Game 		 game;
extern Settings	 *settings;
extern Sound		 *sound;
extern Trace 		 *trace;
extern Button 		 *buttons[MAX_BUTTONS];

// ------------------------------
// Constructor 
// ------------------------------

/**
 * Constructor
 * Init all properties with default values.
 */
Pointer::Pointer(void)
{
   const char *s_fn="Pointer::Pointer";
   trace->event(s_fn,0,"enter");
   
   x=0;
   xOffset=0;
   y=0;
   yOffset=0;
   angle=0;
   index=0;
   rumble=0;
   
   selectedA=false;   
	selectedB=false; 
   selected1=false;
   selected2=false;
   
   trace->event(s_fn,0,"leave [void]");
}

// ------------------------------
// Destructor
// ------------------------------

/**
 * Destructor
 * Clean up all allocated memory
 */
Pointer::~Pointer(void)
{
  const char *s_fn="Pointer::~Pointer";
  trace->event(s_fn,0,"enter");

  // Stop rumble
  WPAD_Rumble(index,0);  
  
  trace->event(s_fn,0,"Pointer [%d] destroyed", index);
  
  trace->event(s_fn,0,"leave [void]");
}

// ------------------------------
// Others
// ------------------------------

/**
 * Get Next of Previous letter or number.
 * @param letter 	The letter
 * @param up		The next letter (true) previous letter (false)
 * @return letter 
 */
char *Pointer::getLetter(char *name, int index, bool up)
{
	if (up)
	{	
		if (name[index]==90) 
		{
			name[index]='0';
		}
		else if (name[index]==57) 
		{
			name[index]='A';
		}
		else 
		{
			name[index]++;				
		}
	}
	else
	{	
		if (name[index]==65) 
		{
			name[index]='9';
		}
		else if (name[index]==48) 
		{
			name[index]='Z';
		}
		else 
		{
			name[index]--;
		}					
	}
	return name;
}

/**
 * Process button1x action
 */
void Pointer::button1x(void)
{
   if (!selected1)
   {
      selected1=true;
	  
	  int track=sound->getMusicTrack();
	  sound->setMusicTrack(++track);
   }        
}

/**
 * Process button2y action
 */
void Pointer::button2y(void)
{
   if (!selected2)
   { 
      selected2=true;   
	  
	  int track=sound->getMusicTrack();
	  sound->setMusicTrack(--track);
   }
}

/**
 * Process button plus action
 * @param index	The index identify with action must be executed.
 */
void Pointer::buttonPlus(int index)
{
	const char *s_fn="Pointer::buttonPlus";
	trace->event(s_fn,0,"enter [index=%d]",index);
  
	int volume;
	int track;

	switch (index)
	{	 
		case 0:	// First Character
				settings->setName(getLetter(settings->getName(), 0, true));
				break;
		  
		case 1: // Second Character
				settings->setName(getLetter(settings->getName(), 1, true));
				break;
		  
		case 2: // Third Character
				settings->setName(getLetter(settings->getName(), 2, true));	
				break;
		  
		case 3: // Fourth Character
				settings->setName(getLetter(settings->getName(), 3, true));
				break;
				
		case 4: // Fifth Character
				settings->setName(getLetter(settings->getName(), 4, true));
				break;
				
		case 5: // Sixth Character
				settings->setName(getLetter(settings->getName(), 5, true));
				break;
				
		case 6:	// Music volume
				volume=sound->getMusicVolume();
				sound->setMusicVolume(++volume);   
				break;
       
		case 7:	// Effect volume
				volume=sound->getEffectVolume();
				sound->setEffectVolume(++volume);  
				break;

		case 8:	// Prev music track
				track=sound->getMusicTrack();
				sound->setMusicTrack(++track);   
				break;
   }
   trace->event(s_fn,0,"leave");
}

/**
 * Process button minus action
 * @param index	The index identify with action must be executed.
 */
void Pointer::buttonMinus(int index)
{
	const char *s_fn="Pointer::buttonMinus";
	trace->event(s_fn,0,"enter [index=%d]",index);
   
	int volume;
	int track;
	
	switch (index)   
	{
	   case 0:  // First Character		  
				settings->setName(getLetter(settings->getName(), 0, false));
				break;
			
		case 1:	// Second Character
				settings->setName(getLetter(settings->getName(), 1, false));	
				break;
		  
		case 2: // Third Character
				settings->setName(getLetter(settings->getName(), 2, false));
				break;   
		  				
		case 3: // Fourth Character
				settings->setName(getLetter(settings->getName(), 3, false));
				break; 
				
		case 4: // Fifth Character
				settings->setName(getLetter(settings->getName(), 4, false));
				break; 

		case 5: // Sixth Character
				settings->setName(getLetter(settings->getName(), 5, false));
				break;
				
		case 6:	// Music volume
				volume=sound->getMusicVolume();
				sound->setMusicVolume(--volume);   
				break;
       
		case 7: // Effect volume
				volume=sound->getEffectVolume();
				sound->setEffectVolume(--volume);  
				break;

		case 8:  // Music track
				track=sound->getMusicTrack();
				sound->setMusicTrack(--track);   
				break;

	}
	trace->event(s_fn,0,"leave");
}

/**
 * Process button scroll action
 * @param x	The x location of the pointer.
 * @param y	The y location of the pointer.
 */
void Pointer::buttonScroll(int x,int y )
{ 
	switch (game.stateMachine)
	{
		case stateLocalHighScore:
		case stateTodayHighScore:
		case stateGlobalHighScore:
		case stateReleaseNotes:
		{	     
			if ((buttons[1]!=NULL) && (buttons[1]->onSelect(x,y,false)))
			{
				if ((y-40)>SCROLLBAR_Y_MIN && (y-40)<SCROLLBAR_Y_MAX) 
				{
					buttons[1]->setY(y-40);	
					game.scrollIndex=(buttons[1]->getY()-SCROLLBAR_Y_MIN)/6;
				}
			}
		}
	}
}

/**
 * Process button A action
 * @param x	The x location of the pointer.
 * @param y	The y location of the pointer.
 */
void Pointer::buttonA(int x, int y)
{
	const char *s_fn="Pointer::buttonA";

	if (selectedA) return;
	selectedA=true;
	  
   trace->event(s_fn,0,"enter [x=%d|y=%d]",x,y);
	 
	switch (game.stateMachine)
	{
		case stateIntro1:
		{
			// if A button is pressed continue to next intro screen
			game.stateMachine=stateIntro2;
		}
		break;

		case stateIntro2:
		{
			// if A button is pressed continue to next intro scree
			game.stateMachine=stateIntro3;
		}
		break;
	 
		case stateIntro3:
		{
			// if A button is pressed continue to main menu
			game.stateMachine=stateMainMenu;
		}
		break;
		
	 	case stateMainMenu:
		{	
			if ((buttons[0]!=NULL) && (buttons[0]->onSelect(x,y,true)))
			{   
				// Play button	 	
				game.stateMachine=stateGame;
			}
							
			// Check if button is pressed on screen
			if ((buttons[1]!=NULL) && (buttons[1]->onSelect(x,y,true)))
			{
				// Highscore button	      
				game.stateMachine=stateLocalHighScore;
			}
		
			if ((buttons[2]!=NULL) && (buttons[2]->onSelect(x,y,true)))
			{
				// Credits button	      
				game.stateMachine=stateHelp1;
			}
		
			if ((buttons[3]!=NULL) && (buttons[3]->onSelect(x,y,true)))
			{
				// Credits button	      
				game.stateMachine=stateCredits;
			}
		
			if ((buttons[4]!=NULL) && (buttons[4]->onSelect(x,y,true)))
			{
				// Sound Settings button	      
				game.stateMachine=stateSoundSettings;
			}
		
			if ((buttons[5]!=NULL) && (buttons[5]->onSelect(x,y,true)))
			{
				// Release Notes button	      
				game.stateMachine=stateReleaseNotes;
			}
		
			if ((buttons[6]!=NULL) && (buttons[6]->onSelect(x,y,true)))
			{
				// User Initials button	      
				game.stateMachine=stateGameSettings;
			}
			
			if ((buttons[7]!=NULL) && (buttons[7]->onSelect(x,y,true)))
			{
				// Go back to HBC button    
				game.stateMachine=stateQuit;
			}
			
			if ((buttons[8]!=NULL) && (buttons[8]->onSelect(x,y,true)))
			{
				// Stop rumble
				WPAD_Rumble(index,0);
			
				// Reset Wii
				SYS_ResetSystem(SYS_RESTART,0,0);		   
			}	

			if ((buttons[9]!=NULL) && (buttons[9]->onSelect(x,y,true)))
			{
				// Donate button	      
				game.stateMachine=stateDonate;
			}
				
		}
		break;
   
		case stateHelp1:
		{
			// Check if button is pressed on screen
			if ((buttons[0]!=NULL) && (buttons[0]->onSelect(x,y,true)))
			{
				// Next button	 
				game.stateMachine=stateHelp2;	     
			}
		}
		break;

		case stateHelp2:
		{
			// Check if button is pressed on screen
			if ((buttons[0]!=NULL) && (buttons[0]->onSelect(x,y,true)))
			{
				// Next button	 
				game.stateMachine=stateMainMenu;	     
			}
		}
		break;

		case stateReleaseNotes:
		{
			// Check if button is pressed on screen
			if ((buttons[0]!=NULL) && (buttons[0]->onSelect(x,y,true)))
			{
				// Main Menu button	 
				game.stateMachine=stateMainMenu;	    
			}
		}
		break;

		case stateLocalHighScore:
		{
			// Check if button is pressed on screen
			if ((buttons[0]!=NULL) && (buttons[0]->onSelect(x,y,true)))
			{
				// Main Menu button	 
				game.stateMachine=stateTodayHighScore;	     
			}
		}
		break;
		
		case stateTodayHighScore:
		{
			// Check if button is pressed on screen
			if ((buttons[0]!=NULL) && (buttons[0]->onSelect(x,y,true)))
			{
				// Main Menu button	 
				game.stateMachine=stateGlobalHighScore;	     
			}
		}
		break;

		case stateGlobalHighScore:
		{
			// Check if button is pressed on screen
			if ((buttons[0]!=NULL) && (buttons[0]->onSelect(x,y,true)))
			{
				// Main Menu button	 
				game.stateMachine=stateMainMenu;	     
			}
		}
		break;
		
		case stateSoundSettings:
		{
			// Check if button is pressed on screen
			if ((buttons[0]!=NULL) && (buttons[0]->onSelect(x,y,true)))
			{
				// Main Menu button.
				game.stateMachine=stateMainMenu;	
				
				// Store new values in setting object.
				settings->setMusicVolume(sound->getMusicVolume());
				settings->setEffectVolume(sound->getEffectVolume());
				
				// Store settings to file.
				settings->save(SETTING_FILENAME); 
			}
			
			if ((buttons[1]!=NULL) && (buttons[1]->onSelect(x,y,true)))
			{
				// - music volume button event	           
			    buttonMinus(3);   
			}
			
			if ((buttons[2]!=NULL) && (buttons[2]->onSelect(x,y,true)))
			{
				// + music volume button event	           
			    buttonPlus(3); 
			}
				
			if ((buttons[3]!=NULL) && (buttons[3]->onSelect(x,y,true)))
			{
				// - effect volume button event	           
			    buttonMinus(4);  
			}
			 
			if ((buttons[4]!=NULL) && (buttons[4]->onSelect(x,y,true)))
			{
				// + effect volume button event	           
			    buttonPlus(4);
			}
			
			if ((buttons[5]!=NULL) && (buttons[5]->onSelect(x,y,true)))
			{
				// - music track button event	           
			    buttonMinus(5); 
			}

			if ((buttons[6]!=NULL) && (buttons[6]->onSelect(x,y,true)))
			{			
				// + music track  button event	           
			    buttonPlus(5);
			}
		}
		break;
	 
		case stateGameSettings:
		{ 			
			if ((buttons[0]!=NULL) && (buttons[0]->onSelect(x,y,true)))
			{
				// Main Menu button	 
				settings->save(SETTING_FILENAME); 
				game.stateMachine=stateMainMenu;	     
			}
			
			// Check if button is pressed on screen
			if ((buttons[1]!=NULL) && (buttons[1]->onSelect(x,y,true)))
			{
				// + First Character button event           
				buttonPlus(0);  
			}
		
			if ((buttons[2]!=NULL) && (buttons[2]->onSelect(x,y,true)))
			{
				// - First Character button event           
				buttonMinus(0);  
			}
						
			if ((buttons[3]!=NULL) && (buttons[3]->onSelect(x,y,true)))
			{
				// + Second Character button event           
				buttonPlus(1);  
			}
		
			if ((buttons[4]!=NULL) && (buttons[4]->onSelect(x,y,true)))
			{
				// - Second Character button event           
				buttonMinus(1); 
			}
					    
			if ((buttons[5]!=NULL) && (buttons[5]->onSelect(x,y,true)))
			{
				// + Third Character button event           
				buttonPlus(2);  
			}
			
			if ((buttons[6]!=NULL) && (buttons[6]->onSelect(x,y,true)))
			{
				// - Third Character button event           
				buttonMinus(2);  
			}
			
			if ((buttons[7]!=NULL) && (buttons[7]->onSelect(x,y,true)))
			{
				// + Fourth Character button event           
				buttonPlus(3);  
			}
			
			if ((buttons[8]!=NULL) && (buttons[8]->onSelect(x,y,true)))
			{
				// - Fourth Character button event           
				buttonMinus(3);  
			}
			
			if ((buttons[9]!=NULL) && (buttons[9]->onSelect(x,y,true)))
			{
				// + Fifth Character button event           
				buttonPlus(4);  
			}
			
			if ((buttons[10]!=NULL) && (buttons[10]->onSelect(x,y,true)))
			{
				// - Fifth Character button event           
				buttonMinus(4);  
			}

			if ((buttons[11]!=NULL) && (buttons[11]->onSelect(x,y,true)))
			{
				// + Sixth Character button event           
				buttonPlus(5);  
			}
			
			if ((buttons[12]!=NULL) && (buttons[12]->onSelect(x,y,true)))
			{
				// - Sixth Character button event           
				buttonMinus(5);  
			}
		}
		break; 

		case stateCredits:
		{
			// Check if button is pressed on screen
			if ((buttons[0]!=NULL) && (buttons[0]->onSelect(x,y,true)))
			{
				// Main Menu button	 	
				game.stateMachine=stateMainMenu;	    
			}
		}
		break;
			
		case stateGame:
		{	
			
				
		}
		break;
		
		case stateQuitGame:
		{
			if ((buttons[0]!=NULL) && (buttons[0]->onSelect(x,y,true)))
			{
				// Yes button
				game.stateMachine=stateMainMenu;
				
				// Score current score;
				//game.event=eventSaveHighScore;
			}

			if ((buttons[1]!=NULL) && (buttons[1]->onSelect(x,y,true)))
			{
				// No button
				game.stateMachine=stateGame;
			}	
		}
		break;
		
		case stateGameOver:
		{
			if ((buttons[0]!=NULL) && (buttons[0]->onSelect(x,y,true)))
			{
				// Retry button
				game.stateMachine=stateGame;
			}

			if ((buttons[1]!=NULL) && (buttons[1]->onSelect(x,y,true)))
			{
				// Quit button
				game.stateMachine=stateMainMenu;
			}	
		}
		break;
		
		case stateDonate:
		{
			// Check if button is pressed on screen
			if ((buttons[0]!=NULL) && (buttons[0]->onSelect(x,y,true)))
			{
				// Main Menu button	 
				game.stateMachine=stateMainMenu;	     
			}
		}
		break;
	}
	trace->event(s_fn,0,"leave");
}

/**
 * Process pointer (WiiMotes) events
 */
void Pointer::action(void)
{
	const char *s_fn="Pointer::action";

	// Scan for button events
	WPAD_SetVRes(index, 640, 528);
	WPAD_ScanPads();

	// Scan for ir events 
	WPAD_IR(index, &ir); 
	x=ir.sx-WSP_POINTER_X;
	xOffset=x+IR_X_OFFSET;
	y=ir.sy-WSP_POINTER_Y;
	yOffset=y+IR_Y_OFFSET;
	angle=ir.angle;
	
	// disable angle to improve navigation.
	angle=0;
				
	// Only the first WiiMote can control the game.
	if (index==0)
	{
		u32 wpaddown = WPAD_ButtonsDown(index);
		u32 wpadup   = WPAD_ButtonsUp(index);
		u32 wpadheld = WPAD_ButtonsHeld(index);

		if (wpaddown & BUTTON_A) 
		{
			if (!selectedB) 
			{
				buttonA(xOffset,yOffset); 
			}
		}
		
		if (wpadup & BUTTON_A) 
		{
			if (!selectedB) 
			{
				selectedA=false;

			}
		}
		
		if (wpaddown & BUTTON_B) 
		{
			if (selectedB) return;
			selectedB=true;
	
			// Start fast weapon	select
			if ((!selectedA) && (game.stateMachine==stateGame))
			{	
				// Click sound
				sound->effect(SOUND_CLICK);
			}
		}
		
		if (wpadup & BUTTON_B) 
		{
			selectedB=false;
			
			if ((!selectedA) && (game.stateMachine==stateGame))
			{	

			}
		}
		
			
		if (wpaddown & BUTTON_1 ) 
		{
			button1x();								
		}
    
		if (wpadup & BUTTON_1) 
		{
			selected1=false;		
		}
			
		if (wpaddown & BUTTON_2 ) 
		{
			button2y();		
		}
	
		if (wpadup & BUTTON_2) 
		{
			selected2=false;
		}

		if (wpadheld & BUTTON_A) 
		{
			buttonScroll(xOffset,yOffset);	 
		}
		
		// Scan for button events
		if (wpaddown & WPAD_BUTTON_HOME) 
		{
			trace->event(s_fn,0,"Home button pressed");
			if (game.stateMachine==stateMainMenu)
			{			
				game.stateMachine=stateQuit;
			}
			else
			{
				game.stateMachine=stateMainMenu;
				/*if (game.stateMachine==stateGame)
				{
					game.stateMachine=stateQuitGame;
				}
				else if (game.stateMachine!=stateQuitGame)
				{
					game.stateMachine=stateMainMenu;
				}*/
			}		
		}
	
		// Make screenshot 
		if (wpadheld & BUTTON_PLUS)
		{
			char filename[MAX_LEN];
			struct tm *level;	   
			time_t dt=time(NULL);
			level = localtime(&dt);
			sprintf(filename,"%sKightsQuest-%04d%02d%02d%02d%02d%02d.png", GAME_DIRECTORY, level->tm_year+1900,level->tm_mon+1, level->tm_mday,  level->tm_hour, level->tm_min, level->tm_sec);		  
			GRRLIB_ScrShot(filename);	
		}
	}
	
	if (rumble>0) 
	{
		// Enable rumble
		rumble--;
		WPAD_Rumble(index,1); 
	}
	else 
	{
		// Disable rumble
		WPAD_Rumble(index,0);
	}
}

/**
 * Draw pointer image on screen
 */
void Pointer::draw(void)
{   	
	// Draw Pointer on screen
	GRRLIB_DrawImg( x, y, image, angle, 1.0, 1.0, color );		
}

// ------------------------------
// Setters 
// ------------------------------

/**
 * Set pointer index
 * @param index1	The unique index number of the pointer.
 */
void Pointer::setIndex(int index1)
{
   const char *s_fn="Pointer::setIndex";
   trace->event(s_fn,0,"%d",index1);
  
   index = index1;
}

/**
 * Set pointer x location
 * @param x1		The x position of the pointer.
 */
void Pointer::setX(int x1)
{
   const char *s_fn="Pointer::setX";
   trace->event(s_fn,0,"%d",x1);
   
   if ((x1>=0) && (x1<=MAX_HORZ_PIXELS))
   {
      x = x1;
   }
}

/**
 * Set pointer Y location
 * @param y1		The y position of the pointer.
 */
void Pointer::setY(int y1)
{
   const char *s_fn="Pointer::setY";
   trace->event(s_fn,0,"%d",y1);
   
   if ((y1>=0) && (y1<=MAX_VERT_PIXELS))
   {
      y = y1;
   }
}

/**
 * Set pointer angle
 * @param angle1	The angle of the pointer.
 */
void Pointer::setAngle(int angle1)
{
   const char *s_fn="Pointer::setAngle";
   trace->event(s_fn,0,"%d",angle1);
   
   if ((angle1>=0) && (angle1<=MAX_ANGLE))
   {
      angle=angle1;
   }
} 

/**
 * Set pointer iamge
 * @param image1	The image of the pointer.
 */
void Pointer::setImage(GRRLIB_texImg *image1)
{
   const char *s_fn="Pointer::setImage";
   trace->event(s_fn,0,"data");
   
   image = image1;
}

/**
 * Set pointer rumble counter
 * @param rumble1 	The rumble counter of the pointer.
 */
void Pointer::setRumble(int rumble1)
{
   rumble=rumble1;
}

/**
 * Set pointer color
 * @param color1	The color of the pointer.
 */
void Pointer::setColor(int color1)
{   
   color=color1;
}

// ------------------------------
// Getters
// ------------------------------

/** 
 * Get pointer x value
 * @return x position
 */
int Pointer::getX(void)
{
	return x;
}

/** 
 * Get pointer y value
 * @return y position
 */
int Pointer::getY(void)
{
	return y;
}
	
/** 
 * Get pointer xOffset value
 * @return xOffset position
 */
int Pointer::getXOffset(void)
{
	return xOffset;
}

/** 
 * Get pointer yOffset value
 * @return yOffset position
 */
int Pointer::getYOffset(void)
{
	return yOffset;
}

// ------------------------------
// The End
// ------------------------------