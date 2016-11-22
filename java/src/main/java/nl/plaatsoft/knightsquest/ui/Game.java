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
import nl.plaatsoft.knightsquest.tools.MyData;
import nl.plaatsoft.knightsquest.tools.MyFactory;
import nl.plaatsoft.knightsquest.tools.MyLabel;

public class Game extends StackPane {

	final static Logger log = Logger.getLogger(Game.class);

	private GraphicsContext gc;
	private Canvas canvas;
	private static Player[] players;
	private Pane pane2; 
	private double offsetX = 0;
	private double offsetY = 0;
	private AnimationTimer timer;
	private boolean gameOver;
	private int turn;
	private Task<Void> task;	
	private static MyLabel label1;
	private static MyLabel label2;
	private static MyLabel label3;
	private static MyLabel label4;
	private static MyLabel[] label5;
	
	public void redraw() {
		
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		MyFactory.getLandDAO().draw();
		
		for (int i=0; i <MyData.getPlayers(); i++) {
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
		
	public int calculateScore(Player player, boolean won) {
		
		int score = 0;
				
		if (won) {
			score += 1000;
		}
		
		int tmp = (100-turn) * 5;
		if (tmp>0) {
			score+=tmp;
		}
		
		log.info("turn="+turn);
		log.info("moves="+player.getMoves());
		log.info("creates="+player.getCreates());
		log.info("conquer="+player.getConquer());
		log.info("upgrade="+player.getUpgrades());
		
		score += player.getMoves();
		score += player.getCreates()*2;		
		score += player.getConquer()*5;
		score += player.getUpgrades()*10;
		
		log.info("score="+score);
		
		storeScore(score, MyData.getMap());
		
		return score;		
	}
	
	public boolean checkGameOver1() {
		
		for (int i=0; i<MyData.getPlayers(); i++) {
			
			Player player = players[i];
			if (!player.isBot() && (player.getRegion().size()>0)) {
				return false;
			}
		}
		
		label1.setText("Game Over");
		label2.setText("Computer won");
		return true;		
	}

	public boolean checkGameOver2() {

		int count=0;

		for (int i=0; i<MyData.getPlayers(); i++) {
			
			if (players[i].getRegion().size()>0) {
				count++;
				if (count>1) {
					return false;
				}
			}
		}
		
		int score=0;
		
		label1.setText("Game Over");
				
		if (players[0].getRegion().size()>0) {
			
			label2.setText("Player won");			
			score = calculateScore(players[0],true);
			
			// Next map is unlocked!
			int nextMap = MyData.getNextMap(MyData.getMap());
			if ((nextMap>0) && (!MyFactory.getSettingDAO().getSettings().getMapUnlocked(nextMap))) {
				label3.setText("Map "+nextMap+" is now unlocked!");		
				MyFactory.getSettingDAO().getSettings().setMapUnlocked(MyData.getNextMap(MyData.getMap()), true);
				
			}			
			
		} else {
			
			label2.setText("Computer won");
			score = calculateScore(players[0],false);			
		}
		
		label4.setText("Total score = "+score);	
		MyFactory.getSettingDAO().getSettings().setScore(MyData.getMap(), score);
		MyFactory.getSettingDAO().save();
		
		return true;		
	}
		
	public void drawPlayerScore() {

		for (int i=0; i<MyData.getPlayers(); i++) {
			
			int amount = 0; 
			Iterator<Region> iter2 = players[i].getRegion().iterator();  
			while (iter2.hasNext()) {
				Region region = (Region) iter2.next();
				
				amount += region.getLands().size();  
			}
			
			label5[i].setText("Player "+(i+1)+": "+amount);
		}
	}
		
	public void start() {
		
		// Clear previous game 
		MyFactory.clearFactory();

		players = new Player[MyData.getPlayers()];
		label5 = new MyLabel[MyData.getPlayers()];
		
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
		if (MyFactory.getSettingDAO().getSettings().getWidth()==640) {
					
			size = Constants.SEGMENT_SIZE_640;
			MyFactory.getSoldierDAO().init(size);
			MyFactory.getBuildingDAO().init(size);
			
			canvas = new Canvas((size * 4 * (Constants.SEGMENT_X+1)), (size * 2 * Constants.SEGMENT_Y));
			canvas.setLayoutX(Constants.OFFSET_X_640);
			canvas.setLayoutY(Constants.OFFSET_Y_480);
			
		} else if (MyFactory.getSettingDAO().getSettings().getWidth()==800) {
			
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
		MyFactory.getLandDAO().createMap(gc, size, MyData.getLevel()+1);
		
		pane2.getChildren().add(canvas);
		getChildren().add(pane2);
		
		// ------------------------------------------------------ 
		// Control LAYER 3
		// ------------------------------------------------------
			
		Pane pane3 = new Pane();
		pane3.setScaleX(Constants.SCALE);
		pane3.setScaleY(Constants.SCALE);
		pane3.setId("control");
						
		label1 = new MyLabel(0, (MyFactory.getSettingDAO().getSettings().getHeight()/2)-160, "", 80, "white", "-fx-font-weight: bold;");
		label2 = new MyLabel(0, (MyFactory.getSettingDAO().getSettings().getHeight()/2)-60, "", 40, "white", "-fx-font-weight: bold;");
		label3 = new MyLabel(0, (MyFactory.getSettingDAO().getSettings().getHeight()/2), "", 40, "white", "-fx-font-weight: bold;");
		label4 = new MyLabel(0, (MyFactory.getSettingDAO().getSettings().getHeight()/2)+60, "", 40, "white", "-fx-font-weight: bold;");
								
		MyButton btn = new MyButton(10,10, "Exit", 18, Navigator.NONE);
		btn.setPrefWidth(140);
		pane3.getChildren().add(label1);
		pane3.getChildren().add(label2);
		pane3.getChildren().add(label3);
		pane3.getChildren().add(label4);
		
		// ------------------------------------------------------ 
		// Player score board
		// ------------------------------------------------------
			
		Rectangle r = new Rectangle();
		r.setX(MyFactory.getSettingDAO().getSettings().getWidth()-135);
		r.setY(10);
		r.setWidth(115);		
		r.setHeight(MyData.getPlayers()*20);
		r.setArcWidth(20);
		r.setArcHeight(20);
		r.setFill(Color.rgb(0, 0, 0, 0.7));		
		pane3.getChildren().add(r);
		
		int y=10;
		for (int i=0; i<MyData.getPlayers(); i++) {			
			label5[i] = new MyLabel(MyFactory.getSettingDAO().getSettings().getWidth()-125, y, "", 15, MyFactory.getPlayerDAO().getColor(i), "-fx-font-weight: bold;");
			y+=18;
		}
		
		for (int i=0; i<MyData.getPlayers(); i++) {		
			pane3.getChildren().add(label5[i]);
		}		
		pane3.getChildren().add(btn);
		getChildren().add(pane3);
				
		// ------------------------------------------------------ 
		// Create players
		// ------------------------------------------------------
		
		for (int i=0; i<MyData.getPlayers(); i++) {
			players[i] = MyFactory.getPlayerDAO().createPlayer(gc, i, pane2);
		}
		
		/* Create Harbors */
		MyFactory.getBuildingDAO().createHarbors();
		
		/* Draw everything (map, player, buildings,etc..) */		
		redraw();
		
		/* Draw score board */
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
					MyFactory.getLandDAO().doPlayerActions(land, players[0]);
										
					if (MyFactory.getPlayerDAO().hasPlayerNoMoves(players[0])) {
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
				
				if (turn==1) {
					Navigator.go(Navigator.MAP_SELECTOR);
				}
				turn++;
				btn.setText("Turn ["+turn+"]");
				
				if (gameOver) {
					timer.stop();
					Navigator.go(Navigator.MAP_SELECTOR);
					
				} else {
					
					MyFactory.getPlayerDAO().nextTurn();
					
					if (checkGameOver1() || checkGameOver2()) {
															
						gameOver=true;
						
						
						// human player dead, fight on with the remaining bots
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
