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

import java.util.Date;

import org.apache.log4j.Logger;

import javafx.animation.AnimationTimer;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import nl.plaatsoft.knightsquest.model.Land;
import nl.plaatsoft.knightsquest.model.Player;
import nl.plaatsoft.knightsquest.model.ScoreDAO;
import nl.plaatsoft.knightsquest.network.CloudScore;
import nl.plaatsoft.knightsquest.network.CloudUser;
import nl.plaatsoft.knightsquest.model.Score;
import nl.plaatsoft.knightsquest.tools.MyButton;
import nl.plaatsoft.knightsquest.tools.MyRandom;
import nl.plaatsoft.knightsquest.utils.Constants;
import nl.plaatsoft.knightsquest.utils.LandUtils;
import nl.plaatsoft.knightsquest.utils.PlayerUtils;

public class Game extends StackPane {

	final static Logger log = Logger.getLogger(Game.class);

	private GraphicsContext gc;
	private Canvas canvas;
	private Player[] player = new Player[Constants.START_PLAYERS+1];;	
	private Pane pane2; 
	private double offsetX = 0;
	private double offsetY = 0;
	private AnimationTimer timer;
	private boolean gameOver;
	private int turn;
	private Task<Void> task;

	public void redraw() {
		
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		LandUtils.drawMap();
		
		for (int i = 1; i <= Constants.START_PLAYERS; i++) {
			player[i].draw();
		}
	}
		
	public int storeScore(int points, int level) {
		
		Score score = new Score(new Date(), points, level, CloudUser.getNickname(), "");				
	  	int rank = ScoreDAO.addLocal(score);	 
	  	 
	  	/* Sent score to cloud server */
	  	task = new Task<Void>() {
	  	   public Void call() {
	  		   CloudScore.set(Constants.APP_WS_NAME, Constants.APP_VERSION, score ); 
	  		   return null;
	  	   }
		};
		new Thread(task).start();
		   
		return rank;
	}
		
	public void start() {

		gameOver = false;
		turn = 1;
			
		// ------------------------------------------------------
		// BACKGROUND LAYER 1
		// ------------------------------------------------------
		
		Pane pane1 = new Pane();
		pane1.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		pane1.setId("background");
		
		getChildren().add(pane1);
		
		// ------------------------------------------------------ 
		// MAP LAYER 2
		// ------------------------------------------------------
		
		pane2 = new Pane();
		pane2.setScaleX(Constants.SCALE);
		pane2.setScaleY(Constants.SCALE);
		pane2.setId("map");
		
		canvas = new Canvas(Constants.MAP_WIDTH, Constants.MAP_HEIGHT);
		canvas.setLayoutX(Constants.OFFSET_X);
		canvas.setLayoutY(Constants.OFFSET_Y);
		
		gc = canvas.getGraphicsContext2D();
		
		LandUtils.createMap(gc, Constants.SEGMENT_SIZE);
		
		pane2.getChildren().add(canvas);
		getChildren().add(pane2);
		
		// ------------------------------------------------------ 
		// CONTROL LAYER 3
		// ------------------------------------------------------
			
		Pane pane3 = new Pane();
		pane3.setScaleX(Constants.SCALE);
		pane3.setScaleY(Constants.SCALE);
		pane3.setId("control");
						
		MyButton btn = new MyButton(Constants.WIDTH-210, Constants.HEIGHT-60, "Turn ["+turn+"]", 18, Navigator.NONE);		
		pane3.getChildren().add(btn);
		getChildren().add(pane3);
				
		// ------------------------------------------------------ 
		// Create players
		// ------------------------------------------------------
		
		for (int i = 1; i <= Constants.START_PLAYERS; i++) {
			player[i] = PlayerUtils.createPlayer(gc, i, pane2);
		}
		
		redraw();
		
		// ------------------------------------------------------ 
		// Human Player Actions
		// ------------------------------------------------------
				
		// Mouse select land piece on map
		pane3.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {

				Land land = LandUtils.getPlayerSelectedLand(offsetX,offsetY);				
				if (land!=null) {
					//log.info("land ["+land.getX()+","+land.getY()+" scale="+pane2.getScaleX()+"] selected");
					LandUtils.doPlayerActions(land, player[1]);
					redraw();
				}
			}
		});
		
		// Scroll the map
		pane3.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				offsetX = me.getSceneX() - canvas.getLayoutX();
				offsetY = me.getSceneY() - canvas.getLayoutY();
			}
		});

		// Scroll the map
		pane3.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {

				double tmpX = me.getSceneX() - offsetX;
				double tmpY = me.getSceneY() - offsetY;

				canvas.setLayoutX(tmpX);
				canvas.setLayoutY(tmpY);				
			}
		});
		
		// Button actions
		btn.setOnAction(new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent event) {
				
				turn++;
				btn.setText("Turn ["+turn+"]");
				
				if (gameOver) {
					timer.stop();
					Navigator.go(Navigator.HOME);
					
				} else {
					
					PlayerUtils.nextTurn();
					
					if (PlayerUtils.checkGameOver1()) {
															
						log.info("game over, player dead");
						gameOver=true;
					
						storeScore(turn, MyRandom.getSeek());
						
						// player dead, fight on with the remaining bots
						timer.start();
					}
				}
				redraw();
			}
		});
		
		timer = new AnimationTimer() {
			public void handle(long now) {
											
				// Move bot players automaticly
				turn++;
				PlayerUtils.nextTurn();
				if (PlayerUtils.checkGameOver2()) {
					
					log.info("game over, all bots dead");
					
					// One bot won
					timer.stop();		
				}
				btn.setText("End ["+turn+"]");
				redraw();				
			}		
		};		
	}
}
