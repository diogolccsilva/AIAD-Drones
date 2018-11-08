package warehouse;

import java.awt.geom.Point2D;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Warehouse extends Agent {

	private Point2D location;
	
	public void setup(){
		System.out.println(getLocalName() + ": warehouse created");
		Object[] args = getArguments();
        double xPosition = Double.parseDouble(args[0].toString());
        double yPosition = Double.parseDouble(args[1].toString());
        this.location = new Point2D.Double(xPosition,yPosition);
        
        registerWarehouseService();
        
        //todo: adding behaviours
        
        
	}
	
	public void takeDown() {
		System.out.println(getLocalName() + ": warehouse killed");
	}
	
	public Point2D getLocation() {
		return location;
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

}
