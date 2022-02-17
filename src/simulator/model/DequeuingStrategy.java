package simulator.model;

import java.util.ArrayList;
import java.util.List;

public interface DequeuingStrategy {
	
	List<Vehicle> dequeue(List<Vehicle> q);
		
	public static List<Vehicle> moveFirstStrategy(List<Vehicle> q){
		List<Vehicle> returnList = new ArrayList<Vehicle>();
		Vehicle v =q.get(0);
		returnList.add(v);
		return returnList;
	}
	
	public static List<Vehicle> moveAllStrategy(List<Vehicle>q){
		List<Vehicle> returnList = new ArrayList<Vehicle>();
		returnList = q;
		return returnList;
	}

}
