package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public class Vehicle extends SimulatedObject {

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

	private List<Junction> itinerary = new ArrayList<Junction>();

	private int maximumSpeed = 0;

	private int currentSpeed = 0;

	private int getSpeed() {
		return currentSpeed;
	}


	private VehicleStatus status;

	private Road road = null;

	int location = 0;

	int contClass = 0;

	int totalCO2 = 0;

	int totalTraveledDistance = 0;

	private Road getRoad() {
		return road;
	}

	private void setRoad(Road road) {
		this.road = road;
	}

	private int getLocation() {
		return location;
	}

	private void setLocation(int location) {
		this.location = location;
	}

	private int getContClass() {
		return contClass;
	}

	private void setContClass(int contClass) {
		this.contClass = contClass;
	}

	private int getTotalCO2() {
		return totalCO2;
	}

	private void setTotalCO2(int totalContamination) {
		this.totalCO2 = totalContamination;
	}

	private int getTotalTraveledDistance() {
		return totalTraveledDistance;
	}

	private void setTotalTraveledDistance(int totalTraveledDistance) {
		this.totalTraveledDistance = totalTraveledDistance;
	}

	void setSpeed(int s) {
		if (s < 0) {
			throw new Exception("s cannot be negative");
		}
		int speed = 0;
		if (status == VehicleStatus.TRAVELING) {
			speed = 0;
		} else if (s > maximumSpeed) {
			speed = maximumSpeed;
		} else
			speed = s;

	}

	void setContaminationClass(int c) {

	}
	
	private VehicleStatus getStatus() {
		return status;
	}

	private void setStatus(VehicleStatus status) {
		this.status = status;
	}

	@Override
	void advance(int time) {
		int oldlocation = getLocation();
		if (status == VehicleStatus.TRAVELING) {
			if ((getLocation() + currentSpeed) <= Road.length) {
				setLocation(getLocation() + currentSpeed);
			} else {
				setLocation(Road.length);
				Junction.enter(Vehicle._id);
				status = VehicleStatus.WAITING;
			}

			int contamination = (getLocation() - oldlocation) * getContClass();
			setTotalCO2(contamination);
			Road.addContamination(contamination);

		} else
			break;

	}

	void moveToNextRoad() {
		if (getStatus().equals(VehicleStatus.PENDING)) {
			itinerary
		}
		Road.exit();
		Road.enter();
		
	}

//	@Override
//	void advance(int time) {
//		// TODO Auto-generated method stub
//
//	}

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
