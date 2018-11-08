package client;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

import java.util.concurrent.ThreadLocalRandom;

import deliveryPackage.DeliveryPackage;

public class GenerateRequestsBehaviour extends TickerBehaviour {

	public GenerateRequestsBehaviour(Agent a, long period) {
		super(a, period);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onTick() {
		//randomly generate requests
		boolean gen = ThreadLocalRandom.current().nextBoolean();
		if (gen) {
			Client sender = (Client)this.myAgent;
			Client receiver = new Client(); //TODO: change this
			double weight = 0;
			double size = 0;
			DeliveryPackage nPackage = new DeliveryPackage(sender,receiver,weight,size);
		}
	}

}
