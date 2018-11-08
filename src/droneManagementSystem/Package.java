package droneManagementSystem;

import java.awt.Point;

public class Package {

	private Point destination;
	private float weight;
	private float size; //size in L or dm3
	
	public Package() {
		
	}
	
	public Point getDestination() {
		return destination;
	}
	
	public float getWeight() {
		return weight;
	}
	
	public float getSize() {
		return size;
	}

}
