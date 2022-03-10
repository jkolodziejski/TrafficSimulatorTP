package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {

	public SetWeatherEventBuilder(){
		super("set_weather");
		
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		int time = data.getInt("time");
		List<Pair<String,Weather>> ws = new ArrayList<>();
		JSONArray pairJsonArray = data.getJSONArray("info");
		
		for(int i=0;i<pairJsonArray.length();i++) {
			JSONObject one_pair = pairJsonArray.getJSONObject(i);
			Pair<String,Weather> pair_to_add =  new Pair<>(one_pair.getString("road"),Weather.valueOf(one_pair.getString("weather").toString().toUpperCase()));  
			ws.add(pair_to_add);
		}
		
		return new SetWeatherEvent(time, ws);
	}

}
