package simulator.model;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator {

	RoadMap _roadMap;
	List<Event> _events;
	int _time;

	public TrafficSimulator() {
		_roadMap = new RoadMap();
		_events = new SortedArrayList<>();
		_time = 0;
	}

     public void reset() {
    	 _roadMap = new RoadMap();
    	 _events = new ArrayList<>();
    	 _time = 0;
     }
     
     public void addEvent(Event e) {
    	 if(e.getTime() <=_time) {
    		 throw new IllegalArgumentException("Cannot add events for the past!");
    	 }
    	 else {
    		 _events.add(e);
    		 _events.sort(Comparator.comparing(Event::getTime));
    	 }
    	 
     }
     
     public void advance() {
    	 _time++;
    	 
    	 
    	 while(_events.size()>0 &&  _events.get(0).getTime()==_time) {
    		 
    		 _events.remove(0).execute(_roadMap);
    	 }
    	 for(Junction junction : _roadMap.getJunctions()) {
    		 junction.advance(_time);
    	 }
    	 
    	 for(Road road : _roadMap.getRoads()) {
    		 road.advance(_time);
    	 }
     }
     
     public JSONObject report() {
    	 JSONObject object = new JSONObject();
    	 object.put("time", _time);
    	 object.put("state", _roadMap.report());
    	 
		return object;
    	 
     }
 }