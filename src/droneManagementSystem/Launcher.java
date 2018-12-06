package droneManagementSystem;

import java.io.IOException;
import java.util.ArrayList;

import Utils.Utils;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class Launcher {

	public static void main(String[] args) throws StaleProxyException {
		

		Runtime rt = Runtime.instance();

		Profile p1 = new ProfileImpl();
		ContainerController mainContainer = rt.createMainContainer(p1);

		Profile p2 = new ProfileImpl();
		p2.setParameter(Profile.CONTAINER_NAME, "Drones");
		ContainerController drones = rt.createAgentContainer(p2);

		Profile p3 = new ProfileImpl();
		p3.setParameter(Profile.CONTAINER_NAME, "Clients");
		ContainerController clients = rt.createAgentContainer(p3);

		Profile p4 = new ProfileImpl();
		p4.setParameter(Profile.CONTAINER_NAME, "Warehouses");
		//ContainerController warehouses = rt.createAgentContainer(p4);

		AgentController ac1;
		try {
			ac1 = mainContainer.acceptNewAgent("myRMA", new jade.tools.rma.rma());
			ac1.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}

		/* INIT Drones */
		System.out.println("--- Drones ---");
		AgentController ac2;
		/*try {
			Utils.readFileDrones(Utils.PATH_DRONES);

			for (Object[] drone : Utils.dronesInformation) {
				ac2 = drones.createNewAgent( drone[0].toString(), "drone.Drone", drone);
				//System.out.println("Drones args: "+drone[0].toString()+","+drone[1]+","+drone[2]+","+drone[3]);
				ac2.start();
			}
			System.out.println("\n--- Drones ---");


		} catch (StaleProxyException | IOException e) {
			e.printStackTrace();
		} */
		
		int dn[] = Utils.generateRandomDrones();
		int dsum = dn[0] + dn[1] + dn[2];
		
		System.out.println("Generated " + dsum + " drones");
		System.out.println("Generated " + dn[0] + " drones of type A");
		System.out.println("Generated " + dn[1] + " drones of type B");
		System.out.println("Generated " + dn[2] + " drones of type C");
		
		for (Object[] drone : Utils.dronesInformation) {
			ac2 = drones.createNewAgent( drone[0].toString(), "drone.Drone", drone);
			//System.out.println("Drones args: "+drone[0].toString()+","+drone[1]+","+drone[2]+","+drone[3]);
			ac2.start();
		}
		
		/* INIT Clients */
		System.out.println("/n--- Clients ---");
		AgentController ac3;
		ArrayList<AgentController> clientControllers = new ArrayList<AgentController>();
		/*try {
			Utils.readFileClients(Utils.PATH_CLIENTS);
			for (Object[] client : Utils.clientsInformation) {
				ac3 = clients.createNewAgent((String) client[0], "client.Client", client);
				ac3.start();
				clientControllers.add(ac3);
			}
			System.out.println("--- Clients ---");

		} catch (StaleProxyException | IOException e) {
			e.printStackTrace();
		}*/
		
		int cn = Utils.generateRandomClients();
		System.out.println("Generated " + cn + " clients");
		
		for (Object[] client : Utils.clientsInformation) {
			ac3 = clients.createNewAgent((String) client[0], "client.Client", client);
			ac3.start();
			clientControllers.add(ac3);
		}
		
		/* INIT Warehouses 
		AgentController ac4;
		try {
			Utils.readFileWarehouses(Utils.PATH_WAREHOUSES);
			for (Object[] warehouse : Utils.warehousesInformation) {
				ac4 = warehouses.createNewAgent((String) warehouse[0], "warehouse.Warehouse", warehouse);
				ac4.start();
			}
			System.out.println("\n--- Warehouses ---\n");

		} catch (StaleProxyException | IOException e) {
			e.printStackTrace();
		}
		*/
		boolean deleted = false;
		while (!deleted) {
			deleted = true;
			for (AgentController ac : clientControllers)
				deleted &= (ac.getState().getName() == "Suspended");
		}
		
		drones.kill();
		clients.kill();
		
	
	}

}