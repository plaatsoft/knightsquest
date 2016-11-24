package nl.plaatsoft.knightsquest.ui;

import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import nl.plaatsoft.knightsquest.tools.MyButton;
import nl.plaatsoft.knightsquest.tools.MyFactory;
import nl.plaatsoft.knightsquest.tools.MyLabel;
import nl.plaatsoft.knightsquest.tools.MyPanel;
import nl.plaatsoft.knightsquest.udp.UDPClient;
import nl.plaatsoft.knightsquest.udp.UDPServer;

public class Communication extends MyPanel {
		
	public void draw() {		
					
		Image image1 = new Image("images/background4.jpg");
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		BackgroundImage backgroundImage = new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		setBackground( new Background(backgroundImage));
							
		getChildren().add(new MyLabel(0, 20, "Communication", 50, "white", "-fx-font-weight: bold;"));
						
		MyButton close = new MyButton(0, MyFactory.getSettingDAO().getSettings().getHeight()-60, "Close", 18, Navigator.MODE_SELECTOR);
		getChildren().add(close);
		
		Task<Void> task1 = new Task<Void>() {
	        public Void call() {
	        	try {
	        		UDPServer server = new UDPServer();
					server.init();
	        	} catch (Exception e) {
					
					e.printStackTrace();
				}
	        	return null;	        	
	        }
		};
		
		Task<Void> task2 = new Task<Void>() {
	        public Void call() {
	        	try {		        		
	        		UDPClient client = new UDPClient();
	        		client.init("Hello");
	        	} catch (Exception e) {
					
					e.printStackTrace();
				}
	        	return null;	        	
	        }
		};
		
		new Thread(task1).start();
		new Thread(task2).start();
	}	
}
