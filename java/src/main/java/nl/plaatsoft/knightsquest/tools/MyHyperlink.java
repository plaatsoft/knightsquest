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
import nl.plaatsoft.knightsquest.utils.Constants;

public class MyHyperlink extends Hyperlink {

	public MyHyperlink(int x, int y, String url, int fontSize) {
		
		setText(url);
		setLayoutY(y);
			
		if (x==0) {
			setMinWidth(Constants.WIDTH);
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
