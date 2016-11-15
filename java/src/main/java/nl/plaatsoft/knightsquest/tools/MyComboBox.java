package nl.plaatsoft.knightsquest.tools;

import javafx.scene.control.ComboBox;

public class MyComboBox extends ComboBox<Object> {

	public MyComboBox(int x, int y, String selected, String[] options ) {
	
		setLayoutX(x);
		setLayoutY(y);
		
		setValue(selected);
		
		for (int i=0; i<options.length; i++) {
			getItems().add(options[i]);
		}
	};       
}
