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

package nl.plaatsoft.knightsquest.model;

import java.util.Comparator;

import nl.plaatsoft.knightsquest.model.Score;

public class ScoreSort implements Comparator<Score> {

	public int compare(Score score1, Score score2) {

		int sc1 = score1.getScore();
		int sc2 = score2.getScore();

		if (sc1 > sc2) {
			return -1;
		} else if (sc1 < sc2) {
			return +1;
		} else {
			return 0;
		}
	}
}