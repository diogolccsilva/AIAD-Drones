package warehouse;

import java.awt.geom.Point2D;

import jade.core.Agent;

public class Warehouse extends Agent {

	private Point2D location;
	
	public void setup(){
		System.out.println(getLocalName() + ": warehouse created");
		Object[] args = getArguments();
        double xPosition = Double.parseDouble(args[0].toString());
        double yPosition = Double.parseDouble(args[1].toString());
        this.location = new Point2D.Double(xPosition,yPosition);
	}
	
	public void takeDown() {
		System.out.println(getLocalName() + ": warehouse killed");
	}
	
	public Point2D getLocation() {
		return location;
	}

}
