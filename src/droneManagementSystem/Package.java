package droneManagementSystem;

import java.awt.Point;

public class Package {
	
	private int weight;
	private Point dest;
	
	
	public Package(int weight, Point dest) {
		super();
		this.weight = weight;
		this.dest = dest;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public Point getDest() {
		return dest;
	}
	public void setDest(Point dest) {
		this.dest = dest;
	}
	
	

}
