package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {
	private int maxSpeed, contClass;
	private String id;
	private List<String> itinerary_string;
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
		
		this.id=id;
		this.maxSpeed=maxSpeed;
		this.contClass=contClass;
		this.itinerary_string=itinerary;
	}

	@Override
	public void execute(RoadMap map) {
		List<Junction> itinerary = new ArrayList<>();
		
		
		for (String idString: itinerary_string) {
			itinerary.add(map.getJunction(idString));
		}
		Vehicle vehilce = new Vehicle(id, maxSpeed, contClass, itinerary);
		map.addVehicle(vehilce);
		vehilce.moveToNextRoad();
		

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "New vehicle '"+id+"'";
	}

}
