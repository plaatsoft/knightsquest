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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import nl.plaatsoft.knightsquest.model.Score;
import nl.plaatsoft.knightsquest.tools.MyFactory;
import nl.plaatsoft.knightsquest.ui.Constants;

public class CloudScore {

	final static Logger log = Logger.getLogger( CloudScore.class);
	
	public static void set(String product, String version, Score score) {
					
		String parameters;
		parameters  = "action=setScore&";
		parameters += "pid=" + CloudProduct.getPid()+ "&";
		parameters += "uid=" + CloudUser.getUid()  + "&";
		// Remove milli seconds 
		parameters += "dt=" + (score.getTimestamp().getTime()/1000) + "&";
		parameters += "score=" + score.getScore() + "&";
		parameters += "level=" + score.getLevel();
		
		log.info(Constants.APP_WS_URL+ " "+parameters);
		String result = CloudUtils.executePost("https://"+Constants.APP_WS_URL, parameters);
		log.info(result);
	}
	
	public static void getLocal() {
		
		String parameters;
		parameters  = "action=getLocalScore&";
		parameters += "pid=" + CloudProduct.getPid() + "&";
		parameters += "uid=" + CloudUser.getUid();
		
		log.info(Constants.APP_WS_URL+ " "+parameters);
		String json = CloudUtils.executePost("https://"+Constants.APP_WS_URL, parameters);
		log.info(json);
		
		try {
			JSONArray jsonarray = new JSONArray(json);
			for (int i = 0; i < jsonarray.length(); i++) {
			    JSONObject jsonobject = jsonarray.getJSONObject(i);
			    String dt = jsonobject.getString("dt");
			    int points = jsonobject.getInt("score");
			    int level = jsonobject.getInt("level");
			    String nickname = "";
			    String country = "";
			    
			    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    Date date = df.parse(dt);
			    
				Score score = new Score(date, points, level, nickname, country);
				MyFactory.getScoreDAO().addLocal(score);  	   
			}			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public static void getGlobal() {
		
		String parameters;
		parameters  = "action=getGlobalScore&";
		parameters += "pid=" + CloudProduct.getPid();
		
		log.info(Constants.APP_WS_URL+ " "+parameters);
		String json = CloudUtils.executePost("https://"+Constants.APP_WS_URL, parameters);
		log.info(json);
						
		try {
			JSONArray jsonarray = new JSONArray(json);
			for (int i = 0; i < jsonarray.length(); i++) {
			    JSONObject jsonobject = jsonarray.getJSONObject(i);
			    String dt = jsonobject.getString("dt");
			    int points = jsonobject.getInt("score");
			    int level = jsonobject.getInt("level");
			    String nickname = jsonobject.getString("nickname");
			    String country = jsonobject.getString("country");
			    			    
			    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    Date date = df.parse(dt);
			    
				Score score = new Score(date, points, level, nickname, country);
				MyFactory.getScoreDAO().addGlobal(score);   
			}			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}