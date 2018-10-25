package droneManagementSystem;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Client extends Agent {
	

	Integer xPosition;
	Integer yPosition;
	String articleName;
	
	private ClientGUI myGui;
	

	public void setup() {
		// Create and show the GUI 
				myGui = new ClientGUI(this);
				myGui.showGui();
		
		addBehaviour(new TickerBehaviour(this, 3000) {
			protected void onTick() {
				String targetBookTitle ="asas";
				System.out.println("Trying to buy "+targetBookTitle);
				// Update the list of seller agents
				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("delivery-service");
				template.addServices(sd);
				try {
					DFAgentDescription[] result = DFService.search(myAgent, template); 
					System.out.println("Found the following seller agents:");
					AID[] sellerAgents = new AID[result.length];
					for (int i = 0; i < result.length; ++i) {
						sellerAgents[i] = result[i].getName();
						System.out.println(sellerAgents[i].getName());
					}
				}
				catch (FIPAException fe) {
					fe.printStackTrace();
				}

				
			}
		} );
		
	}
	
	public void takeDown() {
	
		System.out.println(getLocalName() + ": client killed");
	}
	
	public void setAttributes(Integer xPos, Integer yPos, String artc) {
		addBehaviour(new OneShotBehaviour() {
			public void action() {
				xPosition = xPos;
				yPosition = yPos;
				articleName = artc;
				System.out.println("Client launched article " + articleName);
			}
		} );		
		System.out.println(getLocalName() + ": client killed");
	}
	

}
