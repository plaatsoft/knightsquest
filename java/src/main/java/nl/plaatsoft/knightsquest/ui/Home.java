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

import javafx.concurrent.Task;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import nl.plaatsoft.knightsquest.network.CloudCheck;
import nl.plaatsoft.knightsquest.network.CloudNewVersion;
import nl.plaatsoft.knightsquest.tools.MyButton;
import nl.plaatsoft.knightsquest.tools.MyFactory;
import nl.plaatsoft.knightsquest.tools.MyImageView;
import nl.plaatsoft.knightsquest.tools.MyLabel;
import nl.plaatsoft.knightsquest.tools.MyPanel;

public class Home extends MyPanel {
		
	private MyLabel label3;
	private Task<Void> task;
	
	Home () {
		
		Image image1 = new Image("images/background4.jpg");
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		BackgroundImage backgroundImage = new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		Background background = new Background(backgroundImage);
		setBackground(background);
		
		getChildren().add (new MyLabel(30, 20, Constants.APP_NAME+" v"+Constants.APP_VERSION, 30, "white", "-fx-font-weight: bold;"));		
		getChildren().add (new MyLabel(30, 60, Constants.APP_BUILD, 20));
		label3 = new MyLabel(30, 425, "", 20, "white");
		getChildren().add(label3);
				
		int y = 30;
		getChildren().add( new MyButton(MyFactory.getSettingDAO().getSettings().getWidth()-210, y, "Play", 18, Navigator.MAP_SELECTOR));
		y += 45;
		getChildren().add( new MyButton(MyFactory.getSettingDAO().getSettings().getWidth()-210, y, "High Score", 18, Navigator.LOCAL_HIGHSCORE));
		y += 45;	
		getChildren().add( new MyButton(MyFactory.getSettingDAO().getSettings().getWidth()-210, y, "Settings", 18, Navigator.SETTINGS));
		y += 45;
		getChildren().add( new MyButton(MyFactory.getSettingDAO().getSettings().getWidth()-210, y, "Help", 18, Navigator.HELP));
		y += 45;
		getChildren().add( new MyButton(MyFactory.getSettingDAO().getSettings().getWidth()-210, y, "Credits", 18, Navigator.CREDITS));
		y += 45;
		getChildren().add( new MyButton(MyFactory.getSettingDAO().getSettings().getWidth()-210, y, "Release Notes", 18, Navigator.RELEASE_NOTES));
		y += 45;
		getChildren().add( new MyButton(MyFactory.getSettingDAO().getSettings().getWidth()-210, y, "Donate", 18, Navigator.DONATE));
		
		getChildren().add( new MyButton(MyFactory.getSettingDAO().getSettings().getWidth()-210, MyFactory.getSettingDAO().getSettings().getHeight()-70, "Exit", 18, Navigator.EXIT));	
			
		int x =0;
		double scale = 1;
		if (MyFactory.getSettingDAO().getSettings().getWidth()==640) {
			x = -10;
			y = 60;
			scale = 0.75;
		} else if (MyFactory.getSettingDAO().getSettings().getWidth()==800) {
			x = 10;
			y = 130;
			scale = 1;
		} else {
			x = 90;
			y = 200;
			scale = 1.4;
		}
		
		getChildren().add( new MyImageView(x, y, "images/knight1.png", scale));
					
	    task = new Task<Void>() {
	        public Void call() {
	        	if (CloudCheck.isReachableByTCP(Constants.APP_WS_URL)) {	        		
	        		label3.setText(CloudNewVersion.get());
	        	} else {
	        		label3.setText("No Internet connection!");
	        	}
	            return null;
	        }
		};
    }
	
	public void draw() {		
		new Thread(task).start();
	}
}
