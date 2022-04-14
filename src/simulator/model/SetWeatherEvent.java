package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {
	List<Pair<String, Weather>> ws;
	

	public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
		super(time);
		this.ws=ws;
	}

	@Override
	public void execute(RoadMap map) {
		for (Pair<String, Weather> p_ws : ws) {
			Road road = map.getRoad(p_ws.getFirst());
			if(road == null) {
				throw new IllegalArgumentException("This road does not exist in map");
			}else {
				road.setWeather(p_ws.getSecond());
			}
		
		}

	}

	@Override
	public String toString() {
		int which=0;
		String wsString="[";
		for(Pair<String, Weather> c : ws) {
			if(which!=0) {
			wsString+=",";
			}
			wsString+="(";
			wsString+=c.getFirst();
			wsString+=",";
			wsString+=c.getSecond().toString();
			wsString+=")";
			which++;
		}
		wsString+="]";
		
		return "Set weather"+wsString;
	}

}
