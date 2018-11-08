package droneManagementSystem;

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
	    
	    
	    //public final static String PATH_SPECTATORS = "./input/spectators.txt";
	    //public static ArrayList<Object[]> spectatorsInformation;
	    //public final static int ITENERATION_COST_PER_DISTANCE = 10;
	  //  public final static int MAX_SHOWS_PER_BAND = 1;

	public Utils() {
		super();
		// TODO Auto-generated constructor stub
	}

	public double getDistance(Point2D p1, Point2D p2){
		
		double distance = Math.hypot(p2.getX()-p1.getX(), p2.getY()-p1.getY());

		return distance;

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
                

                Object[] drone = new Object[3];

                String[] tokens = line.split(";");

               String[] coords = tokens[0].split(",");

               
               drone[0]=Integer.parseInt(coords[0]);
               drone[1]=Integer.parseInt(coords[1]);
               drone[2] = Integer.parseInt(tokens[1]);
               /**
               System.out.println("x="+drone[0]);
               System.out.println("y="+drone[1]);
               System.out.println("cap="+drone[2]);
*/
               
             
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
            System.out.println("Invalid Band information provided.");
        }
    }
	
	

}
