package nl.plaatsoft.knightsquest.tools;

import java.util.ArrayList;
import java.util.List;

public class MyPlayer {

	String name;
	int number;	
	
	List <MyTown> towns = new ArrayList<MyTown>() ;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public List<MyTown> getTowns() {
		return towns;
	}
	
	public void setTowns(List<MyTown> towns) {
		this.towns = towns;
	}	
}
