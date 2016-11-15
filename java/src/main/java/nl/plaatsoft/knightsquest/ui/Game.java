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
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import nl.plaatsoft.knightsquest.model.Player;
import nl.plaatsoft.knightsquest.tools.MyImageView;
import nl.plaatsoft.knightsquest.utils.Constants;
import nl.plaatsoft.knightsquest.utils.LandUtils;
import nl.plaatsoft.knightsquest.utils.PlayerUtils;

public class Game extends StackPane {

	final static Logger log = Logger.getLogger(Game.class);

	private GraphicsContext gc2;
	private GraphicsContext gc3;

	private Canvas canvas2;
	private Canvas canvas3;

	private double offsetX = 0;
	private double offsetY = 0;

	private AnimationTimer timer;

	public void draw() {

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
		
		Pane pane2 = new Pane();
		pane2.setScaleX(Constants.SCALE);
		pane2.setScaleY(Constants.SCALE);
		pane2.setId("map");
				
		canvas2 = new Canvas(Constants.MAP_WIDTH, Constants.MAP_HEIGHT);
		canvas2.setLayoutX(Constants.OFFSET_X);
		canvas2.setLayoutY(Constants.OFFSET_Y);
		
		gc2 = canvas2.getGraphicsContext2D();
		
		LandUtils.createMap();
		LandUtils.drawMap(gc2, Constants.SEGMENT_SIZE);
		
		pane2.getChildren().add(canvas2);
		
		getChildren().add(pane2);
		
		// ------------------------------------------------------ 
		// PLAYER LAYER 3
		// ------------------------------------------------------
		
		Pane pane3 = new Pane();
		pane3.setScaleX(Constants.SCALE);
		pane3.setScaleY(Constants.SCALE);
		pane3.setId("player");
		
		getChildren().add(pane3);
		
		canvas3 = new Canvas(Constants.MAP_WIDTH, Constants.MAP_HEIGHT);
		canvas3.setId("player");
		canvas3.setLayoutX(Constants.OFFSET_X);
		canvas3.setLayoutY(Constants.OFFSET_Y);
				
		gc3 = canvas3.getGraphicsContext2D();		
		pane3.getChildren().add(canvas3);
						
		for (int i = 1; i <= Constants.START_PLAYERS; i++) {
			Player player = PlayerUtils.createPlayer(i, pane3);
			player.draw(gc3, Constants.SEGMENT_SIZE);
		}
		
	
		timer = new AnimationTimer() {

			@Override
			public void handle(long now) {

				// Move bot players
				if (PlayerUtils.nextTurn(pane3) == true) {
					timer.stop();
				}

				// Clear canvas
				gc3.clearRect(0, 0, canvas3.getWidth(), canvas3.getHeight());

				// Draw new canvas
				Iterator<Player> iter = PlayerUtils.getPlayers().iterator();
				while (iter.hasNext()) {
					Player player = (Player) iter.next();
					player.draw(gc3, Constants.SEGMENT_SIZE);
				}
			}
		};

		if (Constants.BOTS_MODE == 1) {
			timer.start();
		}
				
		canvas3.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				offsetX = me.getSceneX() - canvas3.getLayoutX();
				offsetY = me.getSceneY() - canvas3.getLayoutY();

				if (me.getButton() == MouseButton.SECONDARY) {
					timer.stop();
					Navigator.go(Navigator.HOME);
				}
			}
		});

		canvas3.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {

				double tmpX = me.getSceneX() - offsetX;
				double tmpY = me.getSceneY() - offsetY;

				canvas2.setLayoutX(tmpX);
				canvas2.setLayoutY(tmpY);

				canvas3.setLayoutX(tmpX);
				canvas3.setLayoutY(tmpY);
				
				Iterator<Node> iter = pane3.getChildren().iterator();
				while (iter.hasNext()) {
					Node node = (Node) iter.next();
					if (node.getClass() == MyImageView.class) {
						MyImageView imageView = (MyImageView) node;

						imageView.move(canvas3.getLayoutX(), canvas3.getLayoutY());
					}
				}
			}
		});
		
	}
}
