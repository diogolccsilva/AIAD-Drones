package client;

import droneManagementSystem.ClientGUI;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;

public class Client extends Agent {

	Integer xPosition;
	Integer yPosition;
	String articleName;

	private ClientGUI myGui;

	public void setup() {
		// Create and show the GUI
		// myGui = new ClientGUI(this);
		// myGui.showGui();
		System.out.println("Client created");
		setAttributes(1, 1, "artigo");

		addBehaviour(new TickerBehaviour(this, 3000) {
			protected void onTick() {
				System.out.println("Looking to send a package");
				// Update the list of drone agents
				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("delivery-service");
				template.addServices(sd);
				try {
					DFAgentDescription[] result = DFService.search(myAgent, template);
					System.out.println("Found the following drone agents:");
					drones = new AID[result.length];
					for (int i = 0; i < result.length; ++i) {
						drones[i] = result[i].getName();
						System.out.println(drones[i].getName());
					}
				} catch (FIPAException fe) {
					fe.printStackTrace();
				}

				myAgent.addBehaviour(new RequestPerformer());

			}
		});
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
				System.out.println("Client lauched article " + articleName);
			}
		});
	}

}
