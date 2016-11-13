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

import org.apache.log4j.Logger;

import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import nl.plaatsoft.knightsquest.tools.Constants;
import nl.plaatsoft.knightsquest.tools.LandUtils;
import nl.plaatsoft.knightsquest.tools.MyButton;
import nl.plaatsoft.knightsquest.tools.MyLabel;
import nl.plaatsoft.knightsquest.tools.MyPanel;
import nl.plaatsoft.knightsquest.tools.MyRandom;

public class MapSelector extends MyPanel {

	final static Logger log = Logger.getLogger(MapSelector.class);

	private Task<Void> task;

	private static GraphicsContext[] gc = new GraphicsContext[6];
	private static int seek[] = { 1, 8, 30, 4, 10, 6 };

	private void createMap(GraphicsContext gc, int seek) {

		MyRandom.setSeek(seek);
		LandUtils.createMap();
		LandUtils.drawMap(gc);
	}

	private void createCanvas(int id, int x, int y, final int seek) {

		Canvas canvas = new Canvas((Constants.SEGMENT_X * Constants.SEGMENT_SIZE * 4),
				(Constants.SEGMENT_Y * Constants.SEGMENT_SIZE * 2));
		canvas.setLayoutX(x - 480);
		canvas.setLayoutY(y - 720);
		canvas.setScaleX(0.15);
		canvas.setScaleY(0.15);

		gc[id] = canvas.getGraphicsContext2D();
		getChildren().add(canvas);

		canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {

				MyRandom.setSeek(seek);
				Navigator.go(Navigator.GAME);
			}
		});

	}

	public void draw() {

		Image image1 = new Image("images/background3.jpg");
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		BackgroundImage backgroundImage = new BackgroundImage(image1, BackgroundRepeat.REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		setBackground(new Background(backgroundImage));

		int y = 20;
		int x;
		getChildren().add(new MyLabel(0, y, "Map Selector", 60, "white", "-fx-font-weight: bold;"));

		y += 90;
		x = 25;
		createCanvas(0, x, y, seek[0]);

		x += 200;
		createCanvas(1, x, y, seek[1]);

		x += 200;
		createCanvas(2, x, y, seek[2]);

		y += 150;

		x = 25;
		createCanvas(3, x, y, seek[3]);

		x += 200;
		createCanvas(4, x, y, seek[4]);

		x += 200;
		createCanvas(5, x, y, seek[5]);

		getChildren().add(new MyButton(230, 420, "Close", 18, Navigator.HOME));

		task = new Task<Void>() {
			public Void call() {
				for (int i = 0; i < 6; i++) {
					createMap(gc[i], seek[i]);	
					 updateProgress(i, 6);					
				}
				return null;
			}
		};

		new Thread(task).start();
	}
}
