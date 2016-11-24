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
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import nl.plaatsoft.knightsquest.tools.MyButton;
import nl.plaatsoft.knightsquest.tools.MyFactory;
import nl.plaatsoft.knightsquest.tools.MyLabel;
import nl.plaatsoft.knightsquest.tools.MyPanel;
import nl.plaatsoft.knightsquest.tools.MyScrollBar;

public class ReleaseNotes extends MyPanel {

	private static String[] version = {

			"24-11-2016 (Version 0.4)\n" 
					+ "- Added Mode Selector page.\n"
					+ "- Added Communication page.\n",
					
			"23-11-2016 (Version 0.3)\n" 
					+ "- Added 3 screen resolutions.\n"
					+ "- Increase amount of maps to 60.\n"
					+ "- Improve game play. Now maps must be unlocked.\n"
					+ "- Pawn can now move two land tills per turn.\n"
					+ "- Added harbors so soldiers can travel faster.\n"
					+ "- Game settings and progress are now stored on disk.\n"
					+ "- Bugfix: Second game initialization is working fine.\n"
					+ "- Bugfix: Now autmatic next turn detection is working fine\n",
			
			"18-11-2016 (Version 0.2)\n" 					
					+ "- Add information boxes on game screen\n"
					+ "- Improve bots behalvior\n"
					+ "- Added new background.\n" 
					+ "- Improve help, credit, donate page.\n" 
					+ "- Added intro movie.\n"
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

		MyScrollBar s1 = new MyScrollBar(version.length);
		s1.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				text.setText(version[new_val.intValue()]);
			}
		});

		getChildren().add(s1);

		getChildren().add(new MyLabel(0, 20, "Release Notes", 50, "white", "-fx-font-weight: bold;"));
		text = new MyLabel(30, 120, version[0], 20, "white");
		getChildren().add(text);
		getChildren().add(new MyButton(0, MyFactory.getSettingDAO().getSettings().getHeight()-60, "Close", 18, Navigator.HOME));
	}

	@Override
	public void draw() {
		
	}
}
