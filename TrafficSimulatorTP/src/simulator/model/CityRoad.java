package simulator.model;

public final class CityRoad extends Road{

	public CityRoad(String _id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(_id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		if(getWeather().equals(Weather.WINDY) || getWeather().equals(Weather.STORM)){
			if(contTotal>=10) contTotal-=10;
		}
		else{
			if(contTotal>=2) contTotal-=2;
		}
	}

	@Override
	void updateSpeedLimit() {
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		return ((11-v.getContClass())*getSpeedLimit())/11;
	}

}
