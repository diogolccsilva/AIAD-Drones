package drone;

import java.awt.geom.Point2D;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import deliveryPackage.DeliveryPackage;


public class GetRequests extends CyclicBehaviour {
	
	public void action() {

		MessageTemplate mt1 = MessageTemplate.MatchPerformative(ACLMessage.CFP);
		MessageTemplate mt2 = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
		ACLMessage msg = myAgent.receive(mt1);
		ACLMessage reply = msg.createReply();

		if (msg != null) {
			// CFP Message received. Process it
			
			String received = msg.getContent();
			System.out.println("Drone received request with message:"+received);

			double weight=99;
			Point2D coords = null;

			try {
				weight = ((DeliveryPackage)msg.getContentObject()).getWeight();
				coords = ((DeliveryPackage)msg.getContentObject()).getSource();

			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			float cap = ((Drone)myAgent).getWeightCapacity();
			Point2D droneCoords = ((Drone)myAgent).getCurrentPosition();
			double distance;
			
			if (weight<cap) {
				// The requested drone is available for sale. Reply with the distance
				distance = droneCoords.distance(coords);
				reply.setPerformative(ACLMessage.PROPOSE);
				reply.setContent(String.valueOf(distance));
			}
			else {
				// The requested drone is NOT available 
				reply.setPerformative(ACLMessage.REFUSE);
				reply.setContent("not-available");
			}
			myAgent.send(reply);
		}
		else if ((msg = myAgent.receive(mt2)) != null){
			// ACCEPT_PROPOSAL Message received. Process it
			
			// add working behavior to simulate drone moving
			// send INFORM in the end
			reply.setPerformative(ACLMessage.INFORM);

			myAgent.send(reply);

		}
		else {
			block();
		}
	
		
	}
	
}
