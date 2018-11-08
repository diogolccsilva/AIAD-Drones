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
		
		
        if (msg!=null) {
        	// message received -->process it 
			String received = msg.getContent();
			System.out.println("Drone received request with message:"+received);
			ACLMessage reply = msg.createReply();

			float weight=99;
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
			double result;
			
			if(weight<cap){
				result = droneCoords.distance(coords);
					// The requested book is available for sale. Reply with the distance
					reply.setPerformative(ACLMessage.PROPOSE);
					reply.setContent(String.valueOf(result));
				}
			}
			else{
				// the drone has no capacity for transporting the order
				reply.setPerformative(ACLMessage.REFUSE);
				reply.setContent("No capacity for transporte");
			}

        	
        }
        else if((msg = myAgent.receive(mt2)) != null){
        	// ACCEPT_PROPOSAL Message received. Process it
        	
        	// Procedimento do transporte e no fim enviar INFORM
        	
        	/**
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
			*/
        	
        }
        else {
			block();
		}
		
	}
	
	public void getDistance(){
		
		
		
		
	}
		
		
		
		
		
		
		
		
		
		
		
	
		/**
		
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
	}*/
}
