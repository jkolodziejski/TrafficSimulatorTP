package simulator.model;

import java.net.Socket;

public class InterCityRoad extends Road {
	
	

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) throws Exception {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		
	}

	@Override
	void reduceTotalContamination() {
		int x=0;
		if(this.getWeather().equals(Weather.SUNNY)) {
			x=2;
		}
		
		else if(this.getWeather().equals(Weather.CLOUDY)) {
			x=3;
		}
		else if(this.getWeather().equals(Weather.RAINY)) {
			x=10;
		}
		else if(this.getWeather().equals(Weather.WINDY)) {
			x=15;
		}
		else if(this.getWeather().equals(Weather.STORM)){
			x=20;
		}
	
		contTotal =((100-x)*getTotalCO2())/100;
		System.out.println(getTotalCO2());
	}

	@Override
	void updateSpeedLimit() {
		if (getTotalCO2() >= getContLimit()) {
			current_speed_limit = getMaxSpeed()/2;
		}
		else {
			current_speed_limit = getMaxSpeed();
		}
		
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		if (getWeather().equals(Weather.STORM)) {
			return getSpeedLimit()*8/10;
		}
		else {
		 return getSpeedLimit();

		}
		
	}
}


