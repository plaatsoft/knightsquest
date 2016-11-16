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

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.CacheHint;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import nl.plaatsoft.knightsquest.model.Land;
import nl.plaatsoft.knightsquest.model.LandEnum;
import nl.plaatsoft.knightsquest.model.Soldier;
import nl.plaatsoft.knightsquest.model.SoldierEnum;
import nl.plaatsoft.knightsquest.utils.Constants;
import nl.plaatsoft.knightsquest.utils.LandUtils;

public class MyImageView extends ImageView {

	final static Logger log = Logger.getLogger(MyImageView.class);

	private double origX = -1;
	private double origY = -1;
	private Soldier soldier;
	private double offsetX;
	private double offsetY;

	public MyImageView(double x, double y, Image image, double scale, Soldier soldier) {

		this.setSoldier(soldier);
		
		setImage(image);
		setLayoutX(x);
		setLayoutY(y);
		setScaleX(scale);
		setScaleY(scale);
				
		setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				log.info(soldier.getType()+" ["+soldier.getLand().getX()+","+soldier.getLand().getY()+"] selected");
				
				if ((soldier.getType()!=SoldierEnum.CROSS) && (soldier.getType()!=SoldierEnum.TOWER)) {
					origX = getLayoutX();
					origY = getLayoutY();
					
					offsetX = me.getSceneX() - getLayoutX();
					offsetY = me.getSceneY() - getLayoutY();
					
					List <Land> list1 = LandUtils.getNeigbors(soldier.getLand().getX(), soldier.getLand().getY());
					Iterator<Land> iter1 = list1.iterator();
									
					while (iter1.hasNext()) {				
						Land land = (Land) iter1.next();
						if (
							(land.getType()!=LandEnum.WATER) &&  
							(land.getType()!=LandEnum.OCEAN) //&& 
						   // ((land.getSoldier()!=null) && !soldier.getPlayer().equals(land.getPlayer()) &&
						   // (land.getSoldier().getType()!=SoldierEnum.TOWER)) 
						   ) {
							  //land.drawSelect();
						}
					}	
				}
			}
		});
		
		setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {

				if ((soldier.getType()!=SoldierEnum.CROSS) && (soldier.getType()!=SoldierEnum.TOWER)) {
					double tmpX = me.getSceneX() - offsetX;
					double tmpY = me.getSceneY() - offsetY;

					setLayoutX(tmpX);
					setLayoutY(tmpY);
				}
			}
		});
		
		setOnMouseReleased(new EventHandler<MouseEvent>() {;
			public void handle(MouseEvent me) {
				
				if ((soldier.getType()!=SoldierEnum.CROSS) && (soldier.getType()!=SoldierEnum.TOWER)) {
					
					
					List <Land> list1 = LandUtils.getNeigbors(soldier.getLand().getX(), soldier.getLand().getY());
					Iterator<Land> iter1 = list1.iterator();
									
					while (iter1.hasNext()) {				
						Land land = (Land) iter1.next();
						if (
							(land.getType()!=LandEnum.WATER) &&  
							(land.getType()!=LandEnum.OCEAN) //&& 
						   // ((land.getSoldier()!=null) && !soldier.getPlayer().equals(land.getPlayer()) &&
						   // (land.getSoldier().getType()!=SoldierEnum.TOWER)) 
						   ) {

							  Point2D point = new Point2D(me.getSceneX(), me.getSceneY());
					           if (land.getPolygon().contains(point)) {
					               System.out.println("land "+land.getX()+","+land.getY());
					               
					               moveSoldier(soldier.getLand(),land);
					               return;
					           }
							
							  soldier.getPlayer().draw();
						}
					}
					
					setLayoutX(origX);
					setLayoutY(origY);
				}
			}
		});
	}

	
	private void moveSoldier(Land a, Land b) {
		
		b.setSoldier(a.getSoldier());
		b.setPlayer(a.getPlayer());
		a.getSoldier().setLand(b);
		a.setSoldier(null);		
	}

	public MyImageView(double x, double y, Image image, double scale, boolean invert) {

		ColorAdjust blackout = new ColorAdjust();
		blackout.setBrightness(1.0);

		setImage(image);
		setLayoutX(x);
		setLayoutY(y);
		setScaleX(scale);
		setScaleY(scale);

		setEffect(blackout);
		setCache(true);
		setCacheHint(CacheHint.SPEED);
	}

	public MyImageView(double x, double y, String resource, double scale) {

		Image image = new Image(resource);
		setImage(image);
		setLayoutX(x);
		setLayoutY(y);
		setScaleX(scale);
		setScaleY(scale);
	}

	public void move(double offsetX, double offsetY) {

		//log.info("move [offset=" + offsetX + " offsetY=" + offsetY+" x="+(origX+offsetX)+" y="+(origY + offsetY)+"]");

		if ((this.origX == -1) && (this.origY == -1)) {
			this.origX = getLayoutX();
			this.origY = getLayoutY();
		}

		setLayoutX(origX + offsetX);
		setLayoutY(origY + offsetY);
	}

	public void setPosition(double x, double y) {

		int size = Constants.SEGMENT_SIZE;
		
		int offset = 0;
		if ((y % 2)==1) {
			offset = size*2;
		} 
	             	
		double posX = size+(x*(size*4))+offset-2;
		double posY = (y*size)+(size/2)-2;
				
		log.info("setPosition [x="+posX+" y="+posY+"]");

		setLayoutX(posX);
		setLayoutY(posY);
	}

	public Soldier getSoldier() {
		return soldier;
	}

	public void setSoldier(Soldier soldier) {
		this.soldier = soldier;
	}
}
