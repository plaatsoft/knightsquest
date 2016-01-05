/** 
 *  @file 
 *  @brief The file contain the Sound class methods
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

#include "General.h"
#include "grrlib.h"
#include "Settings.h"
#include "Sound.h"
#include "Trace.h"

#include "click_pcm.h"

#include "track1_mod.h"

extern 	Trace *trace;

// ------------------------------
// Constructor 
// ------------------------------

/**
 * Constructor
 * Init all properties with default values.
 */
Sound::Sound()
{
   const char *s_fn="Sound::Sound";
   trace->event(s_fn,0,"enter");
   
   musicVolume = 5;
   effectVolume = 9;
   musicTrack = 1;
	
   // Init Sound engine	
   SND_Init(INIT_RATE_48000); 
   SND_Pause(0);
   
   trace->event(s_fn,0,"leave [void]");
}

// ------------------------------
// Destructor
// ------------------------------

/**
 * Destructor
 * Clean up all allocated memory
 */
Sound::~Sound()
{
  const char *s_fn="Sound::~Sound";
  trace->event(s_fn,0,"enter");
  
  trace->event(s_fn,0,"Sound destroyed");
		
  // Stop music
  MODPlay_Stop(&snd1);
   
  trace->event(s_fn,0,"leave [void]");
}

// ------------------------------
// Others
// ------------------------------
 
 /**
  * Start playing selected sound track
  */
void Sound::play(void)
{
	const char *s_fn="Sound::play";
	trace->event(s_fn,0,"enter");
   
	MODPlay_Init(&snd1);
	MODPlay_Stop(&snd1);
	MODPlay_Pause(&snd1,false);
 
    // Restart music track after finished
    snd1.manual_polling=false;
   
    switch (musicTrack)
    {
       case 1 : MODPlay_SetMOD(&snd1, track1_mod);
		 	    MODPlay_Start(&snd1);	
		 	    break;	
    } 
    MODPlay_SetVolume( &snd1, musicVolume*MUSIC_MULTIPLER,musicVolume*MUSIC_MULTIPLER); 
    
	trace->event(s_fn,0,"leave [void]");
}

/**
 * Play Sound effect
 * @param type		The selected sound effect
 */
void Sound::effect(int type)
{
	switch(type)
	{			
		// Click
		case SOUND_CLICK:
			SND_SetVoice(SND_GetFirstUnusedVoice(), VOICE_MONO_16BIT, 22050, 
				0, (u8 *) click_pcm, click_pcm_size, 
				effectVolume*EFFECT_MULTIPLER, effectVolume*EFFECT_MULTIPLER, NULL);				   
			break;
	}
}

// ------------------------------
// Setters
// ------------------------------

/** 
 * Set music volume
 * @param volume 		The music volume
 */
void Sound::setMusicVolume(int volume)
{
	const char *s_fn="Sound::setMusicVolume";
	trace->event(s_fn,0,"%d",volume);
	
	if ((volume>=0) && (volume<=MAX_SOUND_VOLUME))
	{
		musicVolume=volume;
		MODPlay_SetVolume( &snd1, musicVolume*MUSIC_MULTIPLER,musicVolume*MUSIC_MULTIPLER);
	}
}

/** 
 * Set effect volume
 * @param volume 		The effect volume
 */
void Sound::setEffectVolume(int volume)
{	
	const char *s_fn="Sound::setEffectVolume";
	trace->event(s_fn,0,"%d",volume);
	
	if ((volume>=0) && (volume<=MAX_SOUND_VOLUME))
	{
		effectVolume=volume;
	}
}

/** 
 * Set music track
 * @param track 		Set the music track [0..9]
 */
void Sound::setMusicTrack(int track)
{
	const char *s_fn="Sound::setMusicTrack";
	trace->event(s_fn,0,"%d",track);
	
	if (track<1) 
	{
		musicTrack=MAX_MUSIC_TRACK;
		play();
	}
	else 
	{
		if (track>MAX_MUSIC_TRACK)
		{
			musicTrack=1;
			play();
		}
		else
		{
			musicTrack=track;
			play();
		}
	}
}
	
// ------------------------------
// Getters
// ------------------------------

/** 
 * Get music volume
 * @return The music volume
 */
int Sound::getMusicVolume()
{
	return musicVolume;
}

/** 
 * Get effect volume
 * @return The effect volume
 */
int Sound::getEffectVolume()
{
	return effectVolume;
}

/** 
 * Get current music track number 
 * @return The music track
 */
int Sound::getMusicTrack()
{
	return musicTrack;
}

// ------------------------------
// The End
// ------------------------------

