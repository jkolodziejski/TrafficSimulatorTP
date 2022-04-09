package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public abstract class Road extends SimulatedObject{
	
	private Junction destJunc, srcJunc ;
	protected int length, maxSpeed, current_speed_limit,  contLimit, contTotal;
	private Weather weather;
	private List<Vehicle> vehicles;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather)  {
		super(id);
		this.vehicles = new ArrayList<>();
		if(maxSpeed>0) {
			this.maxSpeed = maxSpeed;
			this.current_speed_limit=maxSpeed;

		}else  {
		throw new IllegalArgumentException("Max speed is not positive number");
		}
		if(contLimit>=0) {
			this.contLimit = contLimit;
			
		}else {
			throw new IllegalArgumentException("Contamination alarm limit is negative number");
		}
		
		if(length>0) {
			this.length = length;
		}
		else  {
			throw new IllegalArgumentException("Length is not positive number");
		}
		
		
		if(destJunc!=null && srcJunc!= null && weather!=null) {
			this.destJunc = destJunc;
			this.srcJunc = srcJunc;
			destJunc.addIncommingRoad(this);
			
			srcJunc.addOutGotingRoad(this);
			this.weather = weather;
			
		}
		else {
			throw new IllegalArgumentException("Source junction, destination junction or weather conditions   is null");
			}
		
		
		}
	
	
	private static void sort_vehicle(List<Vehicle> vehicles) {
		int size = vehicles.size();
		int min,i,j;
		 Vehicle tmpVehicle;
		for (i=0;i<size-1;i++)  {
			min=i;
			for (j=i+1;j<size;j++) {
				if (vehicles.get(j).getLocation()>vehicles.get(min).getLocation()) {
					
					min=j;
					
				}
			}
		tmpVehicle	= vehicles.get(min);
		vehicles.set(min,vehicles.get(i));
		vehicles.set(i,tmpVehicle);

		}
	}
	
	
	 void enter(Vehicle v)  {
		 if(v.getSpeed()==0 && v.getLocation()==0) {
			 this.vehicles.add(v);
			 vehicles.sort((p1, p2) -> {
		      return Integer.compare(p1.getLocation(), p2.getLocation());
			  });
			 //sort_vehicle(vehicles);
		 }else {
			 throw new IllegalArgumentException("Speed or location is not zero");
		 }
		
	}
	 
	 
	 void exit(Vehicle v) {
			this.vehicles.remove(v);
	}
	 
	 
	 
	 public void setWeather(Weather w)  {
			if(w != null) {
				this.weather=w;
			}
			else {
			throw new IllegalArgumentException("Weather is null");
		}
	}
	 
	 
	 void addContamination(int c) {
			if(c>=0) {
				this.contTotal+=c;
			}else {
			throw new IllegalArgumentException("Adding contamination is negative number");
		}
	}
	
	abstract void reduceTotalContamination();
	
	abstract void updateSpeedLimit();
	
	abstract int calculateVehicleSpeed(Vehicle v) ;
	

	

	@Override
	public void advance(int time) {
		reduceTotalContamination();
		updateSpeedLimit();
		for (Vehicle v : vehicles) {
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
			}
			sort_vehicle(vehicles);
		
	}
	
	
	public int getLength() {
		return length;
	}
	public Junction getDest() {
		return destJunc;
	}
	public Junction getSrc() {
		return srcJunc;
	}
	public Weather getWeather() {
		return weather;
	}
	public int getContLimit() {
		return contLimit;
	}
	public int getMaxSpeed() {
		return maxSpeed;
	}
	public int getTotalCO2() {
		return contTotal;
	}
	public int getSpeedLimit() {
		return current_speed_limit;
	}
	public List<Vehicle> getVehicles() {
		return Collections.unmodifiableList(vehicles);
	}
	

	@Override
	public JSONObject report() {
		JSONObject raportString = new JSONObject();
		raportString.put("id", getId());
		raportString.put("speedlimit",getSpeedLimit());
		raportString.put("weather", weather.toString());
		raportString.put("co2", getTotalCO2());
		JSONArray vehicleString = new JSONArray();
		for(int i=0;i<vehicles.size();i++) {
			vehicleString.put(vehicles.get(i).getId());
		}
		raportString.put("vehicles",vehicleString);

		return raportString;
	}
	
	
	
	
	
	
	
	
	
	

}
	
