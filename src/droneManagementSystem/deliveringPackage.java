package droneManagementSystem;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class deliveringPackage extends Behaviour {

	public deliveringPackage() {
		// TODO Auto-generated constructor stub
	}

	public deliveringPackage(Agent a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean done() {
		return this.myAgent.getPosition() ;
	}

}
