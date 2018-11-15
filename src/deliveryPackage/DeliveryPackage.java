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
	private int id;
	
	private boolean inTransit;
	
	public DeliveryPackage(Client sender, Client receiver, double weight, int id) {
		this.sender = sender;
		this.receiver = receiver;
		this.weight = weight;
		this.id = id;
		this.inTransit=false;
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
	
	public String getOwner(){
		return sender.getLocalName();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isInTransit() {
		return inTransit;
	}

	public void setInTransit(boolean inTransit) {
		this.inTransit = inTransit;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
