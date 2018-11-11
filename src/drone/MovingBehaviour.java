package drone;

import java.awt.geom.Point2D;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import deliveryPackage.DeliveryPackage;

public class MovingBehaviour extends Behaviour{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double m;
	private double b;
	private int i=0;
	
	public MovingBehaviour(double m, double b) {
		super();
		this.m = m;
		this.b = b;
		System.out.println("m -b :"+m+","+b);

	}

	public void action() {
		
		
		Point2D droneCoords = ((Drone)myAgent).getCurrentPosition();
		
		DeliveryPackage pac = ((Drone)myAgent).getPacote();
		
		//Point2D dest= pac.getDestination();
		Point2D src= pac.getSource();

		
		i++;
		Point2D newPos =  new Point2D.Double(droneCoords.getX()-2,m*(droneCoords.getX()-2)+b);
		((Drone)myAgent).setPosition(newPos);
		System.out.println("new drone coords: "+((Drone)myAgent).getCurrentPosition());
		System.out.println("client place src: "+ src);

	}

	public boolean done() {
		Point2D droneCoords = ((Drone)myAgent).getCurrentPosition();
		DeliveryPackage pac = ((Drone)myAgent).getPacote();
		Point2D dest= pac.getDestination();
		System.out.println( dest==droneCoords);
		return dest==droneCoords||i==5;
		
	}
}
