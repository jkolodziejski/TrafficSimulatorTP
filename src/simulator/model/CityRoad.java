package simulator.model;

public final class CityRoad extends Road{

	public CityRoad(String _id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(_id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void updateSpeedLimit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		// TODO Auto-generated method stub
		return 0;
	}

}
