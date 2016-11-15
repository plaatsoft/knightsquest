/**
 *  @file
 *  @brief 
 *  @author wplaat
 *
 *  Copyright (C) 2008-2016 PlaatSoft
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package nl.plaatsoft.knightsquest.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import nl.plaatsoft.knightsquest.tools.MyButton;
import nl.plaatsoft.knightsquest.tools.MyLabel;
import nl.plaatsoft.knightsquest.tools.MyPanel;

public class ReleaseNotes extends MyPanel {

	private static String[] version = {

			"15-11-2016 (Version 0.2)\n" 
					+ "- Added new background.\n" 
					+ "- Improve help page.\n" 
					+ "- Add intro movie.\n"
					+ "- Improve network detection.\n"
					+ "- Add setting option to switch off the music.\n",

			"12-11-2016 (Version 0.1)\n" 
					+ "- Added basic game engine (only bot mode for now).\n" 
					+ "- Added six maps.\n"
					+ "- Added intro background music.\n" 
					+ "- Added new version detection.\n"
					+ "- Added help, credits, release notes and donate page.\n"
					+ "- Added two intro pages with basic animation.\n" };

	private static MyLabel text;

	public ReleaseNotes() {

		Image image1 = new Image("images/background4.jpg");
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		BackgroundImage backgroundImage = new BackgroundImage(image1, BackgroundRepeat.REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		Background background = new Background(backgroundImage);

		setBackground(background);

		ScrollBar s1 = new ScrollBar();
		s1.setMin(0);
		s1.setMax(version.length - 1);
		s1.setValue(0);
		s1.setUnitIncrement(1);
		s1.setBlockIncrement(1);
		s1.setLayoutX(590);
		s1.setLayoutY(125);
		s1.setMinWidth(25);
		s1.setMinHeight(275);
		s1.setOrientation(Orientation.VERTICAL);

		s1.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				text.setText(version[new_val.intValue()]);
			}
		});

		getChildren().add(s1);

		getChildren().add(new MyLabel(0, 20, "Release Notes", 50, "white", "-fx-font-weight: bold;"));
		text = new MyLabel(30, 120, version[0], 20, "white");
		getChildren().add(text);
		getChildren().add(new MyButton(230, 420, "Close", 18, Navigator.HOME));
	}

	@Override
	public void draw() {
		
	}
}
