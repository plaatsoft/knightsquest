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
import nl.plaatsoft.knightsquest.tools.MyMusic;
import nl.plaatsoft.knightsquest.tools.MyPanel;
import nl.plaatsoft.knightsquest.tools.MyToggleButton;
import nl.plaatsoft.knightsquest.tools.MyComboBox;
import nl.plaatsoft.knightsquest.tools.MyFactory;

public class Settings extends MyPanel {
	
	final static Logger log = Logger.getLogger(Settings.class);
	
	private final static int MAX=8;
	private Label[] label = new Label[MAX];
	private char[] letters = {'-','-','-','-','-','-','-','-'};
	private Task<Void> task;
	
	public Label labelSpecial(int pos, int x, int y) {
        			
		label[pos] = new Label();
		label[pos].setText(""+letters[pos]);		
		label[pos].setWrapText(true);
		label[pos].setStyle("-fx-font-size:70px; -fx-text-fill:white; -fx-font-weight: bold;");		
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
		button.setPrefWidth(40);
		button.setStyle("-fx-font-size:15px;");	     
		    	  
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
		   		  
	public Settings() {
		
		String nickName = CloudUser.getNickname();
		int max= nickName.length();
		if (max>MAX) {
			max=MAX;	
		}
		for (int i=0; i<max; i++){
			letters[i]= nickName.toUpperCase().charAt(i);
		}
		
		Image image1 = new Image("images/background4.jpg");
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		BackgroundImage backgroundImage = new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		setBackground( new Background(backgroundImage));
		
		int y=20;    
		getChildren().add (new MyLabel(0, y, "Settings", 50, "white", "-fx-font-weight: bold;"));		
		                
    	y+=80;    
    	getChildren().add (new MyLabel(0, y, "Below nickname is used in the highscore area", 20, "white"));		
    	
    	y+=60;   
    	    	
    	int offset = ((MyFactory.getSettingDAO().getSettings().getWidth()-640)/2);    	
    	int x1 = 100 + offset;
    	int x3 = 480 + offset;
				
		int x=(MyFactory.getSettingDAO().getSettings().getWidth()-(MAX*70))/2;
		
		for (int i=0; i<MAX ;i++) {
			getChildren().add(buttonSpecial(i, x+25, y-20, "+", true));	
			getChildren().add(labelSpecial(i, x, y));
			getChildren().add(buttonSpecial(i, x+25, y+90, "-", false));			
			x+=70;
		}			
		
		MyButton button = new MyButton(0, MyFactory.getSettingDAO().getSettings().getHeight()-60, "Close", 18, Navigator.HOME);		
		getChildren().add(button);	
		
		y+=150;		

		getChildren().add (new MyLabel(x1, y, "Music", 18));
		
		MyToggleButton btn2 = new MyToggleButton(x1-10,y+30,MyFactory.getSettingDAO().getSettings().isMusicOn(),18);		
		getChildren().add(btn2);
		btn2.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent event) {
            	if (MyFactory.getSettingDAO().getSettings().isMusicOn()) {
            		btn2.setText("Off");            	
        			MyFactory.getSettingDAO().getSettings().setMusicOn(false);
        			MyFactory.getSettingDAO().save();
        			MyMusic.stop();
        		} else {
        			btn2.setText("On");
        			MyFactory.getSettingDAO().getSettings().setMusicOn(true);
        			MyFactory.getSettingDAO().save();
        			MyMusic.play();
        		} 
            }
        });
		
		getChildren().add (new MyLabel(0, y, "Sound Effects", 18));
		
		MyToggleButton btn3 = new MyToggleButton(0,y+30,MyFactory.getSettingDAO().getSettings().isSoundEffectsOn(),18);		
		getChildren().add(btn3);
		btn3.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent event) {
            	if (MyFactory.getSettingDAO().getSettings().isSoundEffectsOn()) {
            		btn3.setText("Off");            	
        			MyFactory.getSettingDAO().getSettings().setSoundEffectsOn(false);
        			MyFactory.getSettingDAO().save();
        		} else {
        			btn3.setText("On");
        			MyFactory.getSettingDAO().getSettings().setSoundEffectsOn(true);
        			MyFactory.getSettingDAO().save();
        		} 
            }
        });
		
		        
        /* --------------------------------------- */
            
		getChildren().add (new MyLabel(x3, y, "Size", 20 ));
				
        String[] options2 = {"640x480", "800x600", "1024x768"};
        MyComboBox comboBox1 = new MyComboBox(x3-20, y+30, MyFactory.getSettingDAO().getSettings().getResolution(), options2, 18);
        comboBox1.setOnAction(new EventHandler<ActionEvent>() { 
        	public void handle(ActionEvent event) {
        		String value =  comboBox1.getSelectionModel().getSelectedItem().toString();
        		MyFactory.getSettingDAO().getSettings().setResolution(value);
        		MyFactory.getSettingDAO().save();
        		
      	        if (value.equals("640x480")) {
      	        	Navigator.getStage().setWidth(640);
       	        	Navigator.getStage().setHeight(480+20);
       	        	
       	        } else if (value.equals("800x600")) {
       	        	Navigator.getStage().setWidth(800);
       	        	Navigator.getStage().setHeight(600+20);
       	        	
       	        } else {
       	        	Navigator.getStage().setWidth(1024);
       	        	Navigator.getStage().setHeight(768+20);       	        	
       	        }
      	        Navigator.go(Navigator.HOME);      	        
            }            	
        });
        
        getChildren().add(comboBox1);
                
        /* --------------------------------------- */
        
		task = new Task<Void>() {
	        public Void call() {
	        	CloudUser.set(new String(letters).replaceAll("-", ""));         
	            return null;
	        }
		};
		
		button.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent event) {
            	new Thread(task).start();
            	Navigator.go(Navigator.HOME);
            }
        });		
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}
}
