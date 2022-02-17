package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Junction {
	
	private List<Road> incomingRoads= new ArrayList<Road>();
	
	private Map<Junction,Road> outogingRoads = new HashMap<Junction,Road>();
	
	private List<List<Vehicle>> listOfQvueues = new ArrayList<List<Vehicle>>();
	
	
	
	public void enter(Vehicle v) {
		
	}

}
