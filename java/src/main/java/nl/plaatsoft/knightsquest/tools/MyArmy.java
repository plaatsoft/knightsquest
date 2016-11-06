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
	
	public static int getFoodNeeds(MyArmyEnum army) {
		
		if (army==null) {
			return 0;
		}
	
		switch(army) {
	
			case TOWER: 
				return 0;
		
			case SOLDIER:
				return 1;
				
			case HORSE:
				return 2;
				
			case BISHOP:
				return 3;
				
			case QUEEN:
				return 4;
				
			case KING:
				return 5;
				
			default:
				return 0;			
		}
	}		
}

