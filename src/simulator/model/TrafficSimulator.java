package simulator.model;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>{

	RoadMap _roadMap;
	List<Event> _events;
	int _time;
	protected List<TrafficSimObserver> observer;

	public TrafficSimulator() {
		_roadMap = new RoadMap();
		_events = new SortedArrayList<>();
		observer = new ArrayList<>();
		_time = 0;
	}

     public void reset() {
    	 _roadMap = new RoadMap();
    	 _events = new ArrayList<>();
    	 _time = 0;
    	 for(TrafficSimObserver obs : observer) {
    		 obs.onReset(_roadMap, _events, _time);
    	 }
     }
     
     public void addEvent(Event e) {
    	 if(e.getTime() <=_time) {
    		 for(TrafficSimObserver obs : observer) {
        		 obs.onError("Cannot add events for the past!");
        	 }
    		 throw new IllegalArgumentException("Cannot add events for the past!");
    		 
    	 }
    	 else {
    		 _events.add(e);
    		 for(TrafficSimObserver obs : observer) {
        		 obs.onEventAdded(_roadMap, _events, e, _time);
        	 }
    		 _events.sort((p1,p2)->{return p1.compareTo(p2);});
    		 
    	 }
    	 
     }
     
     public void advance() {
    	 _time++;
    	 for(TrafficSimObserver obs : observer) {
    		 obs.onAdvanceStart(_roadMap, _events, _time);
    	 }
    	 
    	 while(_events.size()>0 &&  _events.get(0).getTime()==_time) {
    		 
    		 _events.remove(0).execute(_roadMap);
    	 }
    	 for(Junction junction : _roadMap.getJunctions()) {
    		 junction.advance(_time);
    	 }
    	 
    	 for(Road road : _roadMap.getRoads()) {
    		 road.advance(_time);
    	 }
    	 for(TrafficSimObserver obs : observer) {
    		 obs.onAdvanceEnd(_roadMap, _events, _time);
    	 }
     }
     
     public JSONObject report() {
    	 JSONObject object = new JSONObject();
    	 object.put("time", _time);
    	 object.put("state", _roadMap.report());
    	 
		return object;
    	 
     }

	@Override
	public void addObserver(TrafficSimObserver o) {
		this.observer.add(o);
		o.onRegister(_roadMap, _events, _time);
		
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		this.observer.remove(o);
		
	}
	
	public int get_time() {
		return _time;
	}
	
	public RoadMap get_roadMap() {
		return _roadMap;
	}
 }