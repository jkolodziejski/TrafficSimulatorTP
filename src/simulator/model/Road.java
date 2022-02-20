package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



import org.json.JSONObject;

public abstract class Road extends SimulatedObject{
	
	private Junction destJunc, srcJunc ;
	protected int length, maxSpeed, current_speed_limit,  contLimit, contTotal;
	private Weather weather;
	private List<Vehicle> vehicles;
	
	Road(String id, Junction destJunc, Junction srcJunc, int maxSpeed, int contLimit, int length, Weather weather) throws Exception {
		super(id);
		this.vehicles = new ArrayList<>();
		if(maxSpeed>0) {
			this.maxSpeed = maxSpeed;
			this.current_speed_limit=maxSpeed;
		
		}else  {
		throw new Exception("Max speed is not positive number");
		}
		if(contLimit>=0) {
			this.contLimit = contLimit;
			
		}else {
			throw new Exception("Contamination alarm limit is negative number");
		}
		
		if(length>0) {
			this.length = length;
		}
		else  {
			throw new Exception("Length is not positive number");
		}
		
		
		if(destJunc!=null && srcJunc!= null && weather!=null) {
			this.destJunc = destJunc;
			this.srcJunc = srcJunc;
			srcJunc.addIncommingRoad(this);
			
			destJunc.addOutGotingRoad(this);
			this.weather = weather;
			
		}
		else {
			throw new Exception("Source junction, destination junction or weather conditions   is null");
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
	
	
	

	

	@Override
	void advance(int time) throws Exception {
		reduceTotalContamination();
		updateSpeedLimit();
		for (Vehicle v : vehicles) {
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
			}
			sort_vehicle(vehicles);
		
	}

	@Override
	public JSONObject report() {
		JSONObject raportString = new JSONObject();
		raportString.put("id", getId());
		raportString.put("speedlimit",getSpeedLimit());
		raportString.put("weather", weather.toString());
		raportString.put("co2", getTotalCO2());
		List<String> vehicleString = new ArrayList<>();
		for(int i=0;i<vehicles.size();i++) {
			vehicleString.add(vehicles.get(i).getId());
		}
		raportString.put("vehicles",vehicleString);

		return raportString;
	}
	
	 void enter(Vehicle v) throws Exception {
		 if(v.getSpeed()==0 || v.getLocation()==0) {
		this.vehicles.add(v);
		 }else {
			 throw new Exception("Speed or location is not zero");
		 }
		
	}
	
	void exit(Vehicle v) {
		this.vehicles.remove(v);
	}
	
	void setWeather(Weather w) throws Exception {
			if(w != null) {
				this.weather=w;
			}
			else {
			throw new Exception("Weather is null");
		}
	}
	
	void addContamination(int c) {
		try {
			if(c>=0) {
				this.contTotal+=c;
			}
		}catch (Exception e) {
			System.out.println("Adding contamination is negative number");
		}
	}
	
	abstract void reduceTotalContamination();
	
	abstract void updateSpeedLimit();
	
	abstract int calculateVehicleSpeed(Vehicle v) ;
	
	
	public int getLength() {
		return length;
	}
	public Junction getDest() {
		return srcJunc;
	}
	public Junction getSrc() {
		return destJunc;
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
	
	public void setCurrent_speed_limit(int current_speed_limit) {
		this.current_speed_limit = current_speed_limit;
	}
	
	
}
	
