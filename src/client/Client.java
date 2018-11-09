package client;

import java.awt.geom.Point2D;
import java.util.concurrent.ThreadLocalRandom;

import drone.Drone;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;

public class Client extends Agent {

	private Point2D location;
	
	//AID[] drones;


	public void setup() {
		System.out.println(getLocalName() + ": client created");
		setClientInformation();
		//System.out.println(location + ": client location");


		int rPeriod = ThreadLocalRandom.current().nextInt(1000,1500);
		
		addBehaviour(new GenerateRequestsBehaviour(this, rPeriod));
	}
	
	
private void setClientInformation() {
		
			int x = (int)getArguments()[1];
			int y = (int)getArguments()[2];
					
			double dx=(double)x;
			double dy=(double)y;
			
			Point2D pa = new Point2D.Double(dx, dy);
			
			setLocation(pa);
	        
	       
	    }

	public void takeDown() {
		System.out.println(getLocalName() + ": client killed");
	}
	
	public Point2D getLocation() {
		return location;
	}


	public void setLocation(Point2D location) {
		this.location = location;
	}


	
	

}
