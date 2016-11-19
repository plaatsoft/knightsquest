package nl.plaatsoft.knightsquest.tools;

import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MySwitch extends HBox {

	private final Label label = new Label();
	private final Button button = new Button();

	private SimpleBooleanProperty switchedOn;

	public SimpleBooleanProperty switchOnProperty() {
		return switchedOn;
	}

	private void init() {
		
		switchedOn = new SimpleBooleanProperty(MyFactory.getConfig().isMusicEnabled());
		
		if (switchedOn.get()) {
			label.setText("ON");
			setAlignment(Pos.CENTER_LEFT);
			label.toFront();
		} else {
			label.setText("OFF");
			setAlignment(Pos.CENTER_RIGHT);
			label.toFront();
		}
		
		getChildren().addAll(label, button);
		button.setOnAction((e) -> {
			switchedOn.set(!switchedOn.get());
		});
		label.setOnMouseClicked((e) -> {
			switchedOn.set(!switchedOn.get());
		});
		setStyle();
		bindProperties();
	}

	private void setStyle() {
		// Default Width
		setWidth(80);
		label.setAlignment(Pos.CENTER);
		setStyle("-fx-background-color: grey; -fx-text-fill:black; -fx-background-radius: 4;");
		//setAlignment(Pos.CENTER_LEFT);
	}

	private void bindProperties() {
		label.prefWidthProperty().bind(widthProperty().divide(2));
		label.prefHeightProperty().bind(heightProperty());
		button.prefWidthProperty().bind(widthProperty().divide(2));
		button.prefHeightProperty().bind(heightProperty());
	}

	public MySwitch(int x, int y) {
		init();
		
		setLayoutX(x);
		setLayoutY(y);
		
		switchedOn.addListener((a, b, c) -> {
			if (!c) {
				label.setText("OFF");
				label.toFront();
				MyFactory.getConfig().setMusicEnabled(false);
				MyMusic.stop();
			} else {
				MyFactory.getConfig().setMusicEnabled(true);
				label.setText("ON");
				button.toFront();
				MyMusic.play();
			}
		});
	}
}