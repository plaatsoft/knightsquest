package nl.plaatsoft.knightsquest.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.scene.image.Image;
import nl.plaatsoft.knightsquest.tools.MyFactory;
import nl.plaatsoft.knightsquest.tools.MyRandom;
import nl.plaatsoft.knightsquest.ui.Constants;

public class BuildingDAO {

	final private static Logger log = Logger.getLogger(BuildingDAO.class);
	
	private List <Land> buildings = new ArrayList<Land>();	
	private Image habor;
			
	public void init(int size) {
		habor = new Image("images/habor.png", size+4, size+4, false, false);
	}
	
	public Image get(BuildingEnum type) {
		
		switch(type) {
	
			case HARBOR: 
				return habor;
				
			default:
				return null;
		}
	}
	
	private boolean checkHarborDistance(Land land) {
		
		/* Check if distance between two habors is at least to land segments */
		
		if ((land.getBuilding()!=null) && (land.getBuilding().getType()==BuildingEnum.HARBOR)) {				
		 	 return false;
		}
				
		List <Land> list1 = MyFactory.getLandDAO().getNeigbors2(land);
		
		Iterator<Land> iter1 = list1.iterator();
						
		while (iter1.hasNext()) {				
			Land land1 = (Land) iter1.next();
			if ((land1.getBuilding()!=null) && (land1.getBuilding().getType()==BuildingEnum.HARBOR)) {				
			 	 return false;
			}
		}	
		return true;
	}
	
	public void createHarbors() {
				
		List <Land> list2 = new ArrayList<Land>();
		
		for (int x=0; x<Constants.SEGMENT_X; x++) {
			for (int y=0; y<Constants.SEGMENT_Y; y++) {
			
				if (MyFactory.getLandDAO().getLands()[x][y].getType()==LandEnum.COAST) {	
								
					list2.add(MyFactory.getLandDAO().getLands()[x][y]);				
				}
			}			
		}
						
		for (int i=0; i<Constants.MAX_HABORS; i++) {
					
			boolean created = false;
			
			while (!created) {
				Land land = MyRandom.nextLand(list2);
				if (checkHarborDistance(land)) {
										
					Building building = new Building(BuildingEnum.HARBOR, land);
					land.setBuilding(building);
					//log.info("create ["+land.getX()+","+land.getY()+"]");
					
					buildings.add(land);		
					
					created = true;
				};
			}
		}			
	}
	
	public List<Land> getFreeHarbor(Land land) {
				
		List <Land> list2 = new ArrayList<Land>();
		
		Iterator<Land> iter1 = buildings.iterator();			
		while (iter1.hasNext()) {				
			Land land1 = (Land) iter1.next();
			if ((land1.getSoldier()==null) || (land.getSoldier().getType().getValue()>land1.getSoldier().getType().getValue())) {
				list2.add(land1);
			}
		}
		return list2;
	}
			

	public List<Land> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<Land> buildings) {
		this.buildings = buildings;
	}
}
