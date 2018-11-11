package drone;


import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.WakerBehaviour;
import deliveryPackage.DeliveryPackage;

public class WorkingBehaviour extends WakerBehaviour {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long elapsedTime;
	private int viagem;   // 1 for pickup .... 2 for delivery
	
	 public WorkingBehaviour(Agent agent,long time,int viagem) {
		super(agent, time);
		this.elapsedTime = time;
		this.viagem=viagem;
		
	}



	@Override
	protected void handleElapsedTimeout() {
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
	}



	@Override
	protected void onWake() {
		// TODO Auto-generated method stub
		super.onWake();
	}


/**
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
