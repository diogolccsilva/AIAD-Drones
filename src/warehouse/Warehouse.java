package warehouse;

import java.awt.geom.Point2D;
import java.util.TreeSet;

import deliveryPackage.DeliveryPackage;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Warehouse extends Agent {

	private Point2D location;
	private TreeSet<DeliveryPackage> deliveries;
	
	public static AID[] getWarehouses(Agent agent) {
		AID[] warehouses = new AID[0];
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("storing-service");
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(agent, template);
			System.out.println("Found the following drone agents:");
			warehouses = new AID[result.length];
			for (int i = 0; i < result.length; ++i) {
				warehouses[i] = result[i].getName();
			}
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		return warehouses;
	}
	
	public void setup(){
		System.out.println(getLocalName() + ": warehouse created");
		//Object[] args = getArguments();
        //double xPosition = Double.parseDouble(args[0].toString());
        //double yPosition = Double.parseDouble(args[1].toString());
        //this.location = new Point2D.Double(xPosition,yPosition);
		
		
		setWarehouseInformation();
		//System.out.println(location+ ": warehouse location");

        registerWarehouseService();
        
        //addBehaviour(new GenerateWarehouseRequestsBehaviour(this,2000));
        
	}
	
	public void takeDown() {
		System.out.println(getLocalName() + ": warehouse killed");
	}
	
	public Point2D getLocation() {
		return location;
	}
	
	public void setLocation(Point2D location) {
		this.location = location;
	}

	public void setDeliveries(TreeSet<DeliveryPackage> deliveries) {
		this.deliveries = deliveries;
	}

	public void registerWarehouseService(){
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("storing-service");
		sd.setName("UPS");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

	}
	
	private void setWarehouseInformation() {
		
		int x = (int)getArguments()[1];
		int y = (int)getArguments()[2];
				
		double dx=(double)x;
		double dy=(double)y;
		
		Point2D pa = new Point2D.Double(dx, dy);
		
		setLocation(pa);
        
       
    }
	
	public TreeSet<DeliveryPackage> getDeliveries() {
		return deliveries;
	}

}
