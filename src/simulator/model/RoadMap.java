package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {

	private List<Junction> listJunction = new ArrayList<Junction>();
	private List<Road> listRoad = new ArrayList<Road>();
	private List<Vehicle> listVehicle = new ArrayList<Vehicle>();

	private Map<String, Junction> mapJunction = new HashMap<String, Junction>();
	private Map<String, Road> mapRoad = new HashMap<String, Road>();
	private Map<String, Vehicle> mapVehicle = new HashMap<String, Vehicle>();

	public RoadMap() {
	}

	public void addJunction(Junction j)  {
		if (!mapJunction.containsKey(j.getId())) {
			listJunction.add(j);
			mapJunction.put(j.getId(), j);
		} else
			throw new IllegalArgumentException("junction is already in list");
	}
	
	public void addRoad(Road r)  {
		if (!mapRoad.containsKey(r.getId())) {
			if (mapJunction.containsKey(r.getDest().getId()) && mapJunction.containsKey(r.getSrc().getId())) {
				listRoad.add(r);
				mapRoad.put(r.getId(), r);
			} else
				throw new IllegalArgumentException("junctions connected to road dont exist in road-map");
		} else
			throw new IllegalArgumentException("road already in list");
	}


	public void addVehicle(Vehicle v){
		if (!mapVehicle.containsKey(v.getId())) {
			if (itinerary_check(v.getItinerary())) {
				listVehicle.add(v);
				mapVehicle.put(v.getId(), v);
			} else
				throw new IllegalArgumentException("itinerary invalid");
		} else
			throw new IllegalArgumentException("vehicle already in list");
	}
	
	public Junction getJunction(String id) {
		if(mapJunction.containsKey(id)){				
			
			return mapJunction.get(id);
		}
		else return null;
	}
	
	public Vehicle getVehicle(String id) {
		if(mapVehicle.containsKey(id)){					
			return mapVehicle.get(id);
		}
		else return null;
	}
	
	public Road getRoad(String id) {
		if(mapRoad.containsKey(id)){					
			return mapRoad.get(id);
		}
		else return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Junction> getJunctions() {
		return Collections.unmodifiableList(new ArrayList(listJunction));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Road> getRoads() {
		return Collections.unmodifiableList(new ArrayList(listRoad));	
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Vehicle> getVehicles() {
		return Collections.unmodifiableList(new ArrayList(listVehicle));
	}
	

	void reset() {
		listJunction.clear();
		listVehicle.clear();
		listRoad.clear();
		mapJunction.clear();
		mapVehicle.clear();
		mapRoad.clear();
	}
	
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		for (Junction junction : getJunctions()) {
			array.put(junction.report());
		}
		obj.put("junctions", array);
		JSONArray array1 = new JSONArray();
		for (Road road : getRoads()) {
			array1.put(road.report());
		}
		obj.put("roads", array1);
		
		JSONArray array2 = new JSONArray();
		
		for (Vehicle vehicle : getVehicles()) {
			array2.put(vehicle.report());
		}
		obj.put("vehicles", array2);
		return obj;
	}
	
	private boolean itinerary_check(List<Junction> itinerary) {
		boolean status=true;
		for(int i=0;i<itinerary.size()-1;i++) {
			if(!mapRoad.containsKey(itinerary.get(i).roadTo(itinerary.get(i+1)).getId())){
				return status;
			}
			
		}
		
		return status;
		
	}


}
