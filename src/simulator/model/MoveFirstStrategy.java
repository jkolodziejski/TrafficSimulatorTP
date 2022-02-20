package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy{


	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> returnList = new ArrayList<Vehicle>();
		Vehicle v =q.get(0);
		returnList.add(v);
		return returnList;
	}

}

