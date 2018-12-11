package client;

import java.awt.geom.Point2D;
import java.util.Random;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import deliveryPackage.DeliveryPackage;
import drone.Drone;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.util.leap.ArrayList;

public class Client extends Agent {

	private Point2D location;
	private Vector<DeliveryPackage> deliveries =  new Vector<DeliveryPackage>();
	private int numberPackages;
	//AID[] drones;


	public void setup() {
		//System.out.println(getLocalName() + ": client created");
		setClientInformation();
		//System.out.println(location + ": client location");

		generatePackages();
		int rPeriod = ThreadLocalRandom.current().nextInt(1000,1500);
		
			//System.out.println(getLocalName() +": "+deliveries.size() +":  pacotes");
			//System.out.println(getLocalName() + deliveries[0]().getWeight());

		addBehaviour(new GenerateRequestsBehaviour(this, rPeriod));
	}
		
	private void setClientInformation() {
		
			int x = (int) getArguments()[1];
			int y = (int) getArguments()[2];
			numberPackages = (int)getArguments()[3];
					
			double dx = (double) x;
			double dy = (double) y;
			
			Point2D pa = new Point2D.Double(dx, dy);
			
			setLocation(pa);
	        	       
	    }

	public void generatePackages(){
		
		int rangeMin=30;
		int rangeMax=0;
		
		for(int i=0;i<numberPackages;i++){
			Random rx = new Random();
			Random ry = new Random();
	
			double randomX = rangeMin + (rangeMax - rangeMin) * rx.nextDouble();
			double randomY = rangeMin + (rangeMax - rangeMin) * ry.nextDouble();
			double randomweight = (rangeMin + (rangeMax - rangeMin) * ry.nextDouble())/2.5;
	
			Point2D point = new Point2D.Double(randomX,randomY);
		    Client c1 = new Client ();
		    c1.setLocation(point);
			DeliveryPackage pp1= new DeliveryPackage(this, c1, randomweight, i);
			///this.addDelivery(pp1);
			//del.add(pp1);
			
			addDelivery(pp1);
			
		}
		//setDeliveries(del);
	
	}

	public void takeDown() {
		//System.out.println(getLocalName() + ": client killed");
	}
	
	public Point2D getLocation() {
		return location;
	}


	public void setLocation(Point2D location) {
		this.location = location;
	}
	public Vector<DeliveryPackage> getDeliveries() {
		return deliveries;
	}
	
	public void setDeliveries(Vector<DeliveryPackage> deliveries) {
		this.deliveries = deliveries;
	}
	
	public void addDelivery(DeliveryPackage dp) {
		deliveries.add(dp);
	}
	public void removeDelivery(DeliveryPackage dp) {
		this.deliveries.remove(dp);
	}


	
	

}
