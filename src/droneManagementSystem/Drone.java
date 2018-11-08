package droneManagementSystem;


import java.awt.Point;
import java.awt.geom.Point2D;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Drone extends Agent {
	
	private int distance;
	private AID[] warehouses;
	
	private int capacity;

	
	private boolean working = false;
	
	private Point coords = new Point(1,1);
	

	public void setup() {
		
		System.out.println(getLocalName() + ": drone created--coords: "+coords);
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("delivery-service");
		sd.setName("AMAZON");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		addBehaviour(new ListeningBehaviour());

	}
	
	public void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		System.out.println(getLocalName() + ": drone killed");
	}
	
	private void setDroneInformation() {
      //todo
    }
	
	private class ListeningBehaviour extends CyclicBehaviour {
		
		public void action() {
			MessageTemplate mt1 = MessageTemplate.MatchPerformative(ACLMessage.CFP);
			MessageTemplate mt2 = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
			ACLMessage msg = myAgent.receive(mt1);
			if (msg != null) {
				// CFP Message received. Process it
				String title = msg.getContent();
				
				//System.out.println("msg received= "+title);

				String[] parts = title.split(",");
				
				double n1 = Double.parseDouble(parts[0]);
				double n2 = Double.parseDouble(parts[1]);
				double n3 = Double.parseDouble(parts[2]);
				double n4 = Double.parseDouble(parts[3]);

				
			      Point2D.Double p1 = new Point2D.Double(n1, n2);
				  Point2D.Double p2 = new Point2D.Double(n3, n4);
				   
				 distance=  (int) p1.distance(p2); // change this so it doesnt lose accuracy
				  
			      //System.out.println("distance="+distance);

				ACLMessage reply = msg.createReply();
				
				
				if (distance != 0 && !working) {
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
					System.out.println("Drone:"+myAgent.getName()+" escolhido");
					working =true;
					addBehaviour(new senderBehaviour(myAgent, 10000));
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
	
private class senderBehaviour extends TickerBehaviour {  // looks for warehouses to deliver the package to
	public Drone agent;
	public long period;


	public senderBehaviour(Agent a, long period) {
		super(a, period);
		this.agent = (Drone) a;
		this.period=period;
	}

	@Override
	protected void onTick() {
		System.out.println("Looking to deliver a package to a warehouse");
		// Update the list of warehouse agents
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("storing-service");
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(myAgent, template); 
			System.out.println(myAgent.getName()+"Found the following Warehouse agents to send to:");
			 warehouses = new AID[result.length];
			for (int i = 0; i < result.length; i++) {
				warehouses[i] = result[i].getName();
				System.out.println(warehouses[i].getName());
			}
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
			System.out.println("No warehouses found!");

		}

	}
		
	}


/**
   Inner class RequestPerformer.
   This is the behaviour used by client-buyer agents to request warehouse agents 
 */
private class RequestPerformer extends Behaviour {
	private AID bestWarehouse; // The agent who provides the best offer 
	private double bestDistance = Double.MAX_VALUE;  // The best offered price
	private int repliesCnt = 0; // The counter of replies from seller agents
	private MessageTemplate mt; // The template to receive replies
	private int step = 0;
	
	private String msg =" looking for warehouse";

	public void action() {
		switch (step) {
		case 0:
			// Send the cfp to all drones
			ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
			for (int i = 0; i < warehouses.length; ++i) {
				cfp.addReceiver(warehouses[i]);
			} 
			cfp.setContent(msg);
			cfp.setConversationId("send");
			cfp.setReplyWith("cfp"+System.currentTimeMillis()); // Unique value
			myAgent.send(cfp);
			// Prepare the template to get proposals
			mt = MessageTemplate.and(MessageTemplate.MatchConversationId("send"),
					MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
			step = 1;
			break;
		case 1:
			// Receive all proposals/refusals from drone agents
			ACLMessage reply = myAgent.receive(mt);
			if (reply != null) {
				// Reply received
				if (reply.getPerformative() == ACLMessage.PROPOSE) {
					// This is an offer 
					int distance = Integer.parseInt(reply.getContent());
					System.out.println("Resposta do drone distancia: "+ distance);
					if (bestWarehouse == null || distance < bestDistance) {
						// This is the best offer at present
						bestDistance = distance;
						bestWarehouse = reply.getSender();
					}
				}
				repliesCnt++;
				if (repliesCnt >= warehouses.length) {
					// We received all replies
					step = 2; 
				}
			}
			else {
				block();
			}
			break;
		case 2:
			// Send the purchase order to the seller that provided the best offer
			ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
			order.addReceiver(bestWarehouse);
			order.setContent(msg);
			order.setConversationId("delivery");
			order.setReplyWith("order"+System.currentTimeMillis());
			myAgent.send(order);
			// Prepare the template to get the purchase order reply
			mt = MessageTemplate.and(MessageTemplate.MatchConversationId("delivery"),
					MessageTemplate.MatchInReplyTo(order.getReplyWith()));
			step = 3;
			break;
		case 3:      
			// Receive the purchase order reply
			reply = myAgent.receive(mt);
			if (reply != null) {
				// Purchase order reply received
				if (reply.getPerformative() == ACLMessage.INFORM) {
					// Purchase successful. We can terminate
					System.out.println(myAgent.getName()+" successfully called from agent "+reply.getSender().getName()+"com Distancia = "+bestDistance);
					//myAgent.doDelete();
				}
				else {
					System.out.println("Attempt failed.");
				}

				step = 4;
			}
			else {
				block();
			}
			break;
		}        
	}

	public boolean done() {
		if (step == 2 && bestWarehouse == null) {
			System.out.println("Attempt failed: "+msg);
		}
		return ((step == 2 && bestWarehouse == null) || step == 4);
	}
}  // End of inner class RequestPerformer


public int getDistance() {
	return distance;
}

public void setDistance(int distance) {
	this.distance = distance;
}

public AID[] getWarehouses() {
	return warehouses;
}

public void setWarehouses(AID[] warehouses) {
	this.warehouses = warehouses;
}

public int getCapacity() {
	return capacity;
}

public void setCapacity(int capacity) {
	this.capacity = capacity;
}

public boolean isWorking() {
	return working;
}

public void setWorking(boolean working) {
	this.working = working;
}

public Point getCoords() {
	return coords;
}

public void setCoords(Point coords) {
	this.coords = coords;
}

	
	

}
