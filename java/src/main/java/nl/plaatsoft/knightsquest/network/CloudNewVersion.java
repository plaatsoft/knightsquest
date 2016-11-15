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

package nl.plaatsoft.knightsquest.network;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import nl.plaatsoft.knightsquest.utils.Constants;

public class CloudNewVersion {
	
	final static Logger log = Logger.getLogger( CloudNewVersion.class);
		
	public static String get() {

		String returnValue="";		
		String parameters = "action=getVersion&product="+Constants.APP_WS_NAME; 
						
		log.info(Constants.APP_WS_URL+ " "+parameters);
		String json = CloudUtils.executePost("https://"+Constants.APP_WS_URL, parameters);
		log.info(json);
		
		try {
			JSONObject obj = new JSONObject(json);
			String newVersion = obj.getString("product");
			if (Float.parseFloat(newVersion)>Float.parseFloat(Constants.APP_VERSION)) {
				returnValue = Constants.APP_NAME+" v"+newVersion+" available.";
			}
			
		} catch (Exception e) {
			returnValue = "";
			log.error(e.getMessage());			
		}

		return returnValue;			
	}
}
