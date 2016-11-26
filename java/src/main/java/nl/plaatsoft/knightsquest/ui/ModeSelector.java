package nl.plaatsoft.knightsquest.ui;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import nl.plaatsoft.knightsquest.tools.MyButton;
import nl.plaatsoft.knightsquest.tools.MyFactory;
import nl.plaatsoft.knightsquest.tools.MyImageView;
import nl.plaatsoft.knightsquest.tools.MyLabel;
import nl.plaatsoft.knightsquest.tools.MyPanel;

public class ModeSelector extends MyPanel {
	
	public void draw() {		
				
		Image image1 = new Image("images/background4.jpg");
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		BackgroundImage backgroundImage = new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		setBackground( new Background(backgroundImage));
						
		getChildren().add(new MyLabel(0, 20, "Mode Selector", 50, "white", "-fx-font-weight: bold;"));
		
		int x= (MyFactory.getSettingDAO().getSettings().getWidth()/2)-270;
		int y= 130;
		
		MyButton option1 = new MyButton(x-20, y-20, "", 18, Navigator.MAP_SELECTOR_1P);
		option1.setMinWidth(280);
		option1.setMinHeight(280);
		getChildren().add(option1);
		
		getChildren().add(new MyImageView(x, y, "images/human.png", 1, Navigator.MAP_SELECTOR_1P));
		x+=120;	
		getChildren().add(new MyLabel(x-15, y+80, "vs", 24 , "black"));		
		getChildren().add(new MyImageView(x, y, "images/robot.png", 1, Navigator.MAP_SELECTOR_1P));
		
		x+=190;	
		
		MyButton option2 = new MyButton(x-30, y-20, "", 18, Navigator.COMMUNICATION);
		option2.setMinWidth(280);
		option2.setMinHeight(280);
		getChildren().add(option2);
		
		getChildren().add( new MyImageView(x, y, "images/human.png", 1, Navigator.COMMUNICATION));
		x+=120;	
		getChildren().add(new MyLabel(x-15, y+80, "vs", 24 , "black"));		
		getChildren().add(new MyImageView(x, y, "images/human.png", 1, Navigator.COMMUNICATION));
			
		
		MyButton close = new MyButton(0, MyFactory.getSettingDAO().getSettings().getHeight()-60, "Close", 18, Navigator.HOME);
		getChildren().add(close);
	}
}
