package deliveryPackage;

import drone.Drone;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class deliveringPackage extends Behaviour {

	int packageId;
	
	public deliveringPackage() {
		// TODO Auto-generated constructor stub
	}

	public deliveringPackage(Agent a, int packageId) {
		super(a);
		this.packageId = packageId;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean done() {
		return ((Drone)this.myAgent).getCurrentPosition() ==  ;
	}

}
