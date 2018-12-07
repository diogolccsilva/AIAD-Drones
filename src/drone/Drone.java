package drone;

import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TreeSet;

import Utils.Utils;
import deliveryPackage.DeliveryPackage;
import request.*;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Drone extends Agent {
	
	private TreeSet<Request> requests;
	
	private DeliveryPackage pacote;
	
	private Point2D position;
	private float weightCapacity;
	private float baseVelocity;
	
	private boolean working;
	
	private long start;
	private int ordersDelivered = 0;
	private long busy = 0;
	private float busyPercent = 0;
	private static final long speed = 5;
	
	
	public static AID[] getDrones(Agent agent) {
		AID[] drones = new AID[0];
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("delivery-service");
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(agent, template);
			//System.out.println("Found the following drone agents:");
			drones = new AID[result.length];
			for (int i = 0; i < result.length; ++i) {
				drones[i] = result[i].getName();
			}
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		return drones;
	}
	
	public void setup() {
		
		System.out.println(getLocalName() + ": drone created");
	
		setDroneInformation();
	
		
		registerDroneService();
	
		start = System.currentTimeMillis();
		
		//TODO adding behaviours
		addBehaviour(new GetRequests());
		// add behavior to get best warehouse
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addBehaviour(new TickerBehaviour(this, 1000) {
			protected void onTick() {
				if (!isWorking()) {
					busyPercent = 100*busy/(System.currentTimeMillis() - start);
					System.out.println("Drone " + getLocalName() + " busy " + busyPercent + " % of time");
				}
			}
		});
	}
	
	
	public void registerDroneService(){
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("delivery-service");
		sd.setName("UPS");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

	}
	
	public void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		System.out.println(getLocalName() + ": drone killed");
		try {
			Utils.addOcupationRate(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setDroneInformation() {
		
		int weight = (int)getArguments()[3];
			int x = (int)getArguments()[1];
			int y = (int)getArguments()[2];
					
			double dx=(double)x;
			double dy=(double)y;
			
			Point2D pa = new Point2D.Double(dx, dy);

	        setPosition(pa);
	        setWeightCapacity(weight);
	        setWorking(false);
	    }
	 

	public void setRequests(TreeSet<Request> requests) {
		this.requests = requests;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public void setWeightCapacity(float weightCapacity) {
		this.weightCapacity = weightCapacity;
	}

	public void setBaseVelocity(float baseVelocity) {
		this.baseVelocity = baseVelocity;
	}

	public Point2D getCurrentPosition() {
		return position;
	}
	
	public float getWeightCapacity() {
		return weightCapacity;
	}

	public int getOrdersDelivered() {
		return ordersDelivered;
	}

	public float getOcupationRate() {
		return busyPercent;
	}

	public DeliveryPackage getPacote() {
		return pacote;
	}

	public void setPacote(DeliveryPackage pacote) {
		this.pacote = pacote;
	}

	public boolean isWorking() {
		return working;
	}

	public void setWorking(boolean working) {
		this.working = working;
	}
	
	public void updateBusy(long time) {
		this.busy += time;
	}

	public long getSpeed() {
		return speed;
	}

	public void countOrder() {
		ordersDelivered++;		
	}
	

}
