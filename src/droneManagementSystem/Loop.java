package droneManagementSystem;

import java.io.IOException;

import jade.wrapper.StaleProxyException;

public class Loop {
	
	
	public static void main(String[] args) throws StaleProxyException, InterruptedException, IOException {
		

		for (int i=0; i<Integer.parseInt(args[0]); i++){
			Launcher launch = new Launcher();

			System.out.println("Iteracao: "+i);
			launch.run();
			
		}
	}


}
