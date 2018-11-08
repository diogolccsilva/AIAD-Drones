package deliveryPackage;

import java.awt.geom.Point2D;

import client.Client;

public class DeliveryPackage {

	private Client receiver, sender;
	private double weight;
	private double  size; //size in L or dm3
	
	public DeliveryPackage(Client sender, Client receiver, double weight, double size) {
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
	
	public double getWeight() {
		return weight;
	}
	
	public double getSize() {
		return size;
	}

}