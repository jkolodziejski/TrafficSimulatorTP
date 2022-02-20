package simulator.model;

public final class CityRoad extends Road{

	public CityRoad(String _id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) throws Exception {
		super(_id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		if(getWeather().equals(Weather.WINDY) || getWeather().equals(Weather.STORM)){
			addContamination(getTotalCO2()-10);
		}
		else {
			addContamination(getTotalCO2()-2);
		}
	}

	@Override
	void updateSpeedLimit() {
		max_speed = getMaxSpeed();
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		return ((11-v.getContClass())*getSpeedlimit())/11;
	}

}
