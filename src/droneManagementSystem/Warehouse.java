package droneManagementSystem;

import java.awt.Point;
import java.util.Vector;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Warehouse extends Agent {	
	
	private Point coords;
	
	private Vector<String> products;
	
	private double distance=12;
	
	

	
	public void setup() {
		this.coords = new Point (2,3);
		
		System.out.println(getLocalName() + ": Warehouse created at coords: "+coords);
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("storing-service");
		sd.setName("AMAZON");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		addBehaviour(new OfferRequestsServer());
	}
	
	public void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		System.out.println(getLocalName() + ": drone killed");
	}
	
	private class OfferRequestsServer extends CyclicBehaviour {
		public void action() {
			MessageTemplate mt1 = MessageTemplate.MatchPerformative(ACLMessage.CFP);
			MessageTemplate mt2 = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
			ACLMessage msg = myAgent.receive(mt1);
			if (msg != null) {
				// CFP Message received. Process it
				String title = msg.getContent();
				ACLMessage reply = msg.createReply();
				
				
				if (distance != 0) {
					// The requested book is available for sale. Reply with the distance
					reply.setPerformative(ACLMessage.PROPOSE);
					reply.setContent(String.valueOf(distance));
				}
				else {
					// The requested book is NOT available for sale.
					reply.setPerformative(ACLMessage.REFUSE);
					reply.setContent("not-available");
				}
				myAgent.send(reply);
			}
			else if ((msg = myAgent.receive(mt2)) != null){
				// ACCEPT_PROPOSAL Message received. Process it
				String title = msg.getContent();
				ACLMessage reply = msg.createReply();

				Integer price = 5;
				if (price != null) {
					reply.setPerformative(ACLMessage.INFORM);
					System.out.println(title+" drone para cliente "+msg.getSender().getName());
				}
				else {
					// The requested book has been sold to another buyer in the meanwhile .
					reply.setPerformative(ACLMessage.FAILURE);
					reply.setContent("not-available");
				}
				myAgent.send(reply);
			}
			else {
				block();
			}
		}
	}  // End of inner class OfferRequestsServer			



	public Point getCoords() {
		return coords;
	}

	public void setCoords(Point coords) {
		this.coords = coords;
	}


	public Vector<String> getProducts() {
		return products;
	}


	public void setProducts(Vector<String> products) {
		this.products = products;
	}
	
	
	
	

}
