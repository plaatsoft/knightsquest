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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

public class SettingDAO {

	/** The log. */
	private final static Logger log = Logger.getLogger(SettingDAO.class.getName());

	private static Setting settings = new Setting();
	private static String filename = System.getProperty("user.home")+"/KnightsQuest.dat";

	public void save() {

		log.info("save settings "+filename);

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(settings);
			oos.close();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public void load() {

		log.info("load settings "+filename);

		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);
			settings = (Setting) ois.readObject();

			ois.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			//e.printStackTrace();
		}
	}

	public Setting getSettings() {
		return settings;
	}

	public void setSettings(Setting settings) {
		SettingDAO.settings = settings;
	}
}
