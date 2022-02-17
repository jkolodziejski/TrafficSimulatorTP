package simulator.model;

public class InterCityRoad extends Road {
	
	

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		
	}

	@Override
	void reduceTotalContamination() {
		int x=0;
		if(weather.equals(Weather.SUNNY)) {
			x=2;
		}
		
		else if(weather.equals(Weather.CLOUDY)) {
			x=3;
		}
		else if(weather.equals(Weather.RAINY)) {
			x=10;
		}
		else if(weather.equals(Weather.WINDY)) {
			x=15;
		}
		else if(weather.equals(Weather.STORM)){
			x=20;
		}
		contTotal = ((100-x)*contTotal/100);
		
	}

	@Override
	void updateSpeedLimit() {
		if (contTotal == contLimit) {
			current_speed_limit = maxSpeed/2;
		}
		else {
			current_speed_limit = maxSpeed;
		}
		
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		if (weather.equals(Weather.STORM)) {
			return v.current_speed = (v.current_speed*8/10);
		}
		else {
		return v.current_speed;
		}
	}

}
