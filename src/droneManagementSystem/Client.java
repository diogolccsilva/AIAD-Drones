package droneManagementSystem;

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
		myGui = new ClientGUI(this);
		myGui.showGui();
		
	}
	
	public void takeDown() {
		System.out.println(getLocalName() + ": done working.");
	}
	
	class WorkingBehaviour extends Behaviour {
		private int n = 0;
		
		public void action() {
			System.out.println(++n + " Client doing something!");
		}

		public boolean done() {
			return n == 5;
		}
	}

	public void setAttributes(Integer xPos, Integer yPos, String artc) {
		addBehaviour(new OneShotBehaviour() {
			public void action() {
				xPosition = xPos;
				yPosition = yPos;
				articleName = artc;
				System.out.println("Client lauched article " + articleName);
			}
		} );		
	}

}
