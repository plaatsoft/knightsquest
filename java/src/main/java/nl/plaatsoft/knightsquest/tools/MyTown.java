package nl.plaatsoft.knightsquest.tools;

import java.util.ArrayList;
import java.util.List;

public class MyTown {

	String name;	
	List <MySegment> segments = new ArrayList<MySegment>();
	int x;
	int y;

	public int getSize() {
		return segments.size();
	}

	public MyTown(String name, int x, int y) {
		super();
		this.name = name;
		this.x = x;
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public List<MySegment> getSegments() {
		return segments;
	}

	public void setSegments(List<MySegment> segments) {
		this.segments = segments;
	}

}
