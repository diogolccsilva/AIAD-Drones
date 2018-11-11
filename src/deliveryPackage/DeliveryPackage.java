package deliveryPackage;

import java.awt.geom.Point2D;
import java.io.Serializable;

import client.Client;

public class DeliveryPackage implements Serializable, Comparable<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		return receiver.getLocation();
	}
	
	public Point2D getSource() {
		return sender.getLocation();
	}
	
	public double getWeight() {
		return weight;
	}
	
	public double getSize() {
		return size;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
