package warehouse;

import java.awt.Point;

import jade.core.Agent;

public class Warehouse extends Agent {

	private Point location;
	
	public void setup(){
		System.out.println("Warehouse created");
		Object[] args = getArguments();
        //double xPostion = args[0].;
	}
	
	public void takeDown() {
		System.out.println(getLocalName() + ": warehouse killed");
	}
	
	public Point getLocation() {
		return location;
	}

}
