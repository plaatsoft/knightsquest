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

package nl.plaatsoft.knightsquest.tools;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import nl.plaatsoft.knightsquest.ui.Navigator;

public class MyButton extends Button {

	public MyButton(int x, int y, String value, int fontSize, final int page) {
				
		int width = 180;
		if (x==0) {
			x= (MyFactory.getConfig().getWidth()/2)-(width/2);
		}				
		setText(value);
	    setPrefWidth(180);
	    setStyle("-fx-font-size:"+fontSize+"px;");	     
	    	  
		setLayoutX(x);
		setLayoutY(y);
		
	    setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent event) {
            	Navigator.go(page);
            }
        });
	}     
}
