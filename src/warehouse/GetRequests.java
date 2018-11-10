package warehouse;

import java.awt.geom.Point2D;
import java.util.Random;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import deliveryPackage.DeliveryPackage;

import drone.Drone;

public class GetRequests extends CyclicBehaviour {
	
	public void action() {

		MessageTemplate mt1 = MessageTemplate.MatchPerformative(ACLMessage.CFP);
		MessageTemplate mt2 = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
		ACLMessage msg = myAgent.receive(mt1);

		if (msg != null) {
			// CFP Message received. Process it
			ACLMessage reply = msg.createReply();

			String received = msg.getContent();
			//System.out.println("Warehouse received request with message:"+received);

			double weight=99;
			Point2D coords = null;
			
			//AID drone =msg.getSender();
			
			try {
				coords = ((DeliveryPackage)msg.getContentObject()).getSource();

			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Point2D warehouseCoords = ((Warehouse)myAgent).getLocation();
			double distance;
			
			Random r = new Random();
			float chance = r.nextFloat();

			//random to sometimes refuse packages?
			if (chance<= 0.30f) {
				// The requested drone is available for sale. Reply with the distance
				distance = warehouseCoords.distance(coords);
				reply.setPerformative(ACLMessage.PROPOSE);
				reply.setContent(String.valueOf(distance));
			}
			else {
				// The requested drone is NOT available 
				reply.setPerformative(ACLMessage.REFUSE);
				reply.setContent("not-available");
				System.out.println("warehouse refused request");

			}
			myAgent.send(reply);
		}
		else if ((msg = myAgent.receive(mt2)) != null){
			ACLMessage reply = msg.createReply();

			// ACCEPT_PROPOSAL Message received. Process it
			
			// WAIT FOR THE PACKAGE TO BE DELIVERED 
			// NO BEHAVIOUR NEEDED i think
			// send INFORM in the end
			//reply.setPerformative(ACLMessage.INFORM);

			//myAgent.send(reply);

		}
		else {
			block();
		}
	
		
	}
	
}
