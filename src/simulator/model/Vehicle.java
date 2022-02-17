package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public class Vehicle extends SimulatedObject {
	
	private List<Junction> itinerary = new ArrayList<Junction>();
	
	private VehicleStatus status;
	
	private int maximumSpeed, currentSpeed, location,contClass,totalTraveledDistance,totalCO2  ;

	private Road road;



	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) throws Exception {
		super(id);
		Collections.unmodifiableList(new ArrayList<>(itinerary));
		if (id == null | id.length() == 0) {
			throw new Exception("id cannot be nonempty");
		}
		if (maxSpeed <= 0) {
			throw new Exception("maxSpeed needs to be positive");
		}
		if (contClass < 0 | contClass > 10) {
			throw new Exception("contClass needs to be between 0 and 10 (inclusive)");
		}
		if (itinerary.size() < 2) {
			throw new Exception("itinerary needs to have a length of at least 2");
		}
	}

	
	public int getLocation() {
		return location;
	}
	
	public int getSpeed() {
		return currentSpeed;
	}

	
	public int getMaxSpeed() {
		return maximumSpeed;
	}
	
	public int getContClass() {
		return contClass;
	}

	public VehicleStatus getVehicleStatus() {
		return status;
	}
	
	public int getTotalCO2() {
		return totalCO2;
	}
	
	public List<Junction> getItinerary() {
		return itinerary;
	}
	
	public Road getRoad() {
		return road;
	}
	

	


	void setSpeed(int s) throws Exception {
		if (s < 0) {
			throw new Exception("s cannot be negative");
		}

		 if (s > maximumSpeed) {
			this.currentSpeed = maximumSpeed;
		} else
			this.currentSpeed = s;

	}

	void setContaminationClass(int c) throws Exception {
		
		if(c>=0 && c<10) {
			this.contClass = c;
		}
		else {
		throw new Exception("C is not between 0 and 10");
		}
	}

	

	

	@Override
	void advance(int time) {
		if(status != VehicleStatus.TRAVELING) {
			return;
		}
		else {
			int old_location = location;
			int c;
			location = Math.min(currentSpeed+location,road.getLength() );
			 c = (location - old_location) * contClass;
			 
			totalCO2+=c;
			road.addContamination(c);
			if(location == road.getLength()) {
				currentSpeed=0;
				status = VehicleStatus.WAITING;
				itinerary.get(-1).enter();
			}
		}
		
	}

	void moveToNextRoad() throws Exception {
			if(status.equals(VehicleStatus.PENDING) || status.equals(VehicleStatus.WAITING)){
				
			}
			else {
			throw new  Exception("status is not PENDING or WAITING");
		}


	}
	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject();
		obj.put("ID", getId());
		obj.put("speed", getSpeed());
		obj.put("distance", getTotalTraveledDistance());
		obj.put("CO2", getTotalCO2());
		obj.put("class", getContClass());
		obj.put("status", getStatus());
		obj.put("road", getRoad());
		obj.put("location", getLocation());
		
		return obj;
	}
}
