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
import nl.plaatsoft.knightsquest.tools.MyHyperlink;
import nl.plaatsoft.knightsquest.tools.MyLabel;
import nl.plaatsoft.knightsquest.tools.MyPanel;
import nl.plaatsoft.knightsquest.utils.Constants;

public class Donate extends MyPanel {

	public Donate() {
		
		Image image1 = new Image("images/background4.jpg");
    	BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
    	BackgroundImage backgroundImage = new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
    	Background background = new Background(backgroundImage);
    	
    	setBackground(background);
    	
    	int y=20;
    	getChildren().add(new MyLabel(0, y, "Donate", 50, "white", "-fx-font-weight: bold;"));
    	y+=80;
    	getChildren().add(new MyLabel(0, y, "If you enjoy this game, please sent me a small donation.", 20, "white"));
    	y+=25;
    	getChildren().add(new MyLabel(0, y, "You can make a donation online with your", 20, "white"));
    	y+=25;
    	getChildren().add(new MyLabel(0, y, "credit card, or PayPal account. Your credit card will", 20, "white"));
    	y+=25;    	 
    	getChildren().add(new MyLabel(0, y, "be processed by PayPal, a trusted name in secure ", 20, "white"));
    	y+=25;
    	getChildren().add(new MyLabel(0, y, "online transactions.", 20, "white"));
    	y+=60;  	
     	
    	// Force focus on button
    	getChildren().add(new MyButton(230, 420, "Close", 18, Navigator.HOME));		
    	
    	getChildren().add(new MyLabel(0, y, "Click on below link and follow the instructions", 20, "white"));    	
    	y+=25;
    	getChildren().add(new MyHyperlink(180, y, Constants.APP_DONATE_URL, 20));
    	y+=60;
    	getChildren().add(new MyLabel(0, y, "Many thanks for your support!", 20, "white"));    	
	}

	@Override
	public void draw() {

		
	}
}
