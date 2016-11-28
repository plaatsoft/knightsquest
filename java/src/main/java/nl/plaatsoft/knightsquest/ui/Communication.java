package nl.plaatsoft.knightsquest.ui;

import org.apache.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import nl.plaatsoft.knightsquest.tools.MyButton;
import nl.plaatsoft.knightsquest.tools.MyData;
import nl.plaatsoft.knightsquest.tools.MyFactory;
import nl.plaatsoft.knightsquest.tools.MyLabel;
import nl.plaatsoft.knightsquest.tools.MyListView;
import nl.plaatsoft.knightsquest.tools.MyPanel;

public class Communication extends MyPanel {
	
	final static Logger log = Logger.getLogger(Communication.class);
	
	private GraphicsContext gc; 
	private int map = 1000;
	private int level = 10;
	private Task<Void> task1;
	private boolean stop = false;
	private ObservableList<String> list = FXCollections.observableArrayList();
	
	private void drawMap(int map) {
				
		int size=5;		
		if (MyFactory.getSettingDAO().getSettings().getWidth()==800) {			
			size=6;			
		} else if (MyFactory.getSettingDAO().getSettings().getWidth()==1024) {			
			size=7;
		}		
		
		MyData.setLevel(level);
		MyData.setMap(map);
		MyData.setMode(MyData.MODE_2P);
		
		MyFactory.getLandDAO().createMap(gc, size, level+1);
		MyFactory.getLandDAO().draw();		
	}
		
	private void initCanvas() {
		
		int size=5;
		int startY = 150;
		
		if (MyFactory.getSettingDAO().getSettings().getWidth()==800) {
			
			size=6;
			startY = 170;
			
		} else if (MyFactory.getSettingDAO().getSettings().getWidth()==1024) {
			
			size=7;
			startY = 190;
		}
		
		int y = startY;		
		int x = 30;		
		
		getChildren().add(new MyLabel(x, y-50, "Map", 30));
		getChildren().add(new MyLabel(x+340, y-50, "Available Players", 30));
		
		MyListView listView = new MyListView(x+340, y, 220, 225, list );
		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        public void handle(MouseEvent event) {
	            log.info("clicked on " + listView.getSelectionModel().getSelectedItem());
	            MyData.setLevel(level);
				MyData.setMap(map);				
	            Navigator.go(Navigator.GAME);
	            MyFactory.getUDPServer().join(map, level);
	        }
	    });		
		getChildren().add(listView);
		
		list.add("Dummy1");
		list.add("Dummy2");
		
		Canvas canvas = new Canvas((Constants.SEGMENT_X * size * 4),(Constants.SEGMENT_Y * size));
		canvas.setLayoutX(x);
		canvas.setLayoutY(y);

		gc = canvas.getGraphicsContext2D();
		getChildren().add(canvas);
	}
	
	public void draw() {		
		
		Image image1 = new Image("images/background4.jpg");
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		BackgroundImage backgroundImage = new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		setBackground( new Background(backgroundImage));
							
		getChildren().add(new MyLabel(0, 20, "Player vs Player", 50, "white", "-fx-font-weight: bold;"));
		
		initCanvas();
		drawMap(map);
		
		MyButton close = new MyButton(0, MyFactory.getSettingDAO().getSettings().getHeight()-60, "Close", 18, Navigator.NONE);
		close.setOnAction(new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent event) {
				stop = true;
				task1.cancel();
				Navigator.go(Navigator.MODE_SELECTOR);
			}
		});		
		
		getChildren().add(close);
				
		MyButton prev= new MyButton(close.getLayoutX()-70, close.getLayoutY(), "<", 18, Navigator.NONE);
		prev.setPrefWidth(50);
		prev.setOnAction(new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent event) {
				if (map>0) {
					map--;
				}
				drawMap(map);
			}
		});		
		getChildren().add(prev);
		
		MyButton next= new MyButton(close.getLayoutX()+200, close.getLayoutY(), ">", 18, Navigator.NONE);
		next.setPrefWidth(50);
		next.setOnAction(new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent event) {
				if (map<2000) {
					map++;
				}
				drawMap(map);
			}
		});
		getChildren().add(next);
						
		task1 = new Task<Void>() {
	        public Void call() throws Exception {
	        	MyFactory.getUDPServer().init(Constants.APP_UDP_PORT);
	        	
	        	while (!stop) {
	        		
	        		MyFactory.getUDPServer().ping();
	        		String json = MyFactory.getUDPServer().receive();
	        		if (json.length()>0) {
	        			stop = true;
	        		}
	        		Thread.sleep(1000);
	        	}	        	
	        	return null;	        	
	        }
		};
		new Thread(task1).start();		
	}	
}
