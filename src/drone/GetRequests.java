package drone;

import java.awt.geom.Point2D;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import deliveryPackage.DeliveryPackage;


public class GetRequests extends CyclicBehaviour {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void action() {

		MessageTemplate mt1 = MessageTemplate.MatchPerformative(ACLMessage.CFP);
		MessageTemplate mt2 = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
		MessageTemplate mt3 = MessageTemplate.MatchPerformative(ACLMessage.REFUSE);

		ACLMessage msg = myAgent.receive(mt1);
		
		

		ACLMessage msg_refuse;
		if (msg != null) {
			// CFP Message received. Process it
			ACLMessage reply = msg.createReply();

			String received = msg.getContent();
			//System.out.println("Drone received request with message:"+received);

			double weight=99;
			Point2D coords = null;

			try {
				weight = ((DeliveryPackage)msg.getContentObject()).getWeight();
				coords = ((DeliveryPackage)msg.getContentObject()).getSource();

			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			float cap = ((Drone)myAgent).getWeightCapacity();
			Point2D droneCoords = ((Drone)myAgent).getCurrentPosition();
			double distance;
			
			if (weight<cap && !((Drone)myAgent).isWorking()) {
				// The requested drone is available for sale. Reply with the distance
				distance = droneCoords.distance(coords);
				reply.setPerformative(ACLMessage.PROPOSE);
				reply.setContent(String.valueOf(distance));
			}
			else {
				// The requested drone is NOT available 
				reply.setPerformative(ACLMessage.REFUSE);
				reply.setContent("not-available");
			}
			myAgent.send(reply);
		}
		else if ((msg = myAgent.receive(mt2)) != null){
			ACLMessage reply = msg.createReply();

			if(!((Drone)myAgent).isWorking()){
			
			// ACCEPT_PROPOSAL Message received. Process it
			((Drone)myAgent).setWorking(true);
			// add working behavior to simulate drone moving
			// send INFORM in the end4
			/**
			try {
				System.out.println("AQUI pacote na msg accept: "+((DeliveryPackage)msg.getContentObject()));
			} catch (UnreadableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
*/
			try {
				((Drone)myAgent).setPacote(((DeliveryPackage)msg.getContentObject()));
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Point2D droneCoords = ((Drone)myAgent).getCurrentPosition();
			
			DeliveryPackage pac = ((Drone)myAgent).getPacote();
			
			
			
			Point2D dest= pac.getDestination();
			Point2D src= pac.getSource();
			
			double distance1 = droneCoords.distance(src);

			//double m= (droneCoords.getY()-src.getY())/(droneCoords.getX()-src.getX());
			//double b = src.getY()-m*src.getX();
			
			long time1 = (long) (distance1*(1000/((Drone)myAgent).getSpeed()));
			
			myAgent.addBehaviour(new WorkingBehaviour(myAgent,time1,1,msg));
			//((Drone)myAgent).setPosition(src);

			
			long time2;
			Point2D newDroneCoords = ((Drone)myAgent).getCurrentPosition();
			double distance2 = newDroneCoords.distance(dest);
			time2= (long) (distance2*(1000/((Drone)myAgent).getSpeed()));
			
			reply.setPerformative(ACLMessage.INFORM);

			//myAgent.send(reply);

			myAgent.addBehaviour(new WorkingBehaviour(myAgent,time2,2,reply));
			}
			else{
				//send FAILURE
				
				reply.setPerformative(ACLMessage.FAILURE);
				reply.setContent("Fail-> drone ja nao esta disponivel");
				myAgent.send(reply);
				System.out.println(myAgent.getLocalName()+" ->mandou fail");
			}
			

		}
		else if((msg = myAgent.receive(mt3)) != null){
			
			//System.out.println("DRONE RECEIVED REFUSE FROM client");
			// DO NOTHING?!
		}
		else {
			block();
		}
	
		
	}
	
}
