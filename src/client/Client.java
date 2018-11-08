package client;

import java.awt.geom.Point2D;
import java.util.concurrent.ThreadLocalRandom;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;

public class Client extends Agent {

	private Point2D location;

	public void setup() {
		System.out.println("Client created");

		int rPeriod = ThreadLocalRandom.current().nextInt(1000,1500);
		
		addBehaviour(new GenerateRequestsBehaviour(this, rPeriod));
	}

	public void takeDown() {
		System.out.println(getLocalName() + ": client killed");
	}
	
	public Point2D getLocation() {
		return location;
	}

}
