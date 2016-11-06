package nl.plaatsoft.knightsquest.ui;

import java.util.Iterator;

import org.apache.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import nl.plaatsoft.knightsquest.model.Player;
import nl.plaatsoft.knightsquest.tools.Constants;
import nl.plaatsoft.knightsquest.tools.MyButton;
import nl.plaatsoft.knightsquest.tools.MyPanel;
import nl.plaatsoft.knightsquest.tools.PlayerUtils;
import nl.plaatsoft.knightsquest.tools.SoldierUtils;
import nl.plaatsoft.knightsquest.tools.MyImageView;
import nl.plaatsoft.knightsquest.tools.LandUtils;

public class Game extends MyPanel {

	final static Logger log = Logger.getLogger( Game.class);
	
	private GraphicsContext gc1;
	private GraphicsContext gc2;
	
	private Canvas canvas1;
	private Canvas canvas2;
	
	private double offsetX = 0;
	private double offsetY = 0;
	
	public void draw() {
		
		setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		canvas1 = new Canvas(Constants.MAP_WIDTH,Constants.MAP_HEIGHT);
		canvas2 = new Canvas(Constants.MAP_WIDTH,Constants.MAP_HEIGHT);
		gc1 = canvas1.getGraphicsContext2D();
		gc2 = canvas2.getGraphicsContext2D();
		 				
		canvas2.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
		   		offsetX = me.getSceneX() - canvas2.getLayoutX();
		   		offsetY = me.getSceneY() - canvas2.getLayoutY();
		   		getScene().setCursor(Cursor.HAND);
		   	}
		});
				
		canvas2.setOnMouseDragged(new EventHandler<MouseEvent>() {
	    	public void handle(MouseEvent me) {		 
	    			    		
	    		double tmpX = me.getSceneX() - offsetX;
	    		double tmpY = me.getSceneY() - offsetY;

	    		canvas1.setLayoutX(tmpX);
	    		canvas1.setLayoutY(tmpY);
	    		
	    		canvas2.setLayoutX(tmpX);
	    		canvas2.setLayoutY(tmpY);
	    		
	    		log.info(canvas2.getLayoutX()+" "+canvas2.getLayoutY());
	    		
	    		Iterator<Node> iter = getChildren().iterator();	    				
	    		while (iter.hasNext()) {	    			
	    			Node node = (Node) iter.next();
	    			if (node.getClass()==MyImageView.class) {
	    				MyImageView imageView = (MyImageView) node;
	    				
	    				imageView.move(canvas2.getLayoutX(),canvas2.getLayoutY());
	    			}
	    		}   		
	    	}
		});
				
		canvas2.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {		    	
				getScene().setCursor(Cursor.DEFAULT);
	    	}
		});
				
		getChildren().add(canvas1);
		getChildren().add(canvas2);	    
		
	    LandUtils.createMap();
	    LandUtils.drawMap(gc1, gc2);
	    
	    for(int i=1; i<=Constants.START_PLAYERS; i++) {
	    	Player player = PlayerUtils.createPlayer(i, this);	    	
			player.draw(gc2);
		}
			
	    MyButton btn = new MyButton(Constants.WIDTH-230, Constants.HEIGHT-80, "Turn", 20, Navigator.NONE);
	    btn.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent event) {
            	PlayerUtils.nextTurn();
                LandUtils.drawMap(gc1, gc2);
            }
        });
        getChildren().add(btn);	
	}
}
