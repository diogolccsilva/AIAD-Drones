package droneManagementSystem;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class Launcher {

	public static void main(String[] args) {
		Runtime rt = Runtime.instance();

		Profile p = new ProfileImpl();
		//p1.setParameter(...);
		ContainerController cc = rt.createMainContainer(p);
		
		Object[] args1 = new Object[0];
		
		
		try {
			AgentController ag1 = cc.createNewAgent("drone1","droneManagementSystem.Drone",args1);
			ag1.start();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			AgentController ag2 = cc.createNewAgent("drone2","droneManagementSystem.Drone",args1);
			ag2.start();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			AgentController ag3 = cc.createNewAgent("client1","droneManagementSystem.Client",args1);
			ag3.start();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			AgentController ag4 = cc.createNewAgent("warehouse1","droneManagementSystem.Warehouse",args1);
			ag4.start();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			AgentController ag5 = cc.createNewAgent("warehouse2","droneManagementSystem.Warehouse",args1);
			ag5.start();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*
		AgentController ac3;
		try {
			ac3 = cc.acceptNewAgent("myRMA", new jade.tools.rma.rma());
			ac3.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
*/
		
	}

}
