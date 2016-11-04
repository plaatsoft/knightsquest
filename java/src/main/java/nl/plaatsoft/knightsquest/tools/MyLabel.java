package nl.plaatsoft.knightsquest.tools;

import org.apache.log4j.Logger;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class MyLabel extends Label {

	final static Logger log = Logger.getLogger( MyLabel.class);
	
	public MyLabel(int x, int y, String value, int fontSize, String color, String options) {
        
		setText(value);		
		setWrapText(true);
		setStyle("-fx-font-size:"+fontSize+"px; -fx-text-fill:"+color+"; "+options);
		
		if (x==0) {
			setMinWidth(640);
			setAlignment(Pos.CENTER);
			setTextAlignment(TextAlignment.CENTER);
			
		} else {
			
			setLayoutX(x);
		}
		setLayoutY(y);
	}	
	
	public MyLabel(int x, int y, String value, int fontSize, String color) {
		        
		setText(value);		
		setWrapText(true);
		setStyle("-fx-font-size:"+fontSize+"px; -fx-text-fill:"+color+"; ");
		
		if (x==0) {
			setMinWidth(640);
			setAlignment(Pos.CENTER);
			setTextAlignment(TextAlignment.CENTER);
			
		} else {
			
			setLayoutX(x);
		}
		setLayoutY(y);
	}	
	
	public MyLabel(int x, int y, String value, int fontSize) {
        
		setText(value);		
		setWrapText(true);
		setStyle("-fx-font-size:"+fontSize+"px; -fx-text-fill:white; ");
		
		if (x==0) {
			setMinWidth(640);
			setAlignment(Pos.CENTER);
			setTextAlignment(TextAlignment.CENTER);
			
		} else {
			
			setLayoutX(x);
		}
		setLayoutY(y);
	}	
}
