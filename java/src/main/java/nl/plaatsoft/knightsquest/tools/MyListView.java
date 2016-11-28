package nl.plaatsoft.knightsquest.tools;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class MyListView extends ListView<Object> {

	@SuppressWarnings("unchecked")
	public MyListView(int x, int y, int width, int height, @SuppressWarnings("rawtypes") ObservableList items) {

		setLayoutX(x);
		setLayoutY(y);
		setMaxHeight(height);
		setMaxWidth(width);
	
		setItems(items);
	}	
}
