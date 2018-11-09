package client;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import request.*;

import java.util.concurrent.ThreadLocalRandom;

import deliveryPackage.DeliveryPackage;
import drone.Drone;

public class GenerateRequestsBehaviour extends TickerBehaviour {

	public GenerateRequestsBehaviour(Agent a, long period) {
		super(a, period);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onTick() {
		// randomly generate requests
		boolean gen = ThreadLocalRandom.current().nextBoolean();
		if (gen) {
			Client sender = (Client) this.myAgent;
			Client receiver = new Client(); // TODO: change this
			double weight = 0;
			double size = 0;
			DeliveryPackage nPackage = new DeliveryPackage(sender, receiver, weight, size);
			
			Request request = new PickupRequest(nPackage);
			//System.out.println(this.myAgent.getLocalName() + ": new request created");
			
			AID[] drones = Drone.getDrones(myAgent);
			if (drones.length > 0) {
				System.out.println(this.myAgent.getLocalName() + ": sending request to drones");
				//start comunication behaviour here
				System.out.println("Found the following drone agents:");
				for (int i = 0; i < drones.length; ++i) {
					System.out.println(drones[i].getName());
				}
				myAgent.addBehaviour(new RequestPerfomer(drones));

			}
			else {
				System.out.println(this.myAgent.getLocalName() + ": no drones were found");
			}
			return;
		}
		//System.out.println(this.myAgent.getLocalName() + ": no request was generated");
	}
}
