package simulator.model;

public final class CityRoad extends Road{

	public CityRoad(String _id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) throws Exception {
		super(_id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		if(getWeather().equals(Weather.WINDY) || getWeather().equals(Weather.STORM)){
			contTotal-=10;
		}
	//ASK Teacher about this
	}

	@Override
	void updateSpeedLimit() {
		current_speed_limit = getMaxSpeed();
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		return ((11-v.getContClass())*getSpeedLimit())/11;
	}

}
