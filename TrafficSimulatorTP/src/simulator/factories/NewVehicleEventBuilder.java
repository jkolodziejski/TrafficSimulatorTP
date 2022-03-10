package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;


public class NewVehicleEventBuilder extends Builder<Event> {

	public NewVehicleEventBuilder() {
		super("new_vehicle");
	}



	@Override
	public Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		int maxSpeed = data.getInt("maxspeed");
		int class_co2 = data.getInt("class");
		List<String> itinerary = new ArrayList<>();
		JSONArray itinerary_json = data.getJSONArray("itinerary");
		for(Object x : itinerary_json) {
			itinerary.add(x.toString());
		}
		
		return new NewVehicleEvent(time, id, maxSpeed, class_co2, itinerary);
	}

}
