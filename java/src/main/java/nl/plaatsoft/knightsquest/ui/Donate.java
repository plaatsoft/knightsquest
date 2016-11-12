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

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import nl.plaatsoft.knightsquest.tools.MyButton;
import nl.plaatsoft.knightsquest.tools.MyLabel;
import nl.plaatsoft.knightsquest.tools.MyPanel;

public class Donate extends MyPanel {

	public void draw() {
		
		Image image1 = new Image("images/background1.jpg");
    	BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
    	BackgroundImage backgroundImage = new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
    	Background background = new Background(backgroundImage);
    	
    	setBackground(background);
    	
    	int y=20;
    	getChildren().add(new MyLabel(0, y, "Donate", 60, "white", "-fx-font-weight: bold;"));
    	y+=80;
    	getChildren().add(new MyLabel(0, y, "If you enjoy this game, please sent me a", 24, "white"));
    	y+=30;
    	getChildren().add(new MyLabel(0, y, "small donation. You can make a donation", 24, "white"));
    	y+=30;
    	getChildren().add(new MyLabel(0, y, "online with your credit card, or PayPal account.", 24, "white"));
    	y+=30;    	 
    	getChildren().add(new MyLabel(0, y, "Your credit card will be processed by PayPal, a", 24, "white"));
    	y+=30;
    	getChildren().add(new MyLabel(0, y, "trusted name in secure online transactions.", 24, "white"));
    	y+=60;
    	getChildren().add(new MyLabel(0, y, "Please visit www.plaatsoft.nl", 24, "white"));
    	y+=30;
    	getChildren().add(new MyLabel(0, y, "Click on the donate link and follow the instructions", 24, "white"));    	
    	y+=60;
    	getChildren().add(new MyLabel(0, y, "Many thanks for your support!", 24, "white"));
    	       		
    	getChildren().add(new MyButton(230, 420, "Close", 18, Navigator.HOME));		
	}
}
