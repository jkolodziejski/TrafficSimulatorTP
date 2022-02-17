package simulator.model;

import java.util.List;

import javax.xml.transform.Source;

import org.json.JSONObject;

public abstract class Road extends SimulatedObject{
	
	protected Junction srcJunc, destJunc ;
	protected int length, maxSpeed, current_speed_limit,  contLimit, contTotal;
	protected Weather weather;
	protected List<Vehicle> vehicles;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		try {
		if(maxSpeed>0) {
			this.maxSpeed = maxSpeed;
		}
		}catch (Exception e) {
			System.out.println("Max speed is not positive number");
		}
		
		try {
			if(contLimit>=0) {
				this.contLimit = contLimit;
			}
			}catch (Exception e) {
				System.out.println("Contamination alarm limit is negative number");
			}
		
		try {
			if(length>0) {
				this.length = length;
			}
			}catch (Exception e) {
				System.out.println("Length is not positive number");
			}
		
		try {
			if(srcJunc!=null && destJunc!= null && weather!=null) {
				this.srcJunc = srcJunc;
				this.destJunc = destJunc;
				this.weather = weather;
			}
			}catch (Exception e) {
				System.out.println("Source junction, destination junction or weather conditions   is null");
			}
		
		
		}
	
	
	

	

	@Override
	void advance(int time) {
		reduceTotalContamination();
		updateSpeedLimit();
		for (Vehicle v : vehicles) {
			
		}
			
		
	}

	@Override
	public JSONObject report() {
		JSONObject raportString = new JSONObject();
		raportString.put(_id, getId());
		raportString.put("speedimit",getSpeedlimit());
		raportString.put("weather", getWeather());
		raportString.put("co2", getTotalCO2());
		
		
		
		

		return raportString;
	}
	
	 void enter(Vehicle v) {
		this.vehicles.add(v);
		
	}
	
	void exit(Vehicle v) {
		this.vehicles.remove(v);
	}
	
	void setWeather(Weather w) {
		try {
			if(w != null) {
				this.weather=w;
			}
		}
		catch (Exception e) {
			System.out.println("Weather is null");
		}
	}
	
	void addContamination(int c) {
		try {
			if(c>=0) {
				this.contTotal=c;
			}
		}catch (Exception e) {
			System.out.println("Adding contamination is negative number");
		}
	}
	
	abstract void reduceTotalContamination();
	
	abstract void updateSpeedLimit();
	
	abstract int calculateVehicleSpeed(Vehicle v);
	
	
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
	public int getSpeedlimit() {
		return current_speed_limit;
	}
	public List<Vehicle> getVehicles() {
		return vehicles;
	}
	
	
}
	
