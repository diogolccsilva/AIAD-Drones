package Utils;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {
	
	  	public final static String PATH_DRONES = "./input/drones.txt";
	    public static ArrayList<Object[]> dronesInformation;
	    public final static String PATH_CLIENTS = "./input/clients.txt";
	    public static ArrayList<Object[]> clientsInformation;
	    
	    
	    public final static String PATH_SPECTATORS = "./input/warehouses.txt";
	    public static ArrayList<Object[]> warehousesInformation;
	    

	public Utils() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public static void readFileDrones(String filePath) throws IOException {
        String line;

        dronesInformation = new ArrayList<>();

        try {

            File f = new File(filePath);
            if((!f.exists()) || (f.isDirectory()))
                throw new FileNotFoundException();

            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                
            	// droneName;x,y;capacity

               Object[] drone = new Object[4];

               String[] tokens = line.split(";");

               String[] coords = tokens[1].split(",");

               drone[0]=tokens[0];
               drone[1]=Integer.parseInt(coords[0]);
               drone[2]=Integer.parseInt(coords[1]);
               drone[3] = Integer.parseInt(tokens[2]);
               
               System.out.println("droneName"+drone[0]);
               System.out.println("x="+drone[1]);
               System.out.println("y="+drone[2]);
               System.out.println("cap="+drone[3]);

                dronesInformation.add(drone);
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Unable to open file '" + filePath + "'");
        }
        catch(IOException ex) {
            ex.printStackTrace();
            System.out.println("Invalid  information provided.");
        }
    }
	
	public static void readFileClients(String filePath) throws IOException {
        String line;

        clientsInformation = new ArrayList<>();

        try {

            File f = new File(filePath);
            if((!f.exists()) || (f.isDirectory()))
                throw new FileNotFoundException();

            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                

                Object[] client = new Object[3];
                
                //name;x,y

                String[] tokens = line.split(";");

               String[] coords = tokens[1].split(",");
               client[0] = Integer.parseInt(tokens[0]);

               
               client[1]=Integer.parseInt(coords[0]);
               client[2]=Integer.parseInt(coords[1]);
               
               
               System.out.println("ClientName="+client[0]);
               System.out.println("x="+client[1]);
               System.out.println("y="+client[2]);

               
             
                clientsInformation.add(client);
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Unable to open file '" + filePath + "'");
        }
        catch(IOException ex) {
            ex.printStackTrace();
            System.out.println("Invalid Band information provided.");
        }
    }
	
	public static void readFileWarehouses(String filePath) throws IOException {
        String line;

        warehousesInformation = new ArrayList<>();

        try {

            File f = new File(filePath);
            if((!f.exists()) || (f.isDirectory()))
                throw new FileNotFoundException();

            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                

                Object[] warehouse = new Object[3];
                
                //name;x,y

                String[] tokens = line.split(";");

               String[] coords = tokens[1].split(",");
               warehouse[0] = Integer.parseInt(tokens[0]);

               
               warehouse[1]=Integer.parseInt(coords[0]);
               warehouse[2]=Integer.parseInt(coords[1]);
               
               
               System.out.println("ClientName="+ warehouse[0]);
               System.out.println("x="+ warehouse[1]);
               System.out.println("y="+ warehouse[2]);

               
             
                warehousesInformation.add( warehouse);
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Unable to open file '" + filePath + "'");
        }
        catch(IOException ex) {
            ex.printStackTrace();
            System.out.println("Invalid Band information provided.");
        }
    }
	
	

}
