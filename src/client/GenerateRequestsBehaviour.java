package client;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import request.*;
import warehouse.RequestDrone;
import warehouse.Warehouse;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Random;
import java.util.TreeSet;
import java.util.Vector;
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
		// TODO Auto-generated method stub
				Vector<DeliveryPackage> deliveries = ((Client)myAgent).getDeliveries();

				if (deliveries.size() < 1) {
					//System.out.println(this.myAgent.getLocalName() + ": no deliveries were found");
					myAgent.doDelete();
					return;
				}
				
				AID[] drones = Drone.getDrones(myAgent);
				if (drones.length > 0) {
					System.out.println(this.myAgent.getLocalName() + ": sending request to drones");
					//start comunication behaviour here
					/**System.out.println(this.myAgent.getLocalName()+"Found the following drone agents:");
					for (int i = 0; i < drones.length; ++i) {
						System.out.println(drones[i].getName());
					}*/
					for (DeliveryPackage dp : deliveries) {
						//System.out.println(this.myAgent.getLocalName() + ": looking to send a package");
						myAgent.addBehaviour(new RequestPerfomer(drones, dp) );

					}

				}
				else {
					System.out.println(this.myAgent.getLocalName() + ": no drones were found");
				}
				return;
	
	}
}
