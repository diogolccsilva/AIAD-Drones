package droneManagementSystem;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Client extends Agent {
	

	Integer xPosition;
	Integer yPosition;
	String articleName;
	
	private ClientGUI myGui;
	
	private String msg = "ENCOMENDA";
	
	
	// The list of known drone agents
	private AID[] drones;

	

	public void setup() {
		// Create and show the GUI 
			//	myGui = new ClientGUI(this);
			//	myGui.showGui();
		System.out.println("Client created");

		addBehaviour(new TickerBehaviour(this, 3000) {
			protected void onTick() {
				System.out.println("Looking to send a package");
				// Update the list of drone agents
				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("delivery-service");
				template.addServices(sd);
				try {
					DFAgentDescription[] result = DFService.search(myAgent, template); 
					System.out.println("Found the following drone agents:");
					 drones = new AID[result.length];
					for (int i = 0; i < result.length; ++i) {
						drones[i] = result[i].getName();
						System.out.println(drones[i].getName());
					}
				}
				catch (FIPAException fe) {
					fe.printStackTrace();
				}

				myAgent.addBehaviour(new RequestPerformer());

			}
		} );
		
	}
	
	public void takeDown() {
	
		System.out.println(getLocalName() + ": client killed");
	}
	
	
	
	/**
	   Inner class RequestPerformer.
	   This is the behaviour used by Book-buyer agents to request drone agents 
	 */
	private class RequestPerformer extends Behaviour {
		private AID bestDrone; // The agent who provides the best offer 
		private int bestDistance = Integer.MAX_VALUE;  // The best offered price
		private int repliesCnt = 0; // The counter of replies from seller agents
		private MessageTemplate mt; // The template to receive replies
		private int step = 0;

		public void action() {
			switch (step) {
			case 0:
				// Send the cfp to all drones
				ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
				for (int i = 0; i < drones.length; ++i) {
					cfp.addReceiver(drones[i]);
				} 
				cfp.setContent(msg);
				cfp.setConversationId("delivery");
				cfp.setReplyWith("cfp"+System.currentTimeMillis()); // Unique value
				myAgent.send(cfp);
				// Prepare the template to get proposals
				mt = MessageTemplate.and(MessageTemplate.MatchConversationId("delivery"),
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
						if (bestDrone == null || distance < bestDistance) {
							// This is the best offer at present
							bestDistance = distance;
							bestDrone = reply.getSender();
						}
					}
					repliesCnt++;
					if (repliesCnt >= drones.length) {
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
				order.addReceiver(bestDrone);
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
						System.out.println(msg+" successfully purchased from agent "+reply.getSender().getName());
						System.out.println("Distancia = "+bestDistance);
						myAgent.doDelete();
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
			if (step == 2 && bestDrone == null) {
				System.out.println("Attempt failed: "+msg);
			}
			return ((step == 2 && bestDrone == null) || step == 4);
		}
	}  // End of inner class RequestPerformer
	
	public void setAttributes(Integer xPos, Integer yPos, String artc) {
		addBehaviour(new OneShotBehaviour() {
			public void action() {
				xPosition = xPos;
				yPosition = yPos;
				articleName = artc;
				System.out.println("Client lauched article " + articleName);
			}
		} );
	}
	

}
