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
import nl.plaatsoft.knightsquest.model.SoldierType;
import nl.plaatsoft.knightsquest.tools.MyButton;
import nl.plaatsoft.knightsquest.tools.MyImageView;
import nl.plaatsoft.knightsquest.tools.MyLabel;
import nl.plaatsoft.knightsquest.tools.MyPanel;
import nl.plaatsoft.knightsquest.tools.SoldierUtils;

public class Help extends MyPanel {

	public void draw() {
		Image image1 = new Image("images/background3.jpg");
    	BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
    	BackgroundImage backgroundImage = new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
    	Background background = new Background(backgroundImage);
    	    	
    	
    
    	setBackground(background);
    	
    	int y=20;
    	getChildren().add( new MyLabel(0, y, "Help", 60, "white", "-fx-font-weight: bold;"));
    	y+=90;
    	getChildren().add( new MyLabel(40, y, "Icon", 24, "white", "-fx-font-weight: bold;"));
    	getChildren().add( new MyLabel(130, y, "Type", 24, "white", "-fx-font-weight: bold;"));
    	getChildren().add( new MyLabel(250, y, "Strength", 24, "white", "-fx-font-weight: bold;"));
    	getChildren().add( new MyLabel(400, y, "Food Demand", 24, "white", "-fx-font-weight: bold;"));
    	y+=40;    	
    	getChildren().add( new MyImageView(50,y+5,SoldierUtils.get(SoldierType.TOWER),1));
    	getChildren().add( new MyLabel(130, y, "Castle", 24, "white"));
    	getChildren().add( new MyLabel(250, y, "unlimit", 24, "white"));
    	getChildren().add( new MyLabel(400, y, "n.a.", 24, "white"));
    	y+=30;
    	getChildren().add( new MyImageView(50,y+5,SoldierUtils.get(SoldierType.SOLDIER),1));
    	getChildren().add( new MyLabel(130, y, "Soldier", 24, "white"));
    	getChildren().add( new MyLabel(250, y, "2", 24, "white"));
    	getChildren().add( new MyLabel(400, y, "2", 24, "white"));
    	y+=30;
    	getChildren().add( new MyImageView(50,y+5,SoldierUtils.get(SoldierType.BISHOP),1));
    	getChildren().add( new MyLabel(130, y, "Bishop", 24, "white"));
    	getChildren().add( new MyLabel(250, y, "3", 24, "white"));
    	getChildren().add( new MyLabel(400, y, "5", 24, "white"));
    	y+=30;
    	getChildren().add( new MyImageView(50,y+5,SoldierUtils.get(SoldierType.HORSE),1));
    	getChildren().add( new MyLabel(130, y, "Horse", 24, "white"));
    	getChildren().add( new MyLabel(250, y, "4", 24, "white"));
    	getChildren().add( new MyLabel(400, y, "10", 24, "white"));
    	y+=30;
    	getChildren().add( new MyImageView(50,y+5,SoldierUtils.get(SoldierType.QUEEN),1));
    	getChildren().add( new MyLabel(130, y, "Queen", 24, "white"));
    	getChildren().add( new MyLabel(250, y, "5", 24, "white"));
    	getChildren().add( new MyLabel(400, y, "20", 24, "white"));
    	y+=30;
    	getChildren().add( new MyImageView(50,y+5,SoldierUtils.get(SoldierType.KING),1));
    	getChildren().add( new MyLabel(130, y, "King", 24, "white"));
    	getChildren().add( new MyLabel(250, y, "6", 24, "white"));
    	getChildren().add( new MyLabel(400, y, "40", 24, "white"));
    	y+=30;
    	getChildren().add( new MyImageView(50,y+5,SoldierUtils.get(SoldierType.CROSS),1));
    	getChildren().add( new MyLabel(130, y, "Cross", 24, "white"));
    	getChildren().add( new MyLabel(250, y, "n.a.", 24, "white"));
    	getChildren().add( new MyLabel(400, y, "n.a.", 24, "white"));
    	       		
    	getChildren().add( new MyButton(230, 420, "Close", 18, Navigator.HOME));		
	}
}
