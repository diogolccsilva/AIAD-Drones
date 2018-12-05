package drone;


import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

import java.awt.geom.Point2D;

import deliveryPackage.DeliveryPackage;

public class WorkingBehaviour extends WakerBehaviour {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long elapsedTime;
	private int viagem;   // 1 for pickup .... 2 for delivery
	private ACLMessage msg;
	
	 public WorkingBehaviour(Agent agent,long time,int viagem,ACLMessage msg) {
		super(agent, time);
		this.elapsedTime = time;
		this.viagem=viagem;
		
		this.msg=msg;
		
	}



	@Override
	protected void handleElapsedTimeout() {
		if(viagem ==1){
			 //  System.out.println("Agent "+myAgent.getLocalName()+": It's wakeup-time. Bye...");
			    DeliveryPackage pac = ((Drone)myAgent).getPacote();
			   Point2D source = pac.getSource();
			   ((Drone)myAgent).setPosition(source);
			   //+((Drone)myAgent).getCurrentPosition()
			    
			    System.out.println(myAgent.getLocalName()+" got to the client: "+pac.getOwner()+" to pickup package: "+pac.getId());

			    //myAgent.doDelete();
				long time2;
				Point2D newDroneCoords = ((Drone)myAgent).getCurrentPosition();
				double distance2 = newDroneCoords.distance(((Drone)myAgent).getPacote().getDestination());
				time2= (long) (distance2*(1000/((Drone)myAgent).getSpeed()));
				
				msg.setPerformative(ACLMessage.INFORM);

				//myAgent.send(reply);

				myAgent.addBehaviour(new WorkingBehaviour(myAgent,time2,2,msg));

		}
		else if( viagem ==2){
			//myAgent.addBehaviour(new DelayBehaviour(myAgent,elapsedTime));

			
			// System.out.println("Agent "+myAgent.getLocalName()+": It's wakeup-time. Bye...");
			    DeliveryPackage pac = ((Drone)myAgent).getPacote();
			   java.awt.geom.Point2D dest = pac.getDestination();
			   ((Drone)myAgent).setPosition(dest);
			    
			    System.out.println(myAgent.getLocalName()+" got to the destination of the package: "+pac.getId()+"from "+pac.getOwner()+" at "+((Drone)myAgent).getCurrentPosition());
			    msg.setContent("success");
			    myAgent.send(msg);
			    ((Drone)myAgent).setWorking(false);
			    ((Drone)myAgent).countOrder();
				//((Drone)myAgent).setPacote(null);

		}
		((Drone)myAgent).updateBusy(this.elapsedTime);
	}


/**
	@Override
	protected void onWake() {
		// TODO Auto-generated method stub
		super.onWake();
	}



	@Override
	public void action() {
		
		
		//myAgent.addBehaviour(new DelayBehaviour(myAgent,22000));
		
		if(viagem ==1){
			 //  System.out.println("Agent "+myAgent.getLocalName()+": It's wakeup-time. Bye...");
			    DeliveryPackage pac = ((Drone)myAgent).getPacote();
			   java.awt.geom.Point2D source = pac.getSource();
			 //  ((Drone)myAgent).setPosition(source);
			    
			    System.out.println("Agent "+myAgent.getLocalName()+" got to the client at: "+((Drone)myAgent).getCurrentPosition());

			    //myAgent.doDelete();

		}
		else if( viagem ==2){
			//myAgent.addBehaviour(new DelayBehaviour(myAgent,elapsedTime));

			
			// System.out.println("Agent "+myAgent.getLocalName()+": It's wakeup-time. Bye...");
			    DeliveryPackage pac = ((Drone)myAgent).getPacote();
			   java.awt.geom.Point2D dest = pac.getDestination();
			   ((Drone)myAgent).setPosition(dest);
			    
			    System.out.println("Agent "+myAgent.getLocalName()+" got to the destination of the package: "+((Drone)myAgent).getCurrentPosition());
			    ((Drone)myAgent).setWorking(false);
				((Drone)myAgent).setPacote(null);
			
		}
		
		
	}*/

}
