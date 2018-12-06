package Utils;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import drone.Drone;

public class Utils {

	public final static String PATH_DRONES = "./input/drones.txt";
	public static ArrayList<Object[]> dronesInformation;
	public final static String PATH_CLIENTS = "./input/clients.txt";
	public static ArrayList<Object[]> clientsInformation;
	public final static String PATH_WAREHOUSES = "./input/warehouses.txt";
	public static ArrayList<Object[]> warehousesInformation;
	public final static String PATH_OUTPUT_DRONES = "./output/dronedata.csv";

	public static PrintWriter saveData;

	public Utils() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void readFileDrones(String filePath) throws IOException {
		String line;

		dronesInformation = new ArrayList<>();

		try {

			File f = new File(filePath);
			if ((!f.exists()) || (f.isDirectory()))
				throw new FileNotFoundException();

			FileReader fileReader = new FileReader(filePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {

				// droneName;x,y;capacity

				Object[] drone = new Object[4];

				String[] tokens = line.split(";");

				String[] coords = tokens[1].split(",");

				drone[0] = tokens[0];
				drone[1] = Integer.parseInt(coords[0]);
				drone[2] = Integer.parseInt(coords[1]);
				drone[3] = Integer.parseInt(tokens[2]);
				/**
				 * System.out.println("droneName"+drone[0]);
				 * System.out.println("x="+drone[1]);
				 * System.out.println("y="+drone[2]);
				 * System.out.println("cap="+drone[3]);
				 */
				dronesInformation.add(drone);
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			System.out.println("Unable to open file '" + filePath + "'");
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("Invalid  information provided.");
		}
	}

	public static void readFileClients(String filePath) throws IOException {
		String line;

		clientsInformation = new ArrayList<>();

		try {

			File f = new File(filePath);
			if ((!f.exists()) || (f.isDirectory()))
				throw new FileNotFoundException();

			FileReader fileReader = new FileReader(filePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {

				Object[] client = new Object[3];

				// name;x,y

				String[] tokens = line.split(";");

				String[] coords = tokens[1].split(",");
				/**
				 * System.out.println(tokens[0]); System.out.println(tokens[1]);
				 */

				client[0] = tokens[0];
				client[1] = Integer.parseInt(coords[0]);
				client[2] = Integer.parseInt(coords[1]);

				/**
				 * System.out.println("ClientName="+client[0]);
				 * System.out.println("x="+client[1]);
				 * System.out.println("y="+client[2]);
				 */

				clientsInformation.add(client);
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			System.out.println("Unable to open file '" + filePath + "'");
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("Invalid Band information provided.");
		}
	}

	public static void readFileWarehouses(String filePath) throws IOException {
		String line;

		warehousesInformation = new ArrayList<>();

		try {

			File f = new File(filePath);
			if ((!f.exists()) || (f.isDirectory()))
				throw new FileNotFoundException();

			FileReader fileReader = new FileReader(filePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {

				Object[] warehouse = new Object[3];

				// name;x,y

				String[] tokens = line.split(";");

				String[] coords = tokens[1].split(",");

				warehouse[0] = tokens[0];
				warehouse[1] = Integer.parseInt(coords[0]);
				warehouse[2] = Integer.parseInt(coords[1]);

				/**
				 * System.out.println("WarehouseName="+ warehouse[0]);
				 * System.out.println("x="+ warehouse[1]);
				 * System.out.println("y="+ warehouse[2]);
				 */

				warehousesInformation.add(warehouse);
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			System.out.println("Unable to open file '" + filePath + "'");
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("Invalid Band information provided.");
		}
	}

	public synchronized static void saveFileDrones(Drone drone) throws IOException {
		if (saveData == null)
			saveData = new PrintWriter(new FileWriter(PATH_OUTPUT_DRONES, true), true);
		// eliminar
		saveData.println(drone.getLocalName() + "," + drone.getWeightCapacity() + "," + drone.getOrdersDelivered() + ","
				+ drone.getOcupationRate());
	}

	public static int[] generateRandomDrones() {
		int n = 0;
		int nd[] = { 0, 0, 0 };
		// gena pelo menos 1 drone de cada tipo
		for (int i = 0; i < 3; i++) {
			Object[] drone = new Object[4];

			drone[0] = "drone" + i;
			drone[1] = 11;
			drone[2] = 11;
			switch (i) {
			case 0:
				drone[3] = 8;
				break;
			case 1:
				drone[3] = 10;
				break;
			case 2:
				drone[3] = 12;
				break;
			}
			nd[i] += 1;
			dronesInformation.add(drone);
		}
		Random r = new Random();
		n = r.nextInt(27) + 1; // para atingir um maximo de 30 drones
		for (int i = 3; i < n + 3; i++) {
			Object[] drone = new Object[4];
			drone[0] = "drone" + i;
			drone[1] = 11;
			drone[2] = 11;
			int type = r.nextInt(3);
			switch (type) {
			case 0:
				drone[3] = 8;
				break;
			case 1:
				drone[3] = 10;
				break;
			case 2:
				drone[3] = 12;
				break;
			}
			nd[type] += 1;
			dronesInformation.add(drone);
		}
		return nd;
	}

	public static int generateRandomClients() {
		int n = 0;
		Random r = new Random();
		n = r.nextInt(100) + 1; // maximo 100 clientes
		for (int i = 0;i<n;i++) {
			Object[] client = new Object[3];
			
			int x = r.nextInt(30) + 1;
			int y = r.nextInt(30) + 1;
			
			client[0] = "cliente" + i;
			client[1] = x;
			client[2] = y;

			clientsInformation.add(client);
		}
		return n;
	}

}
