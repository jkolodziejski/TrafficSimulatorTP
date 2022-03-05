package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder {

	public NewCityRoadEventBuilder() {
		super("new_city_road");
		
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		read_data(data);
		return new NewCityRoadEvent(time, id, src, dest, length, co2limit, maxSpeed, weather);
	}

}
