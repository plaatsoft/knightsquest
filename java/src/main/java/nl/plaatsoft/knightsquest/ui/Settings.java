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

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.TextAlignment;
import nl.plaatsoft.knightsquest.network.CloudUser;
import nl.plaatsoft.knightsquest.tools.MyButton;
import nl.plaatsoft.knightsquest.tools.MyLabel;
import nl.plaatsoft.knightsquest.tools.MyPanel;

public class Settings extends MyPanel {

	private final static int MAX=8;
	private Label[] label = new Label[MAX];
	private char[] letters = {'-','-','-','-','-','-','-','-'};
	
	public Label labelSpecial(int pos, int x, int y) {
        			
		label[pos] = new Label();
		label[pos].setText(""+letters[pos]);		
		label[pos].setWrapText(true);
		label[pos].setStyle("-fx-font-size:80px; -fx-text-fill:white; -fx-font-weight: bold;");		
		label[pos].setMinWidth(90);
		label[pos].setAlignment(Pos.CENTER);
		label[pos].setTextAlignment(TextAlignment.CENTER);
		label[pos].setLayoutX(x);
		label[pos].setLayoutY(y);
			
		return label[pos];
	}	
	
	private Button buttonSpecial(final int pos, int x, int y, String text, final boolean up) {
	
		Button button = new Button();
		
		button.setText(text);
		button.setPrefWidth(60);
		button.setStyle("-fx-font-size:20px;");	     
		    	  
		button.setLayoutX(x);
		button.setLayoutY(y);
			
		button.setOnAction(new EventHandler<ActionEvent>() { 
	       public void handle(ActionEvent event) {
	    	   	if (up==true) {
	    	   		letters[pos]++;
	    	   		if (letters[pos]>90) {
	    	   			letters[pos]=40;
	    	   		}
	       		} else {
	       			letters[pos]--;
	       			if (letters[pos]<40) {
	       				letters[pos]=90;
	    	   		}
	       		}
	    	   		    	   	
    	   		label[pos].setText(""+letters[pos]);
	       }
	    });
		return button;
	}
		   		  
	public void draw() {
		
		String nickName = CloudUser.getNickname();
		int max= nickName.length();
		if (max>MAX) {
			max=MAX;	
		}
		for (int i=0; i<max; i++){
			letters[i]= nickName.toUpperCase().charAt(i);
		}
		
		Image image1 = new Image("images/background3.jpg");
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		BackgroundImage backgroundImage = new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		setBackground( new Background(backgroundImage));
		
		int y=0;    
		getChildren().add (new MyLabel(0, y, "Settings", 50, "white", "-fx-font-weight: bold;"));		
		                
    	y+=80;    
    	getChildren().add (new MyLabel(0, y, "Below nickname is used in the highscore area", 20, "white"));		
    	y+=80;   
	
		int x=30;
		
		for (int i=0; i<MAX ;i++) {
			getChildren().add(buttonSpecial(i, x+10, y-40, "+", true));	
			getChildren().add(labelSpecial(i, x, y));
			getChildren().add(buttonSpecial(i, x+10, y+120, "-", false));
			
			x+=70;
		}			
		
		MyButton button = new MyButton(230, 420, "Close", 18, Navigator.HOME);		
		getChildren().add(button);	
		
		button.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent event) {
            	CloudUser.set(new String(letters).replaceAll("-", ""));
            	Navigator.go(Navigator.HOME);
            }
        });
		
	}
}
