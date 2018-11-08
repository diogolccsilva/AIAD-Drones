package deliveryPackage;

import java.awt.geom.Point2D;

import client.Client;

public class DeliveryPackage {

	private Client receiver, sender;
	private float weight;
	private float size; //size in L or dm3
	
	public DeliveryPackage(Client sender, Client receiver, float weight, float size) {
		this.sender = sender;
		this.receiver = receiver;
		this.weight = weight;
		this.size = size;
	}
	
	public Point2D getDestination() {
		return sender.getLocation();
	}
	
	public Point2D getSource() {
		return receiver.getLocation();
	}
	
	public float getWeight() {
		return weight;
	}
	
	public float getSize() {
		return size;
	}

}
