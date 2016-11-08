package nl.plaatsoft.knightsquest.model;

public enum SoldierType {
	
	TOWER(0),
	CROSS(1),
	
	SOLDIER(2),
	BISHOP(3),
	HORSE(4),
	QUEEN(5),
	KING(6);	
	
	private final int value;
	
    private SoldierType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
