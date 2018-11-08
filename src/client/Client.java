package client;

import java.awt.geom.Point2D;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;

public class Client extends Agent {

	private Point2D location;

	public void setup() {
		System.out.println("Client created");
		setAttributes(1, 1, "artigo");

		//addBehaviour();
	}

	public void takeDown() {
		System.out.println(getLocalName() + ": client killed");
	}

	public void setAttributes(Integer xPos, Integer yPos, String artc) {
		addBehaviour(new OneShotBehaviour() {
			public void action() {
				xPosition = xPos;
				yPosition = yPos;
				articleName = artc;
				System.out.println("Client lauched article " + articleName);
			}
		});
	}
	
	public Point2D getLocation() {
		return location;
	}

}
