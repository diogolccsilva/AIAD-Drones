package client;

import java.awt.geom.Point2D;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;

public class Client extends Agent {

	private Point2D location;

	public void setup() {
		System.out.println("Client created");

		//addBehaviour();
	}

	public void takeDown() {
		System.out.println(getLocalName() + ": client killed");
	}
	
	public Point2D getLocation() {
		return location;
	}

}
