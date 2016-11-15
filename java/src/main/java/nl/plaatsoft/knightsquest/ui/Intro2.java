package nl.plaatsoft.knightsquest.ui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import nl.plaatsoft.knightsquest.tools.MyLabel;
import nl.plaatsoft.knightsquest.tools.MyPanel;
import nl.plaatsoft.knightsquest.utils.Constants;

public class Intro2 extends MyPanel {

	private MediaPlayer mediaPlayer = null;
	
	public void draw() {
		setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		
		try {
			String path = Intro2.class.getResource("/video/intro.mp4").toExternalForm();		
			Media media = new Media(path);
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.setAutoPlay(true);
			
			MediaView mediaView = new MediaView(mediaPlayer);	    
			mediaView.setFitWidth(Constants.WIDTH);
			mediaView.setFitHeight(Constants.HEIGHT);
			
			getChildren().add(mediaView);
				
			mediaPlayer.setOnEndOfMedia(new Runnable() {
				public void run() {
					Navigator.go(Navigator.HOME);
				}
			});
		}
		catch (Exception e) {
			
			Image image1 = new Image("images/background4.jpg");
	    	BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
	    	BackgroundImage backgroundImage = new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
	    	setBackground(new Background(backgroundImage));
	    	
		    getChildren().add(new MyLabel(0,160,"PlaatSoft",90));	 
		}	
		
		setOnMousePressed(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent t) {
				if (mediaPlayer!=null) {
					mediaPlayer.stop();
				}
				Navigator.go(Navigator.HOME);						
			}
		});
	}
}
