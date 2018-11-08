package warehouse;

import java.awt.Point;

import jade.core.Agent;

public class Warehouse extends Agent {

	private Point location;
	
	public Warehouse(Point location) {
		this.location = location;
	}
	
	public Point getLocation() {
		return location;
	}

}
