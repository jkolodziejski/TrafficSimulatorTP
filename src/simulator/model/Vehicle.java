package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public class Vehicle extends SimulatedObject {
	
	private List<Junction> itinerary = new ArrayList<Junction>();
	
	private VehicleStatus status;
	
	private int maximumSpeed, currentSpeed, location,contClass,totalTraveledDistance,totalCO2   ;

	private Road road;
	
	private int last_seen_junction;



	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) throws Exception {
		
		super(id);
		if (id == null | id.length() == 0) {
			throw new Exception("id cannot be nonempty");
			
		}
		if (maxSpeed <= 0) {
			throw new Exception("maxSpeed needs to be positive");
			
		}
		else {
			this.maximumSpeed = maxSpeed;
		}
		if (contClass < 0 | contClass > 10) {
			throw new Exception("contClass needs to be between 0 and 10 (inclusive)");
		}
		else {
			this.contClass=contClass;
		}
		if (itinerary.size() < 2) {
			throw new Exception("itinerary needs to have a length of at least 2");
		}
		else {
			this.itinerary=itinerary;
		}
		this.status = VehicleStatus.PENDING;
		Collections.unmodifiableList(new ArrayList<>(itinerary));
		this.currentSpeed=0;
		this.location=0;
		this.totalTraveledDistance=0;
		this.totalCO2=0;
		this.last_seen_junction=0;
		
		
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
	public VehicleStatus getStatus() {
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
	
	public int getTotalTraveledDistance() {
		return totalTraveledDistance;
	}
	


	void setSpeed(int s) throws Exception {
		if (s < 0) {
			throw new Exception("s cannot be negative");
		}
		if(status == VehicleStatus.TRAVELING) {

		 if (s > maximumSpeed) {
			this.currentSpeed = maximumSpeed;
		} else
			this.currentSpeed = s;

	}
		
	}
	void setContClass(int c) throws Exception {
		
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
			location = Math.min((currentSpeed+location),road.getLength() );
			
			 c = (location - old_location) * contClass;
			 totalTraveledDistance+=location - old_location;
			 
			totalCO2+=c;
			road.addContamination(c);
			if(location == road.getLength()) {
				currentSpeed=0;
				status = VehicleStatus.WAITING;
				itinerary.get(last_seen_junction).enter(this);
				last_seen_junction++;
			}
		}
		
	}

	void moveToNextRoad() throws Exception {
			if(status.equals(VehicleStatus.PENDING) || status.equals(VehicleStatus.WAITING)){
				if(road != null && last_seen_junction==0) {
					road.exit(this);
				}
				else if (last_seen_junction == itinerary.size()) {
					status = VehicleStatus.ARRIVED;
					road = null;
					currentSpeed=0;
					location=0;
					
				}  
				else {
					status = VehicleStatus.TRAVELING;
					road= itinerary.get(last_seen_junction).roadTo(itinerary.get(last_seen_junction+1));
					location=0;
					road.enter(this);
				}
			}
			else {
			throw new  Exception("status is not PENDING or WAITING");
		}


	}
	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject();
		obj.put("id",  getId());
		obj.put("speed", getSpeed());
		obj.put("distance", getTotalTraveledDistance());
		obj.put("co2", getTotalCO2());
		obj.put("class", getContClass());
		obj.put("status",status.toString());
	
		if(status != VehicleStatus.PENDING && status != VehicleStatus.ARRIVED) {
			obj.put("road", getRoad().toString());
			obj.put("location", getLocation());
		}
		
		return obj;
	}
}
