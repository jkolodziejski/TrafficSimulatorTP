package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

public class Controller {
	TrafficSimulator traffic_simulator;
	Factory<Event> events_factory;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		if(sim != null && eventsFactory != null) {
		traffic_simulator=sim;
		events_factory=eventsFactory;
		}
		else {
			throw new IllegalArgumentException("Traffic Simulator or events Factory are null");
		}
	}
	
	public void loadEvents(InputStream in) {

		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray events = jo.getJSONArray("events");
		for(int i=0;i<events.length();i++) {
			traffic_simulator.addEvent(events_factory.createInstance(events.getJSONObject(i)));
			
		}
		
	}
	

	public void run(int n, OutputStream out) {
		
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("  \"states\": [");
		for(int i=0;i<n-1;i++) {
			traffic_simulator.advance();
			p.println(traffic_simulator.report());
			p.print(",");
		}
		if(n>0) {
			traffic_simulator.advance();
			p.println(traffic_simulator.report());
		}
		p.println("]");
		p.println("}");
	}
	
	public void reset() {
		traffic_simulator = new TrafficSimulator();
	}
	
	public void addObserver(TrafficSimObserver o) { 
		traffic_simulator.addObserver(o);
	}
	
	public void removeObserver(TrafficSimObserver o) {
		traffic_simulator.removeObserver(o);
	}
	
	public void addEvent(Event e) {
		traffic_simulator.addEvent(e);
	}
}
