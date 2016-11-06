package nl.plaatsoft.knightsquest.tools;

import javafx.scene.image.Image;

public class MyArmy {

	private static Image tower = new Image("images/tower.png");
	private static Image soldier = new Image("images/soldier.png");
	private static Image horse = new Image("images/horse.png");
	private static Image bishop = new Image("images/bishop.png");
	private static Image queen = new Image("images/queen.png");
	private static Image king = new Image("images/king.png");
	
	public static Image get(MyArmyEnum army) {
	
		switch(army) {
	
			case TOWER: 
				return tower;
		
			case SOLDIER:
				return soldier;
				
			case HORSE:
				return horse;
				
			case BISHOP:
				return bishop;
				
			case QUEEN:
				return queen;
				
			case KING:
				return king;
			
			default:
				break;
		}
		return null;
	}		
}

