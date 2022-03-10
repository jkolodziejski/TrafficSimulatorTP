package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent {

	public NewInterCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed,
			Weather weather) {
		super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
		
	}

	@Override
	public void execute(RoadMap map) {
		InterCityRoad interCityRoad=null;
		
		try {
			interCityRoad = new InterCityRoad(id, map.getJunction(srcJun), map.getJunction(destJunc), maxSpeed, co2limit, length, weather);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			map.addRoad(interCityRoad);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
