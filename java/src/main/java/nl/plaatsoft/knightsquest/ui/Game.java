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

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import nl.plaatsoft.knightsquest.model.Player;
import nl.plaatsoft.knightsquest.tools.Constants;
import nl.plaatsoft.knightsquest.tools.MyButton;
import nl.plaatsoft.knightsquest.tools.PlayerUtils;
import nl.plaatsoft.knightsquest.tools.MyImageView;
import nl.plaatsoft.knightsquest.tools.LandUtils;

public class Game extends StackPane {

	final static Logger log = Logger.getLogger( Game.class);
	
	private GraphicsContext gc1;
	private GraphicsContext gc2;
	
	private Canvas canvas1;
	private Canvas canvas2;
	
	private double offsetX = 0;
	private double offsetY = 0;
	
	private AnimationTimer timer;
	  	
	public void draw() {
				
		Pane pane1 = new Pane();		
		pane1.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		pane1.setId("background");

		Pane pane2 = new Pane();			
		pane2.setScaleX(Constants.SCALE);
    	pane2.setScaleY(Constants.SCALE);
    	pane2.setId("map");
		
		canvas1 = new Canvas(Constants.MAP_WIDTH,Constants.MAP_HEIGHT);
		canvas1.setLayoutX(Constants.OFFSET_X);
		canvas1.setLayoutY(Constants.OFFSET_Y);
		
		canvas2 = new Canvas(Constants.MAP_WIDTH,Constants.MAP_HEIGHT);
		canvas2.setLayoutX(Constants.OFFSET_X);
		canvas2.setLayoutY(Constants.OFFSET_Y);
		
		gc1 = canvas1.getGraphicsContext2D();
		 				
		canvas2.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
		   		offsetX = me.getSceneX() - canvas2.getLayoutX();
		   		offsetY = me.getSceneY() - canvas2.getLayoutY();
		   		getScene().setCursor(Cursor.HAND);
		   	}
		});
				
		canvas2.setOnMouseDragged(new EventHandler<MouseEvent>() {
	    	public void handle(MouseEvent me) {		 
	    			    		
	    		double tmpX = me.getSceneX() - offsetX;
	    		double tmpY = me.getSceneY() - offsetY;

	    		canvas1.setLayoutX(tmpX);
	    		canvas1.setLayoutY(tmpY);
	    		
	    		canvas2.setLayoutX(tmpX);
	    		canvas2.setLayoutY(tmpY);
	    		
	    		//log.info(canvas2.getLayoutX()+" "+canvas2.getLayoutY());
	    		
	    		Iterator<Node> iter = getChildren().iterator();	    				
	    		while (iter.hasNext()) {	    			
	    			Node node = (Node) iter.next();
	    			if (node.getClass()==MyImageView.class) {
	    				MyImageView imageView = (MyImageView) node;
	    				
	    				imageView.move(canvas2.getLayoutX(),canvas2.getLayoutY());
	    			}
	    		}   		
	    	}
		});
				
		canvas2.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {		    	
				getScene().setCursor(Cursor.DEFAULT);
	    	}
		});
				
		pane2.getChildren().add(canvas1);
		pane2.getChildren().add(canvas2);	    
		
	    LandUtils.createMap();
	    LandUtils.drawMap(gc1);
	    
	    gc2 = canvas2.getGraphicsContext2D();
		
	    for(int i=1; i<=Constants.START_PLAYERS; i++) {
	    	Player player = PlayerUtils.createPlayer(i, this);	    	
			player.draw(gc2);
		}
			
	    MyButton btn = new MyButton(Constants.WIDTH+290, Constants.HEIGHT+290, "Turn", 20, Navigator.NONE);
	    
	    btn.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent event) {
            	               	              	
            	// Move bot players
            	PlayerUtils.nextTurn();
            	
            	// Clear canvas
            	gc2.clearRect(0, 0, canvas2.getWidth(), canvas2.getHeight());

            	// Draw new canvas
				Iterator <Player> iter = PlayerUtils.getPlayers().iterator();
				while (iter.hasNext()) {
							
					Player player = (Player) iter.next();
        			player.draw(gc2);
        		}
            }
        });
	    
	    if (Constants.BOTS_MODE==0) {
	    	getChildren().add(btn);
	    }
        
        timer = new AnimationTimer() {			 
			 	
		@Override
		public void handle(long now) {
	            		
			// Move bot players
           	if (PlayerUtils.nextTurn()==true) {
           		timer.stop();
           	}
            	
           	// Clear canvas
           	gc2.clearRect(0, 0, canvas2.getWidth(), canvas2.getHeight());

           	// Draw new canvas
			Iterator <Player> iter = PlayerUtils.getPlayers().iterator();
			while (iter.hasNext()) {							
				Player player = (Player) iter.next();
        		player.draw(gc2);
        	}
		}
	};
		
	if (Constants.BOTS_MODE==1) {
		timer.start();
	}
	
	getChildren().add(pane1);
	getChildren().add(pane2);	
	}
}
