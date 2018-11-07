package droneManagementSystem;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Utils {
	
	public double getDistance(Point2D p1, Point2D p2){
		
		double distance = Math.hypot(p2.getX()-p1.getX(), p2.getY()-p1.getY());

		return distance;

	}
	
	

}
