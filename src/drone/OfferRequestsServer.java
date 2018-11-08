package drone;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class OfferRequestsServer extends CyclicBehaviour {
	
	public void action() {
		MessageTemplate mt1 = MessageTemplate.MatchPerformative(ACLMessage.CFP);
		MessageTemplate mt2 = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
		ACLMessage msg = myAgent.receive(mt1);
		if (msg != null) {
			// CFP Message received. Process it
			String msgPos[] = msg.getContent().split(";", 2);
			ACLMessage reply = msg.createReply();
			int clientPos[] = new int[2];
			for (int i = 0; i < clientPos.length; i++)
				clientPos[i] = Integer.parseInt(msgPos[i]);
			
			double xPosition = ((Drone)this.myAgent).getCurrentPosition().getX();
			double yPosition = ((Drone)this.myAgent).getCurrentPosition().getY();
			
			double distance = Math.sqrt(Math.pow(xPosition - clientPos[0], 2) + Math.pow(yPosition - clientPos[1], 2));
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
}
