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
import nl.plaatsoft.knightsquest.model.SoldierEnum;
import nl.plaatsoft.knightsquest.tools.MyButton;
import nl.plaatsoft.knightsquest.tools.MyFactory;
import nl.plaatsoft.knightsquest.tools.MyImageView;
import nl.plaatsoft.knightsquest.tools.MyLabel;
import nl.plaatsoft.knightsquest.tools.MyPanel;
import nl.plaatsoft.knightsquest.utils.Constants;
import nl.plaatsoft.knightsquest.utils.SoldierUtils;

public class Help extends MyPanel {

	public void draw() {
		Image image1 = new Image("images/background4.jpg");
    	BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
    	BackgroundImage backgroundImage = new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);   
    	setBackground(new Background(backgroundImage));
    	
    	int y=20;
    	getChildren().add( new MyLabel(0, y, "Help", 50, "white", "-fx-font-weight: bold;"));
    	y+=80;
    	
    	getChildren().add( new MyLabel(0, y, "KnightsQuest is a strategic game. Build your empire ", 20));
    	y+=20;  
    	getChildren().add( new MyLabel(0, y, "and destroy all other kingdoms. If you have conquered ", 20));
    	y+=20;  
    	getChildren().add( new MyLabel(0, y, "the whole world you have won!", 20));
    	y+=50;  
    	
    	getChildren().add( new MyLabel(45, y, "Icon", 20, "white", "-fx-font-weight: bold;"));
    	getChildren().add( new MyLabel(130, y, "Type", 20, "white", "-fx-font-weight: bold;"));
    	getChildren().add( new MyLabel(250, y, "Strength", 20, "white", "-fx-font-weight: bold;"));
    	getChildren().add( new MyLabel(400, y, "Food Demand", 20, "white", "-fx-font-weight: bold;"));
    	
    	boolean enabled = false;
    	
    	double scale = 20 / SoldierUtils.get(SoldierEnum.TOWER, enabled).getHeight();
    	y+=30;    	
    	getChildren().add( new MyImageView(50, y, SoldierUtils.get(SoldierEnum.TOWER, enabled), scale, true));
    	getChildren().add( new MyLabel(130, y, "Castle", 20, "white"));
    	getChildren().add( new MyLabel(250, y, "unlimit", 20, "white"));
    	getChildren().add( new MyLabel(400, y, "n.a.", 20, "white"));
    	y+=25;
    	getChildren().add( new MyImageView(50, y, SoldierUtils.get(SoldierEnum.PAWN, enabled), scale, true));
    	getChildren().add( new MyLabel(130, y, "Pawn", 20, "white"));
    	getChildren().add( new MyLabel(250, y, ""+SoldierEnum.PAWN.getValue(), 20, "white"));
    	getChildren().add( new MyLabel(400, y, ""+SoldierUtils.food(SoldierEnum.PAWN), 20, "white"));
    	y+=25;
    	getChildren().add( new MyImageView(50, y, SoldierUtils.get(SoldierEnum.BISHOP, enabled), scale, true));
    	getChildren().add( new MyLabel(130, y, "Bishop", 20, "white"));
    	getChildren().add( new MyLabel(250, y, ""+SoldierEnum.BISHOP.getValue(), 20, "white"));
    	getChildren().add( new MyLabel(400, y, ""+SoldierUtils.food(SoldierEnum.BISHOP), 20, "white"));
    	y+=25;
    	getChildren().add( new MyImageView(50, y, SoldierUtils.get(SoldierEnum.HORSE, enabled), scale, true));
    	getChildren().add( new MyLabel(130, y, "Horse", 20, "white"));
    	getChildren().add( new MyLabel(250, y, ""+SoldierEnum.HORSE.getValue(), 20, "white"));
    	getChildren().add( new MyLabel(400, y, ""+SoldierUtils.food(SoldierEnum.HORSE), 20, "white"));
    	y+=25;
    	getChildren().add( new MyImageView(50, y, SoldierUtils.get(SoldierEnum.QUEEN, enabled), scale, true));
    	getChildren().add( new MyLabel(130, y, "Queen", 20, "white"));
    	getChildren().add( new MyLabel(250, y, ""+SoldierEnum.QUEEN.getValue(), 20, "white"));
    	getChildren().add( new MyLabel(400, y, ""+SoldierUtils.food(SoldierEnum.QUEEN), 20, "white"));
    	y+=25;
    	getChildren().add( new MyImageView(50, y, SoldierUtils.get(SoldierEnum.KING, enabled), scale, true));
    	getChildren().add( new MyLabel(130, y, "King", 20, "white"));
    	getChildren().add( new MyLabel(250, y, ""+SoldierEnum.KING.getValue(), 20, "white"));
    	getChildren().add( new MyLabel(400, y, ""+SoldierUtils.food(SoldierEnum.KING), 20, "white"));
    	y+=25;
    	getChildren().add( new MyImageView(50, y, SoldierUtils.get(SoldierEnum.CROSS, enabled), scale, true));
    	getChildren().add( new MyLabel(130, y, "Cross", 20, "white"));
    	getChildren().add( new MyLabel(250, y, "0", 20, "white"));
    	getChildren().add( new MyLabel(400, y, "0 (Dead soldier)", 20, "white"));
    	       		
    	getChildren().add( new MyButton(0, MyFactory.getConfig().getHeight()-60, "Close", 18, Navigator.HOME));		
	}
}
