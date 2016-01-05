/** 
 *  @file 
 *  @brief  The file contain the main class methods
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

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <malloc.h>
#include <math.h>
#include <ogcsys.h>
#include <gccore.h>
#include <gcmodplay.h> 
#include <wiiuse/wpad.h>
#include <fcntl.h>
#include <unistd.h>
#include <time.h> 
#include <stdarg.h>
#include <asndlib.h>
#include <fat.h>
#include <mxml.h>
#include <sys/dir.h>
#include <ogc/lwp_watchdog.h>	

#include "grrlib.h"
#include "General.h"
#include "Trace.h"
#include "Settings.h"
#include "HighScore.h"
#include "Sound.h"
#include "Button.h"
#include "Pointer.h"
#include "http.h"
#include "font_ttf.h" 
#include "Land.h"

// -----------------------------------------------------------
// PROTOTYPES
// -----------------------------------------------------------

static u8 calculateFrameRate(void);

// -----------------------------------------------------------
// TYPEDEF
// -----------------------------------------------------------

typedef struct 
{
  // png + jpg Image index     
  GRRLIB_texImg *intro1;
  GRRLIB_texImg *intro2;
  GRRLIB_texImg *intro3;
  
  GRRLIB_texImg *soundicon;
  
  GRRLIB_texImg *logo1;
  GRRLIB_texImg *logo2;
  GRRLIB_texImg *logo3;
  GRRLIB_texImg *logo4;
  GRRLIB_texImg *logo5;
  GRRLIB_texImg *logo6;		
  GRRLIB_texImg *logo;
  
  GRRLIB_texImg *background1;
  GRRLIB_texImg *background2;
  GRRLIB_texImg *bar;
  GRRLIB_texImg *barCursor;  
  
  GRRLIB_texImg *scrollbar;
  GRRLIB_texImg *scrollTop;
  GRRLIB_texImg *scrollMiddle;
  GRRLIB_texImg *scrollBottom;
      
  GRRLIB_texImg *button1;
  GRRLIB_texImg *buttonFocus1;  
  GRRLIB_texImg *button2;
  GRRLIB_texImg *buttonFocus2;
  GRRLIB_texImg *button3;
  GRRLIB_texImg *buttonFocus3;
  GRRLIB_texImg *button4;
  GRRLIB_texImg *buttonFocus4;
  
  GRRLIB_texImg *pointer1;
  GRRLIB_texImg *pointer2;
  GRRLIB_texImg *pointer3; 
  GRRLIB_texImg *pointer4;
} 
image;

image images;

typedef struct
{
   time_t dt;							/**< Time stamp of entry */
   char   score[MAX_LEN];			/**< Score */
   char   name[MAX_LEN];			/**< Player nickname */
   char   location[MAX_LEN];		/**< Location (Country - City) */
}
topscore;

/** topscore contains today highscore list [0..40] */
topscore todayHighScore[MAX_TODAY_HIGHSCORE+1];

/** topscore contains global highscore list [0..40] */
topscore globalHighScore[MAX_GLOBAL_HIGHSCORE+1];

// -----------------------------------------------------------
// VARIABLES
// -----------------------------------------------------------

// logo1 Image
extern const unsigned char     pic4data[];
extern int      pic4length;

// logo2 Image
extern const unsigned char     pic5data[];
extern int      pic5length;

// logo3 Image
extern const unsigned char     pic6data[];
extern int      pic6length;

// logo4 Image
extern const unsigned char     pic7data[];
extern int      pic7length;

// logo5 Image
extern const unsigned char     pic8data[];
extern int      pic8length;

// logo6 Image
extern const unsigned char     pic9data[];
extern int      pic9length;

// Background1 Image
extern const unsigned char     pic10data[];
extern int      pic10length;

// Background2 Image
extern const unsigned char     pic11data[];
extern int      pic11length;

// Background3 Image
extern const unsigned char     pic12data[];
extern int      pic12length;

// Bar Image
extern const unsigned char     pic14data[];
extern int      pic14length;

// Bar_cursor Image
extern const unsigned char     pic15data[];
extern int      pic15length;

// Sound Icon Image
extern const unsigned char     pic16data[];
extern int      pic16length;

// Scrollbar image
extern const unsigned char     pic33data[];
extern const int      pic33length;

// ScrollTop image
extern const unsigned char     pic34data[];
extern const int      pic34length;

// ScrollMiddle image
extern const unsigned char     pic35data[];
extern const int      pic35length;

// scrollBottom image
extern const unsigned char     pic36data[];
extern const int      pic36length;

// Pointer1 Image
extern const unsigned char     pic200data[];
extern int      pic200length;

// Pointer2 Image
extern const unsigned char     pic201data[];
extern int      pic201length;

// Pointer3 Image
extern const unsigned char     pic202data[];
extern int      pic202length;

// Pointer4 Image
extern const unsigned char     pic203data[];
extern int      pic203length;

// Button1 Image
extern const unsigned char     pic600data[];
extern int      pic600length;

// Button1Focus Image
extern const unsigned char     pic601data[];
extern int      pic601length;

// Button2 Image
extern const unsigned char     pic602data[];
extern int      pic602length;

// Button2Focus Image
extern const unsigned char     pic603data[];
extern int      pic603length;

// Button3 Image
extern const unsigned char     pic604data[];
extern int      pic604length;

// Button3Focus Image
extern const unsigned char     pic605data[];
extern int      pic605length;

// Button4 Image
extern const unsigned char     pic606data[];
extern int      pic606length;

// Button4Focus Image
extern const unsigned char     pic607data[];
extern int      pic607length;

u32          *frameBuffer[1] 	= {NULL};
GXRModeObj   *rmode 				= NULL;
Mtx          GXmodelView2D;

Game 			 game;							/**< Game parameters */
Trace     	 *trace;							/**< Trace object */
Settings  	 *settings;						/**< Settings object */
HighScore 	 *highScore;					/**< Highscore object */
Sound      	 *sound;							/**< Sound object */
Pointer   	 *pointers[MAX_POINTERS];	/**< Pointer array */
Button    	 *buttons[MAX_BUTTONS];		/**< Button array */
GRRLIB_ttfFont *myFont;						/**< TTF Font */
 
// -----------------------------------
// Destroy METHODES
// -----------------------------------

/**
 * destroy all button objects.
 */
void destroyButtons(void)
{
	const char *s_fn="destroyButtons";
	trace->event(s_fn,0,"enter");
	
	// Destroy all Buttons
	for( int i=0; i<MAX_BUTTONS; i++)
   {
		if (buttons[i]!=NULL)
		{
			delete buttons[i];
			buttons[i]=NULL;
		}
   }	
	trace->event(s_fn,0,"leave");
}

/**
 * destroy all pointers objects.
 */
void destroyPointers(void)
{
	const char *s_fn="destroyPointers";
	trace->event(s_fn,0,"enter");
	
	// Destroy Pointers
	for( int i=0; i<MAX_POINTERS; i++)
	{
		if (pointers[i]!=NULL)
		{
			delete pointers[i];
			pointers[i]=NULL;
		}
	}	
	
	trace->event(s_fn,0,"leave");
}


/**
 * destroy sound object.
 */
void destroySound(void)
{
	const char *s_fn="destroySound";
	trace->event(s_fn,0,"enter");
  
	// Destroy Sound
	if (sound!=NULL)
	{
		delete sound;
		sound=NULL;
	}	
	trace->event(s_fn,0,"leave");
}

/**
 * destroy local highscore object.
 */
void destroyHighScore(void)
{
	const char *s_fn="destroyHighScore";
	trace->event(s_fn,0,"enter");
	
	// Destroy Highscore
	if (highScore!=NULL)
	{
		delete highScore;
		highScore=NULL;
	}
	
	trace->event(s_fn,0,"leave");
}

/**
 * destroy setting object.
 */
void destroySettings(void)
{
	const char *s_fn="destroySettings";
	trace->event(s_fn,0,"enter");
	
	// Destroy Settings
	if (settings!=NULL)
	{
		delete settings;
		settings=NULL;
	}
	
	trace->event(s_fn,0,"leave");
}

/**
 * destroy trace object.
 */
void destroyTrace(void)
{	
	// Destroy Trace
	if (trace!=NULL)
	{
		delete trace;
		trace=NULL;
	}
}

/**
 * destroy all images in memory.
 */
void destroyImages(void)
{
   const char *s_fn="destroyImages";
   trace->event(s_fn,0,"enter");

   GRRLIB_FreeTexture(images.logo1);
   GRRLIB_FreeTexture(images.logo2);
	GRRLIB_FreeTexture(images.logo3);
	GRRLIB_FreeTexture(images.logo4);
	GRRLIB_FreeTexture(images.logo5);
	GRRLIB_FreeTexture(images.logo6);
		
   GRRLIB_FreeTexture(images.background1);
   GRRLIB_FreeTexture(images.background2);
   
   GRRLIB_FreeTexture(images.bar);
   GRRLIB_FreeTexture(images.barCursor);
   GRRLIB_FreeTexture(images.soundicon);
		
   GRRLIB_FreeTexture(images.pointer1);
   GRRLIB_FreeTexture(images.pointer2);
   GRRLIB_FreeTexture(images.pointer3);
   GRRLIB_FreeTexture(images.pointer4);
         	
   GRRLIB_FreeTexture(images.button1);
   GRRLIB_FreeTexture(images.buttonFocus1);
   GRRLIB_FreeTexture(images.button2);
   GRRLIB_FreeTexture(images.buttonFocus2);
   GRRLIB_FreeTexture(images.button3);
   GRRLIB_FreeTexture(images.buttonFocus3);
   GRRLIB_FreeTexture(images.button4);
   GRRLIB_FreeTexture(images.buttonFocus4);
     
   trace->event(s_fn,0,"leave");
}

// -----------------------------------
// INIT METHODES
// -----------------------------------

/**
 * Initialise today highscore.
 */
void initTodayHighScore(void)
{
	const char *s_fn="initTodayHighScore";
	trace->event(s_fn,0,"enter");
   
	// Init today highscore memory
	for(int i=0; i<MAX_TODAY_HIGHSCORE; i++)
	{
		todayHighScore[i].score[0]=0x00;
		todayHighScore[i].dt=0;
		todayHighScore[i].name[0]=0x00;
		todayHighScore[i].location[0]=0x00;
	} 
	trace->event(s_fn,0,"leave [void]");
}

/**
 * Initialise global highscore.
 */
void initGlobalHighScore(void)
{
	const char *s_fn="initGlobalHighScore";	
	trace->event(s_fn,0,"enter");
    
	// Init global highscore memory
	for(int i=0; i<MAX_GLOBAL_HIGHSCORE; i++)
	{
		globalHighScore[i].score[0]=0x00;
		globalHighScore[i].dt=0;
		globalHighScore[i].name[0]=0x00;
		globalHighScore[i].location[0]=0x00;
	} 
  
	trace->event(s_fn,0,"leave [void]");
}

/**
 * Initialise images.
 */
void initImages(void)
{
	const char *s_fn="initImages";
	trace->event(s_fn,0,"enter");

   images.logo1=GRRLIB_LoadTexture( pic4data );
	GRRLIB_SetMidHandle( images.logo1, true );
	
	images.logo2=GRRLIB_LoadTexture( pic5data );
	images.logo=GRRLIB_LoadTexture( pic5data );
	GRRLIB_InitTileSet(images.logo, images.logo->w, 1, 0);
	
	images.logo3=GRRLIB_LoadTexture( pic6data );
	images.logo4=GRRLIB_LoadTexture( pic7data );
	images.logo5=GRRLIB_LoadTexture( pic8data );
	images.logo6=GRRLIB_LoadTexture( pic9data );
   
	images.background1=GRRLIB_LoadTexture( pic10data );
	images.background2=GRRLIB_LoadTexture( pic11data );
   
	images.bar=GRRLIB_LoadTexture( pic14data );
	images.barCursor=GRRLIB_LoadTexture( pic15data );
	
	images.soundicon=GRRLIB_LoadTexture( pic16data );
	GRRLIB_SetMidHandle( images.soundicon, true ); 	
	
	images.scrollbar=GRRLIB_LoadTexture(pic33data);
	images.scrollTop=GRRLIB_LoadTexture( pic34data);
	images.scrollMiddle=GRRLIB_LoadTexture( pic35data);
	images.scrollBottom=GRRLIB_LoadTexture( pic36data);
   
	images.pointer1=GRRLIB_LoadTexture( pic200data); 
	images.pointer2=GRRLIB_LoadTexture( pic201data);
	images.pointer3=GRRLIB_LoadTexture( pic202data);
	images.pointer4=GRRLIB_LoadTexture( pic203data);
      
	images.button1=GRRLIB_LoadTexture( pic600data );
	images.buttonFocus1=GRRLIB_LoadTexture( pic601data );  
	images.button2=GRRLIB_LoadTexture( pic602data );
	images.buttonFocus2=GRRLIB_LoadTexture( pic603data );  
	images.button3=GRRLIB_LoadTexture( pic604data );
	images.buttonFocus3=GRRLIB_LoadTexture( pic605data );  
	images.button4=GRRLIB_LoadTexture( pic606data );
	images.buttonFocus4=GRRLIB_LoadTexture( pic607data );  
     
	trace->event(s_fn,0,"leave [void]");
}


/**
 * Initialise WiiMote pointers.
 */
void initPointers(void)
{
   const char *s_fn="initPointers";
   trace->event(s_fn,0,"enter");
      
   pointers[0] = new Pointer();   
   pointers[0]->setX(320);
   pointers[0]->setY(240);
   pointers[0]->setAngle(0);
   pointers[0]->setImage(images.pointer1);
   pointers[0]->setIndex(0);
	pointers[0]->setColor(IMAGE_COLOR);

   pointers[1] = new Pointer(); 
   pointers[1]->setX(320);
   pointers[1]->setY(240);
   pointers[1]->setAngle(0);
   pointers[1]->setImage(images.pointer2);
   pointers[1]->setIndex(1);
	pointers[1]->setColor(IMAGE_COLOR);

   pointers[2] = new Pointer(); 
   pointers[2]->setX(320);
   pointers[2]->setY(240);
   pointers[2]->setAngle(0);
   pointers[2]->setImage(images.pointer3);
   pointers[2]->setIndex(2);
	pointers[2]->setColor(IMAGE_COLOR);

   pointers[3] = new Pointer(); 
   pointers[3]->setX(340);
   pointers[3]->setY(240);
   pointers[3]->setAngle(0);
   pointers[3]->setImage(images.pointer4);	
   pointers[3]->setIndex(3);
	pointers[3]->setColor(IMAGE_COLOR);
      
   trace->event(s_fn,0,"leave [void]");
}


/**
 * Init screen buttons
 */
void initButtons(void)
{
	const char *s_fn="initButtons";
	trace->event(s_fn,0,"enter");

	// First destroy existing buttons
	destroyButtons();
   
	switch( game.stateMachine )	
	{			
		case stateMainMenu:
		{
			int ypos=40;
			if (rmode->xfbHeight==MAX_VERT_PIXELS) ypos-=15;
			
			// Play Button 
			buttons[0]=new Button();
			buttons[0]->setX(440);
			buttons[0]->setY(ypos);
			buttons[0]->setImageNormal(images.button2);
			buttons[0]->setImageFocus(images.buttonFocus2);
			buttons[0]->setLabel("Play");
			buttons[0]->setColor(IMAGE_COLOR);
			buttons[0]->setIndex(6);
			
			// HighScore Button 
			ypos+=40;
			buttons[1]=new Button();
			buttons[1]->setX(440);
			buttons[1]->setY(ypos);
			buttons[1]->setImageNormal(images.button2);
			buttons[1]->setImageFocus(images.buttonFocus2);
			buttons[1]->setLabel("High Score");
			buttons[1]->setColor(IMAGE_COLOR);
			buttons[1]->setIndex(0);
			
			// Help Button 
			ypos+=40;
			buttons[2]=new Button();
			buttons[2]->setX(440);
			buttons[2]->setY(ypos);
			buttons[2]->setImageNormal(images.button2);
			buttons[2]->setImageFocus(images.buttonFocus2);
			buttons[2]->setLabel("Help");			
			buttons[2]->setColor(IMAGE_COLOR);
			buttons[2]->setIndex(1);

			// Credits Button 
			ypos+=40;
			buttons[3]=new Button();
			buttons[3]->setX(440);
			buttons[3]->setY(ypos);
			buttons[3]->setImageNormal(images.button2);
			buttons[3]->setImageFocus(images.buttonFocus2);
			buttons[3]->setLabel("Credits");	
			buttons[3]->setColor(IMAGE_COLOR);
			buttons[3]->setIndex(2);
			
			// Release Notes Button 
			ypos+=40;
			buttons[5]=new Button();
			buttons[5]->setX(440);
			buttons[5]->setY(ypos);
			buttons[5]->setImageNormal(images.button2);
			buttons[5]->setImageFocus(images.buttonFocus2);
			buttons[5]->setLabel("Release Notes");	
			buttons[5]->setColor(IMAGE_COLOR);
			buttons[5]->setIndex(4);
			
			// Sound Settings Button 
			ypos+=40;
			buttons[4]=new Button();
			buttons[4]->setX(440);
			buttons[4]->setY(ypos);
			buttons[4]->setImageNormal(images.button2);
			buttons[4]->setImageFocus(images.buttonFocus2);
			buttons[4]->setLabel("Sound Settings");	
			buttons[4]->setColor(IMAGE_COLOR);
			buttons[4]->setIndex(3);
			
			// Game Settings Button 
			ypos+=40;
			buttons[6]=new Button();
			buttons[6]->setX(440);
			buttons[6]->setY(ypos);
			buttons[6]->setImageNormal(images.button2);
			buttons[6]->setImageFocus(images.buttonFocus2);
			buttons[6]->setLabel("Game Settings");	
			buttons[6]->setColor(IMAGE_COLOR);
			buttons[6]->setIndex(5);
			
			// Donate Button 
			ypos+=40;
			buttons[9]=new Button();
			buttons[9]->setX(440);
			buttons[9]->setY(ypos);
			buttons[9]->setImageNormal(images.button2);
			buttons[9]->setImageFocus(images.buttonFocus2);
			buttons[9]->setLabel("Donate");	
			buttons[9]->setColor(IMAGE_COLOR);
			buttons[9]->setIndex(5);
					
			// Exit HBC Button 
			ypos=400;
			if (rmode->xfbHeight==MAX_VERT_PIXELS) ypos-=38;
			
			buttons[7]=new Button();
			buttons[7]->setX(440);
			buttons[7]->setY(ypos);
			buttons[7]->setImageNormal(images.button2);
			buttons[7]->setImageFocus(images.buttonFocus2);
			buttons[7]->setLabel("Exit to HBC");	
			buttons[7]->setColor(IMAGE_COLOR);
			buttons[7]->setIndex(7);
	 
			// Reset Wii Button 
			ypos+=40;
			buttons[8]=new Button();
			buttons[8]->setX(440);
			buttons[8]->setY(ypos);
			buttons[8]->setImageNormal(images.button2);
			buttons[8]->setImageFocus(images.buttonFocus2);
			buttons[8]->setLabel("Reset Wii");	
			buttons[8]->setColor(IMAGE_COLOR);
			buttons[8]->setIndex(8);
		}
		break;
						
		case stateLocalHighScore:
	    {
			int ypos=460;
			if (rmode->xfbHeight==MAX_VERT_PIXELS) ypos-=20;
			
			// Next Button
			buttons[0]=new Button();
			buttons[0]->setX(225);
			buttons[0]->setY(ypos);
			buttons[0]->setImageNormal(images.button2);
			buttons[0]->setImageFocus(images.buttonFocus2);
			buttons[0]->setLabel("Next");	
			buttons[0]->setColor(IMAGE_COLOR);
			buttons[0]->setIndex(0);
			
			// Scrollbar button 
			buttons[1]=new Button();
			buttons[1]->setX(SCROLLBAR_x);
			buttons[1]->setY(SCROLLBAR_Y_MIN);
			buttons[1]->setImageNormal(images.scrollbar);
			buttons[1]->setImageFocus(images.scrollbar);
			buttons[1]->setLabel("");
			buttons[1]->setColor(IMAGE_COLOR);
			buttons[1]->setIndex(1);
		}
		break;
		
		case stateTodayHighScore:
	   {
		   int ypos=460;
			if (rmode->xfbHeight==MAX_VERT_PIXELS) ypos-=20;
			
			// Next Button
			buttons[0]=new Button();
			buttons[0]->setX(225);
			buttons[0]->setY(ypos);
			buttons[0]->setImageNormal(images.button2);
			buttons[0]->setImageFocus(images.buttonFocus2);
			buttons[0]->setLabel("Next");	
			buttons[0]->setColor(IMAGE_COLOR);
			buttons[0]->setIndex(0);
			
			// Scrollbar button 
			buttons[1]=new Button();
			buttons[1]->setX(SCROLLBAR_x);
			buttons[1]->setY(SCROLLBAR_Y_MIN);
			buttons[1]->setImageNormal(images.scrollbar);
			buttons[1]->setImageFocus(images.scrollbar);
			buttons[1]->setLabel("");
			buttons[1]->setColor(IMAGE_COLOR);
			buttons[1]->setIndex(1);
		}
		break;
		
		case stateGlobalHighScore:
	   {
		   int ypos=460;
			if (rmode->xfbHeight==MAX_VERT_PIXELS) ypos-=20;
			
			// Next Button
			buttons[0]=new Button();
			buttons[0]->setX(225);
			buttons[0]->setY(ypos);
			buttons[0]->setImageNormal(images.button2);
			buttons[0]->setImageFocus(images.buttonFocus2);
			buttons[0]->setLabel("Main Menu");	
			buttons[0]->setColor(IMAGE_COLOR);
			buttons[0]->setIndex(0);
			
			// Scrollbar button 
			buttons[1]=new Button();
			buttons[1]->setX(SCROLLBAR_x);
			buttons[1]->setY(SCROLLBAR_Y_MIN);
			buttons[1]->setImageNormal(images.scrollbar);
			buttons[1]->setImageFocus(images.scrollbar);
			buttons[1]->setLabel("");
			buttons[1]->setColor(IMAGE_COLOR);
			buttons[1]->setIndex(1);
		}
		break;
		
		case stateHelp1:
		{
		   int ypos=460;
			if (rmode->xfbHeight==MAX_VERT_PIXELS) ypos-=20;
			
			// Next Button
			buttons[0]=new Button();
			buttons[0]->setX(240);
			buttons[0]->setY(ypos);
			buttons[0]->setImageNormal(images.button2);
			buttons[0]->setImageFocus(images.buttonFocus2);
			buttons[0]->setLabel("Next");	
			buttons[0]->setColor(IMAGE_COLOR);	
			buttons[0]->setIndex(0);
		}
		break;
		
		case stateHelp2:
		{
	    	int ypos=460;
			if (rmode->xfbHeight==MAX_VERT_PIXELS) ypos-=20;
			
			// Next Button
			buttons[0]=new Button();
			buttons[0]->setX(240);
			buttons[0]->setY(ypos);
			buttons[0]->setImageNormal(images.button2);
			buttons[0]->setImageFocus(images.buttonFocus2);
			buttons[0]->setLabel("Next");	
			buttons[0]->setColor(IMAGE_COLOR);	
			buttons[0]->setIndex(0);
		}
		break;
				
		case stateCredits:
		{
		   int ypos=460;
			if (rmode->xfbHeight==MAX_VERT_PIXELS) ypos-=20;
			
			// Main Menu Button
			buttons[0]=new Button();
			buttons[0]->setX(240);
			buttons[0]->setY(ypos);
			buttons[0]->setImageNormal(images.button2);
			buttons[0]->setImageFocus(images.buttonFocus2);
			buttons[0]->setLabel("Main Menu");	
			buttons[0]->setColor(IMAGE_COLOR);
			buttons[0]->setIndex(0);
		}
		break;

		case stateDonate:
		{
		   int ypos=460;
			if (rmode->xfbHeight==MAX_VERT_PIXELS) ypos-=20;
			
			// Main Menu Button
			buttons[0]=new Button();
			buttons[0]->setX(240);
			buttons[0]->setY(ypos);
			buttons[0]->setImageNormal(images.button2);
			buttons[0]->setImageFocus(images.buttonFocus2);
			buttons[0]->setLabel("Main Menu");	
			buttons[0]->setColor(IMAGE_COLOR);
			buttons[0]->setIndex(0);
		}
		break;
		
		
		case stateReleaseNotes:
		{
		   int ypos=460;
			if (rmode->xfbHeight==MAX_VERT_PIXELS) ypos-=20;
			
			// Main Menu Button
			buttons[0]=new Button();
			buttons[0]->setX(240);
			buttons[0]->setY(ypos);
			buttons[0]->setImageNormal(images.button2);
			buttons[0]->setImageFocus(images.buttonFocus2);
			buttons[0]->setLabel("Main Menu");		
			buttons[0]->setColor(IMAGE_COLOR);
			buttons[0]->setIndex(0);
			
			// Scrollbar button 
			buttons[1]=new Button();
			buttons[1]->setX(SCROLLBAR_x);
			buttons[1]->setY(SCROLLBAR_Y_MIN);
			buttons[1]->setImageNormal(images.scrollbar);
			buttons[1]->setImageFocus(images.scrollbar);
			buttons[1]->setLabel("");
			buttons[1]->setColor(IMAGE_COLOR);
			buttons[1]->setIndex(1);
		}
		break;
		
		case stateSoundSettings:
		{
			int ypos=195;
			if (rmode->xfbHeight==MAX_VERT_PIXELS) ypos-=35; 
	
			// Music Volume - button 
			buttons[1]=new Button();
			buttons[1]->setX(20);
			buttons[1]->setY(ypos);
			buttons[1]->setImageNormal(images.button1);
			buttons[1]->setImageFocus(images.buttonFocus1);
			buttons[1]->setLabel("-");	
			buttons[1]->setColor(IMAGE_COLOR);
			buttons[1]->setIndex(1);
			
			// Music Volume + button 
			buttons[2]=new Button();
			buttons[2]->setX(540);
			buttons[2]->setY(ypos);
			buttons[2]->setImageNormal(images.button1);
			buttons[2]->setImageFocus(images.buttonFocus1);
			buttons[2]->setLabel("+");	
			buttons[2]->setColor(IMAGE_COLOR);
			buttons[2]->setIndex(2);
			
			ypos+=105;
			// Effect Volume - button 
			buttons[3]=new Button();
			buttons[3]->setX(20);
			buttons[3]->setY(ypos);
			buttons[3]->setImageNormal(images.button1);
			buttons[3]->setImageFocus(images.buttonFocus1);
			buttons[3]->setLabel("-");	
			buttons[3]->setColor(IMAGE_COLOR);
			buttons[3]->setIndex(3);
			
			// Effect Volume + button 
			buttons[4]=new Button();
			buttons[4]->setX(540);
			buttons[4]->setY(ypos);
			buttons[4]->setImageNormal(images.button1);
			buttons[4]->setImageFocus(images.buttonFocus1);
			buttons[4]->setLabel("+");	
			buttons[4]->setColor(IMAGE_COLOR);
			buttons[4]->setIndex(4);
			
			ypos+=80;
			// Music track - button 
			buttons[5]=new Button();
			buttons[5]->setX(140);
			buttons[5]->setY(ypos);
			buttons[5]->setImageNormal(images.button1);
			buttons[5]->setImageFocus(images.buttonFocus1);
			buttons[5]->setLabel("-");	
			buttons[5]->setColor(IMAGE_COLOR);
			buttons[5]->setIndex(5);
		
			// Music track + button 
			buttons[6]=new Button();
			buttons[6]->setX(420);
			buttons[6]->setY(ypos);
			buttons[6]->setImageNormal(images.button1);
			buttons[6]->setImageFocus(images.buttonFocus1);
			buttons[6]->setLabel("+");	
			buttons[6]->setColor(IMAGE_COLOR);
			buttons[6]->setIndex(6);
			
			ypos=460;
			if (rmode->xfbHeight==MAX_VERT_PIXELS) ypos-=20;
			
			// Main Menu Button
			buttons[0]=new Button();
			buttons[0]->setX(240);
			buttons[0]->setY(ypos);
			buttons[0]->setImageNormal(images.button2);
			buttons[0]->setImageFocus(images.buttonFocus2);
			buttons[0]->setLabel("Main Menu");	
			buttons[0]->setColor(IMAGE_COLOR);
			buttons[0]->setIndex(0);
		}
		break;
				
		case stateGameSettings:
	    {
			int xpos=35;			
			int ypos=150;
			
			if (rmode->xfbHeight!=MAX_VERT_PIXELS) ypos+=15; 
			
			// First letter + button 
			buttons[1]=new Button();
			buttons[1]->setX(xpos);
			buttons[1]->setY(ypos);
			buttons[1]->setImageNormal(images.button1);
			buttons[1]->setImageFocus(images.buttonFocus1);
			buttons[1]->setLabel("+");	
			buttons[1]->setColor(IMAGE_COLOR);
			buttons[1]->setIndex(1);

			// First letter - button 
			buttons[2]=new Button();
			buttons[2]->setX(xpos);
			buttons[2]->setY(ypos+145);
			buttons[2]->setImageNormal(images.button1);
			buttons[2]->setImageFocus(images.buttonFocus1);
			buttons[2]->setLabel("-");	
			buttons[2]->setColor(IMAGE_COLOR);
			buttons[2]->setIndex(2);

			// Second letter + button 
			xpos+=95;
			buttons[3]=new Button();
			buttons[3]->setX(xpos);
			buttons[3]->setY(ypos);
			buttons[3]->setImageNormal(images.button1);
			buttons[3]->setImageFocus(images.buttonFocus1);
			buttons[3]->setLabel("+");	
			buttons[3]->setColor(IMAGE_COLOR);
			buttons[3]->setIndex(3);

			// second letter - button 
			buttons[4]=new Button();
			buttons[4]->setX(xpos);
			buttons[4]->setY(ypos+145);
			buttons[4]->setImageNormal(images.button1);
			buttons[4]->setImageFocus(images.buttonFocus1);
			buttons[4]->setLabel("-");	
			buttons[4]->setColor(IMAGE_COLOR);
			buttons[4]->setIndex(4);

			// Third letter + button 
			xpos+=95;
			buttons[5]=new Button();
			buttons[5]->setX(xpos);
			buttons[5]->setY(ypos);
			buttons[5]->setImageNormal(images.button1);
			buttons[5]->setImageFocus(images.buttonFocus1);
			buttons[5]->setLabel("+");	
			buttons[5]->setColor(IMAGE_COLOR);
			buttons[5]->setIndex(5);

			// Third letter - button 
			buttons[6]=new Button();
			buttons[6]->setX(xpos);
			buttons[6]->setY(ypos+145);
			buttons[6]->setImageNormal(images.button1);
			buttons[6]->setImageFocus(images.buttonFocus1);
			buttons[6]->setLabel("-");
			buttons[6]->setColor(IMAGE_COLOR);		
			buttons[6]->setIndex(6);

			// Fourth letter + button 
			xpos+=95;
			buttons[7]=new Button();
			buttons[7]->setX(xpos);
			buttons[7]->setY(ypos);
			buttons[7]->setImageNormal(images.button1);
			buttons[7]->setImageFocus(images.buttonFocus1);
			buttons[7]->setLabel("+");	
			buttons[7]->setColor(IMAGE_COLOR);
			buttons[7]->setIndex(7);

			// Fourth letter - button 
			buttons[8]=new Button();
			buttons[8]->setX(xpos);
			buttons[8]->setY(ypos+145);
			buttons[8]->setImageNormal(images.button1);
			buttons[8]->setImageFocus(images.buttonFocus1);
			buttons[8]->setLabel("-");
			buttons[8]->setColor(IMAGE_COLOR);		
			buttons[8]->setIndex(8);
			
			// Fifth letter + button 
			xpos+=95;
			buttons[9]=new Button();
			buttons[9]->setX(xpos);
			buttons[9]->setY(ypos);
			buttons[9]->setImageNormal(images.button1);
			buttons[9]->setImageFocus(images.buttonFocus1);
			buttons[9]->setLabel("+");	
			buttons[9]->setColor(IMAGE_COLOR);
			buttons[9]->setIndex(9);

			// Fifth letter - button 
			buttons[10]=new Button();
			buttons[10]->setX(xpos);
			buttons[10]->setY(ypos+145);
			buttons[10]->setImageNormal(images.button1);
			buttons[10]->setImageFocus(images.buttonFocus1);
			buttons[10]->setLabel("-");
			buttons[10]->setColor(IMAGE_COLOR);	
			buttons[10]->setIndex(10);
			
			// Sixth letter + button 
			xpos+=95;
			buttons[11]=new Button();
			buttons[11]->setX(xpos);
			buttons[11]->setY(ypos);
			buttons[11]->setImageNormal(images.button1);
			buttons[11]->setImageFocus(images.buttonFocus1);
			buttons[11]->setLabel("+");	
			buttons[11]->setColor(IMAGE_COLOR);
			buttons[11]->setIndex(11);

			// Sixth letter - button 
			buttons[12]=new Button();
			buttons[12]->setX(xpos);
			buttons[12]->setY(ypos+145);
			buttons[12]->setImageNormal(images.button1);
			buttons[12]->setImageFocus(images.buttonFocus1);
			buttons[12]->setLabel("-");
			buttons[12]->setColor(IMAGE_COLOR);
			buttons[12]->setIndex(12);
			
			ypos=460;
			if (rmode->xfbHeight==MAX_VERT_PIXELS) ypos-=20;
			
			// Main Menu Button
			buttons[0]=new Button();
			buttons[0]->setX(240);
			buttons[0]->setY(ypos);
			buttons[0]->setImageNormal(images.button2);
			buttons[0]->setImageFocus(images.buttonFocus2);
			buttons[0]->setLabel("Main Menu");	
			buttons[0]->setColor(IMAGE_COLOR);
			buttons[0]->setIndex(0);
		}
		break;	
		
		case stateGame:
	    {									
			
		}
		break;
	
		case stateQuitGame:	
		{

		}
		break;
		
		case stateGameOver:	
		{

		}
		break;
	}
	trace->event(s_fn,0,"leave [void]");
}

/**
 * Init Network Module
 */
void initNetwork(void)
{ 
   const char *s_fn="initNetwork";
   trace->event(s_fn,0,"enter");
   
   char userData1[MAX_LEN];
   char userData2[MAX_LEN];

   // Set userData1
   memset(userData1,0x00, MAX_LEN);
   sprintf(userData1,"%s=%s",PROGRAM_NAME,PROGRAM_VERSION);
		
   // Get userData2 
   memset(userData2,0x00, MAX_LEN);
   sprintf(userData2,"appl=%s",PROGRAM_NAME);
	 
   tcp_init_layer();
    
   tcp_start_thread(PROGRAM_NAME, PROGRAM_VERSION, 
			ID1, URL1, 
			ID2, URL2, 
			ID3, URL3, 
			ID4, URL4, 
			URL_TOKEN, userData1, userData2);
   
   trace->event(s_fn,0,"leave [void]");
}

/** 
  * Init game parameters
  * @param wave	The wave number
  */
void initGame(int wave)
{	
	// Init game variables
	game.score=0;	
	game.wave=wave;

	// Show New Wave text on screen
	game.alfa=MAX_ALFA;     
}		

/** 
 * Init application parameters
 */
void initApplication(void)
{
	const char *s_fn="initApplication";
	
	// Open trace module
	trace = new Trace();
	trace->open(TRACE_FILENAME);
	trace->event(s_fn,0,"enter");
	trace->event(s_fn, 0,"%s %s Started", PROGRAM_NAME, PROGRAM_VERSION);

	game.stateMachine=stateIntro1;
	game.prevStateMachine=stateNone;
	//game.event=eventNone;
	game.wave1 = 0;
	game.wave2 = 0;
	game.angle = 0;
	game.alfa = 0;
		
   // Init Images
	initImages();
   
   // Init pointers
   initPointers();

	// Load Settings from SDCard	
	settings = new Settings();
	settings->load(SETTING_FILENAME);
	
	// Load Local Highscore from SDCard
	highScore = new HighScore();
	highScore->load(HIGHSCORE_FILENAME);
	
	// Init Sound (Start play first mod file)
	sound = new Sound();
	sound->setMusicVolume(settings->getMusicVolume());
	sound->setEffectVolume(settings->getEffectVolume());	

	// Init Today HighScore
	initTodayHighScore();
	
	// Init Global HighScore
	initGlobalHighScore();
	
	// Init network Thread
	initNetwork();
			
	trace->event(s_fn,0,"leave");
}
				
// -----------------------------------
// DRAW METHODES
// -----------------------------------

/** 
 * Draw pointers on screen
 */
void drawPointers(void)
{
   for( int i=0; i<MAX_POINTERS; i++ ) 
   {
	  if (pointers[i]!=NULL) 
	  {
			pointers[i]->action();
			pointers[i]->draw();
	  }
   }
}

/** 
 * Draw buttons on screen
 */
void drawButtons(void)
{
	for( int i=0; i<MAX_BUTTONS; i++ ) 
	{
		if (buttons[i]!=NULL)
		{
			buttons[i]->draw();
		}
	}
}

/** 
 * Draw buttons Text on screen
 */
void drawButtonsText(int offset)
{
	for( int i=0; i<MAX_BUTTONS; i++ ) 
	{
		if (buttons[i]!=NULL)
		{
			buttons[i]->text(offset);
		}
	}
}

/** 
 * Draw text on screen
 * @param x		The x location of the text.
 * @param y		The y location of the text.
 * @param type	The font type.
 * @param text The text
 * @param ...	Optional parameters
 *
 */
void drawText(int x, int y, int type, const char *text, ...)
{
	char buf[MAX_LEN];
	memset(buf,0x00,sizeof(buf));
   
	if (text!=NULL)
	{    		
		// Expend event string
		va_list list;
		va_start(list, text );
		vsprintf(buf, text, list);
   	 
		switch (type)
		{  	
			case fontWelcome:
				GRRLIB_PrintfTTF(x, y, myFont, buf, 40, GRRLIB_WHITESMOKE); 
				break;
 
			case fontTitle: 
				if (x==0) x=320-((strlen(buf)*34)/2);  
				GRRLIB_PrintfTTF(x, y, myFont, buf, 72, GRRLIB_WHITESMOKE); 
				break;
  	   
			case fontSubTitle:
				if (x==0) x=320-((strlen(buf)*20)/2);
				GRRLIB_PrintfTTF(x, y, myFont, buf, 30, GRRLIB_WHITESMOKE); 
				break;
	   
			case fontPanel:
				GRRLIB_PrintfTTF(x, y, myFont, buf, 14, GRRLIB_WHITESMOKE); 
				break;
	   	   
			case fontParagraph:
				if (x==0) x=320-((strlen(buf)*10)/2);	   
				GRRLIB_PrintfTTF(x, y, myFont, buf, 24, GRRLIB_WHITESMOKE); 
				break;
	   	   
			case fontNormal:
				if (x==0) x=320-((strlen(buf)*7)/2);
				GRRLIB_PrintfTTF(x, y, myFont, buf, 18, GRRLIB_WHITESMOKE); 
				break;
	         
			case fontNew:
				if (x==0) x=320-((strlen(buf)*8)/2);	   
				GRRLIB_PrintfTTF(x, y, myFont, buf, 22, GRRLIB_WHITESMOKE); 
				break;
	   
			case fontSmall:
				if (x==0) x=320-((strlen(buf)*10)/2);
				GRRLIB_PrintfTTF(x, y, myFont, buf, 10, GRRLIB_WHITESMOKE); 
				break;
	   
			case fontButton:
				if (strlen(buf)==1)
				{
					GRRLIB_PrintfTTF(x+35, y, myFont, buf, 24, GRRLIB_WHITESMOKE); 
				}
				else
				{
					GRRLIB_PrintfTTF(x+20, y, myFont, buf, 24, GRRLIB_WHITESMOKE); 
				}		   
				break;
				
			case fontBanner:
				GRRLIB_PrintfTTF(x, y, myFont, buf, 80, GRRLIB_RED); 
				break;
			
		}
	}
}

/**
 * draw map
 */
void drawMap(void) {

	Land *land;
	
	land = new Land(5,5,1,1);
	land->draw();
	delete(land);
	
	land = new Land(10,10,2,2);
	land->draw();
	delete(land);
	
	land = new Land(15,15,3,3);
	land->draw();
	delete(land);
}

/**
 * draw screens
 */
void drawScreen(void)
{ 	   	
	char tmp[MAX_LEN];
	
	int  ypos;
	if (rmode->xfbHeight==MAX_VERT_PIXELS) ypos=10; else ypos=25;
		  
   switch( game.stateMachine )	
	{		   
	   case stateIntro1:
	   { 		
			// Draw background
			GRRLIB_DrawImg(0,0, images.background1, 0, 1, 1, IMAGE_COLOR );
		  
		   // Draw game logo
		   GRRLIB_DrawImg(320, (rmode->xfbHeight/2), 
				images.logo1, 0, game.size, game.size, IMAGE_COLOR );
		   if (game.size<=MAX_SIZE) game.size+=0.05;	
		  
			drawText(0, ypos, fontParagraph,  "Created by wplaat"  );
			ypos+=20;
			drawText(0, ypos, fontParagraph,  "http://www.plaatsoft.nl"  );
			
			ypos+=380;
			if (rmode->xfbHeight==MAX_VERT_PIXELS) ypos-=28;
			
			drawText(40, ypos, fontNormal,  "This software is open source and may be copied, distributed or modified"  );
			ypos+=20;
			drawText(60, ypos, fontNormal,  "under the terms of the GNU General Public License (GPL) version 2" );
	   }	   
	   break;
	   
	   case stateIntro2:
	   {
			unsigned int j;
		  
	      // Draw background
			GRRLIB_DrawImg(0,0, images.background2, 0, 1, 1, IMAGE_COLOR );

			// Draw Plaatsoft logo		 
   	   for(j=0;j<images.logo->h;j++)
			{
            GRRLIB_DrawTile(
					((640-images.logo2->w)/2)+sin(game.wave1)*50, 
					(((480-images.logo2->h)/2)-50)+j, 
					images.logo, 0, 1, 1, IMAGE_COLOR, j );
            game.wave1+=0.02;
         }
			game.wave2+=0.02;
         game.wave1=game.wave2;
		  
			ypos+=320;
			drawText(0, ypos, fontParagraph,  "Please visit my website for more information." );
			ypos+=40;
			drawText(0, ypos, fontParagraph,  "http://www.plaatsoft.nl" );
	   }	   
	   break;
	   	   
		case stateIntro3:
	   { 
	      int xpos, xpos2;
			
			// Draw background
			GRRLIB_DrawImg(0,0, images.background1, 0, 1, 1, IMAGE_COLOR );

		   // Let the logo's slided in the screen
			if (game.location<640) game.location+=4;

			drawText(95, ypos, fontParagraph,  "Some more Wii games developed by PlaatSoft" );

			// Draw Space Bubble logo
			ypos+=40;
			xpos=(images.logo3->w*-1)+game.location;
			xpos2=40;
			if (xpos>xpos2) xpos=xpos2;
   	   GRRLIB_DrawImg(xpos, ypos, images.logo3, 	0, 0.9, 0.9, IMAGE_COLOR );		
				
			// Draw RedSquare logo
			ypos+=images.logo3->h-5;
			xpos=640-game.location;
			xpos2=635-images.logo4->w;
			if (xpos<xpos2) xpos=xpos2;			
   	   GRRLIB_DrawImg(xpos, ypos, images.logo4, 0, 0.9, 0.9, IMAGE_COLOR );
		
			// Draw BibleQuiz logo
			ypos+=images.logo5->h-10;
			xpos=(images.logo5->w*-1)+game.location;
			xpos2=30;
			if (xpos>xpos2) xpos=xpos2;		
   	   GRRLIB_DrawImg(xpos, ypos, images.logo5, 	0, 0.9, 0.9, IMAGE_COLOR );
			
			// Draw Pong2 logo
			ypos+=images.logo5->h-10;	
			xpos=640-game.location;
			xpos2=630-images.logo6->w;
			if (xpos<xpos2) xpos=xpos2;			
   	   GRRLIB_DrawImg(xpos, ypos, images.logo6, 0, 0.9, 0.9, IMAGE_COLOR );
	   }	   
	   break;
		
		case stateMainMenu:
		{
			char *version=NULL;

			// Draw background
			GRRLIB_DrawImg(0,0, images.background1, 0, 1, 1, IMAGE_COLOR );
		  
			// Draw Buttons
			drawButtons();
			
			drawText(20, ypos, fontWelcome, "%s v%s", PROGRAM_NAME, PROGRAM_VERSION );
			ypos+=40;
			drawText(20, ypos, fontParagraph, RELEASE_DATE );
	
			version=tcp_get_version();
         if ( (version!=NULL) && (strlen(version)>0) && (strcmp(version,PROGRAM_VERSION)!=0) )
         {    
				ypos+=255;
	         drawText(20, ypos, fontNew, "New version [v%s] is available.",version);
				 	
				ypos+=20;	 			 
	         drawText(20, ypos, fontNew, "Check the release notes.");			 
         }  
		  	
			// Draw Button Text labels
			drawButtonsText(0);
		}
		break;
					
		case stateGame:
		{	
			drawMap();
		}
		break;
				
		case stateGameOver:
		{	  
			// Draw elements
	      drawButtons();
			 
			// Draw text elements
			drawButtonsText(-20);
			  
			drawText(260, 220, fontParagraph, "Game Over!");
		}
		break;
		
		case stateQuitGame:
		{
			// Draw elements
			drawButtons();
	
			// Draw Transparent Box
			GRRLIB_Rectangle(210, 210, 220, 100, GRRLIB_BLACK_TRANS, 1);
	
			// Draw Text elements
			drawButtonsText(-10);
	
 	      drawText(0, 220, fontParagraph, "Quit game?");	
		}
		break;
		
		case stateLocalHighScore:
	   {
			struct tm *local;
			int startEntry;
			int endEntry;
		  		  
			if (highScore->getAmount()<15)
			{
				startEntry=0;
				endEntry=highScore->getAmount();
			}
			else
			{
				startEntry=(((float) highScore->getAmount()-15.0)/30.0)*(float)game.scrollIndex;
				endEntry=startEntry+15;
			}
				   
         // Draw background
         GRRLIB_DrawImg(0,0, images.background1, 0, 1, 1, IMAGE_COLOR2 );
		  
			// Draw scrollbar
			int y=SCROLLBAR_Y_MIN;
         GRRLIB_DrawImg(SCROLLBAR_x,y, images.scrollTop, 0, 1, 1, IMAGE_COLOR );
			for (int i=0; i<10; i++) 
			{
				y+=24;
				GRRLIB_DrawImg(SCROLLBAR_x,y, images.scrollMiddle, 0, 1, 1, IMAGE_COLOR );
			}
			y+=24;
			GRRLIB_DrawImg(SCROLLBAR_x,y, images.scrollBottom, 0, 1, 1, IMAGE_COLOR );
		  		  
			// Draw buttons
	      drawButtons(); 
		  
	      // Draw title
	      drawText(80, ypos, fontTitle, "Local High Score");	

         // Show Content
         ypos+=90;
			drawText(20, ypos, fontParagraph, "TOP"  );
	      drawText(80, ypos, fontParagraph, "DATE"  );
	      drawText(270, ypos, fontParagraph, "SCORE" );
			drawText(350, ypos, fontParagraph, "NAME"  );
			drawText(440, ypos, fontParagraph, "WAVE" );
			drawText(520, ypos, fontParagraph, "MAP" );
			ypos+=10;
		  
			for (int i=startEntry; i<endEntry; i++)
	      {
				// Only show highscore entries which contain data
				if (highScore->getDate(i)!=0)
				{
					ypos+=20;  
					drawText(20, ypos, fontNormal, "%02d", i+1);
			  
					local = localtime(highScore->getDate(i));
					sprintf(tmp,"%02d-%02d-%04d %02d:%02d:%02d", 
						local->tm_mday, local->tm_mon+1, local->tm_year+1900, 
						local->tm_hour, local->tm_min, local->tm_sec);
					drawText(80, ypos, fontNormal, tmp);
					drawText(270, ypos, fontNormal, "%05d", highScore->getScore(i));
					drawText(350, ypos, fontNormal, highScore->getName(i));
					drawText(440, ypos, fontNormal, "%02d", highScore->getWave(i));
					drawText(520, ypos, fontNormal, "%02d", highScore->getMap(i));
				}
			}	
		  
			// Draw Button Text labels
			drawButtonsText(0); 	   
	   }
	   break;

		case stateTodayHighScore:
	   {	      
			struct tm *local;
			int startEntry;
			int endEntry;
		  		  
			if (game.maxTodayHighScore<15)
			{
				startEntry=0;
				endEntry=game.maxTodayHighScore;
			}
			else
			{
				startEntry=(((float) game.maxTodayHighScore-15.0)/30.0)*(float)game.scrollIndex;
				endEntry=startEntry+15;
			}
		   
         // Draw background
         GRRLIB_DrawImg(0,0, images.background1, 0, 1, 1, IMAGE_COLOR2 );
      	     	
			// Draw scrollbar
			int y=SCROLLBAR_Y_MIN;
         GRRLIB_DrawImg(SCROLLBAR_x,y, images.scrollTop, 0, 1, 1, IMAGE_COLOR );
			for (int i=0; i<10; i++) 
			{
				y+=24;
				GRRLIB_DrawImg(SCROLLBAR_x,y, images.scrollMiddle, 0, 1, 1, IMAGE_COLOR );
			}
			y+=24;
			GRRLIB_DrawImg(SCROLLBAR_x,y, images.scrollBottom, 0, 1, 1, IMAGE_COLOR );
		  		  
	      // Draw title
	      drawText(0, ypos, fontTitle, "   Today High Score");	

         // Show Content
         ypos+=90;

			drawText(20, ypos, fontParagraph,  "TOP" );
	      drawText(80, ypos, fontParagraph,  "DATE" );
	      drawText(270, ypos, fontParagraph, "SCORE" );
			drawText(350, ypos, fontParagraph, "NAME"  );
			drawText(430, ypos, fontParagraph, "LOCATION" );
			ypos+=10;
		  
			if (todayHighScore[0].dt!=0)
			{
            for (int i=startEntry; i<endEntry; i++)
            {
					ypos+=20;  
	    
					drawText(20, ypos, fontNormal, "%02d", i+1);
			  			  
					local = localtime(&todayHighScore[i].dt);
					sprintf(tmp,"%02d-%02d-%04d %02d:%02d:%02d", 
						local->tm_mday, local->tm_mon+1, local->tm_year+1900, 
						local->tm_hour, local->tm_min, local->tm_sec);
					drawText(80, ypos, fontNormal, tmp);
	   
					drawText(270, ypos, fontNormal, todayHighScore[i].score);			  
					drawText(350, ypos, fontNormal, todayHighScore[i].name);			  
					drawText(430, ypos, fontNormal, todayHighScore[i].location);
				}			
			}
			else
			{
		      ypos+=120;
		      drawText(0, ypos, fontParagraph, "No information available!");
				ypos+=20;
				drawText(0, ypos, fontParagraph, "Information could not be fetch from internet.");
			}
			 
         // Draw buttons
	      drawButtons(); 
		  
			// Draw Button Text labels
			drawButtonsText(0);	   
	   }
	   break;
	   
		case stateGlobalHighScore:
	   {	      
			struct tm *local;
			int startEntry;
			int endEntry;
		  		  
			if (game.maxGlobalHighScore<15)
			{
				startEntry=0;
				endEntry=game.maxGlobalHighScore;
			}
			else
			{
				startEntry=(((float) game.maxGlobalHighScore-15.0)/30.0)*(float)game.scrollIndex;
				endEntry=startEntry+15;
			}
		  		   
         // Draw background
         GRRLIB_DrawImg(0,0, images.background1, 0, 1, 1, IMAGE_COLOR2 );
	      	     	
			// Draw scrollbar
			int y=SCROLLBAR_Y_MIN;
         GRRLIB_DrawImg(SCROLLBAR_x,y, images.scrollTop, 0, 1, 1, IMAGE_COLOR );
			for (int i=0; i<10; i++) 
			{
				y+=24;
				GRRLIB_DrawImg(SCROLLBAR_x,y, images.scrollMiddle, 0, 1, 1, IMAGE_COLOR );
			}
			y+=24;
			GRRLIB_DrawImg(SCROLLBAR_x,y, images.scrollBottom, 0, 1, 1, IMAGE_COLOR );
		  		  
	      // Draw title
	      drawText(0, ypos, fontTitle, "   Global High Score");	

         // Show Content
         ypos+=90;

			drawText(20, ypos, fontParagraph,  "TOP"  );
	      drawText(80, ypos, fontParagraph,  "DATE" );
	      drawText(270, ypos, fontParagraph, "SCORE" );
			drawText(350, ypos, fontParagraph, "NAME"  );
			drawText(430, ypos, fontParagraph, "LOCATION" );
			ypos+=10;
		  
			if (globalHighScore[0].dt!=0)
			{
            for (int i=startEntry; i<endEntry; i++)
            {
					ypos+=20;  
	    
					drawText(20, ypos, fontNormal, "%02d", i+1);
			  			  
					local = localtime(&globalHighScore[i].dt);
					sprintf(tmp,"%02d-%02d-%04d %02d:%02d:%02d", 
						local->tm_mday, local->tm_mon+1, local->tm_year+1900, 
						local->tm_hour, local->tm_min, local->tm_sec);
					drawText(80, ypos, fontNormal, tmp);
	   
					drawText(270, ypos, fontNormal, globalHighScore[i].score);			  
					drawText(350, ypos, fontNormal, globalHighScore[i].name);			  
					drawText(430, ypos, fontNormal, globalHighScore[i].location);
				}			
			}
			else
			{
		      ypos+=120;
		      drawText(0, ypos, fontParagraph, "No information available!");
				ypos+=20;
				drawText(0, ypos, fontParagraph, "Information could not be fetch from internet.");
			}
			 
         // Draw buttons
	      drawButtons(); 
		  
			// Draw Button Text labels
			drawButtonsText(0);	   
	   }
	   break;

	   case stateHelp1:
	   {	  
			// Draw background
			GRRLIB_DrawImg(0,0, images.background1, 0, 1, 1, IMAGE_COLOR2 );
		 
			// Draw buttons
	      drawButtons(); 
 
			// Show title
			drawText(0, ypos, fontTitle, "Help");
			ypos+=100;
		  
			drawText(0, ypos, fontParagraph, "Wii TowerDefense is an classic 2D action game. Protect ");
			ypos+=25;
			drawText(0, ypos, fontParagraph, "your base with all kind of defense systems and kill");	
			ypos+=25;
			drawText(0, ypos, fontParagraph, "all the waves of enemies. If ten enemies reach the");		
			ypos+=25;
			drawText(0, ypos, fontParagraph, "base the game is over. Good Luck!");		

		   ypos+=60;
			drawText(0, ypos, fontParagraph, "You can only build weapons on land. It is not allowed");
			ypos+=25;
			drawText(0, ypos, fontParagraph, "to build on water, roads or other deployed weapons.");		
		
			ypos+=60;
			drawText(0, ypos, fontParagraph, "Note: The global highscore contains the Top 40 of best");
			ypos+=25;
			drawText(0, ypos, fontParagraph, "internet players. Only one entry per player is showed.");	  

			// Draw Button Text labels
			drawButtonsText(0);
		}
		break;
		
		case stateHelp2:
	   {	  
			// Draw background
			GRRLIB_DrawImg(0,0, images.background1, 0, 1, 1, IMAGE_COLOR2 );
			 
			// Draw buttons
	      drawButtons(); 
		  
			// Show title
			drawText(0, ypos, fontTitle, "WiiMote Control");
		  
			int xoffset=50;
	
         ypos+=100;
			drawText(60+xoffset, ypos,  fontParagraph, "Button");
			drawText(180+xoffset, ypos,  fontParagraph, "Action");
	
			ypos+=50;	  
			drawText(60+xoffset, ypos, fontParagraph, "A");
			drawText(180+xoffset, ypos, fontParagraph, "Select button on screen" ); 

			ypos+=25;	  
			drawText(60+xoffset, ypos, fontParagraph, "B");
			drawText(180+xoffset, ypos, fontParagraph, "Buy new weapon" ); 

			ypos+=25;	  
			drawText(60+xoffset, ypos, fontParagraph, "-");
			drawText(180+xoffset, ypos, fontParagraph, "Sell selected weapon" ); 
			
			ypos+=25;	  
			drawText(60+xoffset, ypos, fontParagraph, "<");
			drawText(180+xoffset, ypos, fontParagraph, "Select previous weapon type" ); 

			ypos+=25;	  
			drawText(60+xoffset, ypos, fontParagraph, ">");
			drawText(180+xoffset, ypos, fontParagraph, "Select next weapon type" ); 	

			ypos+=25;	  
			drawText(60+xoffset, ypos, fontParagraph, "1");
			drawText(180+xoffset, ypos, fontParagraph, "Play next music track" ); 

			ypos+=25;	  
			drawText(60+xoffset, ypos, fontParagraph, "2");
			drawText(180+xoffset, ypos, fontParagraph, "Play previous music track" ); 	

			ypos+=25;	  
			drawText(60+xoffset, ypos, fontParagraph, "+");
			drawText(180+xoffset, ypos, fontParagraph, "Make screenshot" ); 		
			
			ypos+=25;	  
			drawText(60+xoffset, ypos, fontParagraph, "Home");
			drawText(180+xoffset, ypos, fontParagraph, "Quit the game" );
		  
			// Draw Button Text labels
			drawButtonsText(0);
		}
		break;
  
	   case stateCredits:
	   {  
	      // Draw background
			GRRLIB_DrawImg(0,0,images.background1, 0, 1.0, 1.0, IMAGE_COLOR2 );
		  
			// Draw buttons
	      drawButtons(); 
		  
			// Show title
			drawText(220, ypos, fontTitle, "Credits");
         ypos+=90;
		  
			// Show Content
	      drawText(0, ypos, fontParagraph, "GAME LOGIC ");
         ypos+=20;
	      drawText(0, ypos, fontNormal, "wplaat");

	      ypos+=30;
	      drawText(0, ypos, fontParagraph, "GAME GRAPHICS  ");
  	      ypos+=20;
	      drawText(0, ypos, fontNormal, "wplaat");

	      ypos+=30;
	      drawText(0, ypos, fontParagraph, "MUSIC ");
	      ypos+=20;
	      drawText(0, ypos, fontNormal, "modarchive.org  ");

	      ypos+=30;
	      drawText(0, ypos, fontParagraph, "SOUND EFFECTS  ");
	      ypos+=20;
	      drawText(0, ypos, fontNormal, "wplaat");
  
  	      ypos+=30;
	      drawText(0, ypos, fontParagraph, "TESTERS");	  
			ypos+=20;
	      drawText(0, ypos, fontNormal, "wplaat");	  
			
	      ypos+=30;
	      drawText(140, ypos, fontNormal,"Greetings to everybody in the Wii homebrew scene");
		  
			// Draw Button Text labels
			drawButtonsText(0);
	   }
	   break;
	   
	   case stateReleaseNotes:
	   {
	      int  i=0;
			int  len=0;
			int  lineCount=0;
			int  maxLines=0;
			char *buffer=NULL;
			char text[MAX_BUFFER_SIZE];
		  
			int startEntry;
			int endEntry;
		  		  
			// Fetch release notes from network thread
			buffer=tcp_get_releasenote();
         if (buffer!=NULL) 
			{
				strncpy(text,buffer,MAX_BUFFER_SIZE);
				len=strlen(text);
				for (i=0;i<len;i++) if (text[i]=='\n') maxLines++;
			}
		  
			// Calculate start and end line.
			if (maxLines<20)
			{
				startEntry=0;
				endEntry=maxLines;
			}
			else
			{
				startEntry=(((float) maxLines-18.0)/30.0)*(float)game.scrollIndex;
				endEntry=startEntry+20;
			}
		   
			// Draw background
			GRRLIB_DrawImg(0,0,images.background1, 0, 1.0, 1.0, IMAGE_COLOR2 );

			// Draw buttons
	      drawButtons(); 
		  
         // Draw scrollbar
			int y=SCROLLBAR_Y_MIN;
         GRRLIB_DrawImg(SCROLLBAR_x,y, images.scrollTop, 0, 1, 1, IMAGE_COLOR );
			for (i=0; i<10; i++) 
			{
				y+=24;
				GRRLIB_DrawImg(SCROLLBAR_x,y, images.scrollMiddle, 0, 1, 1, IMAGE_COLOR );
			}
			y+=24;
			GRRLIB_DrawImg(SCROLLBAR_x,y, images.scrollBottom, 0, 1, 1, IMAGE_COLOR );
		  		 
	      // Draw Title	
         drawText(100, ypos, fontTitle, "Release Notes");
         ypos+=80;
	      
			if (len!=0)
			{		  
				int startpos=0;
				for (i=0; i<len; i++)
				{
					if (text[i]=='\n') 
					{			   
						text[i]=0x00;
				 
						// Show only 19 lines on screen
						if ((lineCount++)>endEntry) break;
						if (lineCount>startEntry) 
						{
							ypos+=15;
							drawText(40, ypos, fontNormal, text+startpos);	
						}
						startpos=i+1;
					}
				}
			}
			else
			{
				ypos+=120;
				drawText(0, ypos, fontParagraph, "No information available!" );	
				ypos+=20;
				drawText(0, ypos, fontParagraph, "Information could not be fetch from internet.");
			}
		  
			// Draw Button Text labels
			drawButtonsText(0);
	   }
	   break;
	   
	   case stateSoundSettings:
	   { 
	      // Draw background
			GRRLIB_DrawImg(0,0,images.background1, 0, 1.0, 1.0, IMAGE_COLOR );
		
		   // Draw Sound icon
			int yoffset=20;
	      if (rmode->xfbHeight==MAX_VERT_PIXELS) yoffset-=20;
	      GRRLIB_DrawImg((640/2), ((480/2))+yoffset, images.soundicon, 
				game.angle, 1.4, 1.4, IMAGE_COLOR );
			if (game.angle<MAX_ANGLE) game.angle++; else game.angle=0;
			
			// Draw buttons
	      drawButtons(); 
		  		  
			// Show title
			drawText(100, ypos, fontTitle, "Sound Settings");
        
		   ypos+=140;
	      if (rmode->xfbHeight==MAX_VERT_PIXELS) ypos-=20;
			
         // Draw content	
         drawText(0, ypos, fontParagraph, "Music Volume");	
	      ypos+=20;
         GRRLIB_DrawImg(104,ypos,images.bar, 0, 1, 1, IMAGE_COLOR );
	      ypos+=10;
	      GRRLIB_DrawImg(115+(sound->getMusicVolume()*40),ypos, 
				images.barCursor, 0, 1, 1, IMAGE_COLOR );
  
         ypos+=80;
         drawText(0, ypos, fontParagraph, "Effects Volume" );
	      ypos+=20;	
	      GRRLIB_DrawImg(104,ypos, images.bar, 0, 1, 1, IMAGE_COLOR );
	      ypos+=10;
	      GRRLIB_DrawImg(115+(sound->getEffectVolume()*40),ypos,
				images.barCursor, 0, 1, 1, IMAGE_COLOR );
	
	      ypos+=70;
	      drawText(0, ypos, fontParagraph, "  Music track [%d]", sound->getMusicTrack());	
		  		  		
		   // Draw Button Text labels
		   drawButtonsText(0);
	   }
	   break;
	   
	   case stateGameSettings:
	   {         	
			// Draw background
			GRRLIB_DrawImg(0,0,images.background1, 0, 1.0, 1.0, IMAGE_COLOR );

			// Draw Transparent Panel
			GRRLIB_Rectangle(30, ypos+120, 570, ypos+180, GRRLIB_BLACK_TRANS_2, 1);
			
			// Draw buttons
	      drawButtons(); 
		  
			// Draw Title	
			drawText(100, ypos, fontTitle, "Game Settings");
			ypos+=90;
			
			drawText(0, ypos, fontParagraph, "User initials are used in the highscore area.");	

			// Draw initial characters
			ypos+=90;	
			int xpos=50;
			
			drawText(xpos, ypos, fontTitle, "%c", settings->getName()[0]);
			xpos+=95;
			drawText(xpos, ypos, fontTitle, "%c", settings->getName()[1]);
			xpos+=95;
			drawText(xpos, ypos, fontTitle, "%c", settings->getName()[2]);
			xpos+=95;
			drawText(xpos, ypos, fontTitle, "%c", settings->getName()[3]);
			xpos+=95;
			drawText(xpos, ypos, fontTitle, "%c", settings->getName()[4]);
			xpos+=95;
			drawText(xpos, ypos, fontTitle, "%c", settings->getName()[5]);

			// Draw Button Text labels
			drawButtonsText(0);	
	   }
	   break;
		
		case stateDonate:
	   {         	
			// Draw background
			GRRLIB_DrawImg(0,0,images.background1, 0, 1.0, 1.0, IMAGE_COLOR2 );

			// Draw buttons
	      drawButtons(); 
		  
			// Draw Title	
			drawText(0, ypos, fontTitle, "Donate");
			ypos+=100;
			
			drawText(0, ypos, fontParagraph, "If you enjoy this game please send me a");
			ypos+=25;
		   drawText(0, ypos, fontParagraph, "small donation. You can make a donation ");
			ypos+=25;
		   drawText(0, ypos, fontParagraph, "online with your credit card, or paypal account.");
			ypos+=25;
		   drawText(0, ypos, fontParagraph, "Your credit card will be processed by PayPal, a");
			ypos+=25;
		   drawText(0, ypos, fontParagraph, "trusted name in secure online transactions.");
			ypos+=65;
		   drawText(0, ypos, fontParagraph, "Please visit http://www.plaatsoft.nl");			
			ypos+=25;
		   drawText(0, ypos, fontParagraph, "Click on the donate link and follow the instructions.");	
			ypos+=65;
			drawText(0, ypos, fontParagraph, "Many thanks for your support!");

			// Draw Button Text labels
			drawButtonsText(0);	
	   }
	   break;
	}
	
	// Draw network thread status on screen
	drawText(20, rmode->xfbHeight-38, fontSmall, "Network: %s",tcp_get_state());
			
	// Show FPS information on screen.
	drawText(20, rmode->xfbHeight-28, fontSmall, "%d fps", calculateFrameRate());
}

// -----------------------------------
// SUPPORT METHODES
// -----------------------------------

/**
 * Parse today higshcore received my network thread
 * @param buffer	The buffer contain the raw xml message with contain the highscore.
 */
void loadTodayHighScore(char *buffer)
{
   const char *s_fn="loadTodayHighScore";
   trace->event(s_fn,0,"enter");
	
   int i;
   mxml_node_t *tree=NULL;
   mxml_node_t *data=NULL;
   const char *tmp;
   char temp[MAX_LEN];
   
   game.maxTodayHighScore=0;
   
   // Clear memory
   for(i=0; i<MAX_TODAY_HIGHSCORE; i++)
   {
		todayHighScore[i].score[0]=0x00;
		todayHighScore[i].dt=0;
		todayHighScore[i].name[0]=0x00;
		todayHighScore[i].location[0]=0x00;
   } 
	 
   // If xml data available, parse it....
   if ((buffer!=NULL) && (strlen(buffer)>0))
   {  
      tree = mxmlLoadString(NULL, buffer, MXML_NO_CALLBACK);

      for(i=0; i<MAX_TODAY_HIGHSCORE; i++)
      {		
	    sprintf(temp, "item%d", i+1);
        data = mxmlFindElement(tree, tree, temp, NULL, NULL, MXML_DESCEND);

        tmp=mxmlElementGetAttr(data,"dt");   
        if (tmp!=NULL) todayHighScore[game.maxTodayHighScore].dt=atoi(tmp); else todayHighScore[game.maxTodayHighScore].dt=0; 
		
		tmp=mxmlElementGetAttr(data,"score");   
        if (tmp!=NULL) strcpy(todayHighScore[game.maxTodayHighScore].score,tmp); else strcpy(todayHighScore[game.maxTodayHighScore].score,"");
		
        tmp=mxmlElementGetAttr(data,"name");   
        if (tmp!=NULL) strcpy(todayHighScore[game.maxTodayHighScore].name,tmp); else strcpy(todayHighScore[game.maxTodayHighScore].name,"");

		tmp=mxmlElementGetAttr(data,"location");   
        if (tmp!=NULL) strcpy(todayHighScore[game.maxTodayHighScore].location,tmp); else strcpy(todayHighScore[game.maxTodayHighScore].location,"");
		
		// Entry is valid (Keep the inforamtion)
        if (strlen(todayHighScore[game.maxTodayHighScore].score)>0) game.maxTodayHighScore++;	
      }   
      mxmlDelete(data);
      mxmlDelete(tree);
   }
   
    trace->event(s_fn,0,"leave [void]");
}

/**
 * Parse global higshcore received my network thread
 * @param buffer	The buffer contain the raw xml message with contain the highscore.
 */
void loadGlobalHighScore(char *buffer)
{
    const char *s_fn="loadGlobalHighScore";
    trace->event(s_fn,0,"enter");
	
   int i;
   mxml_node_t *tree=NULL;
   mxml_node_t *data=NULL;
   const char *tmp;
   char temp[MAX_LEN];
   
   game.maxGlobalHighScore=0;
   
   // Clear memory
   for(i=0; i<MAX_GLOBAL_HIGHSCORE; i++)
   {
      globalHighScore[i].score[0]=0x00;
		globalHighScore[i].dt=0;
		globalHighScore[i].name[0]=0x00;
		globalHighScore[i].location[0]=0x00;
   } 
	 
   // If xml data available, parse it....
   if ((buffer!=NULL) && (strlen(buffer)>0))
   {  
      tree = mxmlLoadString(NULL, buffer, MXML_NO_CALLBACK);

      for(i=0; i<MAX_GLOBAL_HIGHSCORE; i++)
      {		
			sprintf(temp, "item%d", i+1);
			data = mxmlFindElement(tree, tree, temp, NULL, NULL, MXML_DESCEND);

			tmp=mxmlElementGetAttr(data,"dt");   
			if (tmp!=NULL) globalHighScore[game.maxGlobalHighScore].dt=atoi(tmp); else globalHighScore[game.maxGlobalHighScore].dt=0; 
		
			tmp=mxmlElementGetAttr(data,"score");   
			if (tmp!=NULL) strcpy(globalHighScore[game.maxGlobalHighScore].score,tmp); else strcpy(globalHighScore[game.maxGlobalHighScore].score,"");
		
			tmp=mxmlElementGetAttr(data,"name");   
			if (tmp!=NULL) strcpy(globalHighScore[game.maxGlobalHighScore].name,tmp); else strcpy(globalHighScore[game.maxGlobalHighScore].name,"");

			tmp=mxmlElementGetAttr(data,"location");   
			if (tmp!=NULL) strcpy(globalHighScore[game.maxGlobalHighScore].location,tmp); else strcpy(globalHighScore[game.maxGlobalHighScore].location,"");
		
			// Entry is valid (Keep the inforamtion)
			if (strlen(globalHighScore[game.maxGlobalHighScore].score)>0) game.maxGlobalHighScore++;	
      }   
      mxmlDelete(data);
      mxmlDelete(tree);
   }
   
    trace->event(s_fn,0,"leave [void]");
}
			
/**
 * Calculate Video Frame Rate (Indication how game engine performs)
 */
static u8 calculateFrameRate(void) 
{
    static u8 frameCount = 0;
    static u32 lastTime;
    static u8 FPS = 0;
    u32 currentTime = ticks_to_millisecs(gettime());

    frameCount++;
    if(currentTime - lastTime > 1000) {
        lastTime = currentTime;
        FPS = frameCount;
        frameCount = 0;
    }
    return FPS;
}	

/**
 * Process state Machine change
 */
void processStateMachine(void)
{
	const char *s_fn="processStateMachine";
	
	// If state is not changed return directly!
	if (game.prevStateMachine==game.stateMachine) return;
    
	// Process new state
	switch (game.stateMachine)
	{
   
		case stateIntro1:
		{
			trace->event(s_fn,0,"stateMachine=stateIntro1");
		
			// Start background music
			sound->play();
			
			game.size=0;
			game.alfa=0;
		}
		break;

		case stateIntro2:
		{
			trace->event(s_fn,0,"stateMachine=stateIntro2");
			game.alfa=0;
		}
		break;
		
		case stateIntro3:
		{
			trace->event(s_fn,0,"stateMachine=stateIntro3");
			game.location=0;
		}
		break;
		
		case stateMainMenu:
		{
			trace->event(s_fn,0,"stateMachine=stateMainMenu");
		
			// Init buttons
			initButtons();
		}
		break;
	
		case stateGame:
		{
			trace->event(s_fn,0,"stateMachine=stateGame");
	
			if (game.prevStateMachine!=stateQuitGame)
			{
				// Init game variables
				initGame(0);
			}
		}
		break;
	
		case stateQuitGame:	
		{
			trace->event(s_fn,0,"stateMachine=stateQuitGame");
		
			// Init buttons
			initButtons();	
		}
		break;
	
		case stateGameOver:
		{
			trace->event(s_fn,0,"stateMachine=stateGameOver");
			
			// Init buttons
			initButtons();	
		}
		break;
	
		case stateReleaseNotes:
		{
			trace->event(s_fn,0,"stateMachine=stateReleaseNotes");
			game.scrollIndex=0;
		
			// Init buttons
			initButtons();		
		}
		break;
	
		case stateLocalHighScore:
		{
			trace->event(s_fn,0,"stateMachine=stateLocalHighScore");
			game.scrollIndex=0;
			
			// Init buttons
			initButtons();
		}
		break;
	
		case stateTodayHighScore:
		{
			trace->event(s_fn,0,"stateMachine=stateTodayHighScore");
			game.scrollIndex=0;
			
			// Fetch data for network thread
			char *buffer=NULL;
			buffer=tcp_get_today_highscore();
			loadTodayHighScore(buffer);		     
				
			// Init buttons
			initButtons();		
		}
		break;
	
		case stateGlobalHighScore:
		{
			trace->event(s_fn,0,"stateMachine=stateGlobalHighScore");
			game.scrollIndex=0;
			
			// Fetch data for network thread
			char *buffer=NULL;
			buffer=tcp_get_global_highscore();
			loadGlobalHighScore(buffer);
				
			// Init buttons
			initButtons();
		}
		break;
			
		case stateCredits:
		{
			trace->event(s_fn,0,"stateMachine=stateCredits");
			
			// Init buttons
			initButtons();	
		}
		break;
	 
		case stateSoundSettings:
		{
			trace->event(s_fn,0,"stateMachine=stateSoundSettings");
			
			// Init buttons
			initButtons();	
		}
		break;
		
		case stateHelp1:
		{
			trace->event(s_fn,0,"stateMachine=stateHelp1");
		
			// Init buttons
			initButtons();
		}
		break;
	
		case stateHelp2:
		{
			trace->event(s_fn,0,"stateMachine=stateHelp2");
			
			// Init buttons
			initButtons();
		}
		break;
			
		case stateGameSettings:
		{
			trace->event(s_fn,0,"stateMachine=stateGameSettings");
			
			// Init buttons
			initButtons();
		}
		break;
		
		case stateDonate:
		{
			trace->event(s_fn,0,"stateMachine=stateDonate");
			
			// Init buttons
			initButtons();
		}
		break;
		
	}
  
	// Store state
	game.prevStateMachine=game.stateMachine;
}

// -----------------------------------
// main
// -----------------------------------

/**
 * Start point of game.
 */
int main(void)
{
   const char *s_fn="main";
	
	// Init video layer
   VIDEO_Init();
	
	// Init wiimote layer
   WPAD_Init();
	
	// Wiimote is shutdown after 300 seconds of innactivity.
   WPAD_SetIdleTimeout(300); 
	
   WPAD_SetDataFormat(WPAD_CHAN_ALL,WPAD_FMT_BTNS_ACC_IR);
  
	// Obtain the preferred video mode from the system
	// This will correspond to the settings in the Wii menu
	rmode = VIDEO_GetPreferredMode(NULL);
	
	// Set up the video registers with the chosen mode
	VIDEO_Configure(rmode);
	
   // Init Fat File system
   fatInitDefault();

	// Init Game variables and objects
	initApplication();
			  			
   // Init GRRLib graphics library
   GRRLIB_Init();
        	
	// Init FreeType font 
	myFont = GRRLIB_LoadTTF(font_ttf, font_ttf_size); 
	
   // To have a cool effect anti-aliasing is turned on
   GRRLIB_Settings.antialias = true;
	 
	// Black background
   GRRLIB_SetBackgroundColour(0x00, 0x00, 0x00, 0xFF);
   GRRLIB_Render();

   // Seed the random-number generator with current time so that
   // the numbers will be different every time we run.
   srand(time(NULL));
	 
	// Repeat forever
   while( game.stateMachine!=stateQuit )
	{			
		// draw Screen
		drawScreen();

		// Draw Wii Motion Pointers
		drawPointers();
			
		// Render screen
		GRRLIB_Render();
		
		// Process state machine
		processStateMachine();
	}
		  
	GRRLIB_Exit();
	
	// Stop network thread
	tcp_stop_thread();
		
	// Destroy all created objects
	destroyImages();
	destroyButtons();
	destroyPointers();
	destroySound();
	destroyHighScore();
	destroySettings();
	
	// Trace last line
	trace->event(s_fn, 0,"%s %s Leaving", PROGRAM_NAME, PROGRAM_VERSION);
	
	// Destroy trace class
	destroyTrace();
	
	// Exit to loader
	exit(0);
}

// ------------------------------
// The end
// ------------------------------