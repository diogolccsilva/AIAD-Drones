package deliveryPackage;

import java.awt.Point;

import client.Client;

public class Package {

	private Client receiver, sender;
	private float weight;
	private float size; //size in L or dm3
	
	public Package(Client sender, Client receiver, float weight, float size) {
		this.sender = sender;
		this.receiver = receiver;
		this.weight = weight;
		this.size = size;
	}
	
	public Point getDestination() {
		return sender.getLocation();
	}
	
	public Point getSource() {
		return receiver.getLocation();
	}
	
	public float getWeight() {
		return weight;
	}
	
	public float getSize() {
		return size;
	}

}
