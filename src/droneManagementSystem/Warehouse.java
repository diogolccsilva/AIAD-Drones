package droneManagementSystem;

import java.awt.Point;
import java.awt.geom.Point2D;
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
	
	private Vector<Package> packagesToSend;

	
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
		
		addBehaviour(new ListeningBehaviour(this));
	}
	
	public void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		System.out.println(getLocalName() + ": drone killed");
	}
	
	private class ListeningBehaviour extends CyclicBehaviour {
		private static final long serialVersionUID = 1L;
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		public Warehouse agent;

		public ListeningBehaviour(Agent a) {
			super(a);
			this.agent = (Warehouse) a;
		}

			
			public void action() {
				MessageTemplate mt1 = MessageTemplate.MatchPerformative(ACLMessage.CFP);
				MessageTemplate mt2 = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
				ACLMessage msg = myAgent.receive(mt1);
				
				if(msg!=null)
				System.out.println("msg from drone received:"+msg);
				
			}	
			
			
		
		}
		



	public Point getCoords() {
		return coords;
	}

	public void setCoords(Point coords) {
		this.coords = coords;
	}


	

}
