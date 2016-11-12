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

package nl.plaatsoft.knightsquest.ui;

import java.util.Iterator;

import org.apache.log4j.Logger;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import nl.plaatsoft.knightsquest.tools.Constants;

public class Navigator {
		
	final static Logger log = Logger.getLogger( Navigator.class);
	
	private static Intro1 intro1;
	private static Intro2 intro2;
	private static Home home;	
	private static MapSelector selector;	
	private static Game game;	
	private static Donate donate;
	//private static HighScore1 highScore1;
	//private static HighScore2 highScore2;
	private static Credits credits;	
	private static ReleaseNotes releaseNotes;
	private static Help help;
	private static Settings settings;	
	
	private static Scene scene;	
			
	public static final int NONE = 0;
	public static final int INTRO1 = 1;
	public static final int INTRO2 = 2;
	public static final int HOME = 3;
	public static final int GAME = 4;
	public static final int MAP_SELECTOR = 5;
	public static final int DONATE = 6;
	public static final int LOCAL_HIGHSCORE = 7;
	public static final int GLOBAL_HIGHSCORE = 8;
	public static final int CREDITS = 9;
	public static final int RELEASE_NOTES = 10;
	public static final int HELP = 11;
	public static final int SETTINGS = 12;
	public static final int EXIT = 13;
			
	public static Scene getScene() {
		return scene;
	}
	
	//handles mouse scrolling
	private static void setSceneEvents(final Scene scene, Pane page) {	    
	    scene.setOnScroll(
	            new EventHandler<ScrollEvent>() {
	              @Override
	              public void handle(ScrollEvent event) {
	                double zoomFactor = 1.10;
	                double deltaY = event.getDeltaY();
	                if (deltaY < 0){
	                  zoomFactor = 2.0 - zoomFactor;
	                }
	                	               	               
	               Iterator <Node> iter =  page.getChildren().iterator();			
	    		   while(iter.hasNext()) {		
	    			 	    
	    				Node node = (Node) iter.next();
	    				if(node instanceof Pane){
	    			         Pane pane = (Pane) node;
	    			         if (pane.getId().equals("map")) {
	    			        	 
	    			        	 double scale = pane.getScaleX() * zoomFactor;
	    			        	 
	    			        	 //if (scale>(Constants.SCALE-0.05)) 
	    			        	 {	                	
	    			              	pane.setScaleX(scale);
	    			               	pane.setScaleY(scale);
	    			               	event.consume();
	    			             }
	    			         }
	    			    }      
	    		   }
	           }
	      });
	  }
	
	public static void go(int page) {
				
	  switch (page ) {

		case INTRO1:
			intro1 = new Intro1();
			intro1.draw();
			scene = new Scene(intro1, Constants.WIDTH, Constants.HEIGHT);	
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			    public void handle(KeyEvent key) {
			    	Navigator.go(Navigator.INTRO2);			
			    }
			});						
			break;
		
		case INTRO2:
			intro2 = new Intro2();				
			intro2.draw();
			scene.setRoot(intro2);
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			    public void handle(KeyEvent key) {
			    	Navigator.go(Navigator.GAME);			
			    }
			});		
			break;		
			
		case HOME:
			if (home==null) {
				home = new Home();
			}
			home.draw();
			scene.setRoot(home);	
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			    public void handle(KeyEvent key) {
			       // switch it off
			    }
			});		
			break;		
			
		case MAP_SELECTOR:
			selector = new MapSelector();				
			selector.draw();						
			scene.setRoot(selector);	
			break;	
			
		case GAME:
			game = new Game();				
			game.draw();						
			scene.setRoot(game);	
			setSceneEvents(scene, game);
			break;	
			
		/*case LOCAL_HIGHSCORE:
			ScoreGlobal.clear();
			
			highScore1 = new HighScore1();
			highScore1.draw();
			
			scene.setRoot(highScore1);
			break;
			
		case GLOBAL_HIGHSCORE:
			highScore2 = new HighScore2();
			scene.setRoot(highScore2);
			highScore2.draw();
			break;	*/	
			
		case DONATE:
			donate = new Donate();
			scene.setRoot(donate);
			donate.draw();
			break;
							
		case CREDITS:
			credits = new Credits();
			credits.draw();
			scene.setRoot(credits);
			break;	
			
		case RELEASE_NOTES:
			releaseNotes = new ReleaseNotes();
			releaseNotes.draw();
			scene.setRoot(releaseNotes);				
			break;		
			
		case SETTINGS:
			settings = new Settings();
			settings.draw();
			scene.setRoot(settings);			
			break;	
			
		case HELP:
			help = new Help();
			help.draw();
			scene.setRoot(help);			
			break;	
								
		case EXIT:
			System.exit(0);
			break;
		}
	}
}
