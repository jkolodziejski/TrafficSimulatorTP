package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {

	public SetContClassEventBuilder() {
		super("set_cont_class");
		// TODO Auto-generated constructor stub
		
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		
		int time = data.getInt("time");
		List<Pair<String,Integer>> ws = new ArrayList<>();
		JSONArray pairJsonArray = data.getJSONArray("info");
		
		for(int i=0;i<pairJsonArray.length();i++) {
			JSONObject one_pair = pairJsonArray.getJSONObject(i);
			Pair<String,Integer> pair_to_add =  new Pair<>(one_pair.getString("vehicle"), one_pair.getInt("class"));  
			ws.add(pair_to_add);
		}
		return new SetContClassEvent(time,ws);
	}

	
}
