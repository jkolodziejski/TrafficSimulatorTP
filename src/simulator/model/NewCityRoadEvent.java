package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent {

	public NewCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed,
			Weather weather) {
		super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
	}
	
	
	@Override
	public void  execute(RoadMap map)  {
		CityRoad interCityRoad =null;
		
			interCityRoad = new CityRoad(id, map.getJunction(srcJun), map.getJunction(destJunc), maxSpeed, co2limit, length, weather);
	
			map.addRoad(interCityRoad);
			
	

	}
}