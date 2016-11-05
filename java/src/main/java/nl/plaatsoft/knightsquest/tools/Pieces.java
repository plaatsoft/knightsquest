package nl.plaatsoft.knightsquest.tools;

import javafx.scene.image.Image;

public class Pieces {

	private static Image tower = new Image("images/tower.png");
	private static Image horse = new Image("images/horse.png");
	
	public static Image getPieces(Army army) {
	
		switch(army) {
	
			case PRIVATE: 
				return tower;
		
			case COLONEL:
				return horse;
				
			case GENERAL:
				return tower;
				
			case QUEEN:
				return tower;
			
			default:
				break;
		}
		return null;
	}		
}
