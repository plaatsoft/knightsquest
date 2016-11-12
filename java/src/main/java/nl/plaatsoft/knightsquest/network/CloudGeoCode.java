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

public class CloudGeoCode {

	final static Logger log = Logger.getLogger( CloudScore.class);
	
	static private String country;
	static private String city;
			
	static private void fetch() {
		
		String url = "http://freegeoip.net/json";
			
		log.info(url);		
		String json = CloudUtils.executeGet(url);
		log.info(json);
		
		try {
			JSONObject obj = new JSONObject(json);
			country = obj.getString("country_code");
			city = obj.getString("city");
				
		} catch (Exception e) {
			log.error(e.getMessage());
		}		
	}

	static public String getCountry() {
		
		if (country==null) {
			fetch();
		}
		return country.toLowerCase();
	}

	static public String getCity() {
		if (city==null) {
			fetch();
		}
		return city.toLowerCase();
	}
}
