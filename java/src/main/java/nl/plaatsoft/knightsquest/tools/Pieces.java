package nl.plaatsoft.knightsquest.tools;

import javafx.scene.image.Image;

public class Pieces {

	private static Image tower = new Image("images/tower.png");
	private static Image horse = new Image("images/horse.png");
	
	public static Image getTower() {
		return tower;
	}
		
	public static Image getHorse() {
		return horse;
	}	
}
