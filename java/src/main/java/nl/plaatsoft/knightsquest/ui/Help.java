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

public class Help extends MyPanel {

	public void draw() {
		
		MyFactory.getSoldierDAO().init(20);
				
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
    	
    	int offset = ((MyFactory.getSettingDAO().getSettings().getWidth()-640)/2);
    	int x1=50+offset;
    	int x2=110+offset;
    	int x3=210+offset;
    	int x4=320+offset;
    	int x5=500+offset;
    	
    	getChildren().add( new MyLabel(x1, y, "Icon", 20, "white", "-fx-font-weight: bold;"));
    	getChildren().add( new MyLabel(x2, y, "Type", 20, "white", "-fx-font-weight: bold;"));
    	getChildren().add( new MyLabel(x3, y, "Strength", 20, "white", "-fx-font-weight: bold;"));
    	getChildren().add( new MyLabel(x4, y, "Food Demand", 20, "white", "-fx-font-weight: bold;"));
    	getChildren().add( new MyLabel(x5, y, "Step", 20, "white", "-fx-font-weight: bold;"));
    	
    	boolean enabled = false;
    	
    	
    	double scale = 20 / MyFactory.getSoldierDAO().get(SoldierEnum.TOWER, enabled).getHeight();
    	
    	y+=30;
    	getChildren().add( new MyImageView(x1, y, MyFactory.getSoldierDAO().get(SoldierEnum.PAWN, enabled), scale, true));
    	getChildren().add( new MyLabel(x2, y, "Pawn", 20, "white"));
    	getChildren().add( new MyLabel(x3, y, ""+SoldierEnum.PAWN.getValue(), 20, "white"));
    	getChildren().add( new MyLabel(x4, y, ""+MyFactory.getSoldierDAO().food(SoldierEnum.PAWN), 20, "white"));
    	getChildren().add( new MyLabel(x5, y, "2", 20, "white"));
    	y+=25;
    	getChildren().add( new MyImageView(x1, y, MyFactory.getSoldierDAO().get(SoldierEnum.BISHOP, enabled), scale, true));
    	getChildren().add( new MyLabel(x2, y, "Bishop", 20, "white"));
    	getChildren().add( new MyLabel(x3, y, ""+SoldierEnum.BISHOP.getValue(), 20, "white"));
    	getChildren().add( new MyLabel(x4, y, ""+MyFactory.getSoldierDAO().food(SoldierEnum.BISHOP), 20, "white"));
    	getChildren().add( new MyLabel(x5, y, "1", 20, "white"));
    	y+=25;
    	getChildren().add( new MyImageView(x1, y, MyFactory.getSoldierDAO().get(SoldierEnum.HORSE, enabled), scale, true));
    	getChildren().add( new MyLabel(x2, y, "Horse", 20, "white"));
    	getChildren().add( new MyLabel(x3, y, ""+SoldierEnum.HORSE.getValue(), 20, "white"));
    	getChildren().add( new MyLabel(x4, y, ""+MyFactory.getSoldierDAO().food(SoldierEnum.HORSE), 20, "white"));
    	getChildren().add( new MyLabel(x5, y, "1", 20, "white"));
    	y+=25;
    	getChildren().add( new MyImageView(x1, y, MyFactory.getSoldierDAO().get(SoldierEnum.QUEEN, enabled), scale, true));
    	getChildren().add( new MyLabel(x2, y, "Queen", 20, "white"));
    	getChildren().add( new MyLabel(x3, y, ""+SoldierEnum.QUEEN.getValue(), 20, "white"));
    	getChildren().add( new MyLabel(x4, y, ""+MyFactory.getSoldierDAO().food(SoldierEnum.QUEEN), 20, "white"));
    	getChildren().add( new MyLabel(x5, y, "1", 20, "white"));
    	y+=25;
    	getChildren().add( new MyImageView(x1, y, MyFactory.getSoldierDAO().get(SoldierEnum.KING, enabled), scale, true));
    	getChildren().add( new MyLabel(x2, y, "King", 20, "white"));
    	getChildren().add( new MyLabel(x3, y, ""+SoldierEnum.KING.getValue(), 20, "white"));
    	getChildren().add( new MyLabel(x4, y, ""+MyFactory.getSoldierDAO().food(SoldierEnum.KING), 20, "white"));
    	getChildren().add( new MyLabel(x5, y, "1", 20, "white"));
    	y+=25;    	
    	getChildren().add( new MyImageView(x1, y, MyFactory.getSoldierDAO().get(SoldierEnum.TOWER, enabled), scale, true));
    	getChildren().add( new MyLabel(x2, y, "Tower", 20, "white"));
    	getChildren().add( new MyLabel(x3, y, ""+SoldierEnum.TOWER.getValue(), 20, "white"));
    	getChildren().add( new MyLabel(x4, y, "0", 20, "white"));
    	getChildren().add( new MyLabel(x5, y, "0", 20, "white"));
    	y+=25;
    	getChildren().add( new MyImageView(x1, y, MyFactory.getSoldierDAO().get(SoldierEnum.CROSS, enabled), scale, true));
    	getChildren().add( new MyLabel(x2, y, "Cross", 20, "white"));
    	getChildren().add( new MyLabel(x3, y, "0", 20, "white"));
    	getChildren().add( new MyLabel(x4, y, "0 (Dead soldier)", 20, "white"));
    	getChildren().add( new MyLabel(x5, y, "0", 20, "white"));
    	       		
    	getChildren().add( new MyButton(0, MyFactory.getSettingDAO().getSettings().getHeight()-60, "Close", 18, Navigator.HOME));		
	}
}
