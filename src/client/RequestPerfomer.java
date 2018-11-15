package client;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Serializable;

import deliveryPackage.DeliveryPackage;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import request.Request;

public class RequestPerfomer extends Behaviour {
	
	
	private AID bestDrone; // The agent who provides the best offer
	private double bestDistance = Double.MAX_VALUE; // The best offer
	private int repliesCnt = 0; // The counter of replies from drone agents
	private MessageTemplate mt; // The template to receive replies
	private int step = 0;
	private AID[] drones;
	private String msg = "ENCOMENDA";
	private DeliveryPackage pacote;

	
	public RequestPerfomer(AID[] dronesFound, DeliveryPackage pp) {
		super();
		this.drones = dronesFound;
		this.pacote = pp;
	}

	@Override
	public void action() {
		
		
		switch (step) {
		case 0:
			// Send the cfp to all drones
			ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
			for (int i = 0; i < drones.length; ++i) {
				cfp.addReceiver(drones[i]);
			}
			cfp.setContent("looking to send a package");
			cfp.setConversationId("delivery");
			cfp.setReplyWith("cfp" + System.currentTimeMillis()); // Unique
			
			try {
				cfp.setContentObject( pacote);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//ADD PACKAGE
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
					double distance = Double.parseDouble(reply.getContent());
					//System.out.println("Resposta do drone distancia: " + distance);
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
			} else {
				block();
			}
			break;
		case 2:
			// Send the purchase order to the seller that provided the best
			// offer
			ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
			order.addReceiver(bestDrone);
			order.setContent("i accept --> distance:"+bestDistance);
			try {
				order.setContentObject(pacote);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			order.setConversationId("delivery");
			order.setReplyWith("order" + System.currentTimeMillis());
			
			pacote.setInTransit(true);
			
			myAgent.send(order);
			// Prepare the template to get the purchase order reply
			mt = MessageTemplate.and(MessageTemplate.MatchConversationId("delivery"),
					MessageTemplate.MatchInReplyTo(order.getReplyWith()));
			System.out.println(myAgent.getLocalName()+": picked drone:" + bestDrone.getLocalName());

			////////////////////////////// SEND REFUSE MESSAGE TO OTHERS ///////////////
			
			ACLMessage refuse = new ACLMessage(ACLMessage.REFUSE);
			for(int i=0;i<drones.length;i++){
				if(drones[i]!= bestDrone){
					refuse.addReceiver(drones[i]);
					refuse.setContent("nao obrigado");
					myAgent.send(refuse);
				}
				
			}
			////////////////////////////     END  ///////////////////////////////////


			step = 3;
			break;
		case 3:
			// Receive the purchase order reply
			reply = myAgent.receive(mt);
			if (reply != null) {
				if (reply.getPerformative() == ACLMessage.INFORM) {
					// Purchase successful. We can terminate
					System.out.println(myAgent.getLocalName() +" received Inform-done: " + reply.getSender().getLocalName()+" -->pacote entregue: "+pacote.getId());
					//myAgent.doDelete();
					 ((Client)myAgent).removeDelivery(pacote);

				} else if (reply.getPerformative() == ACLMessage.FAILURE){
					System.out.println(myAgent.getLocalName()+" Got FAIL from: " +reply.getSender().getLocalName());
				}

				step = 4;
			} else {
				block();
			}
			break;
		}		
	}

	@Override
	public boolean done() {
		if (step == 2 && bestDrone == null) {
			//System.out.println("Attempt failed: " + msg);
		}
		return ((step == 2 && bestDrone == null) || step == 4);
	}

}
