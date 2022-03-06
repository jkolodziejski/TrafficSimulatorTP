package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;

import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event> {
	int time,length,co2limit,maxSpeed ;
	String id,src, dest;
	Weather weather;
	NewRoadEventBuilder(String type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	protected void read_data(JSONObject data) {
		time = data.getInt("time");
		id = data.getString("id");
		src = data.getString("src");
		dest = data.getString("dest");
		length = data.getInt("length");
		co2limit = data.getInt("co2limit");
		maxSpeed = data.getInt("maxspeed");
		weather = Weather.valueOf(data.get("weather").toString().toUpperCase());
	}
	
	@Override
	protected abstract Event createTheInstance(JSONObject data);

}
