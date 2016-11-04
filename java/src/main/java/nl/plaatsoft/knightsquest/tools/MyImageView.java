package nl.plaatsoft.knightsquest.tools;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MyImageView extends ImageView {
	
	private double origX = -1;
	private double origY = -1;
	
	public MyImageView(double x, double y, Image image, double scale) {
		        
		setImage(image);
		setLayoutX(x);
		setLayoutY(y);
		setScaleX(scale);
		setScaleY(scale);
	}
	
	public void move(double offsetX, double offsetY) {
	
			if ((this.origX==-1) && (this.origY==-1)) {
				this.origX = getLayoutX();
				this.origY = getLayoutY();
			}
							
			setLayoutX(origX+offsetX);
			setLayoutY(origY+offsetY);			
	}
}
