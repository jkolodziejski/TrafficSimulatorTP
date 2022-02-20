package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy{

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> returnList = new ArrayList<Vehicle>();
		returnList = q;
		return returnList;
	}

}
