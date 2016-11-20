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
import java.util.Iterator;

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
import javafx.scene.shape.Rectangle;

import nl.plaatsoft.knightsquest.model.Land;
import nl.plaatsoft.knightsquest.model.Player;
import nl.plaatsoft.knightsquest.model.Region;
import nl.plaatsoft.knightsquest.network.CloudScore;
import nl.plaatsoft.knightsquest.network.CloudUser;
import nl.plaatsoft.knightsquest.model.Score;
import nl.plaatsoft.knightsquest.tools.MyButton;
import nl.plaatsoft.knightsquest.tools.MyFactory;
import nl.plaatsoft.knightsquest.tools.MyLabel;
import nl.plaatsoft.knightsquest.tools.MyRandom;

public class Game extends StackPane {

	final static Logger log = Logger.getLogger(Game.class);

	private GraphicsContext gc;
	private Canvas canvas;
	private static Player[] players = new Player[Constants.MAX_PLAYERS+1];
	private Pane pane2; 
	private double offsetX = 0;
	private double offsetY = 0;
	private AnimationTimer timer;
	private boolean gameOver;
	private int turn;
	private Task<Void> task;	
	private static MyLabel label1;
	private static MyLabel label2;
	private static MyLabel[] label3 = new MyLabel[Constants.MAX_PLAYERS+1];
	
	public void redraw() {
		
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		MyFactory.getLandDAO().draw();
		
		for (int i = 1; i <= MyFactory.getConfig().getAmountOfPlayers(); i++) {
			players[i].draw();
		}
	}
		
	public int storeScore(int points, int level) {
		
		Score score = new Score(new Date(), points, level, CloudUser.getNickname(), "");				
	  	int rank = MyFactory.getScoreDAO().addLocal(score);	 
	  	 
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
	
	public static boolean checkGameOver1() {
		
		if (players[1].getRegion().size()>0) {
			return false;
		}
		
		label1.setText("Game Over");
		label2.setText("Bots won!");
		return true;		
	}

	public static boolean checkGameOver2() {

		int count=0;

		for (int i = 1; i <= MyFactory.getConfig().getAmountOfPlayers(); i++) {
			
			if (players[i].getRegion().size()>0) {
				count++;
				if (count>1) {
					return false;
				}
			}
		}
						
		label1.setText("Game Over");
		if (players[1].getRegion().size()>0) {			
			label2.setText("Player wins!");
		} else {
			label2.setText("Bots won!");
		}
		return true;		
	}
		
	public static void drawPlayerScore() {

		for (int i = 1; i <= MyFactory.getConfig().getAmountOfPlayers(); i++) {
			
			int amount = 0; 
			Iterator<Region> iter2 = players[i].getRegion().iterator();  
			while (iter2.hasNext()) {
				Region region = (Region) iter2.next();
				
				amount += region.getLands().size();  
			}
			
			label3[i].setText("Player "+i+" - "+amount);
		}
	}
		
	public void start() {

		// Clear previous game 
		MyFactory.clearFactory();
		
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
				
		int size=0;
		if (MyFactory.getConfig().getWidth()==640) {
					
			size = Constants.SEGMENT_SIZE_640;
			MyFactory.getSoldierDAO().init(size);
			MyFactory.getBuildingDAO().init(size);
			
			canvas = new Canvas((size * 4 * (Constants.SEGMENT_X+1)), (size * 2 * Constants.SEGMENT_Y));
			canvas.setLayoutX(Constants.OFFSET_X_640);
			canvas.setLayoutY(Constants.OFFSET_Y_480);
			
		} else if (MyFactory.getConfig().getWidth()==800) {
			
			size = Constants.SEGMENT_SIZE_800;
			MyFactory.getSoldierDAO().init(size);
			MyFactory.getBuildingDAO().init(size);
			
			canvas = new Canvas((size * 4 * (Constants.SEGMENT_X+1)), (size * 2 * Constants.SEGMENT_Y));
			canvas.setLayoutX(Constants.OFFSET_X_800);
			canvas.setLayoutY(Constants.OFFSET_Y_600);
			
		} else {
			
			size = Constants.SEGMENT_SIZE_1024;
			MyFactory.getSoldierDAO().init(size);
			MyFactory.getBuildingDAO().init(size);
			
			canvas = new Canvas((size * 4 * (Constants.SEGMENT_X+1)), (size * 2 * Constants.SEGMENT_Y));
			canvas.setLayoutX(Constants.OFFSET_X_1024);
			canvas.setLayoutY(Constants.OFFSET_Y_768);
		}
	
		gc = canvas.getGraphicsContext2D();
		
		/* create land */
		MyFactory.getLandDAO().createMap(gc, size);
		
		pane2.getChildren().add(canvas);
		getChildren().add(pane2);
		
		// ------------------------------------------------------ 
		// CONTROL LAYER 3
		// ------------------------------------------------------
			
		Pane pane3 = new Pane();
		pane3.setScaleX(Constants.SCALE);
		pane3.setScaleY(Constants.SCALE);
		pane3.setId("control");
						
		label1 = new MyLabel(0, (MyFactory.getConfig().getHeight()/2)-120, "", 80, "white", "-fx-font-weight: bold;");
		label2 = new MyLabel(0, (MyFactory.getConfig().getHeight()/2)-20, "", 60, "white", "-fx-font-weight: bold;");
				
		MyButton btn = new MyButton(20,MyFactory.getConfig().getHeight()-60, "Turn ["+turn+"]", 18, Navigator.NONE);
		btn.setPrefWidth(140);
		pane3.getChildren().add(label1);
		pane3.getChildren().add(label2);
		
		// ------------------------------------------------------ 
		// Player score board
		// ------------------------------------------------------
			
		Rectangle r = new Rectangle();
		r.setX(MyFactory.getConfig().getWidth()-135);
		r.setY(10);
		r.setWidth(115);		
		r.setHeight(MyFactory.getConfig().getAmountOfPlayers()*20);
		r.setArcWidth(20);
		r.setArcHeight(20);
		r.setFill(Color.rgb(0, 0, 0, 0.7));		
		pane3.getChildren().add(r);
		
		int y=15;
		for (int i = 1; i <= MyFactory.getConfig().getAmountOfPlayers(); i++) {			
			label3[i] = new MyLabel(MyFactory.getConfig().getWidth()-125, y, "", 15, MyFactory.getPlayerDAO().getColor(i), "-fx-font-weight: bold;");
			y+=18;
		}
		
		for (int i = 1; i <= MyFactory.getConfig().getAmountOfPlayers(); i++) {		
			pane3.getChildren().add(label3[i]);
		}		
		pane3.getChildren().add(btn);
		getChildren().add(pane3);
				
		// ------------------------------------------------------ 
		// Create players
		// ------------------------------------------------------
		
		for (int i = 1; i <= MyFactory.getConfig().getAmountOfPlayers(); i++) {
			players[i] =MyFactory.getPlayerDAO().createPlayer(gc, i, pane2);
		}
		
		/* Create Habors */
		MyFactory.getBuildingDAO().createHabors();
		
		redraw();
		drawPlayerScore();
		
		// ------------------------------------------------------ 
		// Human Player Actions
		// ------------------------------------------------------
				
		// Mouse select land piece on map
		pane3.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {

				Land land = MyFactory.getLandDAO().getPlayerSelectedLand(offsetX,offsetY);				
				if (land!=null) {
					//log.info("land ["+land.getX()+","+land.getY()+" scale="+land.getScale()+"] selected");
					MyFactory.getLandDAO().doPlayerActions(land, players[1]);
										
					if (MyFactory.getPlayerDAO().hasPlayerNoMoves(players[1])) {
						turn++;
						btn.setText("Turn ["+turn+"]");
						MyFactory.getPlayerDAO().nextTurn();
					}
					
					redraw();
					drawPlayerScore();
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
					
					MyFactory.getPlayerDAO().nextTurn();
					
					if (checkGameOver1() || checkGameOver2()) {
															
						log.info("game over, player dead");
						gameOver=true;
					
						storeScore(turn, MyRandom.getSeek());
						
						// player dead, fight on with the remaining bots
						timer.start();
					}
				}
				redraw();
				drawPlayerScore();
			}
		});
		
		timer = new AnimationTimer() {
			public void handle(long now) {
											
				// Move bot players automatic
				turn++;
				MyFactory.getPlayerDAO().nextTurn();
				if (checkGameOver2()) {
					
					log.info("game over, only one player over!");
					timer.stop();		
				}
				btn.setText("End ["+turn+"]");
				redraw();	
				drawPlayerScore();
			}		
		};		
	}
}
