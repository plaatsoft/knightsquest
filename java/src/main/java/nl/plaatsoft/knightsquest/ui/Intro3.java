package nl.plaatsoft.knightsquest.ui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import nl.plaatsoft.knightsquest.tools.Constants;
import nl.plaatsoft.knightsquest.tools.MyPanel;

public class Intro3 extends MyPanel {

	public void draw() {
		String path = Intro3.class.getResource("/video/intro.mp4").toExternalForm();		
		Media media = new Media(path);
	    MediaPlayer mediaPlayer = new MediaPlayer(media);
	    mediaPlayer.setAutoPlay(true);
	    //mediaPlayer.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);

	    MediaView view = new MediaView(mediaPlayer);	    
	    view.setFitWidth(Constants.WIDTH);
	    view.setFitHeight(Constants.HEIGHT);
        
	    getChildren().add(view);
	 
	    setOnMousePressed(new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t) {
				Navigator.go(Navigator.HOME);						
	        }
	    });
	    
	}
}
