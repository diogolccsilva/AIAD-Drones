package warehouse;

import java.util.Queue;

import deliveryPackage.DeliveryPackage;
import drone.Drone;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class GenerateWarehouseRequestsBehaviour extends TickerBehaviour {

	public GenerateWarehouseRequestsBehaviour(Agent a, long period) {
		super(a, period);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onTick() {
		// TODO Auto-generated method stub
		Queue<DeliveryPackage> deliveries = ((Warehouse)myAgent).getDeliveries();
		if (deliveries.size() < 1) {
			System.out.println(this.myAgent.getLocalName() + ": no deliveries were found");
			return;
		}
		AID[] drones = Drone.getDrones(myAgent);
		if (drones.length < 1) {
			System.out.println(this.myAgent.getLocalName() + ": no drones were found");
			return;
		}
		System.out.println(this.myAgent.getLocalName() + ": sending request(s) to drones");
		//start communication behaviour here
	}

}
