package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject{
	
	 private List<Road> inRoads;
     private List<List<Vehicle>> queues; // the i-th queue is of the i-th road in _inRoads
     private Map<Road, List<Vehicle>> queueByRoad; // this is just for fast searching of queues
     private Map<Junction, Road> outRoadByJunction; // to tell how to go to a junction
     private int greenLightIndex,  lastSwitchingTime;   // the index of the road (in _inRoads) that has geen light (-1 all red)
     private LightSwitchingStrategy lss;
     private DequeuingStrategy dqs;
     private int x,y  ;
	
	
	
     Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) throws Exception {
    	 super(id);
    	 if(lsStrategy != null && dqStrategy != null) {
    		this.lss = lsStrategy;
    		this.dqs = dqStrategy;
    	 }
    	 else {
    		 throw new Exception("LightSwitchingStrategy or DequeuingStrategy is null ");
			
		}
    	 
    	 if(xCoor >=0 && yCoor >=0) {
    		 this.x = xCoor;
    		 this.y = yCoor;
    	 }
    	 else {
    		 throw new Exception("xCoor or yCoor is negative");
    	 }
    	 this.queues= new ArrayList<>();
    	 this.queueByRoad=new HashMap<>();
    	 this.outRoadByJunction= new HashMap<>();
    	 inRoads= new ArrayList<>() ;
    
    	 }
     
     public List<Road> getInRoads() {
 		return inRoads;
 	}
     
    public int getGreenLightIndex() {
		return greenLightIndex;
	}
    
    public List<List<Vehicle>> getQueues() {
		return queues;
	}
     
     void addIncommingRoad(Road r) throws Exception {
    	 if(r.getDest().equals(this)) {
    		 inRoads.add(r);
    		 LinkedList queue = new LinkedList<Vehicle>();
    		 queues.add(queue);
    		 queueByRoad.put(r, queue);
    		 
    	 }
    	 else {
    		 throw new Exception("destination junction is not  equal to the current junction");
    	 }
     }
     
     void addOutGotingRoad(Road r) throws Exception {
    	 if(r.getSrc().equals(this)) {
    		 outRoadByJunction.put(r.getDest(),r);
    		 
    		 
    	 }
    	 else {
    		 throw new Exception("Other road go junction or that road is not outgoing road");
    	 }
    	 
     }
     
     void enter(Vehicle v) {
    	int index_road = inRoads.indexOf(v.getRoad());
    	List<Vehicle> support_list = queues.get(index_road);
    	support_list.add(v);
    	queues.add(index_road, support_list);
    	queueByRoad.put(v.getRoad(), support_list);
    	
    	
     }
     
     Road roadTo(Junction j) {
		return outRoadByJunction.get(j);
    	 
     }
     
    



	@Override
	void advance(int time) throws Exception  {
		if(greenLightIndex != -1) {
			List<Vehicle> supprotList = queues.get(greenLightIndex);
			supprotList=dqs.dequeue(supprotList);
			for(Vehicle v : supprotList) {
				v.moveToNextRoad();
				supprotList.remove(v);
				
			}
			int status_light = lss.chooseNextGreen(inRoads, queues, greenLightIndex, lastSwitchingTime, time);
			 if (status_light != greenLightIndex) {
				 greenLightIndex=status_light;
				 lastSwitchingTime = time;
			 }
			
			
		}
		
	}



	@Override
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		obj.put("id", getId());
		obj.put("green", getGreenLightIndex());
		JSONArray array = new JSONArray();
		for (int i=0;i<getQueues().size();i++) {
			JSONObject road = new JSONObject();
			road.put("road", getInRoads().get(i));
			road.put("vehicles", getQueues().get(i));
			array.put(road);
		}
		obj.put("queues", array);
		
		
		return obj;
	}

	
}
