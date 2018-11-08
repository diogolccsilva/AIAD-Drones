package deliveryPackage;

import java.awt.Point;

import client.Client;

public class Package {

	private Client receiver, sender;
	private float weight;
	private float size; //size in L or dm3
	
	public Package() {
		
	}
	
	public Point getDestination() {
		return destination;
	}
	
	public float getWeight() {
		return weight;
	}
	
	public float getSize() {
		return size;
	}

}
