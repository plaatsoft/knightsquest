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

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.TextAlignment;

public class MyHyperlink extends Hyperlink {

	public MyHyperlink(int x, int y, String url, int fontSize) {
		
		setText(url);
		setLayoutY(y);
			
		if (x==0) {
			setMinWidth(MyFactory.getConfig().getWidth());
			setAlignment(Pos.CENTER);
			setTextAlignment(TextAlignment.CENTER);
			
		} else {
			
			setLayoutX(x);
		}
		
		setStyle("-fx-font-size:"+fontSize+"px; ");
		
    	setOnAction(new EventHandler<ActionEvent>() {
    	    public void handle(ActionEvent e) {
    	    	try {
					Desktop.getDesktop().browse(new URL(url).toURI());
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
    	    }
    	});
	}

}
