package nl.plaatsoft.knightsquest.tools;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;

public class MyHyperlink extends Hyperlink {

	public MyHyperlink(int x, int y, String url, int fontSize) {
		
		setText(url);
    	setLayoutX(x);
		setLayoutY(y);
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
