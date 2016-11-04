package nl.plaatsoft.knightsquest.tools;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import nl.plaatsoft.knightsquest.ui.Navigator;

public class MyButton extends Button {

	public MyButton(int x, int y, String value, int fontSize, final int page) {
		
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
