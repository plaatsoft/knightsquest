/**
 *  @file
 *  @brief 
 *  @author wplaat
 *
 *  Copyright (C) 2008-2016 PlaatSoft
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
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

package nl.plaatsoft.knightsquest.tools;

import org.apache.log4j.Logger;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import nl.plaatsoft.knightsquest.utils.Constants;

public class MyMusic {

	final static Logger log = Logger.getLogger( MyMusic.class);
	
	private static MediaPlayer mp;
	
	public static void init() {
		log.info("init");
		String path = MyMusic.class.getResource("/sounds/intro.mp3").toExternalForm();
        Media media = new Media(path);
        mp = new MediaPlayer(media);
        mp.setCycleCount(MediaPlayer.INDEFINITE);
	}
	
	public static void play() {  
		if (mp==null) {
			init();
		}
        if (Constants.MUSIC_MODE==1) {
        	mp.play();
        }
	}
		
	public static void stop() {
		mp.stop();
	}
}