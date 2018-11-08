package client;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.concurrent.ThreadLocalRandom;

import deliveryPackage.DeliveryPackage;

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
			System.out.println(this.myAgent.getLocalName() + ": new request created");

			AID[] drones = getDrones();
			if (drones.length > 0) {
				System.out.println(this.myAgent.getLocalName() + ": sending request to drones");
			}
			else {
				System.out.println(this.myAgent.getLocalName() + ": no drones were found");
			}
			return;
		}
		System.out.println(this.myAgent.getLocalName() + ": no request was generated");
	}

	public AID[] getDrones() {
		AID[] drones = new AID[0];
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
			}
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		return drones;
	}
}
