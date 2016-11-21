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

package nl.plaatsoft.knightsquest.tools;

import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;

public class MyScrollBar extends ScrollBar {

	public MyScrollBar(int max) {
		setMin(0);
		setMax(max - 1);
		setValue(0);
		setUnitIncrement(1);
		setBlockIncrement(1);
		setLayoutX(MyFactory.getSettingDAO().getSettings().getWidth()-50);
		setLayoutY(125);
		setMinWidth(25);
		setMinHeight(MyFactory.getSettingDAO().getSettings().getHeight()-205);
		setOrientation(Orientation.VERTICAL);
	}
	
}
