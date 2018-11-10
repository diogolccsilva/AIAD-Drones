package warehouse;

import java.awt.geom.Point2D;
import java.util.TreeSet;

import client.Client;
import deliveryPackage.DeliveryPackage;
import drone.Drone;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import request.*;

public class GenerateWarehouseRequestsBehaviour extends TickerBehaviour {

	public GenerateWarehouseRequestsBehaviour(Agent a, long period) {
		super(a, period);
		/**  THIS WAS TO TEST
		 Point2D p1 = new Point2D.Double(2,3);
	        Point2D p2 = new Point2D.Double(2,3);

	        Client c1 = new Client ();
	        Client c2 = new Client ();
	        c1.setLocation(p1);
	        c2.setLocation(p2);

			DeliveryPackage pp1= new DeliveryPackage(c1, c2, 2, 1);
			DeliveryPackage pp2= new DeliveryPackage(c2, c1, 11, 1);
			
			TreeSet<DeliveryPackage> del = new TreeSet<DeliveryPackage>(); 
			del.add(pp1);
			del.add(pp2);
			((Warehouse)myAgent).setDeliveries(del);
			*/

	
	}

	@Override
	protected void onTick() {
		// TODO Auto-generated method stub
		TreeSet<DeliveryPackage> deliveries = ((Warehouse)myAgent).getDeliveries();
        //System.out.println("Fruits Set : " + ((Warehouse)myAgent).getDeliveries());

		if (deliveries.size() < 1) {
			System.out.println(this.myAgent.getLocalName() + ": no deliveries were found");
			return;
		}
		AID[] drones = Drone.getDrones(myAgent);
		if (drones.length < 1) {
			System.out.println(this.myAgent.getLocalName() + ": no drones were found");
			return;
		}
		else{				

			for (int i = 0; i < drones.length; ++i) {
				System.out.println(drones[i].getName());
			}
		
		}
		System.out.println(this.myAgent.getLocalName() + ": sending request(s) to drones");
		for (DeliveryPackage dp : deliveries) {
			Request request = new DeliverRequest(dp);
			System.out.println(this.myAgent.getLocalName() + ": new request created");
			//send request to agents
			
			myAgent.addBehaviour(new RequestPerfomer(drones,dp));
		}
	}

}
