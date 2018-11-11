package drone;


import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import com.sun.javafx.geom.Point2D;

import deliveryPackage.DeliveryPackage;

public class WorkingBehaviour extends OneShotBehaviour {
	
	private long elapsedTinme;
	private int viagem;   // 1 for pickup .... 2 for delivery
	
	 public WorkingBehaviour(Agent agent,long time,int viagem) {
		super();
		this.elapsedTinme = time;
		this.viagem=viagem;
		
	}


	protected void handleElapsedTimeout() {
		
		if(viagem ==1){
			 //  System.out.println("Agent "+myAgent.getLocalName()+": It's wakeup-time. Bye...");
			    DeliveryPackage pac = ((Drone)myAgent).getPacote();
			   java.awt.geom.Point2D source = pac.getSource();
			   ((Drone)myAgent).setPosition(source);
			    
			    System.out.println("Agent "+myAgent.getLocalName()+" got to the client at: "+((Drone)myAgent).getCurrentPosition());

			    //myAgent.doDelete();

		}
		else if( viagem ==2){
			// System.out.println("Agent "+myAgent.getLocalName()+": It's wakeup-time. Bye...");
			    DeliveryPackage pac = ((Drone)myAgent).getPacote();
			   java.awt.geom.Point2D dest = pac.getDestination();
			   ((Drone)myAgent).setPosition(dest);
			    
			    System.out.println("Agent "+myAgent.getLocalName()+" got to the destination of the package: "+((Drone)myAgent).getCurrentPosition());
			    ((Drone)myAgent).setWorking(false);
				((Drone)myAgent).setPacote(null);
			
		}
		 
		  }


	@Override
	public void action() {
		
		if(viagem ==1){
			 //  System.out.println("Agent "+myAgent.getLocalName()+": It's wakeup-time. Bye...");
			    DeliveryPackage pac = ((Drone)myAgent).getPacote();
			   java.awt.geom.Point2D source = pac.getSource();
			   ((Drone)myAgent).setPosition(source);
			    
			    System.out.println("Agent "+myAgent.getLocalName()+" got to the client at: "+((Drone)myAgent).getCurrentPosition());

			    //myAgent.doDelete();

		}
		else if( viagem ==2){
			
			// System.out.println("Agent "+myAgent.getLocalName()+": It's wakeup-time. Bye...");
			    DeliveryPackage pac = ((Drone)myAgent).getPacote();
			   java.awt.geom.Point2D dest = pac.getDestination();
			   ((Drone)myAgent).setPosition(dest);
			    
			    System.out.println("Agent "+myAgent.getLocalName()+" got to the destination of the package: "+((Drone)myAgent).getCurrentPosition());
			    ((Drone)myAgent).setWorking(false);
				((Drone)myAgent).setPacote(null);
			
		}
		
		
		
	}

}
