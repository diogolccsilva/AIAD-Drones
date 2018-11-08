package drone;

import java.awt.geom.Point2D;
import java.util.TreeSet;

import request.*;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class Drone extends Agent {
	
	private TreeSet<Request> requests;
	
	private Point position;
	private float weightCapacity;
	private float baseVelocity;

	public void setup() {
		
		System.out.println(getLocalName() + ": drone created");
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("delivery-service");
		sd.setName("AMAZON");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		Random r = new Random();
		xPosition = r.nextInt(20);
		yPosition = r.nextInt(20);
		
		addBehaviour(new OfferRequestsServer());

	}
	
	public void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		System.out.println(getLocalName() + ": drone killed");
	}

	public Point getCurrentPosition() {
		return position;
	}
	
	public float getWeightCapacity() {
		return weightCapacity;
	}

}
